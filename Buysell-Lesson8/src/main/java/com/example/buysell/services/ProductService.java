package com.example.buysell.services;

import com.example.buysell.models.Image;
import com.example.buysell.models.Product;
import com.example.buysell.models.User;
import com.example.buysell.repositories.ProductRepository;
import com.example.buysell.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findByCitiesAndPriceRange(String depCity, String arrCity, Integer minPrice, Integer maxPrice) {
        if (depCity == null && arrCity == null) {
            return productRepository.findByPriceBetween(minPrice, maxPrice);
        }
        if (depCity == null) {
            return productRepository.findByArrCityAndPriceBetween(arrCity, minPrice, maxPrice);
        }
        if (arrCity == null) {
            return productRepository.findByDepCityAndPriceBetween(depCity, minPrice, maxPrice);
        }
        return productRepository.findByDepCityAndArrCityAndPriceBetween(depCity, arrCity, minPrice, maxPrice);
    }

    public void saveProduct(Principal principal, Product product, MultipartFile file1) throws IOException {
        product.setUser(getUserByPrincipal(principal));
        Image image1;

        if (file1.getSize() != 0) {
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
        }

        log.info("Saving new Product. Title: {}; Author email: {}", product.getTitle(), product.getUser().getEmail());
        Product productFromDb = productRepository.save(product);
        productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId());
        productRepository.save(product);
    }

    @Autowired
    private UserRepository userRepository; // Добавьте инъекцию репозитория

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName()); // Используйте инстанс репозитория
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }


    @Transactional
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
