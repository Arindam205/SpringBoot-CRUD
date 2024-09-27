package com.Test1RorApplication.RORApplicationTesting.Service;

import com.Test1RorApplication.RORApplicationTesting.DTO.*;
import com.Test1RorApplication.RORApplicationTesting.Exception.ResourceNotFoundException;
import com.Test1RorApplication.RORApplicationTesting.Model.Address;
import com.Test1RorApplication.RORApplicationTesting.Model.CivicDetails;
import com.Test1RorApplication.RORApplicationTesting.Model.FamilyMembers;
import com.Test1RorApplication.RORApplicationTesting.Model.RorMaster;
import com.Test1RorApplication.RORApplicationTesting.Model.RorId;
import com.Test1RorApplication.RORApplicationTesting.Repository.AddressRepository;
import com.Test1RorApplication.RORApplicationTesting.Repository.FamilyMembersRepository;
import com.Test1RorApplication.RORApplicationTesting.Repository.RorIdRepository;
import com.Test1RorApplication.RORApplicationTesting.Repository.RorMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class RorMasterService {

    @Autowired
    private RorMasterRepository rorMasterRepository;
    @Autowired
    private RorIdRepository rorIdRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private FamilyMembersRepository familyMembersRepository;

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


    public SuccessDTO getSuccessDTO(UUID rorMasterId) {
        // Fetch RorMaster
        RorMaster rorMaster = rorMasterRepository.findByRorMasterId(rorMasterId)
                .orElseThrow(() -> new ResourceNotFoundException("RorMaster not found with ID: " + rorMasterId));

        // Fetch RorId
        RorId rorId = rorIdRepository.findByRorMasterId(rorMasterId)
                .orElseThrow(() -> new ResourceNotFoundException("RorId not found for RorMaster ID: " + rorMasterId));

        // Fetch Address
        Address address = addressRepository.findByRorMasterId(rorMasterId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found for RorMaster ID: " + rorMasterId));

        // Fetch FamilyMembers
        List<FamilyMembers> familyMembersList = familyMembersRepository.findByRorMasterId(rorMasterId);

        // Map FamilyMembers to FamilyMemberDTO
        List<FamilyMemberDTO> familyMemberDTOs = familyMembersList.stream().map(member -> {
            String fullName = String.format("%s %s %s",
                    member.getFirstName(),
                    member.getMiddleName() != null ? member.getMiddleName() : "",
                    member.getLastName()).trim();

            return FamilyMemberDTO.builder()
                    .fullName(fullName)
                    .phoneNumber(member.getPhoneNumber())
                    .relationWithHOF(member.getRelationWithHOF())
                    .build();
        }).collect(Collectors.toList());

        // Build SuccessDTO
        return SuccessDTO.builder()
                .rorId(rorId.getRorId())
                .wardNumber(address.getWardNumber())
                .rationCardNumber(rorMaster.getRationCardNumber())
                .familyMembers(familyMemberDTOs)
                .build();
    }
}
