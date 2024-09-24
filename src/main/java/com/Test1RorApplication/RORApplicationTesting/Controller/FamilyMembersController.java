package com.Test1RorApplication.RORApplicationTesting.Controller;

import com.Test1RorApplication.RORApplicationTesting.Model.FamilyMembers;
import com.Test1RorApplication.RORApplicationTesting.Service.FamilyMembers_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/familyMember")
public class FamilyMembersController {

    @Autowired
    FamilyMembers_Service familyMembersService;

    @PostMapping("/add/{rorMasterId}")
    public ResponseEntity<FamilyMembers> addFamilyMember(@RequestBody FamilyMembers familyMembers, @PathVariable UUID rorMasterId){
        FamilyMembers newFamilyMember = familyMembersService.saveFamilyMember(familyMembers, rorMasterId);
        return new ResponseEntity<>(newFamilyMember, HttpStatus.CREATED);
    }

}
