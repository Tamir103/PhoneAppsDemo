/**
 * Message object, three optional constructor
 * 1 - Default constructor only setting message time
 * 2 - Setting message content as a String, and message time
 * 3 - Setting message content as a String, boolean flagging if it is a message sent (true) or message received (false) , and message time
 */
package SmsApp;

import java.time.LocalDateTime;

public class Message {
    private String messageContent;
    private boolean isOutGoingMessage;
    private final LocalDateTime messageTime;

    public Message() {
        this.messageTime = LocalDateTime.now();
    }
    public Message(String messageContent) {
        this.messageContent = messageContent;
        this.messageTime = LocalDateTime.now();
    }
    public Message(String messageContent, boolean isOutGoingMessage) {
        this.messageContent = messageContent;
        this.isOutGoingMessage = isOutGoingMessage;
        this.messageTime = LocalDateTime.now();
    }

    void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
    void setOutGoingMessage(boolean isOutGoingMessage) {
        this.isOutGoingMessage = isOutGoingMessage;
    }
    String getMessageContent() {
        return messageContent;
    }
    LocalDateTime getMessageTime() {
        return messageTime;
    }
    boolean isOutGoingMessage() {
        return isOutGoingMessage;
    }
}
