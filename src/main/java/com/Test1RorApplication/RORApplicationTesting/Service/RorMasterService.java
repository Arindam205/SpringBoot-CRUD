package com.Test1RorApplication.RORApplicationTesting.Service;

import com.Test1RorApplication.RORApplicationTesting.DTO.*;
import com.Test1RorApplication.RORApplicationTesting.Exception.*;
import com.Test1RorApplication.RORApplicationTesting.Model.*;
import com.Test1RorApplication.RORApplicationTesting.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;
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
    private CivicDetailsRepository civicDetailsRepository;

    @Transactional
    public UUID createFullRorMasterRecord(RorMasterDTO rorMasterDTO) throws ValidationException, DuplicateResourceException {
        validateRorMasterDTO(rorMasterDTO);

        RorMaster rorMaster = saveRorMaster(rorMasterDTO.getRorMasterCoreDTO());
        UUID rorMasterId = rorMaster.getRorMasterId();

        saveCivicDetails(rorMasterDTO.getCivicDetailsDTO(), rorMasterId);
        saveAddress(rorMasterDTO.getAddressDTO(), rorMasterId);
        saveAllFamilyMembers(rorMasterDTO.getFamilyMembers(), rorMasterId);

        return rorMasterId;
    }

    private void validateRorMasterDTO(RorMasterDTO rorMasterDTO) throws ValidationException {
        if (rorMasterDTO == null) {
            throw new ValidationException("RorMasterDTO cannot be null");
        }
        if (rorMasterDTO.getRorMasterCoreDTO() == null) {
            throw new ValidationException("RorMasterCoreDTO cannot be null");
        }
        if (rorMasterDTO.getCivicDetailsDTO() == null) {
            throw new ValidationException("CivicDetailsDTO cannot be null");
        }
        if (rorMasterDTO.getAddressDTO() == null) {
            throw new ValidationException("AddressDTO cannot be null");
        }
        if (rorMasterDTO.getFamilyMembers() == null || rorMasterDTO.getFamilyMembers().isEmpty()) {
            throw new ValidationException("At least one family member is required");
        }
        // Add more specific validations as needed
    }

    private RorMaster saveRorMaster(RORMasterCoreDTO rorMasterCoreDTO) throws DuplicateResourceException {
        RorMaster rorMaster = RorMaster.builder()
                .oldRorId(rorMasterCoreDTO.getOldRorId())
                .rationCardNumber(rorMasterCoreDTO.getRationCardNumber())
                .familyIncome(rorMasterCoreDTO.getFamilyIncome())
                .build();
        try {
            return rorMasterRepository.save(rorMaster);
        } catch (Exception e) {
            throw new DuplicateResourceException("A RorMaster with the same details already exists");
        }
    }

    private void saveCivicDetails(CivicDetailsDTO civicDetailsDTO, UUID rorMasterId) {
        CivicDetails civicDetails = CivicDetails.builder()
                .rorMasterId(rorMasterId)
                .electricityConnection(civicDetailsDTO.getElectricityConnection())
                .septicTankSewerageConnection(civicDetailsDTO.getSepticTankSewerageConnection())
                .constructionType(civicDetailsDTO.getConstructionType())
                .build();
        civicDetailsRepository.save(civicDetails);
    }

    private void saveAddress(AddressDTO addressDTO, UUID rorMasterId) {
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
        addressRepository.save(address);
    }

    private void saveAllFamilyMembers(List<FamilyMembersDTO> familyMembers, UUID rorMasterId) {
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
            familyMembersRepository.save(familyMember);
        });
    }

    @Transactional(readOnly = true)
    public SuccessDTO getSuccessDTO(UUID rorMasterId) throws ResourceNotFoundException {
        RorMaster rorMaster = rorMasterRepository.findByRorMasterId(rorMasterId)
                .orElseThrow(() -> new ResourceNotFoundException("RorMaster not found with ID: " + rorMasterId));

        RorId rorId = rorIdRepository.findByRorMasterId(rorMasterId)
                .orElseThrow(() -> new ResourceNotFoundException("RorId not found for RorMaster ID: " + rorMasterId));

        Address address = addressRepository.findByRorMasterId(rorMasterId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found for RorMaster ID: " + rorMasterId));

        List<FamilyMembers> familyMembersList = familyMembersRepository.findByRorMasterId(rorMasterId);

        if (familyMembersList.isEmpty()) {
            throw new ResourceNotFoundException("No family members found for RorMaster ID: " + rorMasterId);
        }

        List<FamilyMemberDTO> familyMemberDTOs = familyMembersList.stream()
                .map(this::mapToFamilyMemberDTO)
                .collect(Collectors.toList());

        return SuccessDTO.builder()
                .rorId(rorId.getRorId())
                .wardNumber(address.getWardNumber())
                .rationCardNumber(rorMaster.getRationCardNumber())
                .familyMembers(familyMemberDTOs)
                .build();
    }

    private FamilyMemberDTO mapToFamilyMemberDTO(FamilyMembers member) {
        String fullName = String.format("%s %s %s",
                member.getFirstName(),
                member.getMiddleName() != null ? member.getMiddleName() : "",
                member.getLastName()).trim();

        return FamilyMemberDTO.builder()
                .fullName(fullName)
                .phoneNumber(member.getPhoneNumber())
                .relationWithHOF(member.getRelationWithHOF())
                .build();
    }
}