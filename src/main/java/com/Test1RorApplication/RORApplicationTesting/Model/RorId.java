package com.Test1RorApplication.RORApplicationTesting.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class RorId {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String rorId;  // The generated e-ROR ID

    @Column(nullable = false)
    private UUID rorMasterId;  // Reference to rorMaster UUID
}
