package Main;

import PhoneBookApp.Contact;
import PhoneBookApp.PhoneBookAppMethods;
import SmsApp.MessageCorrespondence;
import SmsApp.MessagesAppMethods;

import java.util.ArrayList;
import java.util.Scanner;

public class PhoneData {

    private static PhoneData mPhoneData;
    public ArrayList<Contact> contactsList;
    public ArrayList<MessageCorrespondence> allSMS;
//    public PhoneBookAppMethods phoneBook;
//    public MessagesAppMethods messagesApp;
    public Scanner scan;
    public final String contactsFileName;
    public final String messagesFileName;

    private PhoneData() {
        contactsList = new ArrayList<>();
        allSMS = new ArrayList<>();
//        phoneBook = new PhoneBookAppMethods();
//        messagesApp = new MessagesAppMethods();
        scan = new Scanner(System.in);
        contactsFileName = "Contacts.bin";
        messagesFileName = "allMessages.bin";
    }

    public static PhoneData getInstance() {
        if (mPhoneData == null) {
            mPhoneData = new PhoneData();
            return mPhoneData;
        }
        return mPhoneData;
    }

}
