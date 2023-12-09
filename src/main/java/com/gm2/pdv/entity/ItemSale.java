package com.gm2.pdv.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "item_sale")
@Entity
public class ItemSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;  // Corrigi o nome do campo para "quantity"

    @ManyToOne  // Removi a referência à lista de items
    @JoinColumn(name = "parent_item_id")  // Adicionei um novo campo para mapear o item pai
    private ItemSale parentItem;

    @OneToMany(mappedBy = "parentItem", fetch = FetchType.LAZY)  // Alterei o mappedBy para "parentItem"
    private List<ItemSale> childItems;
}
