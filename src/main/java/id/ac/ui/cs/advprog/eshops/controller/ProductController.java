package id.ac.ui.cs.advprog.eshops.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import id.ac.ui.cs.advprog.eshops.model.Product;
import id.ac.ui.cs.advprog.eshops.service.ProductService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Controller
@RequestMapping("/product")
public class ProductController {



    @Autowired
    private ProductService service;
    @GetMapping ("/create")
    public String createProductPage(Model model){
        Product product= new Product();
        model.addAttribute ("product",product);
        return "createProduct";
    }
    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model){
        service.create (product);
        return "redirect:list";
    }
    @GetMapping ("/list")
    public String productListPage(Model model){
        List<Product>allProducts= service.findAll();
        model.addAttribute ("products",allProducts);
        return "productList";
    }
}
