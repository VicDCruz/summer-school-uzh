package unam.cruz.victor;

import com.hedera.hashgraph.sdk.*;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class HederaExamples {
    public static void main(String[] args) throws Exception {
        AccountId myAccountId = AccountId.fromString(Dotenv.load().get("MY_ACCOUNT_ID"));
        PrivateKey myPrivateKey = PrivateKey.fromString(Dotenv.load().get("MY_PRIVATE_KEY"));

        AccountId newAccountId = AccountBuilder.createAccount();

//        TransferService.transferHbar(myAccountId, newAccountId, 1000);
//        QueryService.getQueryCost(newAccountId);
//        QueryService.getAccountBalance(newAccountId);

        // ========================================================
        TokenCredentialAccount treasuryTokenAccount = AccountBuilder.createTokenAccount();
        TokenCredentialAccount supplyTokenAccount = AccountBuilder.createTokenAccount();
        FungibleTokenService.createToken(treasuryTokenAccount, supplyTokenAccount);

        // TOKEN ASSOCIATION WITH ALICE's ACCOUNT
        TokenAssociateTransaction associateAliceTx = new TokenAssociateTransaction()
                .setAccountId(Objects.requireNonNull(aliceAccountId))
                .setTokenIds(Collections.singletonList(tokenId))
                .freezeWith(client)
                .sign(aliceKey);

        //Submit the transaction
        TransactionResponse associateAliceTxSubmit = associateAliceTx.execute(client);

        printTransactionReceipt(associateAliceTxSubmit, client);
    }

    private static void printTransactionReceipt(TransactionResponse associateAliceTxSubmit, Client client) throws TimeoutException, PrecheckStatusException, ReceiptStatusException {
        //Get the receipt of the transaction
        TransactionReceipt associateAliceRx = associateAliceTxSubmit.getReceipt(client);
        System.out.println(associateAliceRx);
    }
}
