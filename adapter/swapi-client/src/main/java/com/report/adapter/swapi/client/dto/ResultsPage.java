package com.report.adapter.swapi.client.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultsPage {
    private Long count;
    private String next;
    private List<JsonNode> results;
}
