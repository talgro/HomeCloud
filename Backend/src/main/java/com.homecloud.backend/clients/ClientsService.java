package com.homecloud.backend.clients;

import com.homecloud.backend.clients.entities.UsersServers;
import com.homecloud.backend.exceptions.ClientServiceException;
import com.homecloud.backend.home_server.HomeServerRepository;
import com.homecloud.backend.home_server.entities.HomeServerConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientsService {

    private ClientsRepository clientsRepository;
    private HomeServerRepository homeServerRepository;

    @Autowired
    public ClientsService(ClientsRepository clientsRepository,
                          HomeServerRepository homeServerRepository) {
        this.clientsRepository = clientsRepository;
        this.homeServerRepository = homeServerRepository;
    }

    public String getHomeServerAddress(String userId) throws ClientServiceException {
        UsersServers usersServers = this.clientsRepository.getByUserId(userId);
        if (usersServers == null){
            throw new ClientServiceException();
        }
        HomeServerConnection homeServerConnection = this.homeServerRepository.getByHomeServerId(usersServers.getHomeServerId());
        if (homeServerConnection == null){
            throw new ClientServiceException();
        }
        return homeServerConnection.getAddress();
    }

}
