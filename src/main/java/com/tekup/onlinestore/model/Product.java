package com.tekup.onlinestore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Product {
    Long id;
    String code;
    String name;
    Double price;
    Integer quantity;
    String Image;
}
