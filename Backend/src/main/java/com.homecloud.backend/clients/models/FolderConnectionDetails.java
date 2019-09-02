package com.homecloud.backend.clients.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderConnectionDetails {

  private String username;

  private String homeServerAddress;

}
