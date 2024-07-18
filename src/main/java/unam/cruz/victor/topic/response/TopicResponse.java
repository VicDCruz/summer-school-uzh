package unam.cruz.victor.topic.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TopicResponse {
    @JsonProperty("messages")
    private List<Message> messages;
    @JsonProperty("links")
    private Link links;

    public TopicResponse() {
    }

    public TopicResponse(List<Message> messages, Link links) {
        this.messages = messages;
        this.links = links;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Link getLinks() {
        return links;
    }

    public void setLinks(Link links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "TopicResponse{" +
                "messages=" + messages +
                ", links=" + links +
                '}';
    }
}
