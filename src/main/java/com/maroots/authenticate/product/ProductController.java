package com.maroots.authenticate.product;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @GetMapping()
    public ResponseEntity<String> getProducts() {
        return ResponseEntity.ok("Product list");
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody  Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(product));
    }
}
