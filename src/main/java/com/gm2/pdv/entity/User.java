package com.gm2.pdv.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User {

    private Long id;
    private String name;
    private boolean isEnabled;


}
