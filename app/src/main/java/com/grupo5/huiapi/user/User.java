package com.grupo5.huiapi.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Entity
@Table
public class User {
    @Getter @Setter
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String nombre_apellidos;
    @Getter @Setter
    private String instagram;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String telegram;
    @Getter @Setter
    private String youtube;
    @Getter @Setter
    private String facebook;

    public User() {
    }

    public User(Long id, String username, String nombre_apellidos, String instagram, String email, String telegram, String youtube, String facebook) {
        this.id = id;
        this.username = username;
        this.nombre_apellidos = nombre_apellidos;
        this.instagram = instagram;
        this.email = email;
        this.telegram = telegram;
        this.youtube = youtube;
        this.facebook = facebook;
    }

    public User(String username, String nombre_apellidos, String instagram, String email, String telegram, String youtube, String facebook) {
        this.username = username;
        this.nombre_apellidos = nombre_apellidos;
        this.instagram = instagram;
        this.email = email;
        this.telegram = telegram;
        this.youtube = youtube;
        this.facebook = facebook;
    }
}
