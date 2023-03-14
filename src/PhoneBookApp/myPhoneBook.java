package PhoneBookApp;

import Main.PhoneData;
import com.google.gson.Gson;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class myPhoneBook extends PhoneBookBlueprint {

    static PhoneData mPhoneData = PhoneData.getInstance();;
    public HashMap<Integer, String> menu;
    public static HashMap<String, String> textsMap = new HashMap<>();
    static ContactInfoTypes NAME = ContactInfoTypes.NAME;
    static ContactInfoTypes PHONE = ContactInfoTypes.PHONE;
    static ContactInfoTypes COMPANY = ContactInfoTypes.COMPANY;
    public myPhoneBook() {
        textsMap = generateTexts();
        menu = generatePhoneBookMenu();
    }

    /**
     * Importing texts from json file to a HashMap
     * @return HashMap containing texts used in the app
     */

    public static HashMap<String, String> generateTexts(/*String jsonPath*/) {
        try {
            // create Gson instance
            Gson gson = new Gson();
            HashMap<String, String> map;

            // create a reader
            BufferedReader bufferedReader = new BufferedReader(new FileReader("Texts.json"));

            // convert JSON file to map
            map = gson.fromJson(bufferedReader, HashMap.class);

            bufferedReader.close();
            return map;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Printing text from HashMap containing texts
     * @param key - HashMap key for the text to be printed
     */
    public static void printTextsFromMap(String key) {
        System.out.println(textsMap.get(key));
    }

    @Override
    public HashMap<Integer, String> generatePhoneBookMenu() {
        HashMap<Integer, String> menu = new HashMap<>();
        menu.put(1, "Add contact");
        menu.put(2, "Delete contact");
        menu.put(3, "Print all contacts");
        menu.put(4, "Search and print contact");
        menu.put(5, "Sort by name alphabetically");
        menu.put(6, "Sort by phone");
        menu.put(7, "Sort by name alphabetically - reversed");
        menu.put(8, "Remove duplicates");
        menu.put(9, "Save phone book to a text file");
        menu.put(10, "Upload phone book from a file");
        menu.put(11, "Exit");

        return menu;
    }

    @Override
    public ArrayList<Contact> generateContactsList() {
        return null;
    }

    /**
     * Create contacts object using setContact object
     * In this phone book number of name characters is limited to 20
     *
     * @return Contact object with valid fields filled, or null if contact already exist in contactList (phone book)
     */
    @Override
    public Contact createContact() {
        Contact contact = setContactFullInfo();
        if (mPhoneData.contactsList.contains(contact.getFullName()) || mPhoneData.contactsList.contains(contact.getPhoneNumber())) {
            System.out.println("CONTACT ALREADY EXIST IN PHONE BOOK");
            return null;
        }
        return contact;

    }

    /**
     * Adding contact object to an ArrayList of contacts
     * @param contact - Contact to be added
     * @param listOfContacts - ArrayList of contacts to be added to
     * @return - ArrayList containing the added contact
     */
    @Override
    public ArrayList<Contact> addContact(Contact contact, ArrayList<Contact> listOfContacts) {
        listOfContacts.add(listOfContacts.size(), contact);
        return listOfContacts;

    }

    static boolean compareContactDetails(Contact c, String nameOrPhone) {
        return c.getFullName().equalsIgnoreCase(nameOrPhone) || c.getPhoneNumber().equals(nameOrPhone);
    }
    public static ArrayList<Contact> findDuplicates(ArrayList<Contact> listOfContacts) {
        ArrayList<Contact> duplicatesList = new ArrayList<>();
        for (int i = 0; i < listOfContacts.size(); i++) {
            Contact c1 = listOfContacts.get(i);
            for (int j = i + 1; j < listOfContacts.size(); j++) {
                Contact c2 = listOfContacts.get(j);
                if (c1.equals(c2)) {
                    duplicatesList.add(c2);
                    i++;
                }
            }
        }
        return duplicatesList;
    }
    /**
     * Removing duplicates (identical contacts)
     * @param listOfContacts List for duplicates removal
     * @return List with duplicates removed
     */
    public ArrayList<Contact> removeContactDuplicates(ArrayList<Contact> listOfContacts) {
        ArrayList<Contact> duplicatesList = findDuplicates(listOfContacts);
        if (!duplicatesList.isEmpty()) {
            for (Contact contact : duplicatesList) {
                listOfContacts.remove(contact);
            }
        }
        return listOfContacts;
    }
    /**
     * Removing contact object from phone book list
     * @param listOfContacts - Phone book list from which contact needs to be removed
     * @param nameOrPhone - Name or phone number of contact to be removed
     * @param removeAll - true for Removing all contact matching, false for only the first to be found
     * @return - Phone book list without removed contact
     */
    @Override
    public ArrayList<Contact> removeContact(ArrayList<Contact> listOfContacts, String nameOrPhone, boolean removeAll) {
        ArrayList<Contact> inMethodList = findContact(listOfContacts, nameOrPhone);
        if (!inMethodList.isEmpty()) {
            if (removeAll) {
                ArrayList<Contact> foundList = new ArrayList<>();
                Contact[] contactsArr = new Contact[listOfContacts.size()];
                for (int i = 0; i < listOfContacts.size(); i++) {
                    contactsArr[i] = listOfContacts.get(i);
                }
                for (int i = 0; i < contactsArr.length; i++) {
                    if (compareContactDetails(contactsArr[i], nameOrPhone)) {
                        contactsArr[i] = null;
                    }
                }
                for (Contact contact : contactsArr) {
                    if (contact != null) {
                        foundList.add(contact);
                    }
                }
                return foundList;
            } else {
                for (Contact c : listOfContacts) {
                    if (compareContactDetails(c, nameOrPhone)) {
                        listOfContacts.remove(c);
                        break;
                    }
                }
                return listOfContacts;
            }
        } else {
            return listOfContacts;
        }
    }

    public void printContact(Contact c) {
        for(int i = 0; i < 30; i++) {
            System.out.print("-");
        }
        System.out.println("*");
//        int dotAmount = (c.getFullName().length() + 8) / 2;
        System.out.print("Name: " + c.getFirstName() + " : ");
        if (c.getMiddleName() != null) System.out.print(c.getMiddleName() + " : ");
        if(c.getLastName() != null) {System.out.println(c.getLastName());} else {
            System.out.println();
        }
//        System.out.println("Name: " + c.getFullName());
        System.out.println("Phone Number: " + c.getPhoneNumber());
        if (c.getCompanyName() != null) {
            System.out.println("Company: " + c.getCompanyName());
        } else {
            System.out.println("Company:");
        }
    }

    /**
     * Printing a partially designed phone book
     * @param listOfContacts - List of contacts to be printed
     */
    @Override
    public void printPhoneBook(ArrayList<Contact> listOfContacts) {
        for (Contact c: listOfContacts) {
            printContact(c);
        }
        for(int i = 0; i < 30; i++) {
            System.out.print("-");
        }
        System.out.println("*");
    }

    /**
     * Finding contacts objects in a list of contacts, adding them to a designated list
     * @param listOfContacts - List of contacts to be searched
     * @param contactNameOrPhone - Contact name or phone number - CASE SENSITIVE
     * @return - List of contacts matching the name or phone number parameter
     */
    // TODO if there is time, use contains to find partial names
    @Override
    public ArrayList<Contact> findContact(ArrayList<Contact> listOfContacts, String contactNameOrPhone) {
        ArrayList<Contact> contactsFound = new ArrayList<>();
        for (Contact c : listOfContacts) {
            if (compareContactDetails(c, contactNameOrPhone)) {
                contactsFound.add(c);
            }
        }
        return contactsFound;
    }

    @Override
    public ArrayList<Contact> sortByNameAlphabetically(ArrayList<Contact> listOfContacts) {
        return super.sortByNameAlphabetically(listOfContacts);
    }

    /**
     * Printing the phone book to a txt file
     * @param listOfContacts - Phone book to be exported to txt file
     */
    @Override
    public void exportPhoneBook(ArrayList<Contact> listOfContacts) { //TODO update to fit new contact requirements
        printTextsFromMap("enterFileName");
        String fileName = validateFileName(mPhoneData.scan.nextLine());
        try {
            // Creating a File object that
            // represents the disk file
            PrintStream o = new PrintStream(new FileOutputStream(fileName, true));

            // Store current System.out
            // before assigning a new value
            PrintStream console = System.out;

            // Assign o to output stream
            // using setOut() method
            System.setOut(o);

            // printing
            System.out.println();
            printPhoneBook(listOfContacts);

            // Use stored value for output stream
            System.setOut(console);

            // Print success message
            printTextsFromMap("actionSuccess");
        } catch (FileNotFoundException fnfe) {
            PhoneBookAppMethods.printErrorMessages(9);
        }
    }

    public String validateFileName(String fileName) { //TODO update and consider importing relevant validationMethods
        String fileFullName;
        if (fileName.contains(".txt")) {
            fileFullName = fileName;
        } else {
            fileFullName = fileName + ".txt";
        }
        File file = new File(fileFullName);
        if (!file.isFile()) {
            if (fileName.length() <= 15) {
                return fileFullName;
            } else {
                printTextsFromMap("nameTooLong");
                printTextsFromMap("createRandomFileName");
                return createRandomPhoneBookName();
            }
        } else {
            int yORn = PhoneBookAppMethods.enterAndValidateYorN("fileExist");
            if (yORn == 1) {
                return fileFullName;
            } else if (yORn == 0) {
                printTextsFromMap("createRandomFileName");
                return createRandomPhoneBookName();
            }
        }
        return "0";
    }

    /**
     * Creating a txt file name
     * @return File name: PhoneBook(with current date and time).txt
     */
    public String createRandomPhoneBookName() {
        DateFormat dFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
        Date today = Calendar.getInstance().getTime();
        String printDate = dFormat.format(today);
        return "PhoneBook " + printDate + ".txt";
    }

    /**
     * Reading a phone book from txt file, file name (if in project folder) or path entered by the user
     * @return ArrayList of valid contacts read from file
     */
    //TODO still a bug in here (in file content format), fix if there is time
    @Override
    public ArrayList<Contact> importPhoneBook() {
        ArrayList<Contact> importedContactsList = new ArrayList<>();
        String rawLine, name = "", phone = "";
        for (int i = 0; i < 3; i++) {
            printTextsFromMap("enterFileName");
            String fileName = mPhoneData.scan.nextLine();
            if (!fileName.contains(".txt")) {
                PhoneBookAppMethods.printErrorMessages(11);
                System.err.println("txt file only");
            } else {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(fileName));
                    Contact contact = new Contact();
                    while ((rawLine = reader.readLine()) != null) {  //TODO printing after upload is not good, check it
                        if (rawLine.contains("Name")) {
                            contact = new Contact();
                            String[] splitNameArr = rawLine.split(":");
                            for (int k = 0; k < splitNameArr.length; k++) {
                                String name2 = splitNameArr[k].trim();
                                switch (k) {
                                    case 1 -> contact.setFirstName(name2);
                                    case 2 -> {
                                        if (splitNameArr.length == 4) {
                                            contact.setMiddleName(name2);
                                        } else {
                                            contact.setLastName(name2);
                                        }
                                    }
                                    case 3 -> contact.setLastName(name2);
                                }
                            }
                        } else if (rawLine.contains("Phone Number:")) {
                            String[] splitNameArr = rawLine.split(":");
                            contact.setPhoneNumber(splitNameArr[1]);
                        } else if (rawLine.contains("Company")) {
                            String[] splitNameArr = rawLine.split(":");
                            if (splitNameArr.length > 1) {contact.setCompanyName(splitNameArr[1]);}
                        }

                        if (contact.getFirstName() != null && contact.getPhoneNumber() != null) {
                            importedContactsList = addContact(contact, importedContactsList);
                            importedContactsList = removeContactDuplicates(importedContactsList);
                        //    deleteContactInfo(contact);
                        }
                    }
                    if (reader.readLine() == null) {break;}
                } catch (FileNotFoundException fnfe) {
                    System.err.println("File Not Found"); //TODO maybe put text in errors map
                } catch (IOException ioe) {
                    System.err.println("Error In Reading Phone Book");
                } catch (Exception e) {
                    System.err.println("ERROR - Text file data incomplete (must have first name and phone number) or wrongfully formatted");
                }
            }
            if (i == 2) {
                PhoneBookAppMethods.printErrorMessages(7);
            }
        }
        return importedContactsList;
    }

    public Contact deleteContactInfo(Contact contact) {
        contact.setFirstName(null);
        contact.setMiddleName(null);
        contact.setLastName(null);
        contact.setPhoneNumber(null);
        contact.setCompanyName(null);
        return contact;
    }
    @Override
    public int compareTo(Object o) {
        return 0;
    }

}
