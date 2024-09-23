package com.Test1RorApplication.RORApplicationTesting.Conntroller;


import com.Test1RorApplication.RORApplicationTesting.Model.RorMaster;
import com.Test1RorApplication.RORApplicationTesting.Service.RorMaster_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rorMaster")
public class RorMasterController123 {

    @Autowired
    private RorMaster_Service rorMasterService;  // Inject the service properly

    @PostMapping
    public ResponseEntity<RorMaster> createFamily(@RequestBody RorMaster master) {
        // Use the injected instance of the service
        RorMaster newRorMaster = rorMasterService.saveRorMaster(master);  // Correct instance usage
        return new ResponseEntity<>(newRorMaster, HttpStatus.CREATED);
    }
}


