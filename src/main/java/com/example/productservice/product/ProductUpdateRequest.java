package com.example.productservice.product;

import jakarta.validation.constraints.NotBlank;

public record ProductUpdateRequest(
        @NotBlank String name,
        @NotBlank String description,
        @NotBlank String imageUrl
) {
}
