package com.grupo5.huiapi.entities.event;

import com.grupo5.huiapi.entities.category.Category;
import com.grupo5.huiapi.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor @AllArgsConstructor
@Entity @Table
public class Event {
    @Id
    @SequenceGenerator(name = "event_sequence", sequenceName = "event_sequence", allocationSize = 1 )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_sequence")
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = true)
    private String descripcion;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "event_categories",
        joinColumns = {
            @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = true,updatable = false)
        },
        inverseJoinColumns = {
            @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = true, updatable = false)
        }
    )
    private Set<Category> categories = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;
}
