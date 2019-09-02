package com.homecloud.backend.clients.Repositories;

import com.homecloud.backend.clients.entities.ServerDetails;
import com.homecloud.backend.clients.entities.UserServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerDetailsRepository extends JpaRepository<ServerDetails, Integer> {

    ServerDetails getByServerId(String id);

}