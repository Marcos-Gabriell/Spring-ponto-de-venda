package com.gm2.pdv.controller;

import com.gm2.pdv.respository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
