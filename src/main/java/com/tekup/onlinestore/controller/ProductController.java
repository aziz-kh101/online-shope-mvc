package com.tekup.onlinestore.controller;

import com.tekup.onlinestore.model.Product;
import com.tekup.onlinestore.dto.ProductDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("products")
public class ProductController {

    private static List<Product> products = new ArrayList<>();
    private static Long idCount = 0L;

    static {
        products.add(new Product(++idCount, "SS-S9", "Samsung Galaxy S9", 500D, 50, "samsung-s9.png"));
        products.add(new Product(++idCount, "NK-5P", "Nokia 5.1 Plus", 60D, 60, null));
        products.add(new Product(++idCount, "IP-7", "iPhone 7", 600D, 30, "iphone-7.png"));
    }

    @GetMapping
    public String getAllProducts(Model model){
        model.addAttribute("products",products);
        return "list";
    }

    @GetMapping("/create")
    public String getCreateForm(Model model){
        model.addAttribute("productDto", new ProductDto());
        return "create";
    }

    @PostMapping("/create")
    public String saveProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "create";
        }
        Product product = Product.builder()
                .id(++idCount)
                .code(productDto.getCode())
                .name(productDto.getName())
                .price(Double.valueOf(productDto.getPrice()))
                .quantity(Integer.valueOf(productDto.getQuantity())).build();
        products.add(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String getUpdateForm(Model model, @PathVariable("id") Long id){
        Product product = products.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        if (product == null){
            return "redirect:/products";
        }
        ProductDto productDto = new ProductDto();
        productDto.setId(id);
        productDto.setCode(product.getCode());
        productDto.setName(product.getName());
        productDto.setPrice(String.valueOf(product.getPrice()));
        productDto.setQuantity(String.valueOf(product.getQuantity()));
        model.addAttribute("productDto", productDto);

        return "update";
    }

    @PostMapping("/{id}/edit")
    public String updateProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, @PathVariable("id") Long id){
        Product product = products.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        if (product == null){
            return "redirect:/products";
        }

        if(bindingResult.hasErrors()){
            return "update";
        }

        product.setCode(productDto.getCode());
        product.setName(productDto.getName());
        product.setQuantity(Integer.valueOf(productDto.getQuantity()));
        product.setPrice(Double.valueOf(productDto.getQuantity()));

        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct( @PathVariable("id") Long id){
        if(id != null)
            products = products.stream().filter(p -> !p.getId().equals(id)).collect(Collectors.toList());
        return "redirect:/products";
    }
}
