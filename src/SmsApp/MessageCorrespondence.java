package SmsApp;

import PhoneBookApp.PhoneBookUtils.Contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MessageCorrespondence extends Message {

    private Contact contact;
    ArrayList<Message> Correspondence;

//    HashMap<String, ArrayList<Message>> CorrespondenceMap;

    //TODO find contact in phone book
/*    public MessageCorrespondence(String contactName, String messageContent, boolean isOutGoingMessage) {
        super(messageContent);
        this.contact = new Contact();
        this.Correspondence = new ArrayList<>();
//        this.CorrespondenceMap = new HashMap<>();
    }*/
    public MessageCorrespondence(/*String contactName*/) {
        super();
//        this.contact = new Contact();
        this.Correspondence = new ArrayList<>();
    }

    void sendMessage(String messageSent) {
        Message sentMessage = new Message(messageSent/*, true*/);
        sentMessage.setOutGoingMessage(true);
        Correspondence.add(sentMessage);
//        CorrespondenceMap.put("Me", Correspondence);
    }
    void receiveMessage(String messageReceived) {
        Message receivedMessage = new Message(messageReceived/*, false*/);
        receivedMessage.setOutGoingMessage(false);
        Correspondence.add(receivedMessage);
//        CorrespondenceMap.put(contact.getFullName(), Correspondence);
    }

    void printMessages() {
//        Correspondence.stream().sorted();
        for (Message message: Correspondence) {
            if (message.isOutGoingMessage()) {
                System.out.println(message.getMessageTime() + " - " + contact.getFullName() + " - " + message.getMessageContent());
            } else {
                System.out.println(message.getMessageTime() + " - " + "Me" + " - " + message.getMessageContent());
            }
        }
//        Iterator iterator = CorrespondenceMap.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry pairs = (Map.Entry)iterator.next();
//
//            if(pairs.getKey().equals("Me"))
//            {
//              //  CorrespondenceMap.put(pairs.getKey(), pairs.getValue().add(18));
//                CorrespondenceMap.get(pairs.getKey(), Correspondence);
//            }
//
//            else if(!map.containsKey("mango"))
//            {
//                List<Integer> ints = new ArrayList<Integer>();
//                ints.add(18);
//                map.put("mango",ints);
//            }
//
//            it.remove(); // avoids a ConcurrentModificationException
//        }
    }
}
