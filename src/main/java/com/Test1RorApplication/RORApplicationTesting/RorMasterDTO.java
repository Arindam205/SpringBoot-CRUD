package com.Test1RorApplication.RORApplicationTesting;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RorMasterDTO {

    // Fields for RorMaster
    private String oldRorId;
    private String rationCardNumber;
    private int familyIncome;

    // Fields for CivicDetails
    private String electricityConnection;
    private String septicTankSewerageConnection;
    private String constructionType;

    // Fields for Address
    private String gisPropertyId;
    private int wardNumber;
    private String locality;
    private String subLocality;
    private String roadName;
    private String postOfficeName;
    private int pinCode;
    private String policeStation;

    // List to handle multiple family members
    private List<FamilyMembersDTO> familyMembers;

    // Nested DTO for FamilyMembers (list of this DTO)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FamilyMembersDTO {
            private String title;  // Mr., Mrs., etc.
            private String firstName;
            private String middleName;  // Optional
            private String lastName;
            private String occupation;
            private String relationWithHOF;
            private LocalDate dateOfBirth;
            private String gender;  // Ensure consistent naming with the model
            private boolean isHeadOfFamily;
            private String phoneNumber;
            private String educationQualification;
            private String religion;
            private String identityProof;
            private String idNumber;
        }

    }

