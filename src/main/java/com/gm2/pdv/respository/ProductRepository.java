package com.gm2.pdv.respository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<com.gm2.pdv.ItemSale.ItemSale, Long> {
}
