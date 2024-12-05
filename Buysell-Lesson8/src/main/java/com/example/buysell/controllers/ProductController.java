package com.example.buysell.controllers;

import com.example.buysell.models.Product;
import com.example.buysell.models.User;
import com.example.buysell.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    public String products(@RequestParam(name = "depCity", required = false) String depCity,
                           @RequestParam(name = "arrCity", required = false) String arrCity,
                           @RequestParam(name = "minPrice", required = false) Integer minPrice,
                           @RequestParam(name = "maxPrice", required = false) Integer maxPrice,
                           Principal principal,
                           Model model) {

        // Если город не выбран, то устанавливаем его в null
        if (depCity != null && depCity.isEmpty()) {
            depCity = null;
        }
        if (arrCity != null && arrCity.isEmpty()) {
            arrCity = null;
        }

        // Логика фильтрации: если цена не установлена, пропускаем этот фильтр
        if (minPrice == null) {
            minPrice = 0; // Нет минимальной цены, пропускаем фильтр по минимальной цене
        }
        if (maxPrice == null) {
            maxPrice = Integer.MAX_VALUE; // Нет максимальной цены, пропускаем фильтр по максимальной цене
        }

        // Получаем список продуктов, применяя фильтрацию
        List<Product> products = productService.findByCitiesAndPriceRange(depCity, arrCity, minPrice, maxPrice);

        // Добавляем данные в модель для отображения в шаблоне
        model.addAttribute("products", products);
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("depCity", depCity);  // передаем выбранные города
        model.addAttribute("arrCity", arrCity);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        return "products"; // Страница с отображением списка продуктов
    }





    @PostMapping("/product/create")
    public String createProduct(@RequestParam("file1") MultipartFile file1, Product product, Principal principal) throws IOException {
        productService.saveProduct(principal, product, file1);
        return "redirect:/my/products";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/my/products";
    }

    @GetMapping("/my/products")
    public String userProducts(Principal principal, Model model) {
        User user = productService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProducts());
        return "my-products";
    }

    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model, Principal principal) {
        // Получаем информацию о продукте и авторе
        Product product = productService.getProductById(id);
        User author = product.getUser();

        // Добавляем данные в модель
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages()); // Изображения продукта
        model.addAttribute("authorProduct", author);
        model.addAttribute("user", productService.getUserByPrincipal(principal)); // Пользователь, просматривающий страницу

        return "product-info";
    }
}
