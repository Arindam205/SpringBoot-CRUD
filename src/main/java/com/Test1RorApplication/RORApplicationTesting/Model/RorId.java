package com.Test1RorApplication.RORApplicationTesting.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RorId {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String rorId;  // The generated e-ROR ID

    @Column(nullable = false)
    private UUID rorMasterId;  // Reference to HeadOfFamily UUID

    @Column(nullable = false)
    private int wardNumber;  // Store the ward number for querying
}
