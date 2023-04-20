package com.homework.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Web resource to manage the products.
 */
@RestController
@RequestMapping(path = "api/v1/product")
@CrossOrigin
public class ProductWebResource {
    private ProductService productService;

    @Autowired
    ProductWebResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Cacheable("products")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable String id) {
        return productService.getById(id);
    }

    @PostMapping("/create")
    @CachePut(value = "product", key = "#product.productId")
    public ResponseEntity<String> create(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/purchase")
    @CacheEvict(value = "products", key = "#id")
    public ResponseEntity<String> purchase(@RequestParam String id) {
        return productService.purchase(id);
    }

    @PutMapping("/modify")
    @CacheEvict(value = "products", key = "#product.productId")
    public ResponseEntity<String> modify(@RequestBody Product product) {
        return productService.modifyProduct(product);
    }

    @DeleteMapping("/delete")
    @CacheEvict(value = "users", key = "#product.productId")
    public ResponseEntity<String> delete(@RequestParam String id) {
        return productService.deleteProduct(id);
    }
}
