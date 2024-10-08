package com.Test1RorApplication.RORApplicationTesting.Controller;

import com.Test1RorApplication.RORApplicationTesting.Model.CivicDetails;
import com.Test1RorApplication.RORApplicationTesting.Service.CivicDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/civicDetails")
public class CivicDetailsController {

    @Autowired
    private CivicDetailsService civicDetailsService;

    @PostMapping("/add/{rorMasterId}")
    public ResponseEntity<CivicDetails> addCivicDetails(@RequestBody CivicDetails civicDetails, @RequestHeader UUID rorMasterId){
        CivicDetails newCivicDetails = civicDetailsService.saveCivicDetail(civicDetails, rorMasterId);
        return new ResponseEntity<>(newCivicDetails, HttpStatus.CREATED);
    }
}
