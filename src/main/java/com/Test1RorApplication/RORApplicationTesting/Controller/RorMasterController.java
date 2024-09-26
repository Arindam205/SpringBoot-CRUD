package com.Test1RorApplication.RORApplicationTesting.Controller;

import com.Test1RorApplication.RORApplicationTesting.DTO.RorResponseDTO;
import com.Test1RorApplication.RORApplicationTesting.DTO.SuccessDTO;
import com.Test1RorApplication.RORApplicationTesting.Model.*;
import com.Test1RorApplication.RORApplicationTesting.Service.*;
import com.Test1RorApplication.RORApplicationTesting.Model.RorMaster;
import com.Test1RorApplication.RORApplicationTesting.DTO.RorMasterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/rorMaster")
public class RorMasterController {

    private final RorMasterService rorMasterService;
    private final CivicDetailsService civicDetailsService;
    private final AddressService addressService;
    private final FamilyMembersService familyMembersService;
    private final RorIdService rorIdService;

    @Autowired
    public RorMasterController(RorMasterService rorMasterService, CivicDetailsService civicDetailsService,
                               AddressService addressService, FamilyMembersService familyMembersService, RorIdService rorIdService) {
        this.rorMasterService = rorMasterService;
        this.civicDetailsService = civicDetailsService;
        this.addressService = addressService;
        this.familyMembersService = familyMembersService;
        this.rorIdService = rorIdService;
    }

    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true", allowedHeaders = "*")
    public ResponseEntity<RorResponseDTO> createRorMasterRecord(@RequestBody RorMasterDTO rorMasterDTO) {

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
        String newRorNumber = rorIdService.generateRorId();

        // Step 5: Save generated e-rorId using the rorMasterId
        RorId rorId = RorId.builder()
                .rorMasterId(rorMasterId)
                .rorId(newRorNumber).build();
        RorId id = rorIdService.saveRorId(rorId, rorMasterId);
        RorResponseDTO rorResponseDTO = new RorResponseDTO();
        rorResponseDTO.setRorId(id.getRorId());
        rorResponseDTO.setRorMasterId(id.getRorMasterId().toString());


        // Return the response map as JSON
        return new ResponseEntity<>(rorResponseDTO, HttpStatus.OK);
    }

    // GET endpoint to fetch ROR and family members
    @GetMapping("/success/{rorMasterId}")
    @CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true", allowedHeaders = "*")
    public ResponseEntity<SuccessDTO> getRorSuccessPage(@PathVariable UUID rorMasterId) {
        SuccessDTO successDTO = rorMasterService.getSuccessDTO(rorMasterId);
        return ResponseEntity.ok(successDTO);
    }
}
