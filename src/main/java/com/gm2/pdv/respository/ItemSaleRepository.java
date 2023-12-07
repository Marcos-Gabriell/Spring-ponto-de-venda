package com.gm2.pdv.respository;


import com.gm2.pdv.entity.ItemSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ItemSaleRepository extends JpaRepository<ItemSale, Long> {
}
