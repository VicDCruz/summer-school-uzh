package unam.cruz.victor.topic;

import com.hedera.hashgraph.sdk.*;
import io.github.cdimascio.dotenv.Dotenv;
import unam.cruz.victor.client.ClientSingleton;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class TopicService {
    public static TopicId createTopic() throws PrecheckStatusException, TimeoutException, ReceiptStatusException {
        Client client = ClientSingleton.getInstance().getClient();
        PrivateKey myPrivateKey = PrivateKey.fromString(Dotenv.load().get("MY_PRIVATE_KEY"));
        // Create a new topic
        TransactionResponse txResponse = new TopicCreateTransaction()
                .setSubmitKey(myPrivateKey.getPublicKey())
                .execute(client);

        TransactionReceipt receipt = txResponse.getReceipt(client);
        TopicId topicId = receipt.topicId;

        showTopicId(topicId);
        return topicId;
    }

    private static void showTopicId(TopicId topicId) {
        System.out.println("Your topic ID is: https://testnet.hederaexplorer.io/search-details/topic/" + topicId);
    }

    public static void subscribe(TopicId topicId) throws PrecheckStatusException, TimeoutException {
        Client client = ClientSingleton.getInstance().getClient();

        new TopicMessageQuery()
                .setTopicId(Objects.requireNonNull(topicId))
                .subscribe(client, resp -> {
                    String messageAsString = new String(resp.contents, StandardCharsets.UTF_8);
                    System.out.println(resp.consensusTimestamp + " received topic message: " + messageAsString);
                });
        System.out.println("Subscribed to topic: " + topicId);
    }

    public static void sendMessage(TopicId topicId, String message) throws PrecheckStatusException, TimeoutException {
        Client client = ClientSingleton.getInstance().getClient();
        sendMessage(topicId, message, client);
    }

    public static void sendMessage(TopicId topicId, String message, Client client) throws PrecheckStatusException, TimeoutException {
        new TopicMessageSubmitTransaction()
                .setTopicId(topicId)
                .setMessage(message)
                .execute(client);
        System.out.println("(topicId: " + topicId + ") Message sent: " + message);
    }
}
