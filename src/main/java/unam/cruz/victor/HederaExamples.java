package unam.cruz.victor;

import com.hedera.hashgraph.sdk.*;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class HederaExamples {
    public static void main(String[] args) throws Exception {

        //AccountId myAccountId = AccountId.fromString(Dotenv.load().get("MY_ACCOUNT_ID"));
        //PrivateKey myPrivateKey = PrivateKey.fromString(Dotenv.load().get("MY_PRIVATE_KEY"));

        // Create Hedera testnet client
        //Client client = Client.forTestnet();
        //client.setOperator(myAccountId, myPrivateKey);

        // Treasury Key
        PrivateKey treasuryKey = PrivateKey.generateED25519();
        PublicKey treasuryPublicKey = treasuryKey.getPublicKey();
         

        // step 1 : create list of people - donors and receivers
        AccountId newAccountId = AccountBuilder.createAccount();

        // step 2 : NFTs logic
        

//        TransferService.transferHbar(myAccountId, newAccountId, 1000);
//        QueryService.getQueryCost(newAccountId);
//        QueryService.getAccountBalance(newAccountId);

        TokenCredentialAccount treasuryTokenAccount = AccountBuilder.createTokenAccount();
        TokenCredentialAccount supplyTokenAccount = AccountBuilder.createTokenAccount();
        TokenId newTokenId = FungibleTokenService.createToken(treasuryTokenAccount, supplyTokenAccount);

        TokenCredentialAccount aliceTokenAccount = AccountBuilder.createTokenAccount();
        FungibleTokenService.associateToken(aliceTokenAccount, newTokenId);
    }
}
