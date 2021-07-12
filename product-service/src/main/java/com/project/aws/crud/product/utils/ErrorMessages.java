package com.project.aws.crud.product.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Mensagens de erro do sistema
 */
@Getter
@AllArgsConstructor
public enum ErrorMessages {

    PRODUCT_NOT_FOUND_BY_ID("product.not-found-by-id"),
    PRODUCT_EXISTENT("product.existent");

    private String description;
}

