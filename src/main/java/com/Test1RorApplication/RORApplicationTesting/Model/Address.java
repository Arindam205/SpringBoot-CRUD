package com.Test1RorApplication.RORApplicationTesting.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String gisPropertyId;

    @Column(nullable = false)
    private int wardNumber;

    @Column(nullable = false)
    private String locality;

    @Column(nullable = false)
    private String subLocality;

    @Column(nullable = false)
    private String roadName;

    @Column(nullable = false)
    private String postOfficeName;

    @Column(nullable = false)
    private int pinCode;

    @Column(nullable = false)
    private String policeStation;

    @Column(nullable = false)
    private UUID rorMasterId;

}
