package com.report.adapter.swapi.client.service;

import com.report.adapter.swapi.client.vo.EndpointName;
import com.report.application.meta.SwapiEntity;
import org.modelmapper.ModelMapper;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

abstract class SwapiCrawler<T extends SwapiEntity> {
    private final ResultCrawler resultCrawler;
    private final ModelMapper modelMapper;

    private final Class<T> genericClass;
    private final EndpointName endpointName;

    SwapiCrawler(ResultCrawler resultCrawler, ModelMapper modelMapper) {
        this.resultCrawler = resultCrawler;
        this.modelMapper = modelMapper;
        this.genericClass = recogniseGenericClass();
        this.endpointName = getEndpointName();
    }

    private Class<T> recogniseGenericClass() {
        return (Class<T>)
                ((ParameterizedType) this.getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0];
    }

    protected abstract EndpointName getEndpointName();

    protected List<T> crawlEndpoint() {
        return resultCrawler.crawl(endpointName)
                .stream()
                .map(result -> modelMapper.map(result, genericClass))
                .collect(Collectors.toList());
    }
}
