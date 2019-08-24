package com.homecloud.backend.clients;

import com.homecloud.backend.clients.entities.UsersServers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends JpaRepository<UsersServers, Integer> {

    UsersServers getByUserId(String id);

}