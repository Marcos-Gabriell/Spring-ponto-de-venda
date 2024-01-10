package com.gm2.pdv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdcutInfoDTO {

    private long id;

    private String description;

    private int quantity;
}
