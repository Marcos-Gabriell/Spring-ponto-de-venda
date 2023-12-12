package com.gm2.pdv.controller;

import com.gm2.pdv.dto.SaleDTO;
import com.gm2.pdv.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;


@Controller
@RequestMapping("/sale")
public class SaleController {

    private SaleService saleService;

    public SaleController(@Autowired SaleService saleService) {
        this.saleService = saleService;
    }

    public ResponseEntity post(@RequestBody SaleDTO saleDTO) {

        try {
            long id = saleService.save(saleDTO);
            return new ResponseEntity("Venda realziada com sucesso: " + id, HttpStatus.CREATED);
        } catch (Exception error){

        }
    }
}
