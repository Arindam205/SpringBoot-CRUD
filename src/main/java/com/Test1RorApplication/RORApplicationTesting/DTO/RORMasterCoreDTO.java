package com.Test1RorApplication.RORApplicationTesting.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RORMasterCoreDTO {
    private String oldRorId;
    private String rationCardNumber;
    private int familyIncome;
}
