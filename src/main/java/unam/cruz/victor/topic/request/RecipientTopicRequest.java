package unam.cruz.victor.topic.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import unam.cruz.victor.donation.OrganType;

public class RecipientTopicRequest {
    @JsonProperty("account_id")
    String accountId;
    @JsonProperty("organ")
    String organ;

    public RecipientTopicRequest() {
    }

    public RecipientTopicRequest(String accountId, String organ) {
        this.accountId = accountId;
        this.organ = organ;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    @Override
    public String toString() {
        return "RecipientTopicRequest{" +
                "accountId='" + accountId + '\'' +
                ", organ='" + organ + '\'' +
                '}';
    }
}
