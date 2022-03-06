package com.grupo5.huiapi.entities.category;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.huiapi.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        this.categoryRepository = repository;
    }

    public List<Category> getCategories() {return categoryRepository.findAll(); }

    public Category getCategory(Long id) throws EntityNotFoundException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty())
            throw new EntityNotFoundException("category");
        return optionalCategory.get();
    }

    public Set<Category> getCategories(JsonNode categoriesNode) throws EntityNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        List<Integer> categoryIds = mapper.convertValue(categoriesNode, ArrayList.class);
        return getCategories(categoryIds);
    }

    public Set<Category> getCategories(List<Integer> categories) throws EntityNotFoundException {
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
