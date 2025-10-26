package dev.bocchi_megane.mcprotocol.lib.exception;

public class ResponseAbnormalException extends RuntimeException {

    public ResponseAbnormalException(String message) {
        super(message);
    }
}