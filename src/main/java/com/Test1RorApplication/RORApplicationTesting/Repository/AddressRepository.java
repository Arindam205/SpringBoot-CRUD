package com.Test1RorApplication.RORApplicationTesting.Repository;

import com.Test1RorApplication.RORApplicationTesting.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
