package org.service.web.task.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message){
        super(message);
    }
}
