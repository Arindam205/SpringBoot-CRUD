package com.Test1RorApplication.RORApplicationTesting.Repository;

import com.Test1RorApplication.RORApplicationTesting.Model.RorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RorId_Repository extends JpaRepository<RorId, Integer> {

    //Find all RorId by ward number
    List<RorId> findByWardNumber(int wardNumber);

}
