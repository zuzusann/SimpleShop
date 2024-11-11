package com.zzs.dreamshop.dto;

import com.zzs.dreamshop.entity.Product;

import lombok.Data;

import java.sql.Blob;

@Data
public class ImageDto {

    private int id;
    private String filename;
    private String fileType;
    private byte[] image;
    private String url;

}
