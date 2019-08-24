package com.homecloud.backend.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientsController {

    private ClientsService clientsService;

    @Autowired
    public ClientsController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @RequestMapping(value = "{userId}/getHomeServerAddress", method = RequestMethod.GET)
    public String getHomeServerAddress(@PathVariable("userId") String userId) {
        return this.clientsService.getHomeServerAddress(userId);
    }


}