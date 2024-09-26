package com.Test1RorApplication.RORApplicationTesting.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private String gisPropertyId;
    private int wardNumber;
    private String locality;
    private String subLocality;
    private String roadName;
    private String postOfficeName;
    private int pinCode;
    private String policeStation;
}
