package com.Test1RorApplication.RORApplicationTesting.Service;

import com.Test1RorApplication.RORApplicationTesting.Model.RorId;
import com.Test1RorApplication.RORApplicationTesting.Repository.RorIdRepository;
import com.Test1RorApplication.RORApplicationTesting.Exception.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RorIdService {

    @Autowired
    private RorIdRepository rorIdRepository;

    @Transactional
    public String generateAndSaveRorId(UUID rorMasterId) throws DuplicateResourceException {
        String newRorId = generateRorId();
        RorId rorId = new RorId();
        rorId.setRorMasterId(rorMasterId);
        rorId.setRorId(newRorId);

        try {
            rorIdRepository.save(rorId);
        } catch (Exception e) {
            throw new DuplicateResourceException("Failed to save ROR ID. It may already exist.");
        }

        return newRorId;
    }

    private String generateRorId() {
        String stateCode = "16";
        String municipalCorporationCode = "AMC";

        long familyCount = rorIdRepository.count() + 1;
        String familyCountString = String.format("%08d", familyCount);

        return stateCode + municipalCorporationCode + familyCountString;
    }
}