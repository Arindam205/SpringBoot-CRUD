package com.Test1RorApplication.RORApplicationTesting.Repository;

import com.Test1RorApplication.RORApplicationTesting.Model.FamilyMembers;
import com.Test1RorApplication.RORApplicationTesting.Model.RorMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RorMasterRepository extends JpaRepository<RorMaster, UUID> {

    //Optional<RorMaster> findById(UUID rorMasterId);

    List<FamilyMembers> findByRorMasterId(UUID rorMasterId);
}
