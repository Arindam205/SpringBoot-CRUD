package com.Test1RorApplication.RORApplicationTesting.Service;

import com.Test1RorApplication.RORApplicationTesting.DTO.*;
import com.Test1RorApplication.RORApplicationTesting.Model.Address;
import com.Test1RorApplication.RORApplicationTesting.Model.CivicDetails;
import com.Test1RorApplication.RORApplicationTesting.Model.FamilyMembers;
import com.Test1RorApplication.RORApplicationTesting.Model.RorMaster;
import com.Test1RorApplication.RORApplicationTesting.Repository.RorMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class RorMasterService {


    @Autowired
    private RorMasterRepository rorMasterRepository;

    @Autowired
    private RORMasterCoreService rorMasterCoreService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private FamilyMembersService familyMembersService;

    @Autowired
    private CivicDetailsService civicDetailsService;

    // Method to save a new RorMaster record
    public RorMaster saveRorMaster(RORMasterCoreDTO rorMasterDTO) {
        RorMaster rorMaster = RorMaster.builder()
                .oldRorId(rorMasterDTO.getOldRorId())
                .rationCardNumber(rorMasterDTO.getRationCardNumber())
                .familyIncome(rorMasterDTO.getFamilyIncome())
                .build();
        return rorMasterCoreService.saveRorMaster(rorMaster);
    }

    // Method to generate a unique ROR number
    public String generateRorNumber(RorMaster rorMaster) {
        // Example ROR number format: 16AMC0000001
        String stateCode = "16";  // Fixed state code
        String municipalCorporationCode = "AMC";  // Fixed code for Agartala Municipal Corporation

        // Count the total records and use it to generate the family number part
        long familyCount = rorMasterRepository.count();  // Get the count of saved records
        String familyId = String.format("%07d", familyCount + 1);  // Generate a zero-padded family ID

        // Concatenate the parts to form the ROR number
        String rorNumber = stateCode + municipalCorporationCode + familyId;

        // You can now associate this ROR number with the `RorMaster` object if needed
        return rorNumber;
    }

    // New method to fetch RorMaster by UUID
    public RorMaster getRorMasterById(UUID rorMasterId) {
        Optional<RorMaster> rorMaster = rorMasterRepository.findById(rorMasterId);
        if (rorMaster.isPresent()) {
            return rorMaster.get();
        } else {
            throw new RuntimeException("RorMaster not found for ID: " + rorMasterId);
        }
    }

    public void saveCivilDetails(CivicDetailsDTO civicDetailsDTO, UUID rorMasterId) {
        CivicDetails civicDetails = CivicDetails.builder()
                .rorMasterId(rorMasterId)
                .electricityConnection(civicDetailsDTO.getElectricityConnection())
                .septicTankSewerageConnection(civicDetailsDTO.getSepticTankSewerageConnection())
                .constructionType(civicDetailsDTO.getConstructionType())
                .build();
        civicDetailsService.saveCivicDetail(civicDetails, rorMasterId);
    }

    public void saveAddresses(AddressDTO addressDTO, UUID rorMasterId) {
        Address address = Address.builder()
                .rorMasterId(rorMasterId)
                .gisPropertyId(addressDTO.getGisPropertyId())
                .wardNumber(addressDTO.getWardNumber())
                .locality(addressDTO.getLocality())
                .subLocality(addressDTO.getSubLocality())
                .roadName(addressDTO.getRoadName())
                .postOfficeName(addressDTO.getPostOfficeName())
                .pinCode(addressDTO.getPinCode())
                .policeStation(addressDTO.getPoliceStation())
                .build();
        addressService.saveAddress(address, rorMasterId);
    }

    public void saveFamilyMembers(List<FamilyMembersDTO> familyMembers, UUID rorMasterId) {
        familyMembers.forEach(familyMemberDTO -> {
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
                    .headOfFamily(familyMemberDTO.isHeadOfFamily())
                    .phoneNumber(familyMemberDTO.getPhoneNumber())
                    .educationQualification(familyMemberDTO.getEducationQualification())
                    .religion(familyMemberDTO.getReligion())
                    .identityProof(familyMemberDTO.getIdentityProof())
                    .idNumber(familyMemberDTO.getIdNumber())
                    .build();
            familyMembersService.saveFamilyMember(familyMember, rorMasterId);
        });
    }

    public void saveAllFamilyMembers(RorMasterDTO rorMasterDTO, UUID rorMasterId) {
        saveFamilyMembers(rorMasterDTO.getFamilyMembers(), rorMasterId);
    }
}
