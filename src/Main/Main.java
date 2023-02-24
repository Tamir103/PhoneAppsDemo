package Main;

import PhoneBookApp.PhoneBookUtils.Contact;
import PhoneBookApp.PhoneBookUtils.PhoneBookAppMethods;
import PhoneBookApp.PhoneBookUtils.myPhoneBook;
import PhoneBookApp.PhoneBookUtils.Contact;

import java.util.ArrayList;
import java.util.Map;

public class Main {
    static ArrayList<Contact> validationList;
    static PhoneBookAppMethods phoneBook = new PhoneBookAppMethods();

    public static void main(String[] args) throws InterruptedException {

        validationList = new ArrayList<>();
        boolean exit = false;
        myPhoneBook.printTextsFromMap("welcome");
        while (!exit) {
            phoneBook.printMenu();
            int menuInput = phoneBook.menuChoice();
            if (menuInput != 0) {
                menuInput = isActionPossibleOnEmptyList(menuInput);
            }
            switch (menuInput) {
                case 1 -> addContactManager();
                case 2 -> removeContactManager();
                case 3 -> phoneBook.printPhoneBook(myPhoneBook.contactsList);
                case 4 -> findContactManager();
                case 5 -> {
                    myPhoneBook.contactsList = phoneBook.sortByNameAlphabetically(myPhoneBook.contactsList);
                    phoneBook.printPhoneBook(myPhoneBook.contactsList);
                }
                case 6 -> {
                    myPhoneBook.contactsList = phoneBook.sortByPhoneBigToSmall(myPhoneBook.contactsList);
                    phoneBook.printPhoneBook(myPhoneBook.contactsList);
                }
                case 7 -> reverseSortingManager();
                case 8 -> removeDuplicatesManager();
                case 9 -> phoneBook.exportPhoneBook(myPhoneBook.contactsList);
                case 10 -> {
                    myPhoneBook.contactsList = phoneBook.importPhoneBook();
                    if (myPhoneBook.contactsList.size() > 0) {
                        myPhoneBook.printTextsFromMap("actionSuccess");
                    }
                }
                case 11 -> exit = true;
            }
            if (exit) {
                myPhoneBook.printTextsFromMap("exit");
            } else {
                Thread.sleep(2000);
                System.out.println();
                myPhoneBook.printTextsFromMap("moreAction");
            }
        }
    }

    static int isActionPossibleOnEmptyList(int menuInput) {
        if (myPhoneBook.contactsList.isEmpty() && !(menuInput == 1 || menuInput == 10 || menuInput == 11)) {
            PhoneBookAppMethods.printErrorMessages(10);
            System.err.println("Only adding (1), uploading (10) or exit (11) are possible");
            System.out.println();
            return  0;
        } else {
            return menuInput;
        }
    }
    // 1
    static void addContactManager() {
        Contact contact = phoneBook.createContact();
        int listSizeBeforeAdd = myPhoneBook.contactsList.size();
        if (contact != null) {
            myPhoneBook.contactsList = phoneBook.addContact(contact, myPhoneBook.contactsList);
        }
        if (myPhoneBook.contactsList.size() == (listSizeBeforeAdd + 1)) {
            myPhoneBook.printTextsFromMap("actionSuccess");
        }
    }
    // 2
    static void removeContactManager() {
            int listSize = myPhoneBook.contactsList.size();
            for (int i = 0; i < 3; i++) {
                myPhoneBook.printTextsFromMap("removeContact");
                String nameOrPhone = myPhoneBook.scan.nextLine();
                validationList = phoneBook.findContact(myPhoneBook.contactsList, nameOrPhone);
                if (!validationList.isEmpty()) {
                    int yORn = phoneBook.enterAndValidateYorN("removeAll");
                    if (yORn == 1) {
                        myPhoneBook.contactsList = phoneBook.removeContact(myPhoneBook.contactsList, nameOrPhone, true);
                        if (PhoneBookAppMethods.isListsSizesEquals(listSize, myPhoneBook.contactsList.size()) && i != 2) {
                            PhoneBookAppMethods.printErrorMessages(8);
                        } else {
                            myPhoneBook.printTextsFromMap("actionSuccess");
                            break;
                        }
                    } else if (yORn == 0) {
                        myPhoneBook.contactsList = phoneBook.removeContact(myPhoneBook.contactsList, nameOrPhone, false);
                        if (PhoneBookAppMethods.isListsSizesEquals(listSize, myPhoneBook.contactsList.size())) {
                            PhoneBookAppMethods.printErrorMessages(8);
                        } else {
                            myPhoneBook.printTextsFromMap("actionSuccess");
                            break;
                        }
                    } else if (yORn == 2 && i == 2) {
                        PhoneBookAppMethods.printErrorMessages(8);
                    } else {
                        i = 3;
                    }
                } else if (i == 2) {
                    PhoneBookAppMethods.printErrorMessages(7);
                } else {
                    PhoneBookAppMethods.printErrorMessages(8);
                }
            }
            validationList.removeAll(validationList);
    }

    // 4
    static void findContactManager() {
        for (int i = 0; i < 3; i++) {
            myPhoneBook.printTextsFromMap("enterContactName");
            String name = myPhoneBook.scan.nextLine();
            if (phoneBook.isOnlyEnglishLetters(name)) {
                ArrayList<Contact> contactsFound = phoneBook.findContact(myPhoneBook.contactsList, name);
                if (!contactsFound.isEmpty()) {
                    phoneBook.printPhoneBook(contactsFound);
                    break;
                } else {
                    PhoneBookAppMethods.printErrorMessages(8);
                    PhoneBookAppMethods.printErrorMessages(PhoneBookAppMethods.calculateMessageIndex(i, true, true, true));
                }
            } else {
                PhoneBookAppMethods.printErrorMessages(PhoneBookAppMethods.calculateMessageIndex(i, true, true, true));
            }
        }
    }

    // 7
    static void reverseSortingManager() {
        System.out.println("BEFORE SORTING: ");
        phoneBook.printPhoneBook(myPhoneBook.contactsList);
        myPhoneBook.contactsList = phoneBook.sortByNameReverse(myPhoneBook.contactsList);
        System.out.println("AFTER SORTING: ");
        phoneBook.printPhoneBook(myPhoneBook.contactsList);
    }

    // 8
    static void removeDuplicatesManager() {
        ArrayList<Contact> duplicatesList = myPhoneBook.findDuplicates(myPhoneBook.contactsList);
        myPhoneBook.contactsList = phoneBook.removeDuplicates(myPhoneBook.contactsList);
        if (!duplicatesList.isEmpty()) {
            System.out.println("DUPLICATES REMOVED: ");
            phoneBook.printPhoneBook(duplicatesList);
        } else {
            System.out.println("No Duplicates Found");
        }
    }

    public static boolean isItemNumValid(int num, Map map) {
        return num <= map.size() && num > 0;
    }


}