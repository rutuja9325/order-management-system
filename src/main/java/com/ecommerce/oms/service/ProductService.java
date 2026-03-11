package com.ecommerce.oms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.oms.dto.CreateProductRequestDTO;
import com.ecommerce.oms.dto.ProductResponseDTO;
import com.ecommerce.oms.dto.UpdateProductRequestDTO;
import com.ecommerce.oms.entity.Product;
import com.ecommerce.oms.exception.ProductInactiveException;
import com.ecommerce.oms.exception.ProductNotFoundException;
import com.ecommerce.oms.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /* =========================
       GET ALL PRODUCTS
       ========================= */
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    /* =========================
       GET PRODUCT BY ID
       
       Deactivated products disappear from list
	UI / Postman shows only valid products
	Order creation logic stays consistent
       ========================= */
    public ProductResponseDTO getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with ID: " + id
                        )
                );

        if (Boolean.FALSE.equals(product.getActive())) {
            throw new ProductInactiveException(
                    "Product '" + product.getName() + "' is inactive"
            );
        }

        return mapToResponseDTO(product);
    }

    /* =========================
       CREATE PRODUCT
       ========================= */
    @Transactional
    public ProductResponseDTO createProduct(CreateProductRequestDTO request) {

        // 2️⃣ Create Product entity
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setInventoryCount(request.getInventoryCount());
        product.setBrand(request.getBrand());
        

        // default values
        product.setActive(true);
        
     // 1️ First save (ID generated)
        Product savedProduct = productRepository.save(product);
        
     // 2️ Generate 5-digit item number
        String itemNumber = String.format("%05d", savedProduct.getId());
        savedProduct.setItemNumber(itemNumber);

        // 3️ Save again
        Product finalProduct = productRepository.save(savedProduct);
        
        
        return mapToResponseDTO(finalProduct);
    }


    /* =========================
       DEACTIVATE PRODUCT
       ========================= */
    @Transactional
    public void deactivateProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with ID: " + id
                        )
                );

        product.setActive(false);
        productRepository.save(product);
    }

   
    
    
    private ProductResponseDTO mapToResponseDTO(Product product) {

        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setItemNumber(product.getItemNumber());
        dto.setName(product.getName());
        dto.setBrand(product.getBrand());
        dto.setPrice(product.getPrice());
        dto.setInventoryCount(product.getInventoryCount());
        dto.setActive(product.getActive());

        return dto;
    }
    
    
 // ✅ INTERNAL USE ONLY (OrderService, etc.)
    public Product getActiveProductEntityById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with ID: " + id
                        )
                );

        if (Boolean.FALSE.equals(product.getActive())) {
            throw new ProductInactiveException(
                    "Product '" + product.getName() + "' is inactive"
            );
        }

        return product;
    }
    
    public ProductResponseDTO updateProduct(Long id, UpdateProductRequestDTO request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        if (request.getDescription() != null)
            product.setDescription(request.getDescription());

        if (request.getPrice() != null)
            product.setPrice(request.getPrice());

        if (request.getBrand() != null)
            product.setBrand(request.getBrand());

        if (request.getInventoryCount() != null)
            product.setInventoryCount(request.getInventoryCount());

        if (request.getSku() != null)
            product.setSku(request.getSku());

        return mapToResponseDTO(productRepository.save(product));
    }
}
