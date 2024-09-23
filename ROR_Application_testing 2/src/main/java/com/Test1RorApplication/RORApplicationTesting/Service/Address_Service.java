package com.Test1RorApplication.RORApplicationTesting.Service;

import com.Test1RorApplication.RORApplicationTesting.Model.Address;
import com.Test1RorApplication.RORApplicationTesting.Repository.Address_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class Address_Service {

    @Autowired
    Address_Repository addressRepository;

    // Set the headOfTheFamilyId before saving the address
    public Address saveAddress(Address address, UUID rorMasterId) {
        address.setRorMasterId(rorMasterId);  // Call the setter here
        return addressRepository.save(address);
    }
}
