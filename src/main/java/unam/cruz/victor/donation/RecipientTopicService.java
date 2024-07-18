package unam.cruz.victor.donation;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.TopicId;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;
import unam.cruz.victor.topic.TopicClient;
import unam.cruz.victor.topic.request.RecipientTopicRequest;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class RecipientTopicService {
    private static final TopicId TOPIC_ID = TopicId.fromString(Objects.requireNonNull(Dotenv.load().get("TOPIC_ID")));
    private static int sequenceNumber = 1;

    public static AccountId getOldestRecipient(OrganType organ, Set<String> recipients) {
        System.out.println("New organ to donate: " + organ);
        List<RecipientTopicRequest> jsonRequests = TopicClient.fetchRecipientTopicRequests(TOPIC_ID, sequenceNumber);
        System.out.println("Recipients in queue: " + jsonRequests.stream().filter(r -> isRecipientWaiting(organ, recipients, r)).toList());

        RecipientTopicRequest recipientTopicRequest = jsonRequests
                .stream().filter(r -> r.getOrgan().equals(organ.toString()) && isRecipientWaiting(organ, recipients, r))
                .findFirst()
                .orElse(null);
        if (recipientTopicRequest == null) {
            System.out.println("No recipient found!");
            return null;
        }
        System.out.println("Recipient (" + recipientTopicRequest.getAccountId() + ") requested: " + recipientTopicRequest.getOrgan() );

        recipients.add(generateRecipientTopicKey(organ, recipientTopicRequest)); // Avoid double spending

        return AccountId.fromString(recipientTopicRequest.getAccountId());
    }

    private static boolean isRecipientWaiting(OrganType organ, Set<String> recipients, RecipientTopicRequest r) {
        return !recipients.contains(generateRecipientTopicKey(organ, r));
    }

    private static @NotNull String generateRecipientTopicKey(OrganType organ, RecipientTopicRequest r) {
        return toMd5(organ + "|" + r.getAccountId());
    }

    private static String toMd5(String original) {
        return DigestUtils.md5Hex(original);
    }
}
