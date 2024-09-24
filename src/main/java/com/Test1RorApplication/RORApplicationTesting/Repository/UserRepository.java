package com.Test1RorApplication.RORApplicationTesting.Repository;

import com.Test1RorApplication.RORApplicationTesting.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
