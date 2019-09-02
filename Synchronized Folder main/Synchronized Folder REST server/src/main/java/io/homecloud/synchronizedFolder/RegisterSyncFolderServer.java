package io.homecloud.synchronizedFolder;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterSyncFolderServer {
	@JsonProperty("folder_address")
	private String folder_address;
}
