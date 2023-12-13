package com.gm2.pdv.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    private String neme;

    private boolean isEnabled;
}
