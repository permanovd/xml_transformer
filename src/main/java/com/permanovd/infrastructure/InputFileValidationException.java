package com.permanovd.infrastructure;

public class InputFileValidationException extends Exception {
    private Throwable wrapped;

    public InputFileValidationException(Throwable throwable) {
        super(throwable);
        wrapped = throwable;
    }

    @Override
    public String getMessage() {
        return wrapped.getMessage();
    }
}
