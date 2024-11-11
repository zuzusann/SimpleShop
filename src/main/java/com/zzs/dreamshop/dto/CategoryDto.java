package com.zzs.dreamshop.dto;

import com.zzs.dreamshop.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {

    private int id;
    private String name;
    private List<ProductDto> products;
}
