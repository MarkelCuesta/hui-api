package com.grupo5.huiapi.user;

import lombok.*;
import javax.persistence.*;

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
    private String nombre_apellidos;

    @Column(nullable = true)
    private String descripcion;

    @Column(nullable = true)
    private String instagram;

    @Column(nullable = true)
    private String telegram;

    @Column(nullable = true)
    private String youtube;

    @Column(nullable = true)
    private String facebook;


    public User(String username, String nombre_apellidos, String instagram, String email, String telegram, String youtube, String facebook) {
        this.username = username;
        this.nombre_apellidos = nombre_apellidos;
        this.instagram = instagram;
        this.email = email;
        this.telegram = telegram;
        this.youtube = youtube;
        this.facebook = facebook;
    }

    // Constructor con los parámetros mínimos
    public User(String username, String password, String mail, String nombre_apellidos) {
        this.password = password;
        this.nombre_apellidos = nombre_apellidos;
        this.email = mail;
        this.username = username;
    }
}
