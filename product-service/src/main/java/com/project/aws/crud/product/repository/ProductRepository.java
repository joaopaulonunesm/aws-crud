package com.project.aws.crud.product.repository;

import com.project.aws.crud.product.model.Product;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Responsável por gerenciar os dados de um Produto no banco de dados
 */
@EnableScan
@Repository
public interface ProductRepository extends CrudRepository<Product, String> {

    /**
     * Responsável por buscar um Produto por sku
     *
     * @param sku SKU a ser encontrado o produto
     * @return {@code Product} dados do produto
     */
    Optional<Product> findBySku(final String sku);
}
