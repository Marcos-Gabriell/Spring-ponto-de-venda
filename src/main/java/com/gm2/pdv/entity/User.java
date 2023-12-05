package com.gm2.pdv.entity;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    private Long id;
    private String name;
    private boolean isEnabled;


}
