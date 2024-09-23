package com.Test1RorApplication.RORApplicationTesting.Repository;

import com.Test1RorApplication.RORApplicationTesting.Model.FamilyMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FamilyMembers_Repository extends JpaRepository<FamilyMembers, Integer> {
    List<FamilyMembers> findByRorMasterId(UUID rorMasterId);
}
