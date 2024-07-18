package unam.cruz.victor.token;

import com.hedera.hashgraph.sdk.*;
import unam.cruz.victor.client.ClientSingleton;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class FungibleTokenService {
    public static TokenId createToken(TokenCredentialAccount treasury, TokenCredentialAccount supplier) throws ReceiptStatusException, PrecheckStatusException, TimeoutException {
        Client client = ClientSingleton.getInstance().getClient();
        TokenCreateTransaction tokenCreateTx = new TokenCreateTransaction()
                .setTokenName("USD Bar")
                .setTokenSymbol("USDB")
                .setTokenType(TokenType.FUNGIBLE_COMMON)
                .setDecimals(2)
                .setInitialSupply(10000)
                .setTreasuryAccountId(Objects.requireNonNull(treasury.getAccountId()))
                .setSupplyType(TokenSupplyType.INFINITE)
                .setSupplyKey(supplier.getKey())
                .freezeWith(client);
        return submitTokenCreation(tokenCreateTx, treasury.getKey());
    }

    private static TokenId submitTokenCreation(TokenCreateTransaction tokenCreateTransaction, PrivateKey signingKey) throws PrecheckStatusException, TimeoutException, ReceiptStatusException {
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
}
