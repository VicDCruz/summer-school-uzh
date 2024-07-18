package unam.cruz.victor.topic.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Base64;

public class Message {
    @JsonProperty("chunk_info")
    private Object chunkInfo;
    @JsonProperty("consensus_timestamp")
    private String consensusTimestamp;
    @JsonProperty("message")
    private String message;
    @JsonProperty("payer_account_id")
    private String payerAccountId;
    @JsonProperty("running_hash")
    private String runningHash;
    @JsonProperty("running_hash_version")
    private int runningHashVersion;
    @JsonProperty("sequence_number")
    private int sequenceNumber;
    @JsonProperty("topic_id")
    private String topicId;

    public Message() {
    }

    public Message(Object chunkInfo, String consensusTimestamp, String message, String payerAccountId, String runningHash, int runningHashVersion, int sequenceNumber, String topicId) {
        this.chunkInfo = chunkInfo;
        this.consensusTimestamp = consensusTimestamp;
        this.message = message;
        this.payerAccountId = payerAccountId;
        this.runningHash = runningHash;
        this.runningHashVersion = runningHashVersion;
        this.sequenceNumber = sequenceNumber;
        this.topicId = topicId;
    }

    public Object getChunkInfo() {
        return chunkInfo;
    }

    public void setChunkInfo(Object chunkInfo) {
        this.chunkInfo = chunkInfo;
    }

    public String getConsensusTimestamp() {
        return consensusTimestamp;
    }

    public void setConsensusTimestamp(String consensusTimestamp) {
        this.consensusTimestamp = consensusTimestamp;
    }

    public String getMessage() {
        return new String(Base64.getDecoder().decode(this.message));
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPayerAccountId() {
        return payerAccountId;
    }

    public void setPayerAccountId(String payerAccountId) {
        this.payerAccountId = payerAccountId;
    }

    public String getRunningHash() {
        return runningHash;
    }

    public void setRunningHash(String runningHash) {
        this.runningHash = runningHash;
    }

    public int getRunningHashVersion() {
        return runningHashVersion;
    }

    public void setRunningHashVersion(int runningHashVersion) {
        this.runningHashVersion = runningHashVersion;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "chunkInfo=" + chunkInfo +
                ", consensusTimestamp='" + consensusTimestamp + '\'' +
                ", message='" + (new String(Base64.getDecoder().decode(this.message))) + '\'' +
                ", payerAccountId='" + payerAccountId + '\'' +
                ", runningHash='" + runningHash + '\'' +
                ", runningHashVersion=" + runningHashVersion +
                ", sequenceNumber=" + sequenceNumber +
                ", topicId='" + topicId + '\'' +
                '}';
    }
}
