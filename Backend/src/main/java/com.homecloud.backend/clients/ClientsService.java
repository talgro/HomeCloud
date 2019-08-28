package com.homecloud.backend.clients;

import com.homecloud.backend.clients.entities.ServerUsers;
import com.homecloud.backend.exceptions.ClientServiceException;
import com.homecloud.backend.home_server.HomeServerConnectionRepository;
import com.homecloud.backend.home_server.entities.HomeServerConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ClientsService {

    private ServerUsersRepository serverUsersRepository;
    private HomeServerConnectionRepository homeServerConnectionRepository;

    @Autowired
    public ClientsService(ServerUsersRepository serverUsersRepository,
                          HomeServerConnectionRepository homeServerConnectionRepository) {
        this.serverUsersRepository = serverUsersRepository;
        this.homeServerConnectionRepository = homeServerConnectionRepository;
    }

    public String getHomeServerAddress(String userId) throws ClientServiceException {
        ServerUsers usersServers = this.serverUsersRepository.getByUserId(userId);
        if (usersServers == null) {
            throw new ClientServiceException();
        }
        HomeServerConnection homeServerConnection = this.homeServerConnectionRepository.getByHomeServerId(usersServers.getHomeServerId());
        if (homeServerConnection == null) {
            throw new ClientServiceException();
        }
        return homeServerConnection.getAddress();
    }

    public String registerNewServer(String userId, String serverName, String serverAddress) {
        String uniqueID = UUID.randomUUID().toString();

        HomeServerConnection newHomeServerConnection =
                new HomeServerConnection(uniqueID, LocalDate.now(), serverAddress);
        homeServerConnectionRepository.save(newHomeServerConnection);

        ServerUsers usersServers = new ServerUsers(userId, uniqueID);
        serverUsersRepository.save(usersServers);

        return uniqueID;
    }

    public void addUserToServer(String userId, String serverId, String addedUserId) {
        List<ServerUsers> serversOfRequester = serverUsersRepository.getAllByUserId(userId);
        if (!serversOfRequester.contains(serverId)) {
            // Requester does not have permission to add another user.
            return;
        }

        ServerUsers serverUsers = new ServerUsers(addedUserId, serverId);
        serverUsersRepository.save(serverUsers);
    }

}
