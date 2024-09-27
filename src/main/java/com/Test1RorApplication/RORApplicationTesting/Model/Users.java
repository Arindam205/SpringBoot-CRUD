package com.Test1RorApplication.RORApplicationTesting.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.Instant;
import java.util.UUID;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private Instant created;

    private boolean admin;
    private boolean active;

    @Column(nullable = false, unique = true)
    private long phNumber;

    private int wardNumber;
}
