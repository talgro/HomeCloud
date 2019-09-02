package com.homecloud.backend.clients.Repositories;

import com.homecloud.backend.clients.entities.FolderDetails;
import com.homecloud.backend.clients.entities.ServerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderDetailsRepository extends JpaRepository<FolderDetails, Integer> {

    FolderDetails getByFolderId(String id);

}