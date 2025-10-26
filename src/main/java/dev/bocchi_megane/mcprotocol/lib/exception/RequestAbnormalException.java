package dev.bocchi_megane.mcprotocol.lib.exception;

public class RequestAbnormalException extends RuntimeException {

    public RequestAbnormalException(String message) {
        super(message);
    }
}