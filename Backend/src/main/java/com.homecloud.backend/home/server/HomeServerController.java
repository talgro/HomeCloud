package com.homecloud.backend.home.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeServerController {

    private HomeServerService homeServerService;

    @Autowired
    public HomeServerController(HomeServerService homeServerService) {
        this.homeServerService = homeServerService;
    }

    @RequestMapping(value = "{serverId}/testConnection", method = RequestMethod.GET)
    public int testConnection(@PathVariable("serverId") int serverId) {
        return serverId;
    }

    @RequestMapping(value = "{serverId}/updateConnection/{address}", method = RequestMethod.POST)
    public void updateConnection(@PathVariable("serverId") int serverId, @PathVariable("address") String address) {
        this.homeServerService.updateConnection(serverId, address);
    }
}