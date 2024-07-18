package unam.cruz.victor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hedera.hashgraph.sdk.PrecheckStatusException;
import com.hedera.hashgraph.sdk.TopicId;
import io.github.cdimascio.dotenv.Dotenv;
import unam.cruz.victor.account.AccountCredential;
import unam.cruz.victor.account.AccountsPreloaded;
import unam.cruz.victor.donation.OrganType;
import unam.cruz.victor.topic.TopicService;
import unam.cruz.victor.topic.request.RecipientTopicRequest;
import unam.cruz.victor.utils.JsonMapper;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class RecipientUnifyIdea {
    private static final TopicId TOPIC_ID = TopicId.fromString(Objects.requireNonNull(Dotenv.load().get("TOPIC_ID")));

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("******** PROCESSING NEW BATCH OF RECIPIENTS ********");
            List<AccountCredential> recipients = AccountsPreloaded.getAccounts().stream().map(a -> new AccountCredential(a.getAccountId(), a.getKey())).toList();
            sendMessageToRecipients(recipients);

            System.out.println("Press any key to repeat or type 'exit' to quit...");
        } while (!scanner.nextLine().equals("exit"));

        scanner.close();
    }

    public static void sendMessageToRecipients(List<AccountCredential> recipients) throws PrecheckStatusException, TimeoutException, JsonProcessingException {
        for (AccountCredential credential : recipients)
            TopicService.sendMessage(TOPIC_ID, JsonMapper.stringify(new RecipientTopicRequest(credential.accountId.toString(), randomEnum(OrganType.class).toString())));
    }

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = new Random().nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}
