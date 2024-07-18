package unam.cruz.victor.account;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.PrivateKey;

public class AccountCredential {
    public AccountId accountId;
    public PrivateKey privateKey;

    public AccountCredential(AccountId accountId, PrivateKey privateKey) {
        this.accountId = accountId;
        this.privateKey = privateKey;
    }

    @Override
    public String toString() {
        return "AccountCredential{" +
                "accountId=" + accountId +
                ", privateKey=" + privateKey +
                '}';
    }
}
