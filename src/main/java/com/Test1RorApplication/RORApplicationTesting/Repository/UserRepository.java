package com.Test1RorApplication.RORApplicationTesting.Repository;

import com.Test1RorApplication.RORApplicationTesting.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
}
