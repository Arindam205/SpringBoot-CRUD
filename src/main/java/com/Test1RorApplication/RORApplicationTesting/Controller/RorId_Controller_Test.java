package com.Test1RorApplication.RORApplicationTesting.Controller;

import com.Test1RorApplication.RORApplicationTesting.Service.RorIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class RorId_Controller_Test {
    @Autowired
    RorIdService rorIdService;

    /**
     * Endpoint to generate a new ROR ID for a specific ward and family
     *
     * @param wardNumber the ward number
     * @param rorMasterId the UUID of the ROR master (Head of Family)
     * @param model the model object to pass data to the view
     * @return Thymeleaf view with acknowledgment message
     */
    @GetMapping("/generate")
    public String generateRorId(@RequestParam int wardNumber, @RequestParam UUID rorMasterId, Model model) {
        // Generate the new ROR ID using the service
        String newRorId = rorIdService.generateRorId();

        // Add data to the model for Thymeleaf view
        model.addAttribute("name", "Mr Akash");  // Hardcoded for now
        model.addAttribute("rorId", newRorId);

        // Return the acknowledgment page (acknowledgment.html in templates)
        return "acknowledgment";
    }
}
