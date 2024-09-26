package com.Test1RorApplication.RORApplicationTesting.Service;

import com.Test1RorApplication.RORApplicationTesting.Model.WardFamilyCounter;
import com.Test1RorApplication.RORApplicationTesting.Repository.WardFamilyCounterRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WardFamilyCounterService {

    @Autowired
    WardFamilyCounterRepository wardFamilyCounterRepository;

    /**
     * Finds the WardCounter for the specified ward, or initializes it if it doesn't exist.
     * This method is transactional to ensure atomic updates.
     *
     * @param wardNumber the ward number
     * @return the current family count for the ward (before incrementing)
     */
    @Transactional
    public int getAndIncrementFamilyCount(int wardNumber) {
        // Try to find the family Count for this ward
        Optional<WardFamilyCounter> wardCounterOptional = wardFamilyCounterRepository.findByWardNumber(wardNumber);

        if (wardCounterOptional.isPresent()) {
            // WardCounter exists, increment the family count
            WardFamilyCounter wardFamilyCounter = wardCounterOptional.get();
            int currentCount = wardFamilyCounter.getFamilyCount();
            wardFamilyCounter.setFamilyCount(currentCount + 1);  // Increment the family count
            wardFamilyCounterRepository.save(wardFamilyCounter);  // Save the updated count
            return currentCount + 1;  // Return the incremented count
        }else {
            // If no WardCounter exists, initialize it with a family count of 1
            WardFamilyCounter newWardFamilyCounter = new WardFamilyCounter();
            newWardFamilyCounter.setWardNumber(wardNumber);
            newWardFamilyCounter.setFamilyCount(1);
            wardFamilyCounterRepository.save(newWardFamilyCounter);  // Save the new ward counter
            return 1;  // This is the first family in the ward
        }

    }
}
