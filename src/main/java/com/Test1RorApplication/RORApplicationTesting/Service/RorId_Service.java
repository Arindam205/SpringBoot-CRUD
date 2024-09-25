package com.Test1RorApplication.RORApplicationTesting.Service;

import com.Test1RorApplication.RORApplicationTesting.Model.RorId;
import com.Test1RorApplication.RORApplicationTesting.Repository.RorIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RorId_Service {

    @Autowired
    private RorIdRepository rorId_repository;

    public String generateRorId(UUID rorMasterId){
        String stateCode = "16";
        String municipalCorporationCode = "AMC";

        // Count total records for uniqueness and pad with zeros (6 digits)
        long familyCount = rorId_repository.count() + 1;  // Assuming count provides the total families inserted
        String familyCountString = String.format("%07d", familyCount);

        // Generate the full ROR ID
        String newRorId = stateCode + municipalCorporationCode + familyCountString;

        // Save the ROR ID to the database
        RorId rorId = new RorId();
        rorId.setRorId(newRorId);
        rorId.setRorMasterId(rorMasterId);
        rorId_repository.save(rorId);

        return newRorId;
    }
}
