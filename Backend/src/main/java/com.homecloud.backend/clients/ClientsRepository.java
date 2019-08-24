package com.homecloud.backend.clients;

import com.homecloud.backend.clients.entities.HomeServerConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends JpaRepository<HomeServerConnection, Integer> {

    HomeServerConnection getByHomeServerId(int id);

}