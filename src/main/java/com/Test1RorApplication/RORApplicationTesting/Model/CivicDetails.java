package com.Test1RorApplication.RORApplicationTesting.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CivicDetails {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String electricityConnection;

    @Column(unique = true)
    private String septicTankSewerageConnection;

    @Column(nullable = false)
    private String constructionType;

    @Column(nullable = false)
    private UUID rorMasterId;
}
