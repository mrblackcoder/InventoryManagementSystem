package com.ims.inventorymanagementsystem.controller;

import com.ims.inventorymanagementsystem.model.User;
import com.ims.inventorymanagementsystem.service.UserService;
import com.ims.inventorymanagementsystem.service.ProductService;
import com.ims.inventorymanagementsystem.service.CategoryService;
import com.ims.inventorymanagementsystem.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final SupplierService supplierService;

    @GetMapping({"/", "/home"})
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            userService.registerUser(user);
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "dashboard";
    }

    @GetMapping("/products")
    public String productsPage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getActiveCategories());
        model.addAttribute("suppliers", supplierService.getActiveSuppliers());
        return "products";
    }

    @GetMapping("/categories")
    public String categoriesPage(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories";
    }

    @GetMapping("/suppliers")
    public String suppliersPage(Model model) {
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "suppliers";
    }

    @GetMapping("/stock-movements")
    public String stockMovementsPage() {
        return "stock-movements";
    }

    @GetMapping("/admin/users")
    public String adminUsersPage() {
        return "admin/users";
    }

    @GetMapping("/admin/reports")
    public String adminReportsPage() {
        return "admin/reports";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/403";
    }
}
