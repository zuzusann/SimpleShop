package com.zzs.dreamshop.service.image;

import com.zzs.dreamshop.dto.ImageDto;
import com.zzs.dreamshop.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    Image getImageById(int id);
    void updateImage(MultipartFile file, int imageId);
    void deleteImageById(int id);
    List<ImageDto> saveImage(int productId, List<MultipartFile> files);
}
