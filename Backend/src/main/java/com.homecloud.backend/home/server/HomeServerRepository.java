package com.homecloud.backend.home.server;

import com.homecloud.backend.home.server.entities.HomeServerConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeServerRepository extends JpaRepository<HomeServerConnection, Integer> {

    HomeServerConnection getByHomeServerId(int id);

}