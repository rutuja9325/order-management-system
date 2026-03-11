package com.ecommerce.oms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.ecommerce.oms.dto.CreateProductRequestDTO;
import com.ecommerce.oms.dto.ProductResponseDTO;
import com.ecommerce.oms.dto.UpdateProductRequestDTO;
import com.ecommerce.oms.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @RequestBody CreateProductRequestDTO request) {

        ProductResponseDTO response = productService.createProduct(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody UpdateProductRequestDTO request) {

        ProductResponseDTO updated =
                productService.updateProduct(id, request);

        return ResponseEntity.ok(updated);
    }
    
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateProduct(@PathVariable Long id) {
        productService.deactivateProduct(id);
        return ResponseEntity.ok("Product deactivated successfully");
    }
}