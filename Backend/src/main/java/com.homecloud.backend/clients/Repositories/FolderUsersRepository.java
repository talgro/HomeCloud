package com.homecloud.backend.clients.Repositories;

import com.homecloud.backend.clients.entities.UserFolder;
import com.homecloud.backend.clients.entities.UserServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderUsersRepository extends JpaRepository<UserFolder, Integer> {

    UserFolder getByUsername(String id);

    List<UserFolder> getAllByUsername(String id);

    List<UserFolder> getAllByFolderId(String id);

}