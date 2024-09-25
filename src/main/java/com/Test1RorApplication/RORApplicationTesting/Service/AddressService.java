package com.Test1RorApplication.RORApplicationTesting.Service;

import com.Test1RorApplication.RORApplicationTesting.Model.Address;
import com.Test1RorApplication.RORApplicationTesting.Repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    // Set the headOfTheFamilyId before saving the address
    public Address saveAddress(Address address, UUID rorMasterId) {
        address.setRorMasterId(rorMasterId);  // Call the setter here
        return addressRepository.save(address);
    }
}
