package com.gm2.pdv.service;


import com.gm2.pdv.dto.ProductInfoDTO;
import com.gm2.pdv.dto.ProductSaleDTO;
import com.gm2.pdv.dto.SaleDTO;
import com.gm2.pdv.dto.SaleInfoDTO;
import com.gm2.pdv.entity.ItemSale;
import com.gm2.pdv.entity.Product;
import com.gm2.pdv.entity.Sale;
import com.gm2.pdv.entity.User;
import com.gm2.pdv.exceptions.InvalidOperationException;
import com.gm2.pdv.exceptions.NoltemException;
import com.gm2.pdv.repository.ItemSaleRepository;
import com.gm2.pdv.repository.ProductRepository;
import com.gm2.pdv.repository.SaleRepository;
import com.gm2.pdv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final ItemSaleRepository itemSaleRepository;

    public List<SaleInfoDTO> findAll() {
        return saleRepository.findAll().stream().map(sale -> getSaleInfo(sale)).collect(Collectors.toList());
    }


    // Remova um dos métodos getProductInfo, mantendo apenas um deles

    // Corrija a chamada do método getSaleInfo
    private SaleInfoDTO getSaleInfo(Sale sale) {
        List<ProductSaleDTO> products = getProductInfo(sale.getItems());
        BigDecimal total = getTotal(products);

        return SaleInfoDTO.builder()
                .user(sale.getUser().getName())
                .date(sale.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .products(products)
                .total(total)
                .build();
    }

    // Corrija a assinatura do método getTotal
    private BigDecimal getTotal(List<ProductSaleDTO> products) {
        BigDecimal total = BigDecimal.ZERO;

        for (ProductSaleDTO currentProduct : products) {
            total = total.add(currentProduct.getPrice().multiply(new BigDecimal(currentProduct.getQuantity())));
        }

        return total;
    }




    @Transactional
    public long save(SaleDTO sale) {
        User user = userRepository.findById(sale.getUserid())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        Sale newSale = new Sale();
        newSale.setUser(user);
        newSale.setDate(LocalDate.now());
        List<ItemSale> items = getItemSale(sale.getItems());

        newSale = saleRepository.save(newSale);

        saveItemSale(items, newSale);

        return newSale.getId();
    }

    public void saveItemSale(List<ItemSale> items, Sale sale) {
        for (ItemSale item : items) {
            item.setSale(sale);
            itemSaleRepository.save(item);
        }
    }

    private List<ItemSale> getItemSale(List<ProductSaleDTO> products) {

        if(products.isEmpty()) {
            throw  new InvalidOperationException("Não é possível adiconar a venda sem itens! ");
        }

        return products.stream().map(item -> {
            Product product = productRepository.findById(item.getProductid()).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            ItemSale itemSale = new ItemSale();
            itemSale.setProduct(product);
            itemSale.setQuantity(item.getQuantity());

            if (product.getQuantity() == 0) {
                throw new NoltemException("Produto sem estoque");
            } else if (product.getQuantity() < item.getQuantity()) {
                throw new InvalidOperationException(
                        String.format("A quantidade de itens da venda (%s) " +
                                "é maior que a quantidade disponível no estoque (%s) ", item.getQuantity(), product.getQuantity()));
            }

            int total = product.getQuantity() - item.getQuantity();
            product.setQuantity(total);
            productRepository.save(product);

            return itemSale;
        }).collect(Collectors.toList());
    }

    public SaleInfoDTO getById(long id) {
       Sale sale = saleRepository.findById(id)
               .orElseThrow( () -> new NoltemException("Venda não encontrada!"));
       return  getSaleInfo(sale);
    }
}
