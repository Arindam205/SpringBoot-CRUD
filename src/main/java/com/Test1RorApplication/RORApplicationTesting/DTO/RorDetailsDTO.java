package com.Test1RorApplication.RORApplicationTesting.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RorDetailsDTO {
    private String rationCardNumber;
    private String rorId;
    private String headOfFamilyName;
}
