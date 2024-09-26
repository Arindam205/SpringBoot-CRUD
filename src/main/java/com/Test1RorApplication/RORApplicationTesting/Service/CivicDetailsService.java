package com.Test1RorApplication.RORApplicationTesting.Service;

import com.Test1RorApplication.RORApplicationTesting.DTO.CivicDetailsDTO;
import com.Test1RorApplication.RORApplicationTesting.Model.CivicDetails;
import com.Test1RorApplication.RORApplicationTesting.Repository.CivicDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CivicDetailsService {

    @Autowired
    CivicDetailsRepository civicDetailsRepository;

    // Set the headOfTheFamilyId before saving the address
    @Transactional
    public CivicDetails saveCivicDetail(CivicDetails civicDetails, UUID rorMasterId) {
        civicDetails.setRorMasterId(rorMasterId);  // Call the setter here
        return civicDetailsRepository.save(civicDetails);
    }

}
