package com.homecloud.backend.clients.controllers;

import com.homecloud.backend.clients.DTOs.HomeServerConnectionDetailsDto;
import com.homecloud.backend.clients.DTOs.SyncedFolderConnectionDetailsDto;
import com.homecloud.backend.clients.DTOs.FolderNewAddressDto;
import com.homecloud.backend.clients.DTOs.ServerRegistrationDetailsDto;
import com.homecloud.backend.clients.DTOs.ServerNewAddressDto;
import com.homecloud.backend.clients.DTOs.UsersDto;
import com.homecloud.backend.clients.models.FolderConnectionDetails;
import com.homecloud.backend.clients.models.Users;
import com.homecloud.backend.clients.services.ClientsService;
import com.homecloud.backend.clients.models.HomeServerConnectionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
public class ClientsController {

    private ClientsService clientsService;

    @Autowired
    public ClientsController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @RequestMapping(value = "getMyHomeServerDetails", method = RequestMethod.GET)
    public HomeServerConnectionDetailsDto getMyHomeServerDetails() {
        HomeServerConnectionDetails homeServerConnectionDetails = this.clientsService.getHomeServerAddress();
        return new HomeServerConnectionDetailsDto(
                homeServerConnectionDetails.getUsername(),
                homeServerConnectionDetails.getHomeServerAddress(),
                homeServerConnectionDetails.getHomeServerName());
    }

    @RequestMapping(value = "getMySyncedFolderDetails", method = RequestMethod.GET)
    public SyncedFolderConnectionDetailsDto getMySyncedFolderDetails() {
        FolderConnectionDetails homeServerConnectionDetails = this.clientsService.getFolderAddress();
        return new SyncedFolderConnectionDetailsDto(
                homeServerConnectionDetails.getUsername(), homeServerConnectionDetails.getHomeServerAddress());
    }

    @RequestMapping(value = "registerNewSyncedFolder", method = RequestMethod.POST)
    public void registerNewSyncedFolder(@RequestBody FolderNewAddressDto folderNewAddressDto) {
        this.clientsService.registerNewSyncedFolder(folderNewAddressDto.getFolderAddress());
    }

    @RequestMapping(value = "registerNewServer", method = RequestMethod.POST)
    public void registerNewServer(@RequestBody ServerRegistrationDetailsDto serverRegistrationDetailsDto) {
        this.clientsService.registerNewServer(
                serverRegistrationDetailsDto.getServerName(), serverRegistrationDetailsDto.getServerAddress());
    }

    @RequestMapping(value = "updateServerAddress", method = RequestMethod.POST)
    public void updateServerAddress(@RequestBody ServerNewAddressDto serverNewAddressDto) {
        this.clientsService.updateServerAddress(serverNewAddressDto.getServerAddress());
    }

    @RequestMapping(value = "updateFolderAddress", method = RequestMethod.POST)
    public void updateFolderAddress(@RequestBody FolderNewAddressDto folderNewAddressDto) {
        this.clientsService.updateFolderAddress(folderNewAddressDto.getFolderAddress());
    }

    @RequestMapping(value = "getAllServerUsers", method = RequestMethod.GET)
    public UsersDto getAllServerUsers() {
        Users users = this.clientsService.getAllServerUsers();
        return new UsersDto(users.getUsers());//mapper.toUsersDto(users);
    }

    @RequestMapping(value = "addUserToServer/{username}", method = RequestMethod.POST)
    public boolean addUserToServer(@PathVariable("username") String usernameToAdd) {
        return this.clientsService.addUserToServer(usernameToAdd);
    }

}