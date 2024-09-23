package com.Test1RorApplication.RORApplicationTesting.Service;

import com.Test1RorApplication.RORApplicationTesting.Model.CivicDetails;
import com.Test1RorApplication.RORApplicationTesting.Repository.CivicDetails_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CivicDetails_Service {

    @Autowired
    CivicDetails_Repository civicDetailsRepository;

    // Set the headOfTheFamilyId before saving the address
    public CivicDetails saveCivicDetails(CivicDetails civicDetails, UUID rorMasterId) {
        civicDetails.setRorMasterId(rorMasterId);  // Call the setter here
        return civicDetailsRepository.save(civicDetails);
    }
}
