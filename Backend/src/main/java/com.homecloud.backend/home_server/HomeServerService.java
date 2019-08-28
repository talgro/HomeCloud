package com.homecloud.backend.home_server;

import com.homecloud.backend.home_server.entities.HomeServerConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class HomeServerService {

    private HomeServerConnectionRepository homeServerConnectionRepository;

    @Autowired
    public HomeServerService(HomeServerConnectionRepository homeServerConnectionRepository) {
        this.homeServerConnectionRepository = homeServerConnectionRepository;
    }

    public void updateAddress(String serverId, String address) {
        HomeServerConnection connection = new HomeServerConnection(serverId, LocalDate.now(), address);
        this.homeServerConnectionRepository.save(connection);
    }
}
