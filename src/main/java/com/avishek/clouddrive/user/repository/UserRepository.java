package com.avishek.clouddrive.user.repository;

import com.avishek.clouddrive.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByName(String username);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String username);

//    boolean existsByUserName(String user);
}

