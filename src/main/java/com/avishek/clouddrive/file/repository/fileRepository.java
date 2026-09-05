package com.avishek.clouddrive.file.repository;

import com.avishek.clouddrive.file.entity.StoredFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface fileRepository extends JpaRepository<StoredFile, Long> {

}
