package com.report.application.service;

import com.report.common.vo.PositiveInteger;
import lombok.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceFactory {
    ExecutorService buildWithSize(@NonNull PositiveInteger poolSize) {
        return Executors.newFixedThreadPool(poolSize.getRaw());
    }
}
