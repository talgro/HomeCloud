package com.homecloud.backend.clients.controllers;

import com.homecloud.backend.clients.DTOs.UsersDto;
import com.homecloud.backend.clients.models.Users;
import com.homecloud.backend.clients.services.ClientsService;
import com.homecloud.backend.clients.DTOs.HomeServerConnectionDetailsDto;
import com.homecloud.backend.clients.models.HomeServerConnectionDetails;
import com.homecloud.backend.clients.DTOs.ServerRegistrationDetailsDto;
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
        return new HomeServerConnectionDetailsDto(homeServerConnectionDetails.getUsername(), homeServerConnectionDetails.getHomeServerAddress(), homeServerConnectionDetails.getHomeServerName());//mapper.toHomeServerConnectionDetailsDto(homeServerConnectionDetails);
    }

    @RequestMapping(value = "registerNewServer", method = RequestMethod.POST)
    public void registerNewServer(@RequestBody ServerRegistrationDetailsDto serverRegistrationDetailsDto) {
        this.clientsService.registerNewServer(
                serverRegistrationDetailsDto.getServerName(), serverRegistrationDetailsDto.getServerAddress());
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