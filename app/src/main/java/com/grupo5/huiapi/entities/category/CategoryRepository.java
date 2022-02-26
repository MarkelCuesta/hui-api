package com.grupo5.huiapi.entities.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT category FROM Category category WHERE name = ?1")
    public Optional<Category> findCategoryByName(String name);
}
