package com.homecloud.backend.clients;

import com.homecloud.backend.clients.DTOs.ServerUsersDto;
import com.homecloud.backend.exceptions.ClientServiceException;
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

    @RequestMapping(value = "registerNewServer/{userId}/{serverName}/{serverAddress}", method = RequestMethod.GET)
    public String registerNewServer(@PathVariable("userId") String userId,
                                    @PathVariable("userId") String serverName,
                                    @PathVariable("userId") String serverAddress) throws ClientServiceException {
        return this.clientsService.registerNewServer(userId, serverName, serverAddress);
    }

    @RequestMapping(value = "getHomeServerAddress/{userId}", method = RequestMethod.GET)
    public String getHomeServerAddress(@PathVariable("userId") String userId) throws ClientServiceException {
        return this.clientsService.getHomeServerAddress(userId);
    }

    @RequestMapping(value = "addUserToServer/{userId}", method = RequestMethod.POST)
    public void addUserToServer(@PathVariable("userId") String userId,
                                  @RequestBody ServerUsersDto serverUsersDto) throws ClientServiceException {
        this.clientsService.addUserToServer(userId, serverUsersDto.getHomeServerId(), serverUsersDto.getUserId());
    }

}