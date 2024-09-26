package com.Test1RorApplication.RORApplicationTesting.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FamilyMemberDTO {
    private String fullName;
    private String phoneNumber;
    private String relationWithHOF;
}
