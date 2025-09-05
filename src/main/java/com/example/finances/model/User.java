package com.example.finances.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "user_id")
    private int userId;

    public User() {

    }
    public int getUserId(int userId) {
        return this.userId;
    }
}
