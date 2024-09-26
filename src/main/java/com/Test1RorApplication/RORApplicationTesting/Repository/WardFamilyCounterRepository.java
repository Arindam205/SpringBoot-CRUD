package com.Test1RorApplication.RORApplicationTesting.Repository;

import com.Test1RorApplication.RORApplicationTesting.Model.WardFamilyCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WardFamilyCounterRepository extends JpaRepository<WardFamilyCounter, Integer> {

    //Find by ward number
    Optional<WardFamilyCounter> findByWardNumber(int wardNumber);
}
