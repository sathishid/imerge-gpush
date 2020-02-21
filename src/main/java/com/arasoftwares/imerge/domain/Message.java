
package com.arasoftwares.imerge.domain;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"data",
"messageId",
"publishTime"
})
public class Message {

@JsonProperty("data")
private String data;
@JsonProperty("messageId")
private String messageId;
@JsonProperty("publishTime")
private String publishTime;

@JsonProperty("data")
public String getData() {
return data;
}

@JsonProperty("data")
public void setData(final String data) {
    this.data = data;
}

@JsonProperty("messageId")
public String getMessageId() {
    return messageId;
}

@JsonProperty("messageId")
public void setMessageId(final String messageId) {
    this.messageId = messageId;
}

@JsonProperty("publishTime")
public String getPublishTime() {
    return publishTime;
}

@JsonProperty("publishTime")
public void setPublishTime(final String publishTime) {
this.publishTime = publishTime;
}

@Override
public String toString() {
    return "Message [data=" + data + ", messageId=" + messageId + ", publishTime=" + publishTime + "]";
}

}