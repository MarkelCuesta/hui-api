package com.grupo5.huiapi.entities.category;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Category {
    @Id
    @SequenceGenerator(sequenceName = "category_sequence", name = "category_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Category parent;

    public Category(String name) {
        this.name = name;
    }
    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }



    public List<Category> addSubCategories(String ...categoryNames) {
        List<Category> ret = new ArrayList<>();
        for(String categoryName : categoryNames)
            ret.add( new Category(categoryName, this) );

        return ret;
    }

    public static List<Category> createCategories(String ...names) {
        List<Category> ret = new ArrayList<>();
        for(String categoryName : names)
            ret.add( new Category(categoryName) );

        return ret;
    }
}
