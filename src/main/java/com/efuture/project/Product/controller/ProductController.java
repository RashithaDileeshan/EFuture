package com.efuture.project.Product.controller;

import com.efuture.project.Product.entity.Product;
import com.efuture.project.Product.exception.ResourceNotFoundException;
import com.efuture.project.Product.repository.ProductRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

        @Autowired
        private ProductRepository  productRepository;

//    get all products
        @GetMapping
        public List<Product> getAllProducts(){
                return this.productRepository.findAll();
        }
//    get product by id
        @GetMapping("/{id}")
        public Product getProductbyId(@PathVariable (value = "id") long prductId){
                return this.productRepository.findById(prductId).orElseThrow(() -> new ResourceNotFoundException("Products not found with id:" + prductId));
        }
//    create product
        @PostMapping
        public Product createProducts(@RequestBody Product product) {
                return this.productRepository.save(product);
        }
//    update product
        @PutMapping("/{id}")
        public Product updateProduct(@RequestBody Product product, @PathVariable ("id") long productId){
             Product existing =   this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Products not found with id:" + productId));
             existing.setName(product.getName());
             existing.setDescription(product.getDescription());
             existing.setPrice(product.getPrice());
             existing.setCategory(product.getCategory());
            return this.productRepository.save(existing);
        }
//    delete product
        @DeleteMapping("{id}")
        public ResponseEntity<Product> deleteProduct(@PathVariable ("id") long productId){
                Product existing =   this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Products not found with id:" + productId));
                this.productRepository.delete(existing);
                return ResponseEntity.ok().build();

        }
}
