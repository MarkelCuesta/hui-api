package com.grupo5.huiapi.modules.event.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.grupo5.huiapi.modules.category.entity.Category;
import com.grupo5.huiapi.modules.user.entity.User;
import lombok.*;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor @AllArgsConstructor
@Entity @Table @Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Event {
    @Id
    @SequenceGenerator(name = "event_sequence", sequenceName = "event_sequence", allocationSize = 1 )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_sequence")
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "event_categories",
        joinColumns = {
            @JoinColumn(name = "event_id", referencedColumnName = "id", updatable = false)
        },
        inverseJoinColumns = {
            @JoinColumn(name = "category_id", referencedColumnName = "id", updatable = false)
        }
    )
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Category> categories = new HashSet<>();

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    private User organizer;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "enrolled_events",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "event_id", referencedColumnName = "id", updatable = false)
            }
    )
    private Set<Event> enrolled_users = new HashSet<>();

    public Event(String title, String description, Set<Category> categories, User organizer) {
        this.title = title;
        this.description = description;
        this.categories = categories;
        this.organizer = organizer;
    }


    public Event(String title, String description, User organizer) {
        this.title = title;
        this.description = description;
        this.organizer = organizer;
    }

        public Set<Category> addCategory(Category cat) {
        this.categories.add(cat);
        if(cat.getParent() != null)
            this.addCategory(cat.getParent());
        return this.categories;
    }

    public String checkNullFields() {
        List<String> missingFields = new ArrayList<>();
        if(ObjectUtils.isEmpty(this.title))
            missingFields.add("title");
        if(ObjectUtils.isEmpty(this.organizer))
            missingFields.add("organizer");
        if(ObjectUtils.isEmpty(this.categories))
            missingFields.add("categories");


        return missingFields.isEmpty() ? null : String.join(", ", missingFields);
    }

}
