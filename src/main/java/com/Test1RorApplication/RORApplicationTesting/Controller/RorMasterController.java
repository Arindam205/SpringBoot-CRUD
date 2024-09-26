package com.Test1RorApplication.RORApplicationTesting.Controller;

import com.Test1RorApplication.RORApplicationTesting.Model.*;
import com.Test1RorApplication.RORApplicationTesting.RorMasterDTO;
import com.Test1RorApplication.RORApplicationTesting.Service.*;
import com.Test1RorApplication.RORApplicationTesting.Model.Address;
import com.Test1RorApplication.RORApplicationTesting.Model.CivicDetails;
import com.Test1RorApplication.RORApplicationTesting.Model.FamilyMembers;
import com.Test1RorApplication.RORApplicationTesting.Model.RorMaster;
import com.Test1RorApplication.RORApplicationTesting.DTO.RorMasterDTO;
import com.Test1RorApplication.RORApplicationTesting.SuccessDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/rorMaster")
public class RorMasterController {

    private final RorMasterService rorMasterService;
    private final CivicDetailsService civicDetailsService;
    private final AddressService addressService;
    private final FamilyMembersService familyMembersService;
    private final RorId_Service rorIdService;

    @Autowired
    public RorMasterController(RorMasterService rorMasterService, CivicDetailsService civicDetailsService,
                               AddressService addressService, FamilyMembersService familyMembersService, RorId_Service rorIdService) {
        this.rorMasterService = rorMasterService;
        this.civicDetailsService = civicDetailsService;
        this.addressService = addressService;
        this.familyMembersService = familyMembersService;
        this.rorIdService = rorIdService;
    }

    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true", allowedHeaders = "*")
    public ResponseEntity<Map<String, String>> createRorMasterRecord(@RequestBody RorMasterDTO rorMasterDTO) {

        // Step 1: Create the RorMaster entry and get the generated UUID
        RorMaster savedRorMaster = rorMasterService.saveRorMaster(rorMasterDTO.getRorMasterCoreDTO());
        UUID rorMasterId = savedRorMaster.getRorMasterId();

        // Step 2: Save CivicDetails using the rorMasterId
        rorMasterService.saveCivilDetails(rorMasterDTO.getCivicDetailsDTO(), rorMasterId);

        // Step 3: Save Address using the rorMasterId
        rorMasterService.saveAddresses(rorMasterDTO.getAddressDTO(), rorMasterId);

        // Step 4: Save all FamilyMembers
        rorMasterDTO.getFamilyMembers().forEach(familyMemberDTO -> {
            System.out.println(familyMemberDTO);
            FamilyMembers familyMember = FamilyMembers.builder()
                    .rorMasterId(rorMasterId)
                    .title(familyMemberDTO.getTitle())
                    .firstName(familyMemberDTO.getFirstName())
                    .middleName(familyMemberDTO.getMiddleName())
                    .lastName(familyMemberDTO.getLastName())
                    .occupation(familyMemberDTO.getOccupation())
                    .relationWithHOF(familyMemberDTO.getRelationWithHOF())
                    .dateOfBirth(familyMemberDTO.getDateOfBirth())
                    .gender(familyMemberDTO.getGender())
                    .isHeadOfFamily(familyMemberDTO.isHeadOfFamily())
                    .phoneNumber(familyMemberDTO.getPhoneNumber())
                    .educationQualification(familyMemberDTO.getEducationQualification())
                    .religion(familyMemberDTO.getReligion())
                    .identityProof(familyMemberDTO.getIdentityProof())
                    .idNumber(familyMemberDTO.getIdNumber())
                    .build();
            familyMembersService.saveFamilyMember(familyMember, rorMasterId);
        });
        rorMasterService.saveAllFamilyMembers(rorMasterDTO, rorMasterId);

        // Generate the unique ROR number based on the saved data
        String newRorNumber = rorIdService.generateRorId();

        // Step 4: Save generated e-rorId using the rorMasterId
        RorId rorId= RorId.builder()
                .rorMasterId(rorMasterId)
                .rorId(newRorNumber)
        // Redirect the user to the success page with the ROR Master ID
        return ResponseEntity.status(HttpStatus.FOUND)  // 302 Found (redirect)
                .header("Location", newRorNumber) // Redirect to the success page
                .build();
        rorIdService.saveRorId(rorId, rorMasterId);


        // Create a response map
        Map<String, String> response = new HashMap<>();
        response.put("eRorNumber", newRorNumber);

        // Return the response map as JSON
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // GET endpoint to fetch ROR and family members
    @GetMapping("/success/{rorMasterId}")
    @CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true", allowedHeaders = "*")
    public ResponseEntity<SuccessDTO> getRorSuccessPage(@PathVariable UUID rorMasterId) {
        // Fetch ROR details
        RorMaster rorMaster = rorMasterService.getRorMasterById(rorMasterId);
        // Fetch family members details
        List<FamilyMembers> familyMembers = familyMembersService.getFamilyMembersByRorMasterId(rorMasterId);

        // Create a DTO for the success page
        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setRorId(rorMaster.getERorId());
        successDTO.setFamilyMembers(familyMembers.stream()
                .map(FamilyMembers::getFirstName) // Collect the names of family members
                .collect(Collectors.toList()));

        return new ResponseEntity<>(successDTO, HttpStatus.OK);
    }
}
