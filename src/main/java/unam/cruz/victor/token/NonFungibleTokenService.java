package unam.cruz.victor.token;

import com.hedera.hashgraph.sdk.*;
import unam.cruz.victor.client.ClientSingleton;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class NonFungibleTokenService {

    public static final int MAX_TRANSACTION_FEE = 20;

    public static TokenId createToken(TokenCredentialAccount treasury, TokenCredentialAccount supplier) throws ReceiptStatusException, PrecheckStatusException, TimeoutException {
        Client client = ClientSingleton.getInstance().getClient();
        TokenCreateTransaction nftCreate = new TokenCreateTransaction()
                .setTokenName("OrganDonation")
                .setTokenSymbol("OrgID")
                .setTokenType(TokenType.NON_FUNGIBLE_UNIQUE)
                .setDecimals(0)
                .setInitialSupply(0)
                .setTreasuryAccountId(treasury.getAccountId())
                .setSupplyType(TokenSupplyType.FINITE)
                .setMaxSupply(250)
                .setSupplyKey(supplier.getKey())
                .freezeWith(client);
        return submitTokenCreation(nftCreate, treasury.getKey());
    }

    private static TokenId submitTokenCreation(TokenCreateTransaction tokenCreateTransaction, PrivateKey signingKey) throws PrecheckStatusException, TimeoutException, ReceiptStatusException {
        System.out.println("Submitting token...");
        Client client = ClientSingleton.getInstance().getClient();
        TokenCreateTransaction tokenCreateSign = tokenCreateTransaction.sign(signingKey);

        TransactionResponse tokenCreateSubmit = tokenCreateSign.execute(client);
        TransactionReceipt tokenCreateRx = tokenCreateSubmit.getReceipt(client);

        printTokenId(tokenCreateRx);

        return tokenCreateRx.tokenId;
    }

    private static void printTokenId(TransactionReceipt tokenCreateRx) {
        System.out.println("Created token with ID: " + tokenCreateRx.tokenId);
    }

    public static void associateToken(TokenCredentialAccount newOwner, TokenId tokenId) throws PrecheckStatusException, TimeoutException, ReceiptStatusException {
        Client client = ClientSingleton.getInstance().getClient();
        TokenAssociateTransaction associateAliceTx = new TokenAssociateTransaction()
                .setAccountId(Objects.requireNonNull(newOwner.getAccountId()))
                .setTokenIds(Collections.singletonList(tokenId))
                .freezeWith(client)
                .sign(newOwner.getKey());

        TransactionResponse associateAliceTxSubmit = associateAliceTx.execute(client);
        TransactionReceipt associateAliceRx = associateAliceTxSubmit.getReceipt(client);

        printTokenAssociationStatus(associateAliceRx);
    }

    private static void printTokenAssociationStatus(TransactionReceipt associateAliceRx) {
        System.out.println("Token association with Alice's account: " + associateAliceRx.status);
    }

    public static void mintToken(TokenId nftTokenId, String ipfsCid, TokenCredentialAccount newOwner, TokenCredentialAccount supplier) throws PrecheckStatusException, TimeoutException, ReceiptStatusException {
        Client client = ClientSingleton.getInstance().getClient();

        TokenMintTransaction mintTx = new TokenMintTransaction()
                .setTokenId(nftTokenId)
                .setMaxTransactionFee(new Hbar(MAX_TRANSACTION_FEE));
        mintTx.addMetadata(("ipfs://" + ipfsCid + "/metadata.json").getBytes());
        mintTx.freezeWith(client);

        mintAndAssociateNFT(nftTokenId, mintTx, newOwner, supplier);

    }

    private static void mintAndAssociateNFT(TokenId nftTokenId, TokenMintTransaction mintTx, TokenCredentialAccount newOwner, TokenCredentialAccount supplier) throws TimeoutException, PrecheckStatusException, ReceiptStatusException {
        Client client = ClientSingleton.getInstance().getClient();

        TokenMintTransaction mintTxSign = mintTx.sign(supplier.getKey());
        TransactionResponse mintTxSubmit = mintTxSign.execute(client);
        TransactionReceipt mintRx = mintTxSubmit.getReceipt(client);
        System.out.println("Created NFT " + nftTokenId + " with serial: " + mintRx.serials);

        TokenAssociateTransaction associateAliceTx = new TokenAssociateTransaction()
                .setAccountId(newOwner.getAccountId())
                .setTokenIds(Collections.singletonList(nftTokenId))
                .freezeWith(client)
                .sign(newOwner.getKey());

        TransactionResponse associateAliceTxSubmit = associateAliceTx.execute(client);
        TransactionReceipt associateAliceRx = associateAliceTxSubmit.getReceipt(client);

        System.out.println("\nNFT association with account " + newOwner.getAccountId() + ": " + associateAliceRx.status);
    }
}
