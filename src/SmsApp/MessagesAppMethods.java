package SmsApp;

import Main.PhoneData;
import PhoneBookApp.Contact;

import java.util.ArrayList;
import java.util.HashMap;


public class MessagesAppMethods {
    static PhoneData mPhoneData = PhoneData.getInstance();

    public HashMap<Integer, String> generateMessagesAppMenu() {
        HashMap<Integer, String> menu = new HashMap<>();
        menu.put(1, "Add message correspondence to contact");
        menu.put(2, "Delete contact message correspondence");
        menu.put(3, "Print all contact messages");
        menu.put(4, "Print all contacts in which their message correspondence contains a specific sentence");
        menu.put(5, "Print all contacts message correspondence");
        menu.put(6, "Exit");
        return menu;
    }

    /**
     * Finding MessageCorrespondence object by contact
     * @param contact - contact related to the correspondence
     * @return MessageCorrespondence object of contact
     */
    public MessageCorrespondence findCorrespondenceByContact(Contact contact) {
        for (MessageCorrespondence mc : mPhoneData.allSMS) {
            if (mc.getContact().equals(contact)) {
                return mc;
            }
        }
        return null;
    }
    public void deleteContactMessages(Contact c) {
        MessageCorrespondence correspondence = findCorrespondenceByContact(c);
        ArrayList<Message> list = correspondence.getCorrespondence();
        list.removeAll(list);
        mPhoneData.allSMS.remove(correspondence);
    }

    public void printContactMessages(Contact c) {
        for (MessageCorrespondence mc : mPhoneData.allSMS) {
            if (mc.getContact().equals(c)) {
                int index = mPhoneData.allSMS.indexOf(mc);
                System.out.println(mc.getContact().getFullName() + " Messages:");
                    for (int j = 0; j < mPhoneData.allSMS.get(index).getCorrespondence().size(); j++) {
                        System.out.println(mPhoneData.allSMS.get(index).getCorrespondence().get(j).toString());
                    }
                System.out.println();
                break;
            }
        }
    }
    public void printContactsContainingMessageSentence(String sentence) {
        ArrayList<Contact> listOfContacts = new ArrayList<>();
        for (MessageCorrespondence mc : mPhoneData.allSMS) {
            for (Message m : mc.getCorrespondence()) {
                if (m.getMessageContent().toLowerCase().contains(sentence.toLowerCase())) {
                    listOfContacts.add(mc.getContact());
                }
            }
        }
        if (!listOfContacts.isEmpty()) {
            System.out.println("Sentence is in all these contacts messages:");
            for (Contact c : listOfContacts) {
                System.out.println(c.getFullName());
            }
        }
        System.out.println();
    }
    public void printAllContactsMessages() {
        for (MessageCorrespondence mc : mPhoneData.allSMS) {
            System.out.println(mc.getContact().getFullName() + ": ");
            for (Message m : mc.getCorrespondence()) {
                System.out.println(m.toString());
            }
            System.out.println("-------------------------------");
        }
    }

    public ArrayList<Contact> getAllContactsFromCorrespondenceList(ArrayList<MessageCorrespondence> listOfCorrespondence) {
        ArrayList<Contact> list = new ArrayList<>();
        for (MessageCorrespondence mc : listOfCorrespondence) {
            list.add(mc.getContact());
        }
        return list;
    }

    public ArrayList<MessageCorrespondence> removeCorrespondenceDuplicatesByContacts(ArrayList<MessageCorrespondence> listOfCorrespondence) {
        ArrayList<Contact> listOfContacts = getAllContactsFromCorrespondenceList(listOfCorrespondence);
        for (Contact c : listOfContacts) {
            for (MessageCorrespondence mc : listOfCorrespondence) {
                if (c.equals(mc.getContact())) {
                    listOfCorrespondence.remove(mc);
                }
            }
        }
        return listOfCorrespondence;
    }
}
