package com.example.buysell.repositories;

import com.example.buysell.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Поиск по цене
    List<Product> findByPriceBetween(Integer minPrice, Integer maxPrice);

    // Поиск по городу прибытия и цене
    List<Product> findByArrCityAndPriceBetween(String arrCity, Integer minPrice, Integer maxPrice);

    // Поиск по городу отправления и цене
    List<Product> findByDepCityAndPriceBetween(String depCity, Integer minPrice, Integer maxPrice);

    // Поиск по обоим городам и цене
    List<Product> findByDepCityAndArrCityAndPriceBetween(String depCity, String arrCity, Integer minPrice, Integer maxPrice);
}




