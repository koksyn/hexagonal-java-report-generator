package com.report.adapter.swapi.client.service;

import com.report.common.exception.ReportException;
import com.report.common.vo.PositiveInteger;
import lombok.NonNull;

import java.time.*;

public class DailyRateCounter {
    private final PositiveInteger requestsPerDayLimit;

    private Clock clock;
    private LocalDate ratedDay;
    private int rate;

    public DailyRateCounter(
            @NonNull final PositiveInteger requestsPerDayLimit,
            @NonNull Clock clock) {
        this.requestsPerDayLimit = requestsPerDayLimit;
        this.clock = clock;
        ratedDay = LocalDate.MIN;
        rate = 0;
    }

    synchronized void increment() {
        resetWhenDayHasPassed();
        requireLimitNotReached();
        rate++;
    }

    private void resetWhenDayHasPassed() {
        LocalDate today = getCurrentLocalDate();

        if(today.isAfter(ratedDay)) {
            rate = 0;
            ratedDay = today;
        }
    }

    private void requireLimitNotReached() {
        if(rate >= requestsPerDayLimit.getRaw()) {
            throw new ReportException("Daily rate limit of API requests reached.");
        }
    }

    private LocalDate getCurrentLocalDate() {
        Instant instant = clock.instant();
        ZoneId zoneId = ZoneOffset.systemDefault();

        return instant.atZone(zoneId)
                .toLocalDate();
    }
}