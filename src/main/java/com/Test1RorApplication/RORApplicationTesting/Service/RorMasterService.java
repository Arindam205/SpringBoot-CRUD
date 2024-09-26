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
    RorMasterRepository rorMasterRepository;

    // Method to save a new RorMaster record
    public RorMaster saveRorMaster(RorMaster rorMaster) {
        return rorMasterRepository.save(rorMaster);
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
