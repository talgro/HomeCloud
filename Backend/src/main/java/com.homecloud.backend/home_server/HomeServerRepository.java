package com.homecloud.backend.home_server;

import com.homecloud.backend.home_server.entities.HomeServerConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeServerRepository extends JpaRepository<HomeServerConnection, Integer> {

    HomeServerConnection getByHomeServerId(String id);

}