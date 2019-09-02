package com.homecloud.backend.clients.Repositories;

import com.homecloud.backend.clients.entities.UserServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerUsersRepository extends JpaRepository<UserServer, Integer> {

    UserServer getByUsername(String id);

    List<UserServer> getAllByUsername(String id);

    List<UserServer> getAllByHomeServerId(String id);

}