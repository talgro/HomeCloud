package com.homecloud.backend.clients.services;

import com.homecloud.backend.clients.Repositories.ServerDetailsRepository;
import com.homecloud.backend.clients.entities.ServerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServerService {

    private ServerDetailsRepository serverDetailsRepository;

    @Autowired
    public HomeServerService(ServerDetailsRepository serverDetailsRepository) {
        this.serverDetailsRepository = serverDetailsRepository;
    }

    public void updateAddress(String serverId, String address) {
        ServerDetails serverDetails = serverDetailsRepository.getByServerId(serverId);
        serverDetails.setServerAddress(address);
        this.serverDetailsRepository.save(serverDetails);
    }
}
