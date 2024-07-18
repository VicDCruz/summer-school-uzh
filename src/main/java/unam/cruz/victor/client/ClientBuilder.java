package unam.cruz.victor.client;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PrivateKey;

public class ClientBuilder {
    public static Client buildHederaClient(AccountId accountId, PrivateKey privateKey) {
        Client client = Client.forTestnet();

        //Set the account as the client's operator
        client.setOperator(accountId, privateKey);
        client.setDefaultMaxTransactionFee(new Hbar(100));
        client.setMaxQueryPayment(new Hbar(50));

        return client;
    }
}
