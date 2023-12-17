package com.gm2.pdv.service;

import com.gm2.pdv.dto.ProdcutInfoDTO;
import com.gm2.pdv.dto.ProductDTO;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    private SaleInfoDTO getSaleInfo(Sale sale) {
        SaleInfoDTO saleInfoDTO = new SaleInfoDTO();
        saleInfoDTO.setUser(sale.getUser().getName());
        saleInfoDTO.setDate(sale.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        saleInfoDTO.setProducts(getproductInfo(sale.getItems()));

        return saleInfoDTO;
    }

    private List<ProdcutInfoDTO> getproductInfo(List<ItemSale> items) {
        return items.stream().map(item -> {
            ProdcutInfoDTO prodcutInfoDTO = new ProdcutInfoDTO();
            prodcutInfoDTO.setDescription(item.getProduct().getDescription());
            prodcutInfoDTO.setQuantity(item.getQuantity());
            return prodcutInfoDTO;
        }).collect(Collectors.toList());
    }

    @Transactional
    public long save(SaleDTO sale) {
        User user = userRepository.findById(sale.getUserid()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

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

    private List<ItemSale> getItemSale(List<ProductDTO> products) {

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
       Sale sale = saleRepository.findById(id).get();
       return  getSaleInfo(sale);
    }
}
