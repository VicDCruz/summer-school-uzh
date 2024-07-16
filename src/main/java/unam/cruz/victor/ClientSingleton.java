package unam.cruz.victor;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PrivateKey;
import io.github.cdimascio.dotenv.Dotenv;

public class ClientSingleton {
    private Client client;

    public Client getClient() {
        return client;
    }

    private static ClientSingleton instance;
    private ClientSingleton() {
        //Grab your Hedera Testnet account ID and private key
        AccountId myAccountId = AccountId.fromString(Dotenv.load().get("MY_ACCOUNT_ID"));
        PrivateKey myPrivateKey = PrivateKey.fromString(Dotenv.load().get("MY_PRIVATE_KEY"));

        this.client = buildHederaClient(myAccountId, myPrivateKey);
    }

    public static ClientSingleton getInstance() {
        if (instance == null) instance = new ClientSingleton();
        return instance;
    }

    private static Client buildHederaClient(AccountId accountId, PrivateKey privateKey) {
        Client client = Client.forTestnet();

        //Set the account as the client's operator
        client.setOperator(accountId, privateKey);
        client.setDefaultMaxTransactionFee(new Hbar(100));
        client.setMaxQueryPayment(new Hbar(50));

        return client;
    }
}
