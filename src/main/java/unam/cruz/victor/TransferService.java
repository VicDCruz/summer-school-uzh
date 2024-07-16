package unam.cruz.victor;

import com.hedera.hashgraph.sdk.*;

import java.util.concurrent.TimeoutException;

public class TransferService {
    public static void transferHbar(AccountId fromAccount, AccountId toAccount, long amount) throws PrecheckStatusException, TimeoutException, ReceiptStatusException {
        Client client = ClientSingleton.getInstance().getClient();
        TransactionResponse sendHbar = new TransferTransaction().addHbarTransfer(fromAccount, Hbar.fromTinybars(-1 * amount)) //Sending account
                .addHbarTransfer(toAccount, Hbar.fromTinybars(amount)) //Receiving account
                .execute(client);
        logTransferStatus(sendHbar);
    }

    private static void logTransferStatus(TransactionResponse sendHbar) throws TimeoutException, PrecheckStatusException, ReceiptStatusException {
        Client client = ClientSingleton.getInstance().getClient();
        System.out.println("The transfer transaction was: " + sendHbar.getReceipt(client).status);
    }
}
