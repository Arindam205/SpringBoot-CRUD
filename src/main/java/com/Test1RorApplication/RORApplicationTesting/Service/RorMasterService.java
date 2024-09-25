package com.Test1RorApplication.RORApplicationTesting.Service;

import com.Test1RorApplication.RORApplicationTesting.Model.RorMaster;
import com.Test1RorApplication.RORApplicationTesting.Repository.RorMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class RorMasterService {


    @Autowired
    private RorMasterRepository rorMasterRepository;

    // Method to save a new RorMaster record
    public RorMaster saveRorMaster(RorMaster rorMaster) {
        return rorMasterRepository.save(rorMaster);
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

    }
