package com.kognitiv_.assignment.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalServerException extends RuntimeException {
    public InternalServerException(String exception) {
        super(exception);
    }
}
