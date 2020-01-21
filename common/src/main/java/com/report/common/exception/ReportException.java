package com.report.common.exception;

public class ReportException extends RuntimeException {
    public ReportException(String s) {
        super(s);
    }

    public ReportException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
