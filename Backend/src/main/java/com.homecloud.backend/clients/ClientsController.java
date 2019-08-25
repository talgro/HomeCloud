package com.homecloud.backend.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientsController {

    private ClientsService clientsService;

    @Autowired
    public ClientsController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @RequestMapping(value = "{userId}/getHomeServerAddress", method = RequestMethod.GET)
    public String getHomeServerAddress() {
        return this.clientsService.getHomeServerAddress;
    }

    @RequestMapping(value = "{serverId}/ /{address}", method = RequestMethod.POST)
    public void updateConnection(@PathVariable("serverId") int serverId, @PathVariable("address") String address) {
        this.clientsService.updateConnection(serverId, address);
    }

}