package unam.cruz.victor.topic;

import com.hedera.hashgraph.sdk.TopicId;
import org.jetbrains.annotations.NotNull;
import unam.cruz.victor.topic.request.RecipientTopicRequest;
import unam.cruz.victor.topic.response.Message;
import unam.cruz.victor.topic.response.TopicResponse;
import unam.cruz.victor.utils.JsonMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class TopicClient {
    public static void main(String[] args) {
        TopicId topicId = TopicId.fromString("0.0.4571352");
        List<Message> latestTopics = getLatestTopics(topicId);
        System.out.println(latestTopics);
    }

    public static List<Message> getLatestTopics(TopicId topic) {
        try {
            URL url = new URL("https://testnet.mirrornode.hedera.com/api/v1/topics/" + topic + "/messages?encoding=base64&order=asc");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/json");

            BufferedReader in;
            StringBuilder content = new StringBuilder();

            int status = connection.getResponseCode();
            if (status > 299) {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            connection.disconnect();

            List<Message> messages = JsonMapper.mapJsonToObject(content.toString(), TopicResponse.class).getMessages();
            System.out.println("Total messages: " + messages.size());
            return messages;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static @NotNull List<RecipientTopicRequest> fetchRecipientTopicRequests(TopicId TOPIC_ID, int sequenceNumber) {
        List<Message> recipientsRequests = TopicClient.getLatestTopics(TOPIC_ID);
        return Objects.requireNonNull(recipientsRequests)
                .stream().map(r -> JsonMapper.mapJsonToObject(r.getMessage(), RecipientTopicRequest.class))
                .toList();
    }
}
