package Main;

import PhoneBookApp.Contact;
import PhoneBookApp.PhoneBookAppMethods;
import SmsApp.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Main {

    static PhoneData mPhoneData = PhoneData.getInstance();
    static PhoneBookAppMethods phoneBook = new PhoneBookAppMethods();
    static MessagesAppMethods messagesApp = new MessagesAppMethods();
    static String runningApp;
    static boolean exit;
    static ArrayList<Contact> validationList;
    public static Contact currentContact;

    public static void main(String[] args) {

    mPhoneData.contactsList = (ArrayList<Contact>) ArrayListInput(mPhoneData.contactsFileName);
    mPhoneData.allSMS = (ArrayList<MessageCorrespondence>) ArrayListInput(mPhoneData.messagesFileName);

    mainPhoneMethod();

    }

    public static void mainPhoneMethod() {
        String mainMenu =  PhoneApps.EXIT.serialNumber + " - " + PhoneApps.EXIT +
                "\n" + PhoneApps.PHONEBOOK.serialNumber + " - " + PhoneApps.PHONEBOOK +
                "\n" + PhoneApps.MESSAGES.serialNumber + " - " + PhoneApps.MESSAGES +
                "\n" + PhoneApps.CALENDER.serialNumber + " - " + PhoneApps.CALENDER + " (Currently unavailable)";
        int counter = 0;
        while (!exit) {
            System.out.println("Welcome to your phone, which app would you like to activate");
            System.out.println(mainMenu);
            String input = mPhoneData.scan.nextLine().trim();
            if (input.equals(String.valueOf(PhoneApps.PHONEBOOK.serialNumber)) || input.equalsIgnoreCase(String.valueOf(PhoneApps.PHONEBOOK))) {
                runningApp = String.valueOf(PhoneApps.PHONEBOOK);
                removeDuplicatesManager(); //TODO sort this as required in the phoneBookApp
                runPhoneBookApp();
                if (exit) {break;}
            } else if (input.equals(String.valueOf(PhoneApps.MESSAGES.serialNumber)) || input.equalsIgnoreCase(String.valueOf(PhoneApps.MESSAGES))) {
                runningApp = String.valueOf(PhoneApps.MESSAGES);
                removeDuplicatesManager();
                runMessagesApp();
            } else if (input.equals(String.valueOf(PhoneApps.CALENDER.serialNumber)) || input.equalsIgnoreCase(String.valueOf(PhoneApps.CALENDER))) {
                System.out.println("As mentioned before, this app is currently unavailable");
                counter++;
                exit = endMain(counter);
            } else if (input.equals(String.valueOf(PhoneApps.EXIT.serialNumber)) || input.equalsIgnoreCase(String.valueOf(PhoneApps.EXIT))) {
                phoneBook.printTextsFromMap("exit");
                exit = true;
            } else {
                phoneBook.printTextsFromMap("invalidInputWarn");
                counter++;
                exit = endMain(counter);
            }
        }
    }

    static boolean endMain(int counter) {
        if (counter >= 3) {
            phoneBook.printTextsFromMap("inputErrMsg");
            phoneBook.printTextsFromMap("exit");
            return true;
        }
        return false;
    }
    static void runPhoneBookApp() {
        boolean phoneBookExit = false;
        validationList = new ArrayList<>();
        exit = false;
        phoneBook.printTextsFromMap("welcome");
        while (!phoneBookExit) {
            phoneBook.printMenu(phoneBook.menu);
            int menuInput = phoneBook.menuChoice();
            if (menuInput != 0) {
                menuInput = isActionPossibleOnEmptyList(menuInput);
            }
            switch (menuInput) {
                case 1 -> addContactManager();
                case 2 -> removeContactManager();
                case 3 -> phoneBook.printPhoneBook(mPhoneData.contactsList);
                case 4 -> findContactManager();
                case 5 -> {
                    mPhoneData.contactsList = phoneBook.sortByNameAlphabetically(mPhoneData.contactsList);
                    phoneBook.printPhoneBook(mPhoneData.contactsList);
                }
                case 6 -> {
                    mPhoneData.contactsList = phoneBook.sortByPhoneBigToSmall(mPhoneData.contactsList);
                    phoneBook.printPhoneBook(mPhoneData.contactsList);
                }
                case 7 -> reverseSortingManager();
                case 8 -> removeDuplicatesManager();
                case 9 -> phoneBook.exportPhoneBook(mPhoneData.contactsList);
                case 10 -> {
                    mPhoneData.contactsList = phoneBook.importPhoneBook();
                    if (mPhoneData.contactsList.size() > 0) {
                        phoneBook.printTextsFromMap("actionSuccess");
                    }
                }
                case 11 -> phoneBookExit = true;
            }
            exitMethod(phoneBookExit);
        }
        ArrayListOutput(mPhoneData.contactsList, mPhoneData.contactsFileName);
    }
    static void runMessagesApp() {
        MessageCorrespondence SMS = new MessageCorrespondence();
        HashMap<Integer, String> menu = messagesApp.generateMessagesAppMenu();
//        boolean messagesExit = findContactManager();
        boolean messagesExit = false;
        while (!messagesExit) {
            System.out.println("WELCOME TO MESSAGES APP");
            phoneBook.printMenu(menu);
            int menuInput = phoneBook.menuChoice();
            switch (menuInput) {
                case 1 -> {
                    messagesExit = findContactManager();  //TODO keep testing the new contact equals override method
                    SMS = messagesApp.findCorrespondenceByContact(currentContact);
                    System.out.println("Enter message:");
                    String messageInput = mPhoneData.scan.nextLine();
                    if (SMS == null) {
                        SMS = new MessageCorrespondence(currentContact);
                        SMS.sendMessage(messageInput);  // Using the constructor in which no I/O message flag
                        mPhoneData.allSMS.add(SMS);
                    } else {
                        int indexInAllSMS = mPhoneData.allSMS.indexOf(SMS);
                        SMS.sendMessage(messageInput);  // Using the constructor in which no I/O message flag
                        mPhoneData.allSMS.set(indexInAllSMS, SMS);
                    }


                }
                case 2 -> {
                    messagesExit = findContactManager();
                    if (mPhoneData.allSMS.isEmpty()) {
                        System.err.println("ERROR - Unable to delete messages, user does not have ny messages");
                    } else {
                        messagesApp.deleteContactMessages(currentContact);
                    }
                }
                case 3 -> {
                    messagesExit = findContactManager();
                    if (mPhoneData.allSMS.isEmpty()) {
                        System.err.println("No messages for this user");
                    } else {
                        messagesApp.printContactMessages(currentContact);
                    }
                }
                case 4 -> {
                    System.out.println("Enter the sentence you wish to search:");
                    String sentenceInput = mPhoneData.scan.nextLine();
                    if (sentenceInput.length() < 2) {
                        System.out.println("Sentence too short, needs to be at least 2 letters");
                        System.out.println();
                    } else {
                        messagesApp.printContactsContainingMessageSentence(sentenceInput);
                    }
                }
                case 5 -> messagesApp.printAllContactsMessages();
                case 6 -> {
                    messagesExit = true;
                    exitMethod(messagesExit);
                }
            }
        }

        ArrayListOutput(mPhoneData.allSMS, mPhoneData.messagesFileName);
    }
    static int isActionPossibleOnEmptyList(int menuInput) {
        try {
            if (mPhoneData.contactsList.isEmpty() && !(menuInput == 1 || menuInput == 10 || menuInput == 11)) {
                phoneBook.printErrorMessages(10);
                System.err.println("Only adding (1), uploading (10) or exit (11) are possible");
                System.out.println();
                return 0;
            } else {
                return menuInput;
            }
        } catch (NullPointerException npe) {
            return menuInput;
        }
    }
    // 1
    static void addContactManager() {
        Contact contact = phoneBook.createContact();
        int listSizeBeforeAdd = mPhoneData.contactsList.size();
        if (contact != null && phoneBook.findContact(mPhoneData.contactsList, contact.getFullName()).size() == 0) {
            mPhoneData.contactsList = phoneBook.addContact(contact, mPhoneData.contactsList);
        } else {
            System.err.println("ERROR - Unable to add contact - contact null or already exist in phone book");
        }
        if (mPhoneData.contactsList.size() == (listSizeBeforeAdd + 1)) {
            phoneBook.printTextsFromMap("actionSuccess");
        }
    }
    // 2
    static void removeContactManager() {
        int listSize = mPhoneData.contactsList.size();
        for (int i = 0; i < 3; i++) {
            phoneBook.printTextsFromMap("removeContact");
            String nameOrPhone = mPhoneData.scan.nextLine();
            validationList = phoneBook.findContact(mPhoneData.contactsList, nameOrPhone);
            if (!validationList.isEmpty()) {
                int yORn = phoneBook.enterAndValidateYorN("removeAll");
                if (yORn == 1) {
                    mPhoneData.contactsList = phoneBook.removeContact(mPhoneData.contactsList, nameOrPhone, true);
                    if (phoneBook.isListsSizesEquals(listSize, mPhoneData.contactsList.size()) && i != 2) {
                        phoneBook.printErrorMessages(8);
                    } else {
                        phoneBook.printTextsFromMap("actionSuccess");
                        break;
                    }
                } else if (yORn == 0) {
                    mPhoneData.contactsList = phoneBook.removeContact(mPhoneData.contactsList, nameOrPhone, false);
                    if (phoneBook.isListsSizesEquals(listSize, mPhoneData.contactsList.size())) {
                        phoneBook.printErrorMessages(8);
                    } else {
                        phoneBook.printTextsFromMap("actionSuccess");
                        break;
                    }
                } else if (yORn == 2 && i == 2) {
                    phoneBook.printErrorMessages(8);
                } else {
                    i = 3;
                }
            } else if (i == 2) {
                phoneBook.printErrorMessages(7);
            } else {
                phoneBook.printErrorMessages(8);
            }
        }
        validationList.removeAll(validationList);
    }

    // 4
    //TODO if there is time, change so that contacts will be found by partial names, add functionality and user inputs to support this
    public static boolean findContactManager() {
        for (int i = 0; i < 3; i++) {
            phoneBook.printTextsFromMap("enterContactName");
            String name = mPhoneData.scan.nextLine();
            if (phoneBook.isOnlyEnglishLetters(name)) {
                ArrayList<Contact> contactsFound = phoneBook.findContact(mPhoneData.contactsList, name);
                if (!contactsFound.isEmpty()) {
                    if (runningApp.equals(String.valueOf(PhoneApps.PHONEBOOK))) {
                        phoneBook.printPhoneBook(contactsFound);
                        return true;
                    } else if (runningApp.equals(String.valueOf(PhoneApps.MESSAGES))) {
                        currentContact = contactsFound.get(0);
                        return false;
                    }
                } else {
                    phoneBook.printErrorMessages(8);
                    phoneBook.printErrorMessages(phoneBook.calculateMessageIndex(i, true, true, true));
                }
            } else {
                phoneBook.printErrorMessages(phoneBook.calculateMessageIndex(i, true, true, true));
            }
        }
        return true;
    }

    // 7
    static void reverseSortingManager() {
        System.out.println("BEFORE SORTING: ");
        phoneBook.printPhoneBook(mPhoneData.contactsList);
        mPhoneData.contactsList = phoneBook.sortByNameReverse(mPhoneData.contactsList);
        System.out.println("AFTER SORTING: ");
        phoneBook.printPhoneBook(mPhoneData.contactsList);
    }

    // 8
    static void removeDuplicatesManager() {
        ArrayList<Contact> duplicatesList = phoneBook.findDuplicates(mPhoneData.contactsList);
        mPhoneData.contactsList = phoneBook.removeDuplicates(mPhoneData.contactsList);
        if (!duplicatesList.isEmpty() && runningApp.equals((String.valueOf(PhoneApps.PHONEBOOK)))) {
            System.out.println("DUPLICATES REMOVED: ");
            phoneBook.printPhoneBook(duplicatesList);
        } else if (runningApp.equals((String.valueOf(PhoneApps.PHONEBOOK)))) {
            System.out.println("No Duplicates Found");
        }
    }

    public static boolean isItemNumValid(int num, Map map) {
        return num <= map.size() && num > 0;
    }

    static void exitMethod(boolean exit) {
        try {
            if (exit) {
                phoneBook.printTextsFromMap("exit");
            } else {
                Thread.sleep(2000);
                System.out.println();
                phoneBook.printTextsFromMap("moreAction");
            }
        } catch (InterruptedException ie) {
            System.err.println("ERROR IN EXIT METHOD");
        }
    }

    static void ArrayListOutput(ArrayList<?> list, String fileName) { //TODO not updating the list on the file, fix it
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.close();
        } catch (IOException ioe) {
            System.err.println("EXCEPTION DURING LIST FILE OUTPUT");
        }
    }
    static ArrayList<?> ArrayListInput(String fileName) {
        ArrayList<?> list = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            list = (ArrayList<?>) ois.readObject();
            ois.close();
        }
        catch (IOException ioe) {
//            System.err.println("IO EXCEPTION IN ARRAYLIST INPUT METHOD");
            return list;
        }
        catch (ClassNotFoundException cnfe) {
//            System.out.println("ClassNotFoundException EXCEPTION IN ARRAYLIST INPUT METHOD");
            cnfe.printStackTrace();
            return list;
        }
        return list;
    }

}

