package com.Test1RorApplication.RORApplicationTesting.Repository;

import com.Test1RorApplication.RORApplicationTesting.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    Optional<Address> findByRorMasterId(UUID rorMasterId);
}
