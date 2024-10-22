package com.Test1RorApplication.RORApplicationTesting.Controller;

import com.Test1RorApplication.RORApplicationTesting.Service.RorIdService;
import com.Test1RorApplication.RORApplicationTesting.Service.RorMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.Test1RorApplication.RORApplicationTesting.DTO.RorDetailsDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deo")
@Validated
public class DeoController {

    private final RorMasterService rorMasterService;

    @Autowired
    public DeoController(RorMasterService rorMasterService) {
        this.rorMasterService = rorMasterService;
    }

    @GetMapping("/viewRorDetails/{createdByUser}")
    @CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true", allowedHeaders = "*")
    public ResponseEntity<List<RorDetailsDTO>> getRorDetailsByCreatedUser(
            @PathVariable String createdByUser,
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(rorMasterService.getRorDetailsByCreatedUser(createdByUser, skip, limit));
    }
}