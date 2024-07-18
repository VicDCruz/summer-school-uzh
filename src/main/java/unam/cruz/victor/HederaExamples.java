package unam.cruz.victor;

import com.hedera.hashgraph.sdk.*;
import unam.cruz.victor.account.AccountBuilder;
import unam.cruz.victor.token.FungibleTokenService;
import unam.cruz.victor.token.NonFungibleTokenService;
import unam.cruz.victor.token.TokenCredentialAccount;

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
//        AccountId newAccountId = AccountBuilder.createAccountId();
//        transferAndQueryAccount(myAccountId, newAccountId);
//        createAndAssociateFungibleTokenAccounts();

        TokenCredentialAccount treasuryTokenAccount = AccountBuilder.createTokenAccount();
        TokenCredentialAccount supplyTokenAccount = AccountBuilder.createTokenAccount();
        TokenId newTokenId = NonFungibleTokenService.createToken(treasuryTokenAccount, supplyTokenAccount);
        TokenCredentialAccount aliceTokenAccount = AccountBuilder.createTokenAccount();
        NonFungibleTokenService.mintToken(newTokenId, "QmTzCmi63hPmj3heFhKXgZRGGwm8P3kygDN5KeMgyvzhoe", aliceTokenAccount, supplyTokenAccount);

        // step 2 : NFTs logic
        

//        TokenCredentialAccount aliceTokenAccount = AccountBuilder.createTokenAccount();
//        FungibleTokenService.associateToken(aliceTokenAccount, newTokenId);
    }

    private static void transferAndQueryAccount(AccountId myAccountId, AccountId newAccountId) throws PrecheckStatusException, TimeoutException, ReceiptStatusException {
        TransferService.transferHbar(myAccountId, newAccountId, 1000);
        QueryService.getQueryCost(newAccountId);
        QueryService.getAccountBalance(newAccountId);
    }

    private static void createAndAssociateFungibleTokenAccounts() throws PrecheckStatusException, TimeoutException, ReceiptStatusException {
        TokenCredentialAccount treasuryTokenAccount = AccountBuilder.createTokenAccount();
        TokenCredentialAccount supplyTokenAccount = AccountBuilder.createTokenAccount();
        TokenId newTokenId = FungibleTokenService.createToken(treasuryTokenAccount, supplyTokenAccount);

        TokenCredentialAccount aliceTokenAccount = AccountBuilder.createTokenAccount();
        FungibleTokenService.associateToken(aliceTokenAccount, newTokenId);
    }
}
