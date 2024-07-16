package unam.cruz.victor;

import com.hedera.hashgraph.sdk.*;

import java.util.concurrent.TimeoutException;

public class AccountBuilder {
    public static AccountId createAccount() throws PrecheckStatusException, TimeoutException, ReceiptStatusException {
        Client client = ClientSingleton.getInstance().getClient();
        // Generate a new key pair
        PrivateKey newAccountPrivateKey = PrivateKey.generateED25519();
        PublicKey newAccountPublicKey = newAccountPrivateKey.getPublicKey();

        //Create new account and assign the public key
        TransactionResponse accountResponse = new AccountCreateTransaction()
                .setKey(newAccountPublicKey)
                .setInitialBalance(Hbar.fromTinybars(1000)) // Initial tokens in account
                .execute(client);

        printAccount(accountResponse, client);
        verifyAccount(accountResponse.getReceipt(client).accountId, client);
        return accountResponse.getReceipt(client).accountId;
    }

    private static void printAccount(TransactionResponse account, Client client) throws ReceiptStatusException, PrecheckStatusException, TimeoutException {
        System.out.println("New account ID is: " + account.getReceipt(client).accountId);
    }

    private static void verifyAccount(AccountId accountId, Client client) throws PrecheckStatusException, TimeoutException {
        AccountBalance accountBalance = new AccountBalanceQuery()
                .setAccountId(accountId)
                .execute(client);

        System.out.println("New account balance: " +accountBalance.hbars);
    }

    public static TokenCredentialAccount createTokenAccount() throws PrecheckStatusException, TimeoutException, ReceiptStatusException {
        Client client = ClientSingleton.getInstance().getClient();
        PrivateKey privateKey = PrivateKey.generate();
        PublicKey publicKey = privateKey.getPublicKey();

        TransactionResponse account = new AccountCreateTransaction()
                .setKey(publicKey)
                .setInitialBalance(new Hbar(5))
                .execute(client);

        printAccount(account, client);
        verifyAccount(account.getReceipt(client).accountId, client);
        return new TokenCredentialAccount(privateKey, account.getReceipt(client).accountId);
    }
}
