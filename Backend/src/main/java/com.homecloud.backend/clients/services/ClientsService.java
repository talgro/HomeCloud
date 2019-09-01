package com.homecloud.backend.clients.services;

import com.homecloud.backend.clients.Repositories.ServerDetailsRepository;
import com.homecloud.backend.clients.Repositories.ServerUsersRepository;
import com.homecloud.backend.clients.entities.ServerDetails;
import com.homecloud.backend.clients.models.HomeServerConnectionDetails;
import com.homecloud.backend.clients.entities.UserServer;
import com.homecloud.backend.clients.models.Users;
import com.homecloud.backend.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientsService {

    private ServerUsersRepository serverUsersRepository;
    private ServerDetailsRepository serverDetailsRepository;
    private SecurityUtils securityUtils;

    @Autowired
    public ClientsService(ServerUsersRepository serverUsersRepository,
                          ServerDetailsRepository serverDetailsRepository,
                          SecurityUtils securityUtils) {
        this.serverUsersRepository = serverUsersRepository;
        this.serverDetailsRepository = serverDetailsRepository;
        this.securityUtils = securityUtils;
    }

    public HomeServerConnectionDetails getHomeServerAddress() {
        String username = securityUtils.getUsername();

        UserServer userServer = this.serverUsersRepository.getByUsername(username);

        if (userServer == null) {
            return new HomeServerConnectionDetails(username, null, null);
        }

        ServerDetails serverDetails = serverDetailsRepository.getByServerId(userServer.getHomeServerId());

        return new HomeServerConnectionDetails(username, serverDetails.getServerAddress(), serverDetails.getServerName());
    }

    public void registerNewServer(String serverName, String serverAddress) {
        String uniqueID = UUID.randomUUID().toString();
        String username = securityUtils.getUsername();

        ServerDetails newHomeServerConnection = new ServerDetails(uniqueID, serverName, serverAddress);
        serverDetailsRepository.save(newHomeServerConnection);

        UserServer usersServers = new UserServer(username, uniqueID);
        serverUsersRepository.save(usersServers);
    }

    public Users getAllServerUsers() {
        String username = securityUtils.getUsername();

        UserServer serverOfUser = serverUsersRepository.getByUsername(username);

        if (serverOfUser == null) {
            return new Users(new LinkedList<>());
        }

        List<UserServer> usersOfServer = serverUsersRepository.getAllByHomeServerId(serverOfUser.getHomeServerId());
        List<String> users = usersOfServer.stream().map((userOfServer) -> {
            return userOfServer.getUsername();
        }).collect(Collectors.toList());

        return new Users(users);
    }

    public boolean addUserToServer(String addedUserId) {
        String username = securityUtils.getUsername();
        UserServer serverOfUser = serverUsersRepository.getByUsername(username);
        String serverId = serverOfUser.getHomeServerId();

        List<UserServer> serversOfRequester = serverUsersRepository.getAllByUsername(username);
        if (!serversOfRequester.contains(serverId)) {
            // Requester does not have permission to add another user.
            return false;
        }

        UserServer userServer = new UserServer(addedUserId, serverId);
        serverUsersRepository.save(userServer);
        return true;
    }

}
