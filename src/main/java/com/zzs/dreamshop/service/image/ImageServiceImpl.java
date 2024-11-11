package com.zzs.dreamshop.service.image;

import com.zzs.dreamshop.dto.ImageDto;
import com.zzs.dreamshop.dto.ProductDto;
import com.zzs.dreamshop.entity.Image;
import com.zzs.dreamshop.entity.Product;
import com.zzs.dreamshop.exceptions.ResourceNotFoundException;
import com.zzs.dreamshop.repository.ImageRepository;
import com.zzs.dreamshop.service.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ProductServiceImpl productService;

    @Override
    public ImageDto getImageById(int id) {
         Image image =  imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found!"));
         return maptoImageDto(image);
    }

    private ImageDto maptoImageDto(Image image) {
        ImageDto imageDto = new ImageDto();
        imageDto.setId(image.getId());
        imageDto.setFilename(image.getFilename());
        imageDto.setUrl(imageDto.getUrl());
        return imageDto;


    }

    @Override
    public void updateImage(MultipartFile file, int imageId) {
        Image image = imageRepository.getReferenceById(imageId);
        try {
            image.setFilename(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteImageById(int id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
            throw new ResourceNotFoundException("Image not found!");
        });
    }

    @Override
    public List<ImageDto> saveImage(int productId, List<MultipartFile> files) {
        ProductDto product = productService.getProductById(productId);

        List<ImageDto> imageDto = new ArrayList<>();
        for(MultipartFile file : files){
            try{
                Image image = new Image();
                image.setFilename(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                String buildDownloadUrl = "/api/images/image/download";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setUrl(downloadUrl);
                Image savedImage = imageRepository.save(image);

                savedImage.setUrl(buildDownloadUrl + savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto savedImageDto = new ImageDto();
                savedImageDto.setId(savedImage.getId());
                savedImageDto.setFilename(image.getFilename());
                savedImageDto.setUrl(savedImage.getUrl());
                imageDto.add(savedImageDto);

            }catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return imageDto;
    }
}
