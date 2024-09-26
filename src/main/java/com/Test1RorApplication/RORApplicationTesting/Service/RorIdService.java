package com.Test1RorApplication.RORApplicationTesting.Service;

import com.Test1RorApplication.RORApplicationTesting.Model.RorId;
import com.Test1RorApplication.RORApplicationTesting.Repository.RorIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RorIdService {

    @Autowired
    RorIdRepository rorIdRepository;

    public String generateRorId(){
        String stateCode = "16";
        String municipalCorporationCode = "AMC";

        // Count total records for uniqueness and pad with zeros (8 digits)
        long familyCount = rorIdRepository.count() + 1;  // Assuming count provides the total families inserted
        String familyCountString = String.format("%08d", familyCount);

        // Generate the full ROR ID
        String newRorId = stateCode + municipalCorporationCode + familyCountString;

        return newRorId;
    }

    public RorId saveRorId(RorId rorId, UUID rorMasterId) {
        rorId.setRorMasterId(rorMasterId);
        return rorIdRepository.save(rorId);
    }
}
