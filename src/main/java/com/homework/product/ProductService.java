package com.homework.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to manage the products.
 */
@Service
public class ProductService {
    @Autowired
    private ProductsRepository productsRepository;

    public ProductService() {
    }

    /**
     * Getting all the products.
     */
    public List<Product> getProducts() {
        return productsRepository.findAll();
    }

    /**
     * Creating a new product.
     *
     * @param product new product to be created
     */
    public ResponseEntity<String> createProduct(Product product) {
        if (product == null) {
            return ResponseEntity.badRequest().body("Product is not provided.");
        }
        productsRepository.save(product);
        if (productsRepository.findById(product.getProductId()).isPresent()) {
            return ResponseEntity.ok().body("The product with id " + product.getProductId() + " is successfully created.");
        } else {
            return ResponseEntity.internalServerError().body("Failed to create a product");
        }
    }

    /**
     * Modifying a product.
     *
     * @param product to be modified
     */
    public ResponseEntity<String> modifyProduct(Product product) {
        if (product == null) {
            return ResponseEntity.badRequest().body("Product is not provided.");
        }

        ResponseEntity<String> response = deleteProduct(product.getProductId());
        if (response.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.internalServerError().body("Failed to modify the product.");
        } else {
            response = createProduct(product);
            if (response.getStatusCode() != HttpStatus.OK) {
                return ResponseEntity.internalServerError().body("Failed to modify the product.");
            }
        }
        return ResponseEntity.ok().body("The product is modified.");
    }

    /**
     * Deleting a product.
     *
     * @param id of the product to be deleted
     */
    public ResponseEntity<String> deleteProduct(String id) {
        if (productsRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            productsRepository.deleteById(id);
        }
        if (productsRepository.findById(id).isEmpty()) {
            return ResponseEntity.ok().body("The product with id " + id + " is deleted.");
        } else {
            return ResponseEntity.internalServerError().body("Failed to delete the product with id: " + id);
        }
    }

    /**
     * Purchasing a product.
     *
     * @param id of the product to be purchased
     */
    public ResponseEntity<String> purchase(String id) {
        if (!productsRepository.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            Product product = productsRepository.findById(id).get();
            if (!product.isPurchased()) {
                product.setPurchased(true);
                productsRepository.save(product);
                return ResponseEntity.ok().body("The product is successfully purchased.");
            } else {
                return ResponseEntity.badRequest().body("Product is already purchased.");
            }
        }
    }

    public Product getById(String id) {
        return productsRepository.findById(id).get();
    }
}
