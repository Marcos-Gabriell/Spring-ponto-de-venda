package com.gm2.pdv.controller;

import com.gm2.pdv.entity.Product;
import com.gm2.pdv.respository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {


    private ProductRepository productRepository;

    public ProductRepository(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping()
    public ResponseEntity getAll() {
        return  new ResponseEntity(productRepository.findAll(), HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity post(@RequestBody Product product) {
        try {
            return new ResponseEntity(productRepository.save(product), HttpStatus.CREATED);
        } catch (Exception error){
            return new ResponseEntity(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntityput(@RequestBody Product product) {
        try {
            return new ResponseEntity(productRepository.save(product), HttpStatus.OK)
        } catch (Exception error){
            return new ResponseEntity(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
