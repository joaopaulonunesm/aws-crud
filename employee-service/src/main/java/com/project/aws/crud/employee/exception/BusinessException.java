package com.project.aws.crud.employee.exception;

import com.project.aws.crud.employee.utils.ErrorMessages;
import lombok.Getter;

/**
 * Exception relacionadas a Regra de Negócio
 */
@Getter
public class BusinessException extends RuntimeException {

    private String codeError;
    private Object[] params;

    public BusinessException(ErrorMessages message, Object... params) {
        super(message.getDescription());
        this.codeError = message.getDescription();
        this.params = params;
    }
}
