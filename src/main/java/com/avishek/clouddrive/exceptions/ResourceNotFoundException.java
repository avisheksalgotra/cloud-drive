package com.avishek.clouddrive.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format(
                "%s with %s %d not found",
                resourceName,
                field,
                fieldId
        ));
    }
}