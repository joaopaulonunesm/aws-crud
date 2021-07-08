package com.project.aws.crud.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Mensagens de erro do sistema
 */
@Getter
@AllArgsConstructor
public enum ErrorMessages {

    EMPLOYEE_NOT_FOUND_BY_ID("employee.not-found-by-id"),
    EMPLOYEE_NOT_FOUND_BY_DOCUMENT("employee.not-found-by-document"),
    EMPLOYEE_EXISTENT("employee.existent"),
    EMPLOYEE_CANT_CHANGE_DOCUMENT("employee.cant-change-document");

    private String description;
}

