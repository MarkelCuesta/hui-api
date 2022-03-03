package com.grupo5.huiapi.entities.category;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.huiapi.exceptions.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        this.categoryRepository = repository;
    }

    public List<Category> getCategories() {return categoryRepository.findAll(); }

    public Category getCategory(Long id) throws CategoryNotFoundException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty())
            throw new CategoryNotFoundException();
        return optionalCategory.get();
    }

    public Set<Category> getCategories(JsonNode categoriesNode) throws CategoryNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode cats = categoriesNode.get("categories");
        List<Integer> categoryIds = mapper.convertValue(cats, ArrayList.class);
        return getCategories(categoryIds);
    }

    public Set<Category> getCategories(List<Integer> categories) throws CategoryNotFoundException {
        Set<Category> ret = new HashSet<>();
        System.out.println(categories);
        for (Integer category : categories) {
            ret.add(getCategory( Long.valueOf(category) ));
        }
        return ret;
    }

    public List<Category> getSubCategories(Long id) {
        return categoryRepository.findSubCategories(id);
    }

}
