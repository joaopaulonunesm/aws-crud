package com.project.aws.crud.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.aws.crud.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * DTO responsável por receber e retornar dados do produto
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    private String id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String sku;
    private String description;
    @NotNull
    @Positive
    private BigDecimal price;

    public Product toModel(){
        return Product.builder()
                .id(this.id)
                .name(this.name)
                .sku(this.sku)
                .description(this.description)
                .price(this.price)
                .build();
    }
}
