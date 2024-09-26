package com.Test1RorApplication.RORApplicationTesting.Controller;

import com.Test1RorApplication.RORApplicationTesting.Model.Address;
import com.Test1RorApplication.RORApplicationTesting.Model.CivicDetails;
import com.Test1RorApplication.RORApplicationTesting.Model.FamilyMembers;
import com.Test1RorApplication.RORApplicationTesting.Model.RorMaster;
import com.Test1RorApplication.RORApplicationTesting.DTO.RorMasterDTO;
import com.Test1RorApplication.RORApplicationTesting.SuccessDTO;
import com.Test1RorApplication.RORApplicationTesting.Service.AddressService;
import com.Test1RorApplication.RORApplicationTesting.Service.CivicDetailsService;
import com.Test1RorApplication.RORApplicationTesting.Service.FamilyMembersService;
import com.Test1RorApplication.RORApplicationTesting.Service.RorMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/rorMaster")
public class RorMasterController {
    private final RorMasterService rorMasterService;
    private final CivicDetailsService civicDetailsService;
    private final AddressService addressService;
    private final FamilyMembersService familyMembersService;

    @Autowired
    public RorMasterController(RorMasterService rorMasterService, CivicDetailsService civicDetailsService,
                               AddressService addressService, FamilyMembersService familyMembersService) {
        this.rorMasterService = rorMasterService;
        this.civicDetailsService = civicDetailsService;
        this.addressService = addressService;
        this.familyMembersService = familyMembersService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createRecord(@RequestBody RorMasterDTO rorMasterDTO) {
        // Step 1: Create the RorMaster entry and get the generated UUID
        RorMaster savedRorMaster = rorMasterService.saveRorMaster(rorMasterDTO.getRorMasterCoreDTO());
        UUID rorMasterId = savedRorMaster.getRorMasterId();

        // Step 2: Save CivicDetails using the rorMasterId
        rorMasterService.saveCivilDetails(rorMasterDTO.getCivicDetailsDTO(), rorMasterId);

        // Step 3: Save Address using the rorMasterId
        rorMasterService.saveAddresses(rorMasterDTO.getAddressDTO(), rorMasterId);

        // Step 4: Save all FamilyMembers
        rorMasterService.saveAllFamilyMembers(rorMasterDTO, rorMasterId);

        // Generate the unique ROR number based on the saved data
        String newRorNumber = rorMasterService.generateRorNumber(savedRorMaster);

        // Redirect the user to the success page with the ROR Master ID
        return ResponseEntity.status(HttpStatus.FOUND)  // 302 Found (redirect)
                .header("Location", newRorNumber) // Redirect to the success page
                .build();
    }

    // GET endpoint to fetch ROR and family members
    @GetMapping("/success/{rorMasterId}")
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
