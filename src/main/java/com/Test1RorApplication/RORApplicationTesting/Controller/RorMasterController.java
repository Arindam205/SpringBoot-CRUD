package com.Test1RorApplication.RORApplicationTesting.Controller;

import com.Test1RorApplication.RORApplicationTesting.DTO.RorResponseDTO;
import com.Test1RorApplication.RORApplicationTesting.DTO.SuccessDTO;
import com.Test1RorApplication.RORApplicationTesting.DTO.RorMasterDTO;
import com.Test1RorApplication.RORApplicationTesting.Exception.*;
import com.Test1RorApplication.RORApplicationTesting.Service.RorMasterService;
import com.Test1RorApplication.RORApplicationTesting.Service.RorIdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/rorMaster")
public class RorMasterController {

    private final RorMasterService rorMasterService;
    private final RorIdService rorIdService;

    @Autowired
    public RorMasterController(RorMasterService rorMasterService, RorIdService rorIdService) {
        this.rorMasterService = rorMasterService;
        this.rorIdService = rorIdService;
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    @CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true", allowedHeaders = "*")
    public ResponseEntity<RorResponseDTO> createRorMasterRecord(@Valid @RequestBody RorMasterDTO rorMasterDTO, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            UUID rorMasterId = rorMasterService.createFullRorMasterRecord(rorMasterDTO, userDetails);
            String newRorNumber = rorIdService.generateAndSaveRorId(rorMasterId);

            RorResponseDTO rorResponseDTO = new RorResponseDTO();
            rorResponseDTO.setRorId(newRorNumber);
            rorResponseDTO.setRorMasterId(rorMasterId.toString());

            return new ResponseEntity<>(rorResponseDTO, HttpStatus.OK);
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (DuplicateResourceException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while processing your request", e);
        }
    }

    @GetMapping("/success/{rorMasterId}")
    @CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true", allowedHeaders = "*")
    public ResponseEntity<SuccessDTO> getRorSuccessPage(@PathVariable UUID rorMasterId) {
        try {
            SuccessDTO successDTO = rorMasterService.getSuccessDTO(rorMasterId);
            return ResponseEntity.ok(successDTO);
        }catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ROR Master ID format", e);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching the data", e);
        }
    }
}