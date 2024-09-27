package com.Test1RorApplication.RORApplicationTesting.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SuccessDTO {
    private String rorId;
    private int wardNumber;
    private String rationCardNumber;
    private List<FamilyMemberDTO> familyMembers;
}
