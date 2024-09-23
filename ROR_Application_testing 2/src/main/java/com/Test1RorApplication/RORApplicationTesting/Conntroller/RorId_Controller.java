//package com.Test1RorApplication.RORApplicationTesting.Conntroller;
//
//import com.Test1RorApplication.RORApplicationTesting.Service.RorId_Service;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.UUID;
//
//@Controller
//public class RorId_Controller {
//    @Autowired
//    RorId_Service rorIdService;
//
//    /**
//     * Endpoint to generate a new ROR ID for a specific ward and family
//     *
//     * @param wardNumber the ward number
//     * @param rorMasterId the UUID of the ROR master (Head of Family)
//     * @return ResponseEntity with the generated ROR ID
//     */
//    @PostMapping("/generate")
//    public ResponseEntity<String> generateRorId(@RequestParam int wardNumber, @RequestParam UUID rorMasterId) {
//        // Generate the new ROR ID using the service
//        String newRorId = rorIdService.generateRorId(wardNumber, rorMasterId);
//
//        // Return the new ROR ID with a status of CREATED
//        return new ResponseEntity<>(newRorId, HttpStatus.CREATED);
//    }
//}
