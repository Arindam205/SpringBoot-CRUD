package com.Test1RorApplication.RORApplicationTesting.Controller;

import com.Test1RorApplication.RORApplicationTesting.DTO.DEOActivateRequest;
import com.Test1RorApplication.RORApplicationTesting.DTO.DEOResponse;
import com.Test1RorApplication.RORApplicationTesting.Service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/list-deos")
    public ResponseEntity<DEOResponse> getAllDEOs() {
        return new ResponseEntity<>(adminService.getAllDEOs(), HttpStatus.OK);
    }

    @PutMapping("/deos/{id}/toggle-active")
    public ResponseEntity<String> toggleActive(@PathVariable UUID id) {
        return new ResponseEntity<>(adminService.toggleIsActive(id), HttpStatus.OK);
    }

}
