package com.homecloud.backend.clients.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "users_folders")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserFolder {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "folder_id")
    private String folderId;

}