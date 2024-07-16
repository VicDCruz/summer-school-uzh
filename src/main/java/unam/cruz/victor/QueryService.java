package unam.cruz.victor;

import com.hedera.hashgraph.sdk.*;

import java.util.concurrent.TimeoutException;

public class QueryService {
    public static void getQueryCost(AccountId accountId) throws PrecheckStatusException, TimeoutException {
        Client client = ClientSingleton.getInstance().getClient();
        //Request the cost of the query
        Hbar queryCost = new AccountBalanceQuery()
                .setAccountId(accountId)
                .getCost(client);

        System.out.println("The cost of this query is: " + queryCost);
    }

    public static void getAccountBalance(AccountId accountId) throws PrecheckStatusException, TimeoutException {
        Client client = ClientSingleton.getInstance().getClient();
        //Check the new account's balance
        AccountBalance accountBalanceNew = new AccountBalanceQuery()
                .setAccountId(accountId)
                .execute(client);

        System.out.println("The account balance after the transfer: " +accountBalanceNew.hbars);
    }
}
