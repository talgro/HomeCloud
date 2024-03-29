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
public class UserServer {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "home_server_id")
    private String homeServerId;

}