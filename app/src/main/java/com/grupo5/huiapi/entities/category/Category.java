package com.grupo5.huiapi.entities.category;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Table
@NoArgsConstructor @AllArgsConstructor
public class Category {
    @Id
    @SequenceGenerator(sequenceName = "category_sequence", name = "category_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne(optional = true)
    @JoinColumn(name = "parent_id", nullable = true, referencedColumnName = "id")
    private Category parent;

    public Category(String name) {
        this.name = name;
    }
    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }
}
