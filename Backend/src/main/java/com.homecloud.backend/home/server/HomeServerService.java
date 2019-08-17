package com.homecloud.backend.home.server;

import com.homecloud.backend.home.server.entities.HomeServerConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class HomeServerService {

    private HomeServerRepository homeServerRepository;

    @Autowired
    public HomeServerService(HomeServerRepository homeServerRepository) {
        this.homeServerRepository = homeServerRepository;
    }

    public void updateConnection(int serverId, String address) {
        HomeServerConnection connection = new HomeServerConnection(serverId, LocalDate.now(), address);
        this.homeServerRepository.save(connection);
    }
}
