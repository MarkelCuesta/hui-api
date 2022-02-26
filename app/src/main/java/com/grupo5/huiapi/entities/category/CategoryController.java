package com.grupo5.huiapi.entities.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("{id}")
    public Optional<Category> getCategory(@PathVariable("id") Long id) {
        return categoryService.getCategory(id);
    }

    @GetMapping("{id}/subcategories")
    public List<Category> getSubCategories(@PathVariable("id") Long id) {
        return categoryService.getSubCategories(id);
    }
}
