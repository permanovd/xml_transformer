package com.permanovd.infrastructure;

public class OutputFileValidationException extends Exception {
    private Throwable wrapped;

    public OutputFileValidationException(Throwable throwable) {
        super(throwable);
        wrapped = throwable;
    }

    @Override
    public String getMessage() {
        return wrapped.getMessage();
    }
}
