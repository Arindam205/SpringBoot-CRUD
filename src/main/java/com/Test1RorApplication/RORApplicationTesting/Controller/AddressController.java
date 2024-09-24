package com.Test1RorApplication.RORApplicationTesting.Controller;

import com.Test1RorApplication.RORApplicationTesting.Model.Address;
import com.Test1RorApplication.RORApplicationTesting.Service.Address_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {

    @Autowired
    private Address_Service addressService;

    @PostMapping("/add/{rorMasterId}")
    public ResponseEntity<Address> addAddress(@RequestBody Address address, @PathVariable UUID rorMasterId){
        Address newAddress = addressService.saveAddress(address,rorMasterId);

        return new ResponseEntity<>(newAddress, HttpStatus.CREATED);
    }
}
