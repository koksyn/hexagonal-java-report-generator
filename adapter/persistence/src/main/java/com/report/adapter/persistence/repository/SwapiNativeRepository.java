package com.report.adapter.persistence.repository;

import com.report.application.dto.FilmCharacterRecord;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SwapiNativeRepository {
    private final EntityManager entityManager;

    private static final List<String> swapiTablesToTruncate = Arrays.asList(
            "planet_film_id",
            "planet_character_id",
            "film_character_id",
            "character"
    );

    private static final List<String> swapiTablesToDelete = Arrays.asList("film", "planet");

    public SwapiNativeRepository(EntityManagerFactory entityManagerFactory) {
        entityManager = entityManagerFactory.createEntityManager();
    }

    List<FilmCharacterRecord> findAllByPlanetNameAndCharacterNameThatContainsPhrase(String planetName, String characterPhrase) {
        final String sql =
                "SELECT f.id   AS filmId, " +
                "       f.name AS filmName, " +
                "       c.id   AS characterId, " +
                "       c.name AS characterName, " +
                "       p.id   AS planetId, " +
                "       p.name AS planetName " +
                "FROM planet p " +
                "INNER JOIN planet_character_id pc ON p.id = pc.planet_id " +
                "INNER JOIN character c ON pc.character_id = c.id " +
                "INNER JOIN planet_film_id pf ON p.id = pf.planet_id " +
                "INNER JOIN film f ON pf.film_id = f.id " +
                "INNER JOIN film_character_id fc ON (f.id = fc.film_id AND c.id = fc.character_id) " +
                "WHERE p.name = :planetName AND POSITION(:characterPhrase, c.name) > 0";

        final Query query =  entityManager.createNativeQuery(sql);

        query.unwrap(SQLQuery.class)
                .addScalar("filmId", LongType.INSTANCE)
                .addScalar("filmName", StringType.INSTANCE)
                .addScalar("characterId", LongType.INSTANCE)
                .addScalar("characterName", StringType.INSTANCE)
                .addScalar("planetId", LongType.INSTANCE)
                .addScalar("planetName", StringType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(FilmCharacterRecord.class));

        return query.setParameter("planetName", planetName)
                .setParameter("characterPhrase", characterPhrase)
                .getResultList();
    }

    void cleanSwapiTables() {
        entityManager.getTransaction().begin();

        List<String> sqls = swapiTablesToTruncate.stream()
                .map(SwapiNativeRepository::generateTruncateSql)
                .collect(Collectors.toList());

        List<String> deleteSqls = swapiTablesToDelete.stream()
                .map(SwapiNativeRepository::generateDeleteSql)
                .collect(Collectors.toList());

        sqls.addAll(deleteSqls);

        sqls.stream()
                .map(entityManager::createNativeQuery)
                .forEach(Query::executeUpdate);

        entityManager.getTransaction().commit();
    }

    private static String generateTruncateSql(String tableName) {
        return "TRUNCATE TABLE " + tableName;
    }

    private static String generateDeleteSql(String tableName) {
        return "DELETE FROM " + tableName;
    }
}