package unam.cruz.victor.topic.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Link {
    @JsonProperty("next")
    private Object next;

    public Link() {
    }

    public Link(Object next) {
        this.next = next;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(Object next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Link{" +
                "next=" + next +
                '}';
    }
}
