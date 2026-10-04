package com.avishek.clouddrive.user.repository;

import com.avishek.clouddrive.user.entity.AppRole;
import com.avishek.clouddrive.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {



    Optional<Role> findByRoleName(AppRole appRole);
}
