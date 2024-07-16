package unam.cruz.victor.ipfs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IpfsResponse {
    @JsonProperty("IpfsHash")
    private String ipfsHash;

    @JsonProperty("PinSize")
    private Integer pinSize;

    @JsonProperty("Timestamp")
    private String timestamp;

    @JsonProperty(value = "isDuplicate", required = false)
    private boolean isDuplicate;

    public String getIpfsHash() {
        return ipfsHash;
    }

    public void setIpfsHash(String ipfsHash) {
        this.ipfsHash = ipfsHash;
    }

    public Integer getPinSize() {
        return pinSize;
    }

    public void setPinSize(Integer pinSize) {
        this.pinSize = pinSize;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isDuplicate() {
        return isDuplicate;
    }

    public void setDuplicate(boolean duplicate) {
        isDuplicate = duplicate;
    }
}
