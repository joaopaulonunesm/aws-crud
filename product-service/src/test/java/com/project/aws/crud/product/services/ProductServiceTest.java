package com.project.aws.crud.product.services;

import com.project.aws.crud.product.dto.ProductDTO;
import com.project.aws.crud.product.exception.BusinessException;
import com.project.aws.crud.product.model.Product;
import com.project.aws.crud.product.repository.ProductRepository;
import com.project.aws.crud.product.service.ProductService;
import com.project.aws.crud.product.utils.ErrorMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    public static final String SKU = "SKU1";
    public static final String NAME = "Product Name";

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void createProduct_ShouldCreate(){

        Product product = Product.builder().sku(SKU).name(NAME).build();

        when(productRepository.findBySku(product.getSku())).thenReturn(Optional.empty());
        when(productRepository.save(product)).thenReturn(product);

        ProductDTO productDTO = productService.create(product.toDTO());

        verify(productRepository).findBySku(any());
        verify(productRepository).save(any(Product.class));
        verifyNoMoreInteractions(productRepository);

        assertEquals(product.getSku(), productDTO.getSku());
        assertEquals(product.getName(), productDTO.getName());
    }

    @Test
    public void createProduct_ShouldNotCreate(){

        Product product = Product.builder().sku(SKU).name(NAME).build();

        when(productRepository.findBySku(product.getSku())).thenReturn(Optional.of(product));

        BusinessException exception = assertThrows(BusinessException.class, () -> productService.create(product.toDTO()));

        verify(productRepository).findBySku(any());
        verifyNoMoreInteractions(productRepository);

        assertEquals(ErrorMessages.PRODUCT_EXISTENT.getDescription(), exception.getCodeError());
    }

    @Test
    public void findAll_ShouldReturnTwo(){

        Product product = Product.builder().sku(SKU).name(NAME).build();
        Product product2 = Product.builder().sku(SKU + "2").name(NAME + "2").build();

        List<Product> products = Arrays.asList(product, product2);
        when(productRepository.findAll()).thenReturn(products);

        List<ProductDTO> productDTO = productService.findAll();

        verify(productRepository).findAll();
        verifyNoMoreInteractions(productRepository);

        assertEquals(products.size(), productDTO.size());
    }

    @Test
    public void findAll_ShouldReturnEmpty(){
        List<Product> products = Collections.emptyList();
        when(productRepository.findAll()).thenReturn(products);

        List<ProductDTO> productDTO = productService.findAll();

        verify(productRepository).findAll();
        verifyNoMoreInteractions(productRepository);

        assertEquals(products.size(), productDTO.size());
    }

    @Test
    public void update_ShouldUpdateProduct(){

        final String beforeName = "Before Product";
        final String afterName = "After Product";

        Product product = Product.builder().sku(SKU).name(beforeName).build();

        when(productRepository.findBySku(anyString())).thenReturn(Optional.empty());
        when(productRepository.findById(anyString())).thenReturn(Optional.of(product));

        ProductDTO productDTOMock = ProductDTO.builder().sku(SKU).name(afterName).build();
        when(productRepository.save(product)).thenReturn(productDTOMock.toModel());

        ProductDTO productDTO = productService.update(anyString(), productDTOMock);

        verify(productRepository).findBySku(SKU);
        verify(productRepository).findById(anyString());
        verify(productRepository).save(product);
        verifyNoMoreInteractions(productRepository);

        assertEquals(afterName, productDTO.getName());
    }

    @Test
    public void update_ShouldNotUpdateWhenNameExists(){

        Product product = Product.builder().sku(SKU).name(NAME).build();

        when(productRepository.findBySku(anyString())).thenReturn(Optional.of(product));

        BusinessException exception = assertThrows(BusinessException.class, () -> productService.update(anyString(), product.toDTO()));

        verify(productRepository).findBySku(anyString());
        verifyNoMoreInteractions(productRepository);

        assertEquals(ErrorMessages.PRODUCT_EXISTENT.getDescription(), exception.getCodeError());
    }

    @Test
    public void update_ShouldReturnExceptionWhenNotFoundProduct(){

        when(productRepository.findBySku(anyString())).thenReturn(Optional.empty());
        when(productRepository.findById(anyString())).thenReturn(Optional.empty());

        Product product = Product.builder().sku(SKU).name(NAME).build();
        BusinessException exception = assertThrows(BusinessException.class, () -> productService.update(anyString(), product.toDTO()));

        verify(productRepository).findById(anyString());
        verifyNoMoreInteractions(productRepository);

        assertEquals(ErrorMessages.PRODUCT_NOT_FOUND_BY_ID.getDescription(), exception.getCodeError());
    }

    @Test
    public void delete_ShouldDeleteProduct(){

        Product product = Product.builder().sku(SKU).name(NAME).build();

        when(productRepository.findById(anyString())).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        productService.delete(anyString());

        verify(productRepository).findById(anyString());
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void delete_ShouldReturnExceptionWhenNotFoundProduct(){

        when(productRepository.findById(anyString())).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> productService.delete(anyString()));

        verify(productRepository).findById(anyString());
        verifyNoMoreInteractions(productRepository);

        assertEquals(ErrorMessages.PRODUCT_NOT_FOUND_BY_ID.getDescription(), exception.getCodeError());
    }
}
