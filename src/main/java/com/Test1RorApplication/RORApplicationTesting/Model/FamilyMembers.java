package com.Test1RorApplication.RORApplicationTesting.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyMembers {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String title;

    @Column(nullable = false)
    private String firstName;

    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private UUID rorMasterId;

    @Column(nullable = false, unique = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String relationWithHOF;

    @Column(nullable = false)
    @JsonProperty
    private boolean headOfFamily;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String educationQualification;

    @Column(nullable = false)
    private String occupation;

    @Column(nullable = false)
    private String religion;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String identityProof;

    @Column(nullable = false, unique = true)
    private String idNumber;

}
