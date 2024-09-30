package com.Test1RorApplication.RORApplicationTesting.Service;


import com.Test1RorApplication.RORApplicationTesting.DTO.DEOResponse;
import com.Test1RorApplication.RORApplicationTesting.Model.Users;
import com.Test1RorApplication.RORApplicationTesting.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public DEOResponse getAllDEOs() {
        List<Users> users = userRepository.findByAdminFalse();
        return new DEOResponse(users);
    }

    public String toggleIsActive(UUID userId) {
        Optional<Users> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            boolean currentStatus = user.isActive();
            user.setActive(!user.isActive());
            userRepository.save(user);
            return currentStatus ? "DEO status changed from active to inactive" : "DEO status changed from inactive to active";
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }
}
