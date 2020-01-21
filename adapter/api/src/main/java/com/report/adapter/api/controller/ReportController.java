package com.report.adapter.api.controller;

import com.report.adapter.api.dto.QueryCriteria;
import com.report.application.dto.ReportDetails;
import com.report.application.port.driver.ReportCreator;
import com.report.application.port.driver.ReportProvider;
import com.report.application.port.driver.ReportTerminator;
import com.report.application.domain.vo.ReportId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportCreator reportCreator;
    private final ReportProvider reportProvider;
    private final ReportTerminator reportTerminator;

    @GetMapping("/{report_id}")
    public ReportDetails get(@PathVariable(name = "report_id") UUID reportId) {
        return reportProvider.get(
                new ReportId(reportId)
        );
    }

    @GetMapping
    public List<ReportDetails> getAll() {
        return reportProvider.getAll();
    }

    @PutMapping("/{report_id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void put(@PathVariable(name = "report_id") UUID reportId,
                    @RequestBody @Valid QueryCriteria queryCriteria) {
        reportCreator.createOrReplace(
                new ReportId(reportId),
                queryCriteria.toCharacterPhrase(),
                queryCriteria.toPlanetName()
        );
    }

    @DeleteMapping("/{report_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "report_id") UUID reportId) {
        reportTerminator.delete(
                new ReportId(reportId)
        );
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAll() {
        reportTerminator.deleteAll();
    }
}
