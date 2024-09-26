package com.Test1RorApplication.RORApplicationTesting.DTO;

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
    private RORMasterCoreDTO rorMasterCoreDTO;
    private CivicDetailsDTO civicDetailsDTO;
    private AddressDTO addressDTO;
    private List<FamilyMembersDTO> familyMembers;
}
