package unam.cruz.victor.topic;

import com.hedera.hashgraph.sdk.TopicId;

public class HederaTopicExample {
    public static void main(String[] args) throws Exception {
        TopicId topicId = TopicService.createTopic();

        // Wait 5 seconds between consensus topic creation and subscription creation
        System.out.println("Waiting topic: " + topicId + " for 5 seconds");
        Thread.sleep(5000);

        TopicService.subscribe(topicId);

        TopicService.sendMessage(topicId, "Hello World!");

        // Prevent the main thread from exiting so the topic message can be returned and printed to the console
        Thread.sleep(30000);
    }
}
