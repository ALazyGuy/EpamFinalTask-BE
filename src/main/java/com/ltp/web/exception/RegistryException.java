package com.ltp.web.exception;

public class RegistryException extends Exception{

    public RegistryException() {
        super();
    }

    public RegistryException(String message) {
        super(message);
    }

    public RegistryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistryException(Throwable cause) {
        super(cause);
    }
}
