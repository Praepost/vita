package org.service.web.exception.dto;

public class SuccessfulResponse implements Response {
    public ResponseStatus getStatus() {
        return ResponseStatus.SUCCESS;
    }
}