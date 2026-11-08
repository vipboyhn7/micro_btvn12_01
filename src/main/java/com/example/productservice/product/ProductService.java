package com.example.productservice.product;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Cacheable(cacheNames = "products", key = "#productId")
    public Product getById(Long productId) {
        return repository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product not found: " + productId));
    }

    @CacheEvict(cacheNames = "products", key = "#productId")
    public Product update(Long productId, ProductUpdateRequest request) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product not found: " + productId));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setImageUrl(request.imageUrl());
        return repository.save(product);
    }
}
