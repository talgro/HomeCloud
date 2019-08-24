package com.homecloud.backend.clients.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "users_servers")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsersServers {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "home_server_id")
    private String homeServerId;

}