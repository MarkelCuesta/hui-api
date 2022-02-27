package com.grupo5.huiapi.entities.user;

import com.grupo5.huiapi.entities.category.Category;
import com.grupo5.huiapi.entities.event.Event;
import lombok.*;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// JPA
@Entity @Table
// Lombok
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
public class User {
    @Getter @Setter
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1 )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String fullName;

    @Column()
    private String description;

    @Column()
    private String instagram;

    @Column()
    private String telegram;

    @Column()
    private String youtube;

    @Column()
    private String facebook;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "enrolled_events",
            joinColumns = {
                    @JoinColumn(name = "event_id", referencedColumnName = "id", updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false)
            }
    )
    private Set<Event> enrolled_events = new HashSet<>();

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Column()
    private Set<Event> organized_events = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "favorite_categories",
            joinColumns = {
                    @JoinColumn(name = "category_id", referencedColumnName = "id", updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false)
            }
    )
    private Set<Category> favorite_categories = new HashSet<>();


    public User(String username, String fullName, String instagram, String email, String telegram, String youtube, String facebook) {
        this.username = username;
        this.fullName = fullName;
        this.instagram = instagram;
        this.email = email;
        this.telegram = telegram;
        this.youtube = youtube;
        this.facebook = facebook;
    }

    // Constructor con los parámetros mínimos
    public User(String username, String password, String mail, String fullName) {
        this.password = password;
        this.fullName = fullName;
        this.email = mail;
        this.username = username;
    }

    public String checkNullFields() {
        List<String> missingFields = new ArrayList<>();
        if(ObjectUtils.isEmpty(this.password))
            missingFields.add("password");
        if(ObjectUtils.isEmpty(this.email))
            missingFields.add("email");
        if(ObjectUtils.isEmpty(this.fullName))
            missingFields.add("fullname");
        if(ObjectUtils.isEmpty(this.username))
            missingFields.add("username");

        return missingFields.isEmpty() ? null : String.join(", ", missingFields);
    }

}
