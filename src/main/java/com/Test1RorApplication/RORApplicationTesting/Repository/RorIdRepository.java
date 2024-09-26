package com.Test1RorApplication.RORApplicationTesting.Repository;

import com.Test1RorApplication.RORApplicationTesting.Model.RorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RorIdRepository extends JpaRepository<RorId, UUID> {

    //Find all RorId by ward number
    //List<RorId> findByWardNumber(int wardNumber);

}
