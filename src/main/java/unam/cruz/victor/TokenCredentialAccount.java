package unam.cruz.victor;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.PrivateKey;

public class TokenCredentialAccount {
    private PrivateKey key;
    private AccountId accountId;

    public TokenCredentialAccount(PrivateKey key, AccountId accountId) {
        this.key = key;
        this.accountId = accountId;
    }

    public PrivateKey getKey() {
        return key;
    }

    public AccountId getAccountId() {
        return accountId;
    }
}
