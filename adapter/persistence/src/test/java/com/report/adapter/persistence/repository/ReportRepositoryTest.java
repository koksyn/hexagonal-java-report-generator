package com.report.adapter.persistence.repository;

import com.report.application.domain.snapshot.ReportSnapshot;
import com.report.application.domain.type.ReportStatus;
import com.report.application.domain.vo.CharacterPhrase;
import com.report.application.domain.vo.FilmCharacterList;
import com.report.application.domain.vo.PlanetName;
import com.report.application.domain.vo.ReportId;
import com.report.application.entity.Report;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportRepositoryTest {
    private static final UUID REPORT_ID = UUID.randomUUID();
    private static ReportId reportId;

    @Mock
    private Report reportEntity;
    @Mock
    private com.report.application.domain.Report report;
    @Mock
    private ReportSnapshot reportSnapshot;
    @Mock
    private ReportJpaRepository jpaRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ReportRepository repository;

    @Captor
    private ArgumentCaptor<List<Report>> captor;

    @BeforeAll
    static void init() {
        reportId = new ReportId(REPORT_ID);
    }

    @Test
    @DisplayName("Getting one, when ReportId is null")
    void shouldNotAcceptNull() {
        // When & Then
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> repository.get(null)
        );

        assertTrue(exception
                .getMessage()
                .contains("is marked non-null but is null")
        );
    }

    @Test
    @DisplayName("Getting one, when it does exist")
    void shouldFindOneThroughJpaRepository() {
        // Given
        when(jpaRepository.findById(REPORT_ID))
                .thenReturn(Optional.of(reportEntity));

        // When
        Optional<Report> result = repository.get(reportId);

        // Then
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Getting one, when it does not exist")
    void shouldReturnEmptyThroughJpaRepository() {
        // Given
        when(jpaRepository.findById(REPORT_ID))
                .thenReturn(Optional.empty());

        // When
        Optional<Report> result = repository.get(reportId);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Getting complete one, when ReportId is null")
    void shouldNotAcceptArgument() {
        // When & Then
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> repository.getComplete(null)
        );

        assertTrue(exception
                .getMessage()
                .contains("is marked non-null but is null")
        );
    }

    @Test
    @DisplayName("Getting complete one, when it does exist")
    void shouldFindCompleteOneThroughJpaRepository() {
        // Given
        when(jpaRepository.findByReportIdAndStatusEquals(REPORT_ID, ReportStatus.COMPLETE))
                .thenReturn(Optional.of(reportEntity));

        // When
        Optional<Report> result = repository.getComplete(reportId);

        // Then
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Getting complete one, when it does not exist")
    void shouldReturnNotPresentThroughJpaRepository() {
        // Given
        when(jpaRepository.findByReportIdAndStatusEquals(REPORT_ID, ReportStatus.COMPLETE))
                .thenReturn(Optional.empty());

        // When
        Optional<Report> result = repository.getComplete(reportId);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Getting all complete")
    void shouldFindCompleteReportsThroughJpaRepository() {
        // When
        repository.getAllComplete();

        // Then
        verify(jpaRepository).findAllByStatusEquals(ReportStatus.COMPLETE);
    }

    @Test
    @DisplayName("Getting all incomplete, when they does not exists.")
    void shouldReturnEmptyList() {
        // Given
        when(jpaRepository.findAllByStatusEquals(ReportStatus.INCOMPLETE))
                .thenReturn(Collections.emptyList());

        // When
        List<com.report.application.domain.Report> results = repository.getAllIncomplete();

        // Then
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Getting all incomplete, when some of them does exists.")
    void shouldReturnListOfReports() {
        // Given
        when(jpaRepository.findAllByStatusEquals(ReportStatus.INCOMPLETE))
                .thenReturn(Collections.singletonList(reportEntity));

        ReportSnapshot reportSnapshot = new ReportSnapshot(
                new ReportId(REPORT_ID),
                new CharacterPhrase("abc"),
                new PlanetName("def"),
                new FilmCharacterList(),
                new com.report.application.domain.vo.ReportStatus(ReportStatus.COMPLETE)
        );

        when(modelMapper.map(reportEntity, ReportSnapshot.class))
                .thenReturn(reportSnapshot);

        // When
        List<com.report.application.domain.Report> results = repository.getAllIncomplete();

        // Then
        assertNotNull(results);
        assertEquals(1, results.size());

        results.forEach(result -> assertEquals(REPORT_ID, result
                .toSnapshot()
                .getReportId()
                .getRaw()
        ));
    }

    @Test
    @DisplayName("Checking, when there are not incomplete ones")
    void shouldReturnFalse() {
        // Given
        when(jpaRepository.countByStatusEquals(any(ReportStatus.class)))
                .thenReturn(0L);

        // When
        boolean result = repository.anyIncomplete();

        // Then
        verify(jpaRepository).countByStatusEquals(ReportStatus.INCOMPLETE);
        assertFalse(result);
    }

    @Test
    @DisplayName("Checking, when there are some incomplete ones")
    void shouldReturnTrue() {
        // Given
        when(jpaRepository.countByStatusEquals(any(ReportStatus.class)))
                .thenReturn(1337L);

        // When
        boolean result = repository.anyIncomplete();

        // Then
        verify(jpaRepository).countByStatusEquals(ReportStatus.INCOMPLETE);
        assertTrue(result);
    }

    @Test
    @DisplayName("Deleting complete one, when ReportId is null")
    void shouldNotAcceptNullReportId() {
        // When & Then
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> repository.deleteComplete(null)
        );

        assertTrue(exception
                .getMessage()
                .contains("is marked non-null but is null")
        );
    }

    @Test
    @DisplayName("Deleting complete one, when ReportId is not null")
    void shouldDeleteOneThroughJpaRepository() {
        // When
        repository.deleteComplete(reportId);

        // Then
        verify(jpaRepository).deleteByReportIdAndStatusEquals(REPORT_ID, ReportStatus.COMPLETE);
    }

    @Test
    @DisplayName("Deleting all complete")
    void shouldDeleteCompleteReportsThroughJpaRepository() {
        // When
        repository.deleteAllComplete();

        // Then
        verify(jpaRepository).deleteAllByStatusEquals(ReportStatus.COMPLETE);
    }

    @Test
    @DisplayName("Saving null")
    void shouldNotAcceptNullReport() {
        // When & Then
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> repository.save(null)
        );

        assertTrue(exception
                .getMessage()
                .contains("is marked non-null but is null")
        );
    }

    @Test
    @DisplayName("Saving report")
    void shouldSaveConvertedEntityThroughJpaRepository() {
        // Given
        when(report.toSnapshot())
                .thenReturn(reportSnapshot);

        when(modelMapper.map(reportSnapshot, Report.class))
                .thenReturn(reportEntity);

        // When
        repository.save(report);

        // Then
        verify(jpaRepository).save(reportEntity);
    }

    @Test
    @DisplayName("Saving all, when null was provided")
    void shouldNotAcceptNullReportList() {
        // When & Then
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> repository.saveAll(null)
        );

        assertTrue(exception
                .getMessage()
                .contains("is marked non-null but is null")
        );
    }

    @Test
    @DisplayName("Saving all, when list of Reports was provided")
    void shouldConvertToListOfEntitiesAndSaveThemAll() {
        // Given
        List<com.report.application.domain.Report> reports = Collections.singletonList(report);

        when(report.toSnapshot())
                .thenReturn(reportSnapshot);

        when(modelMapper.map(reportSnapshot, Report.class))
                .thenReturn(reportEntity);

        // When
        repository.saveAll(reports);

        // Then
        verify(jpaRepository).saveAll(captor.capture());
        List<Report> reportEntities = captor.getValue();

        assertNotNull(reportEntities);
        assertTrue(reportEntities.contains(reportEntity));
    }
}
