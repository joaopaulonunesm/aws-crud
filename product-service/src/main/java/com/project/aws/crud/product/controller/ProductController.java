package com.project.aws.crud.product.controller;

import com.project.aws.crud.product.dto.ProductDTO;
import com.project.aws.crud.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Responsável por prover os endpoints de produto
 */
@RestController
@RequestMapping("/products")
@CrossOrigin
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Endpoint responsável por realizar a criação de um produto
     *
     * @param productDTO dados do produto a ser criado
     * @return {@code ResponseEntity<ProductDTO>} dados do produto criado
     */
    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody @Valid final ProductDTO productDTO){
        return ResponseEntity.ok(productService.create(productDTO));
    }

    /**
     * Endpoint responsável por buscar dos os produtos cadastrados
     *
     * @return {@code ResponseEntity<List<ProductDTO>>} dados de todos os produtos cadastrados
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }

    /**
     * Enpoint responsável por realizar a alteração dos dados de um produto por ID
     *
     * @param id do produto a ser alterado
     * @param product dados alterados do produto
     * @return {@code ResponseEntity<ProductDTO>} novos dados do produto
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable final String id, @RequestBody @Valid final ProductDTO product){
        return ResponseEntity.ok(productService.update(id, product));
    }

    /**
     * Endpoint responsável por fazer a remoção de um produto
     *
     * @param id do produto a ser removido
     * @return {@code Void} retorna apenas pelo HttpStatus
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final String id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
