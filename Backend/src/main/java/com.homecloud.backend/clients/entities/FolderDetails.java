package com.homecloud.backend.clients.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "folder_details")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FolderDetails {

    @Id
    @Column(name = "folder_id")
    private String folderId;

    @Column(name = "folder_address")
    private String folderAddress;

}