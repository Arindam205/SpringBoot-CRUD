package com.Test1RorApplication.RORApplicationTesting.Service;

import com.Test1RorApplication.RORApplicationTesting.Model.FamilyMembers;
import com.Test1RorApplication.RORApplicationTesting.Repository.FamilyMembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FamilyMembersService {

    @Autowired
    FamilyMembersRepository familyMembersRepository;

    public FamilyMembers saveFamilyMember(FamilyMembers familyMember, UUID rorMasterId) {
        familyMember.setRorMasterId(rorMasterId);
        return familyMembersRepository.save(familyMember);
    }

    // Method to fetch family members by rorMasterId
    public List<FamilyMembers> getFamilyMembersByRorMasterId(UUID rorMasterId) {
        return familyMembersRepository.findByRorMasterId(rorMasterId);
    }
}
