package unam.cruz.victor.client;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PrivateKey;
import io.github.cdimascio.dotenv.Dotenv;

public class ClientSingleton {
    private final Client client;

    public Client getClient() {
        return client;
    }

    private static ClientSingleton instance;
    private ClientSingleton() {
        //Grab your Hedera Testnet account ID and private key
        AccountId myAccountId = AccountId.fromString(Dotenv.load().get("MY_ACCOUNT_ID"));
        PrivateKey myPrivateKey = PrivateKey.fromString(Dotenv.load().get("MY_PRIVATE_KEY"));

        this.client = ClientBuilder.buildHederaClient(myAccountId, myPrivateKey);
    }

    public static ClientSingleton getInstance() {
        if (instance == null) instance = new ClientSingleton();
        return instance;
    }
}
