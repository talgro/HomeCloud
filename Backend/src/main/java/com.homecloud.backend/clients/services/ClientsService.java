package com.homecloud.backend.clients.services;

import com.homecloud.backend.clients.Repositories.FolderDetailsRepository;
import com.homecloud.backend.clients.Repositories.FolderUsersRepository;
import com.homecloud.backend.clients.Repositories.ServerDetailsRepository;
import com.homecloud.backend.clients.Repositories.ServerUsersRepository;
import com.homecloud.backend.clients.entities.FolderDetails;
import com.homecloud.backend.clients.entities.ServerDetails;
import com.homecloud.backend.clients.entities.UserFolder;
import com.homecloud.backend.clients.models.FolderConnectionDetails;
import com.homecloud.backend.clients.models.HomeServerConnectionDetails;
import com.homecloud.backend.clients.entities.UserServer;
import com.homecloud.backend.clients.models.Users;
import com.homecloud.backend.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.channels.SeekableByteChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientsService {

    private ServerUsersRepository serverUsersRepository;
    private FolderUsersRepository folderUsersRepository;
    private ServerDetailsRepository serverDetailsRepository;
    private FolderDetailsRepository folderDetailsRepository;
    private SecurityUtils securityUtils;

    @Autowired
    public ClientsService(ServerUsersRepository serverUsersRepository,
                          ServerDetailsRepository serverDetailsRepository,
                          FolderDetailsRepository folderDetailsRepository,
                          SecurityUtils securityUtils) {
        this.serverUsersRepository = serverUsersRepository;
        this.serverDetailsRepository = serverDetailsRepository;
        this.folderDetailsRepository = folderDetailsRepository;
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

    public FolderConnectionDetails getFolderAddress() {
        String username = securityUtils.getUsername();

        UserFolder userFolder = this.folderUsersRepository.getByUsername(username);

        if (userFolder == null) {
            return new FolderConnectionDetails(username, null);
        }

        FolderDetails serverDetails = folderDetailsRepository.getByFolderId(userFolder.getFolderId());

        return new FolderConnectionDetails(username, serverDetails.getFolderAddress());
    }

    public void registerNewServer(String serverName, String serverAddress) {
        String uniqueID = UUID.randomUUID().toString();
        String username = securityUtils.getUsername();

        ServerDetails newHomeServerConnection = new ServerDetails(uniqueID, serverName, serverAddress);
        serverDetailsRepository.save(newHomeServerConnection);

        UserServer usersServers = new UserServer(username, uniqueID);
        serverUsersRepository.save(usersServers);
    }

    public void registerNewSyncedFolder(String folderAddress) {
        String uniqueID = UUID.randomUUID().toString();
        String username = securityUtils.getUsername();

        FolderDetails folderDetails = new FolderDetails(uniqueID, folderAddress);
        folderDetailsRepository.save(folderDetails);

        UserFolder usersFolder = new UserFolder(username, uniqueID);
        folderUsersRepository.save(usersFolder);
    }

    public void updateServerAddress(String serverAddress) {
        String username = securityUtils.getUsername();

        UserServer userServer = serverUsersRepository.getByUsername(username);
        if (userServer == null){
            return;
        }
        ServerDetails serverDetails = serverDetailsRepository.getByServerId(userServer.getHomeServerId());
        if (serverDetails == null){
            return;
        }

        serverDetails.setServerAddress(serverAddress);
        serverDetailsRepository.save(serverDetails);
    }

    public void updateFolderAddress(String folderAddress) {
        String username = securityUtils.getUsername();

        UserFolder userFolder = folderUsersRepository.getByUsername(username);
        if (userFolder == null){
            return;
        }
        FolderDetails folderDetails = folderDetailsRepository.getByFolderId(userFolder.getFolderId());
        if (folderDetails == null){
            return;
        }

        folderDetails.setFolderAddress(folderAddress);
        folderDetailsRepository.save(folderDetails);
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
