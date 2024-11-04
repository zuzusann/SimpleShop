package com.zzs.dreamshop.dto.request;

import com.zzs.dreamshop.entity.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {

    private int id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
