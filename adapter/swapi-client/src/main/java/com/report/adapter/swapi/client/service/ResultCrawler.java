package com.report.adapter.swapi.client.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.report.adapter.swapi.client.dto.ResultsPage;
import com.report.adapter.swapi.client.vo.EndpointName;
import com.report.adapter.swapi.client.vo.Result;
import com.report.common.exception.ReportException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class ResultCrawler {
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;
    private final DailyRateCounter rateCounter;
    private final String baseUrl;

    List<Result> crawl(EndpointName endpointName) {
        String url = endpointName.generatePathForBaseUrl(baseUrl);

        return crawlUrl(url)
                .stream()
                .map(Result::new)
                .collect(Collectors.toList());
    }

    private List<JsonNode> crawlUrl(String url) {
        log.info("Trying to crawl URL: " + url);

        rateCounter.increment();
        Call call = generateCallForUrl(url);

        return executeAndHandle(call);
    }

    private Call generateCallForUrl(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        return client.newCall(request);
    }

    private List<JsonNode> executeAndHandle(Call call) {
        try (Response response = call.execute()) {
            ResponseBody responseBody = response.body();

            return handlePage(responseBody.string());
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new ReportException("Cannot crawl URL correctly. ", exception);
        }
    }

    private List<JsonNode> handlePage(String pageJson) throws IOException {
        ResultsPage entity = objectMapper.readValue(pageJson, ResultsPage.class);

        return mergeWithNextPageResults(entity.getResults(), entity.getNext());
    }

    private List<JsonNode> mergeWithNextPageResults(List<JsonNode> currentResults, String nextPageUrl) {
        boolean nextPageDoesExist = !StringUtils.isBlank(nextPageUrl);

        if(nextPageDoesExist) {
            List<JsonNode> nextPageResults = crawlUrl(nextPageUrl);
            currentResults.addAll(nextPageResults);
        }

        return currentResults;
    }
}
