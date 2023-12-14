package com.gm2.pdv.service;

import com.gm2.pdv.dto.ProductDTO;
import com.gm2.pdv.dto.SaleDTO;
import com.gm2.pdv.entity.ItemSale;
import com.gm2.pdv.entity.Sale;
import com.gm2.pdv.entity.User;
import com.gm2.pdv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {


    private final UserRepository userRepository;


    public long save(SaleDTO sale) {

        User user = userRepository.findAllById(sale.getUserid()).get();



        Sale newSale = new Sale();
        newSale.setUser(user);
        newSale.setDate(LocalDate.now());
        newSale.setItems();
    }

    private List<ItemSale> getItemSale(List<ProductDTO> products){

    }

}
