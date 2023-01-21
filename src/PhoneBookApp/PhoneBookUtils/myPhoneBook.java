package PhoneBookApp.PhoneBookUtils;

//import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class myPhoneBook extends PhoneBookBlueprint {
    static Scanner scan = new Scanner(System.in);
    public static HashMap<String, String> textsMap = new HashMap<>();

    public static ArrayList<Contact> contactsList = new ArrayList<>();

/*    myPhoneBook() { //TODO import and update jsonTexts
        textsMap = generateTexts();
    }
*/
    /**
     * Importing texts from json file to a HashMap
     * @return HashMap containing texts used in the app
     */

//    public static HashMap<String, String> generateTexts(/*String jsonPath*/) {
/*        try {
            // create Gson instance
            Gson gson = new Gson();
            HashMap<String, String> map;

            // create a reader
            BufferedReader bufferedReader = new BufferedReader(new FileReader("./Texts.json"));

            // convert JSON file to map
            map = gson.fromJson(bufferedReader, HashMap.class);

            bufferedReader.close();
            return map;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
*/
    /**
     * Printing text from HashMap containing texts
     * @param key - HashMap key for the text to be printed
     */
    public static void printTextsFromMap(String key) {
        System.out.println(textsMap.get(key));
    }

    @Override
    public Map<Integer, String> generatePhoneBookMenu() {
        Map<Integer, String> menu = new HashMap<>();
        menu.put(1, "Add contact");
        menu.put(2, "Delete contact");
        menu.put(3, "Print all contacts");
        menu.put(4, "Search and print contact");
        menu.put(5, "Sort by name alphabetically");
        menu.put(6, "Sort by phone");
        menu.put(7, "Sort by name alphabetically - reversed");
        menu.put(8, "Remove duplicates");
        menu.put(9, "Save phone book to a file");
        menu.put(10, "Upload phone book from a file");
        menu.put(11, "Exit");

        return menu;
    }

    @Override
    public ArrayList<Contact> generateContactsList() {
        return null;
    }

    /**
     * Create contacts object with name and phone number
     * In this phone book number of name characters is limited to 20
     *
     * @return Contact object with valid fields filled
     */
    @Override
    public Contact createContact() {
    /*TODO turn into crating all contact details: first, middle and last name, company name and phone number. AND VALIDATE THERE IS ONLY ONE.*/
        boolean success = false;
        String firstNameInput;
        String middleNameInput;
        String lastNameInput;
        String fullNameInput;
        Contact contact = new Contact();
        System.out.println("Enter contact first name");
   //     printTextsFromMap("enterContactName");
        for (int i = 0; i < 3; i++) {
            firstNameInput = scan.nextLine();
            System.out.println("Enter contact middle name, if there is none, leave empty and press Enter");
            middleNameInput = scan.nextLine();
            System.out.println("Enter contact last name, if there is none, leave empty and press Enter");
            lastNameInput = scan.nextLine();

            fullNameInput = firstNameInput.trim() + " " + middleNameInput.trim() + " " + lastNameInput.trim();
            if (contactsList.contains(fullNameInput)) {
                try {
                    contact.setFirstName(firstNameInput);
                    contact.setMiddleName(middleNameInput);
                    contact.setLastName(lastNameInput);
                    success = true;
                    break;
                } catch (IllegalArgumentException iae) {
//                    PhoneBookAppMethods.printErrorMessages(PhoneBookAppMethods.calculateMessageIndex(i, false, true, true));
                } catch (ArithmeticException ae) {
//                    PhoneBookAppMethods.printErrorMessages(PhoneBookAppMethods.calculateMessageIndex(i, false, false, true));
                }
            }
        }

 /*       if (success) {
            printTextsFromMap("enterPhone");
            for (int i = 0; i < 3; i++) {
                String phone = scan.nextLine();
                try {
                    contact.setPhoneNumber(phone);
                    return contact;
                } catch (NumberFormatException nfe) {
                    PhoneBookAppMethods.printErrorMessages(PhoneBookAppMethods.calculateMessageIndex(i, false, false, false));
                } catch (IllegalArgumentException iae) {
                    PhoneBookAppMethods.printErrorMessages(PhoneBookAppMethods.calculateMessageIndex(i, false, true, false));
                }
            }
        }*/
        return null;

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
                if (c1.getFirstName().equalsIgnoreCase(c2.getFirstName())) {
                    if (c1.getPhoneNumber().equals(c2.getPhoneNumber())) {
                        duplicatesList.add(c2);
                        i++;
                    }
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
    public ArrayList<Contact> removeDuplicates(ArrayList<Contact> listOfContacts) {
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

    public void printContact(Contact c) {/* //TODO update to fit new contact info
        String name = c.getName();
        String phone = c.getPhoneNumber();
        int dotAmount = 25 - name.length();
        System.out.print(Objects.requireNonNullElse(name, "Null"));
        PhoneBookAppMethods.printMenuDots(dotAmount);
        System.out.print(phone);
*/
    }

    /**
     * Printing a partially designed phone book
     * @param listOfContacts - List of contacts to be printed
     */
    @Override
    public void printPhoneBook(ArrayList<Contact> listOfContacts) { /*//TODO consider importing relevant methods from PhoneBookAppMethods
        PhoneBookAppMethods.printFrame(37);
        System.out.println("CONTACTS: ");
        for (Contact contact : listOfContacts) {
            System.out.print("| ");
            printContact(contact);
            int totalLineLength;
            try {
                totalLineLength = 25 + contact.getPhoneNumber().length();
            } catch (NullPointerException npe) {
                totalLineLength = 35;
                System.out.print("      ");
            }
            if (totalLineLength == 35) {
                System.out.print("|");
            } else {
                System.out.print(" |");
            }
            System.out.println();
        }
        PhoneBookAppMethods.printFrame(37); */
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
    public void exportPhoneBook(ArrayList<Contact> listOfContacts) {/* //TODO update to fit new contact requirements
        printTextsFromMap("enterFileName");
        String fileName = validateFileName(scan.nextLine());
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
        } */
    }

    public String validateFileName(String fileName) {/* //TODO update and consider importing relevant validationMethods
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
            int yORn = validationMethods.enterAndValidateYorN("fileExist");
            if (yORn == 1) {
                return fileFullName;
            } else if (yORn == 0) {
                printTextsFromMap("createRandomFileName");
                return createRandomPhoneBookName();
            }
        } */
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
     * Only name and phone number in the same line format is supported
     * @return ArrayList of valid contacts read from file
     */
    //TODO still a bug in here (in file content format), fix if there is time
    @Override
    public ArrayList<Contact> importPhoneBook() { /* //TODO consider importing relevant PhoneBookAppMethods. AND AUTOMATICALLY REMOVE DUPLICATES
        ArrayList<Contact> importedContactsList = new ArrayList<>();
        String rawLine;
        String name = "";
        String phone = "";
        for (int i = 0; i < 3; i++) {
            printTextsFromMap("enterFileName");
            String fileName = scan.nextLine();
            if (!fileName.contains(".txt")) {
                PhoneBookAppMethods.printErrorMessages(11);
                System.err.println("txt file only");
            } else {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(fileName));
                    while ((rawLine = reader.readLine()) != null) {
                        Contact contact = new Contact();
                        for (int j = 0; j < rawLine.length(); j++) {
                            int charValue = rawLine.charAt(j);
                            String currentChar = Character.toString((char) charValue);
                            if (((charValue >= 65 && charValue <= 90) || (charValue >= 97 && charValue <= 122) || charValue == 32)) {
                                name = name.concat(currentChar);
                            } else if ((charValue >= 48 && charValue <= 57)) {
                                phone = phone.concat(currentChar);
                            }
                        }
                        try {
                            if (!(name.equals("CONTACTS") || name.equals("") || phone.equals(""))) {
                                name = validationMethods.validateLettersOnly(name);
                                contact.setName(name);
                                contact.setPhoneNumber(phone);
                                importedContactsList = addContact(contact, importedContactsList);
                            }
                        } catch (NumberFormatException | ArithmeticException e) {
                            PhoneBookAppMethods.printErrorMessages(11);
                        }
                        name = "";
                        phone = "";
                    }
                    break;
                } catch (FileNotFoundException fnfe) {
                    System.err.println("File Not Found"); //TODO maybe put text in errors map
                } catch (IOException ioe) {
                    System.err.println("Error In Reading Phone Book");
                }
            }
            if (i == 2) {
                PhoneBookAppMethods.printErrorMessages(7);
            }
        }
        importedContactsList = remo
        return importedContactsList; */
        return null;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

}
