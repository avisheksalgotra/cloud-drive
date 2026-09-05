package com.avishek.clouddrive.user.repository;

import com.avishek.clouddrive.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
