package unam.cruz.victor;

import unam.cruz.victor.account.AccountBuilder;
import unam.cruz.victor.topic.TopicService;

public class WholeCredentialsCreator {
    public static void main(String[] args) throws Exception {
        TopicService.createTopic();
//        for (int i = 0; i < 3; i++) System.out.println(AccountBuilder.createAccount());
    }
}
