package io.homecloud.synchronizedFolder.utils;

import java.time.LocalDate;
import java.time.zone.ZoneOffsetTransitionRule.TimeDefinition;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotifyMessage {
	@JsonProperty("Type")
	private String Type;
	@JsonProperty("UnsubscribeURL")
	private String UnsubscribeURL;
	@JsonProperty("SigningCertURL")
	private String SigningCertURL;
	@JsonProperty("Signature")
	private String Signature;
	@JsonProperty("SignatureVersion")
	private String SignatureVersion;
	@JsonProperty("Message")
	private String Message;
	@JsonProperty("Token")
	private String Token;
	@JsonProperty("SubscribeURL")
	private String SubscribeURL;
	@JsonProperty("Subject")
	private String Subject;
	@JsonProperty("TopicArn")
	private String TopicArn;
	@JsonProperty("MessageId")
	private String MessageId;
	@JsonProperty("Timestamp")
	private String Timestamp;

}
