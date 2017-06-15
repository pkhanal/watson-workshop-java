package io.pkhanal.github;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

/**
 * Watson Conversation Service
 *
 */
public class Conversation
{

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    private static Scanner scanner = new Scanner(System.in);

    public static void main( String[] args ) throws Exception
    {
        Properties prop = new Properties();
        prop.load(new FileInputStream("config.properties"));


        ConversationService service = new ConversationService(ConversationService.VERSION_DATE_2017_02_03);
        service.setUsernameAndPassword(
                prop.getProperty("CONVERSATION_USERNAME"),
                prop.getProperty("CONVERSATION_PASSWORD"));

        // conversation context
        Map<String, Object> ctx = new HashMap<>();

        MessageRequest messageRequest = createChatMessage("", ctx);
        do {
            MessageResponse response = service.message(
                    prop.getProperty("CONVERSATION_WORKSPACE_ID"),
                    messageRequest).execute();

            writeBotResponseToConsole(response.getTextConcatenated("\n"));

            messageRequest = createChatMessage(readUserInput(), response.getContext());
        } while (true);
    }


    private static void writeBotResponseToConsole(String  message) {
        System.out.print(ANSI_YELLOW + "Lisa: " + ANSI_RESET);
        System.out.print(ANSI_YELLOW + message + ANSI_RESET);
        System.out.println();
    }

    private static MessageRequest createChatMessage(String message, Map<String, Object> context) {
        return new MessageRequest.Builder().inputText(message).context(context).build();
    }

    private static String readUserInput() {
        System.out.print(ANSI_GREEN + "Me: " + ANSI_RESET);
        return scanner.nextLine();
    }
}
