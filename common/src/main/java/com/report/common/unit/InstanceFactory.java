package com.report.common.unit;

import com.report.common.exception.ReportException;

public abstract class InstanceFactory<T> {
    protected abstract Class<T> getClassOfTheInstanceBeingCreated();

    protected T newInstance() {
        try {
            return getClassOfTheInstanceBeingCreated()
                    .newInstance();
        } catch (ReflectiveOperationException exception) {
            return handleException(exception);
        }
    }

    protected T newInstance(Class[] classes, Object... arguments) {
        try {
            return getClassOfTheInstanceBeingCreated()
                    .getDeclaredConstructor(classes)
                    .newInstance(arguments);
        } catch (ReflectiveOperationException exception) {
            return handleException(exception);
        }
    }

    private T handleException(ReflectiveOperationException exception) {
        Throwable cause = exception.getCause();

        if (cause instanceof NullPointerException) {
            throw (NullPointerException) cause;
        } else if (cause instanceof ReportException) {
            throw (ReportException) cause;
        }

        throw new RuntimeException(exception);
    }
}
