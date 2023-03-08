package SmsApp;

import PhoneBookApp.Contact;

import java.io.*;
import java.util.ArrayList;


public class MessageCorrespondence extends Message implements Serializable {
    private Contact contact;
    private ArrayList<Message> Correspondence;

    public MessageCorrespondence(Contact contact) {
//        super();
        this.contact = contact;
        this.Correspondence = new ArrayList<>();
    }

    public MessageCorrespondence() {

    }
    public void setContact(Contact contact) {
        this.contact = contact;
    }
    public Contact getContact() {
        return contact;
    }

    public void setCorrespondence(ArrayList<Message> listOfMessages) {
        this.Correspondence = listOfMessages;
    }
    public ArrayList<Message> getCorrespondence() {
        return Correspondence;
    }

    public void sendMessage(String messageSent) {
        Message sentMessage = new Message(messageSent/*, true*/);
        sentMessage.setOutGoingMessage(true);
        Correspondence.add(sentMessage);
    }

    void receiveMessage(String messageReceived) {
        Message receivedMessage = new Message(messageReceived/*, false*/);
        receivedMessage.setOutGoingMessage(false);
        Correspondence.add(receivedMessage);
    }

    void printMessages() {
        for (Message message : Correspondence) {
            if (!message.isOutGoingMessage()) {
                System.out.println(message.getMessageTime() + " - " + contact.getFullName() + " - " + message.getMessageContent());
            } else if (message.isOutGoingMessage()) {
                System.out.println(message.getMessageTime() + " - " + "Me" + " - " + message.getMessageContent());
            } else {
                System.out.println(message.getMessageTime() + " - " + message.getMessageContent());
            }
        }
    }
}
