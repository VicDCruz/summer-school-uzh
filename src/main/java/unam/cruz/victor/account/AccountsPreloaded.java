package unam.cruz.victor.account;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.PrivateKey;
import unam.cruz.victor.token.TokenCredentialAccount;

import java.util.List;

// FOR TESTING PURPOSES ONLY
public class AccountsPreloaded {
    private static final List<TokenCredentialAccount> testAccounts = List.of(
            new TokenCredentialAccount(PrivateKey.fromString("305402010104204a6fd8cb25803f0e13be4911976673d0def869777e42cf7b591b642e0bd9d3caa00706052b8104000aa124032200035be9820e5044de08d2683d50f525cc9c980f3a8a817fc1d307939738dc272c4d"), AccountId.fromString("0.0.4573587")),
            new TokenCredentialAccount(PrivateKey.fromString("3054020101042090d8beba8876ff177d09590f6bffc7984662540055ce3fa4bb532f1ea7b55c64a00706052b8104000aa124032200029b4ef5185c17173451d91f37c8de1d98f90d2c80bccb8b834fafebe49d6ba8f9"), AccountId.fromString("0.0.4586994")),
            new TokenCredentialAccount(PrivateKey.fromString("30540201010420e8535f6921e237f1d3822b282c7519ee2aab42163c589b37c1390a71c889ad0fa00706052b8104000aa12403220003010fc6f2d8521690277c21933fb8480f40d2a7fe756ea25b74798dd80da4e279"), AccountId.fromString("0.0.4586995")),
            new TokenCredentialAccount(PrivateKey.fromString("305402010104202790ec6837c812e5f635574afa897b2545a4e0bfb7e96cbc6e933549f125872ba00706052b8104000aa12403220002de2f4270202d8ede9c20fea9f9b91799e135e28f762ade6b6e82b9c749cf7a2a"), AccountId.fromString("0.0.4586996")),
            new TokenCredentialAccount(PrivateKey.fromString("3054020101042080d4819cabc1b2eafd2d65a254f074460bb1020c37f64b4be19c88cd93c56f57a00706052b8104000aa12403220002e9e5fb9aa74c7649fe94c317ceb4deb878a0f3feaa1ab07ba81740db56aad846"), AccountId.fromString("0.0.4586997")),
            new TokenCredentialAccount(PrivateKey.fromString("305402010104205956419b43b6e287077d1776f386dcc3cea2ea054691fae91ab7d209a82a28bca00706052b8104000aa124032200027d7a5238479d0393c22862aa92bde2e554ef6c09492012830b98cef05bd92bbb"), AccountId.fromString("0.0.4587001"))
    );

    public static TokenCredentialAccount getAccountById(AccountId accountId) {
        return testAccounts.stream()
                .filter(account -> account.getAccountId().equals(accountId))
                .findFirst()
                .orElseThrow();
    }

    public static List<TokenCredentialAccount> getAccounts() {
        System.out.println(testAccounts.size() + " Accounts preloaded");
        return testAccounts;
    }

    private static final List<TokenCredentialAccount> testDonorAccounts = List.of(
            new TokenCredentialAccount(PrivateKey.fromString("30540201010420e7aecea9d07ffd856538eb7e887755d034bd8fa95071242bc2751a3b4bea5723a00706052b8104000aa12403220002bcc1a977136e098c025438f5e60c0dc8d97b9b4d9ac77b0d231cf57bc986d2ef"), AccountId.fromString("0.0.4588723")),
            new TokenCredentialAccount(PrivateKey.fromString("3054020101042085d100f60f434d0e1f085a72071bf237bf87c97a0c2c82f56cf16089e5586cdba00706052b8104000aa12403220003bc941af8675fd0c2ead788f9b089c5aad5801defa136c2550bb9d7c464370f7e"), AccountId.fromString("0.0.4588724")),
            new TokenCredentialAccount(PrivateKey.fromString("30540201010420bbc0cf401f68699e56cc1af8b275ea365f63f524fef7680b7f993a59f5cb8bada00706052b8104000aa12403220003e78fe2add91b08fee7644a2cd8dcc4580ae9f937f6f038156a0cbb7cfc6f3919"), AccountId.fromString("0.0.4588725"))
    );

    public static List<TokenCredentialAccount> getDonorAccounts() {
        System.out.println(testDonorAccounts.size() + " Donor Accounts preloaded");
        return testDonorAccounts;
    }
}
