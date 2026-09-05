package com.avishek.clouddrive.folder.repository;

import com.avishek.clouddrive.folder.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface folderRepository extends JpaRepository<Folder, Long> {
}
