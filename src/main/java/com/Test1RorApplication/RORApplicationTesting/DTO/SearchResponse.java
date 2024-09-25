package com.Test1RorApplication.RORApplicationTesting.DTO;

import com.Test1RorApplication.RORApplicationTesting.Model.FamilyMembers;
import com.Test1RorApplication.RORApplicationTesting.Model.RorId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse {
    private RorId rorDetails;
    private FamilyMembers familyMembers;
}
