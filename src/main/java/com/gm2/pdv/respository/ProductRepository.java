package com.gm2.pdv.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository extends  extends JpaRepository<ItemSale, Long {
}
