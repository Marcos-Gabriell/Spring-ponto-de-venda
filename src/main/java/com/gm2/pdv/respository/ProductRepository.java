package com.gm2.pdv.repository;

import com.gm2.pdv.model.ItemSale; // Asegúrate de importar la clase ItemSale correctamente
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ItemSale, Long> {
    // Puedes agregar consultas personalizadas aquí si es necesario
}

