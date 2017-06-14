package io.pkhanal.github;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Watson Conversation Service
 *
 */
public class Conversation
{
    public static void main( String[] args ) throws Exception
    {
        Properties prop = new Properties();
        prop.load(new FileInputStream("config.properties"));


        ConversationService service = new ConversationService(ConversationService.VERSION_DATE_2017_02_03);
        service.setUsernameAndPassword(prop.getProperty("CONVERSATION_USERNAME"), prop.getProperty("CONVERSATION_PASSWORD"));

        MessageRequest message = new MessageRequest.Builder().inputText("").build();
        MessageResponse response = service.message(prop.getProperty("CONVERSATION_WORKSPACE_ID"), message).execute();
        System.out.println(response);

        message = new MessageRequest.Builder().inputText("I would like to learn about Watson Services").build();
        response = service.message(prop.getProperty("CONVERSATION_WORKSPACE_ID"), message).execute();
        System.out.println(response);

    }
}
