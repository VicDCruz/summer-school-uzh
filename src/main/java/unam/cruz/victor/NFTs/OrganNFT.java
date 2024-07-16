package unam.cruz.victor.NFTs;

import com.hedera.hashgraph.sdk.*;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.*;
import java.util.concurrent.TimeoutException;

public class OrganNFT {

    private final Client client;
    private final PrivateKey myPrivateKey;
    private final AccountId myAccountId;
    private final int MAX_TRANSACTION_FEE = 20; // Max transaction fee in Hbars

    public OrganNFT() {
        // Load environment variables
        Dotenv dotenv = Dotenv.load();

        // Grab Hedera testnet account ID and private key
        myAccountId = AccountId.fromString(dotenv.get("MY_ACCOUNT_ID"));
        myPrivateKey = PrivateKey.fromStringDER(dotenv.get("MY_PRIVATE_KEY"));

        // Create Hedera testnet client
        client = Client.forTestnet();
        client.setOperator(myAccountId, myPrivateKey);
    }

    public AccountId createAccount(PrivateKey key, long initialBalance) throws TimeoutException, PrecheckStatusException, ReceiptStatusException {
        TransactionResponse accountResponse = new AccountCreateTransaction()
                .setKey(key.getPublicKey())
                .setInitialBalance(new Hbar(initialBalance))
                .execute(client);

        return accountResponse.getReceipt(client).accountId;
    }

    public TokenId createNFT(PrivateKey treasuryKey, AccountId treasuryId, PrivateKey supplyKey, String tokenName, String tokenSymbol, int maxSupply)
            throws TimeoutException, PrecheckStatusException, ReceiptStatusException {

        TokenCreateTransaction nftCreate = new TokenCreateTransaction()
                .setTokenName(tokenName)
                .setTokenSymbol(tokenSymbol)
                .setTokenType(TokenType.NON_FUNGIBLE_UNIQUE)
                .setDecimals(0)
                .setInitialSupply(0)
                .setTreasuryAccountId(treasuryId)
                .setSupplyType(TokenSupplyType.FINITE)
                .setMaxSupply(maxSupply)
                .setSupplyKey(supplyKey)
                .freezeWith(client);

        TokenCreateTransaction nftCreateTxSign = nftCreate.sign(treasuryKey);
        TransactionResponse nftCreateSubmit = nftCreateTxSign.execute(client);
        TransactionReceipt nftCreateRx = nftCreateSubmit.getReceipt(client);

        return nftCreateRx.tokenId;
    }

    public void mintNFT(TokenId tokenId, PrivateKey supplyKey, List<String> metadataCIDs)
            throws TimeoutException, PrecheckStatusException, ReceiptStatusException {

        TokenMintTransaction mintTx = new TokenMintTransaction().setTokenId(tokenId)
                .setMaxTransactionFee(new Hbar(MAX_TRANSACTION_FEE));

        for (String cid : metadataCIDs) {
            mintTx.addMetadata(cid.getBytes());
        }

        mintTx.freezeWith(client);
        TokenMintTransaction mintTxSign = mintTx.sign(supplyKey);
        TransactionResponse mintTxSubmit = mintTxSign.execute(client);
        TransactionReceipt mintRx = mintTxSubmit.getReceipt(client);

        System.out.println("Created NFT " + tokenId + " with serial: " + mintRx.serials);
    }

    public void associateNFT(AccountId accountId, PrivateKey accountKey, TokenId tokenId)
            throws TimeoutException, PrecheckStatusException, ReceiptStatusException {

        TokenAssociateTransaction associateTx = new TokenAssociateTransaction()
                .setAccountId(accountId)
                .setTokenIds(Collections.singletonList(tokenId))
                .freezeWith(client)
                .sign(accountKey);

        TransactionResponse associateTxSubmit = associateTx.execute(client);
        TransactionReceipt associateRx = associateTxSubmit.getReceipt(client);

        System.out.println("\nNFT association with account " + accountId + ": " + associateRx.status + " ✅");
    }

    public void transferNFT(NftId nftId, AccountId fromAccountId, AccountId toAccountId, PrivateKey fromKey)
            throws TimeoutException, PrecheckStatusException, ReceiptStatusException {

        TransferTransaction transferTx = new TransferTransaction()
                .addNftTransfer(nftId, fromAccountId, toAccountId)
                .freezeWith(client)
                .sign(fromKey);

        TransactionResponse transferSubmit = transferTx.execute(client);
        TransactionReceipt transferRx = transferSubmit.getReceipt(client);

        System.out.println("\nNFT transfer from " + fromAccountId + " to " + toAccountId + ": " + transferRx.status + " ✅");
    }

    public AccountBalance checkBalance(AccountId accountId) throws TimeoutException, PrecheckStatusException {
        return new AccountBalanceQuery().setAccountId(accountId).execute(client);
    }

}
