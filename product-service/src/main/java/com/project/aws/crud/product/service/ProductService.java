package com.project.aws.crud.product.service;

import com.project.aws.crud.product.dto.ProductDTO;
import com.project.aws.crud.product.exception.BusinessException;
import com.project.aws.crud.product.model.Product;
import com.project.aws.crud.product.repository.ProductRepository;
import com.project.aws.crud.product.utils.ErrorMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Responsável por fornecer os serviços relacionados a um produto
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Realiza a criação do Produto
     *
     * @param product dados do produto a ser criado
     * @return {@code ProductDTO} dados do produto criado
     */
    public ProductDTO create(ProductDTO product){
        log.info("M=create, message=Creating product, productName={}", product.getName());
        validate(null, product);
        return productRepository.save(product.toModel()).toDTO();
    }

    /**
     * Lista todos os produtos cadastrados
     *
     * @return {@code List<ProductDTO>} lista de produtos
     */
    public List<ProductDTO> findAll(){
        log.info("M=findAll, message=Finding all product");
        List<ProductDTO> products = new ArrayList<>();
        productRepository.findAll().forEach(product -> products.add(product.toDTO()));
        return products;
    }

    /**
     * Altera os dados de um produto por ID
     *
     * @param id id do produto a ser alterado
     * @param product dados alterados do produto
     * @return {@code ProductDTO} dados do produto alterado
     */
    public ProductDTO update(final String id, final ProductDTO product){
        log.info("M=update, message=Updating product, id={}, product={}", id, product);
        validate(id, product);

        Product existProduct = getById(id);

        existProduct.setSku(product.getSku());
        existProduct.setName(product.getName());
        existProduct.setDescription(product.getDescription());
        existProduct.setPrice(product.getPrice());
        return productRepository.save(existProduct).toDTO();
    }

    /**
     * Deleta produto por ID
     *
     * @param id ID do produto a ser deletado
     */
    public void delete(final String id){
        log.info("M=delete, message=Deleting product by ID, id={}", id);
        Product product = getById(id);
        productRepository.delete(product);
    }

    /**
     * Realiza validações do produto
     *
     * @param id
     * @param product produto a ser validado
     */
    private void validate(final String id, final ProductDTO product) {

        final Optional<Product> productByName = productRepository.findBySku(product.getSku());

        if (productByName.isPresent() && !productByName.get().getId().equals(id)) {
            throw new BusinessException(ErrorMessages.PRODUCT_EXISTENT, product.getSku());
        }
    }

    /**
     * Busca produto por ID no banco de dados
     *
     * @param id id a ser encontrado
     * @return {@code Product} dados do produto encontrado
     */
    private Product getById(final String id){
        log.info("M=getById, message=Finding product by ID, id={}", id);
        return productRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorMessages.PRODUCT_NOT_FOUND_BY_ID, id));
    }
}
