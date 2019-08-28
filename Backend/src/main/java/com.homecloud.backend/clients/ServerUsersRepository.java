package com.homecloud.backend.clients;

import com.homecloud.backend.clients.entities.ServerUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerUsersRepository extends JpaRepository<ServerUsers, Integer> {

    ServerUsers getByUserId(String id);

    List<ServerUsers> getAllByUserId(String id);

    ServerUsers getByHomeServerId(String id);

    List<ServerUsers> getAllByHomeServerId(String id);

}