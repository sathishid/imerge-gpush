package com.arasoftwares.imerge.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "message", "subscription" })
public class ResponseWrapper {

    @JsonProperty("message")
    private Message message;
    
    @JsonProperty("subscription")
    private String subscription;

    @JsonProperty("message")
    public Message getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(Message message) {
        this.message = message;
    }

    @JsonProperty("subscription")
    public String getSubscription() {
        return subscription;
    }

    @JsonProperty("subscription")
    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

	@Override
	public String toString() {
		return "ResponseWrapper [message=" + message + ", subscription=" + subscription + "]";
	}	
}
