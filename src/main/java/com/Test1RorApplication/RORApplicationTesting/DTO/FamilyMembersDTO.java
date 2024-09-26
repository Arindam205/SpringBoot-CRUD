package com.Test1RorApplication.RORApplicationTesting.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyMembersDTO {
    private String title;  // Mr., Mrs., etc.
    private String firstName;
    private String middleName;  // Optional
    private String lastName;
    private String occupation;
    private String relationWithHOF;
    private LocalDate dateOfBirth;
    private String gender;
    private boolean isHeadOfFamily;
    private String phoneNumber;
    private String educationQualification;
    private String religion;
    private String identityProof;
    private String idNumber;
}
