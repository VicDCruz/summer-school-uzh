package unam.cruz.victor;

import com.hedera.hashgraph.sdk.*;

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
        return submitTokenCreation(new TokenCreateTransaction(), supplier.getKey());
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
}
