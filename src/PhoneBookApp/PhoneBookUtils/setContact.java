package PhoneBookApp.PhoneBookUtils;

import java.util.Scanner;

public class setContact {

    static int limitNumOfChars = 20;
    static final String OK = "OK";
    static final String FAIL = "FAIL";
    static Scanner scan = new Scanner(System.in);

    /**
     * Setting Contact full name
     * @throws NullPointerException if first name is invalid
     * @return contact object with full name set
     */
    public Contact setContactFullName() {
        String firstName, middleName, lastName, result = FAIL;
        Contact contact = new Contact();
        firstName = namePerTypeInput("FirstName");
        if (isNamePerTypeValid("First Name", firstName)) {
            contact.setFirstName(firstName);
            result = OK;
        }
        if (result.equals(FAIL)) {
            System.err.println("UNABLE TO SET CONTACT DETAILS, FIRST NAME IS MANDATORY");
            return null;
        }
        middleName = namePerTypeInput("MiddleName");
        if (isNamePerTypeValid("Middle Name", middleName)) {
            contact.setMiddleName(middleName);
        }
        lastName = namePerTypeInput("LastName");
        if (isNamePerTypeValid("Last Name", lastName)) {
            contact.setLastName(lastName);
        }


        return contact;
    }

    private boolean isNamePerTypeValid(String nameType, String name) {
        if (!name.equals(FAIL)) {
            return true;
        } else {
            System.err.println("Invalid " + nameType + ", unable to set");
            return false;
        }
    }

    /**
     * Prompting the user and receiving input from scanner
     * @param inputType First Name, Middle Name, Last Name, Company Name, Phone Number
     * @return User input if valid, "middle" if middle name invalid, FAIL if input invalid
     */
    private String namePerTypeInput(String inputType) {
        String input;
        for (int i = 0; i < 3; i++) {
            System.out.println("Enter contact " + inputType);
            input = scan.nextLine();
            if (!inputType.equals("middleName")) {
                if(isNameValid(input) && !(inputType.equals("PhoneNumber") || inputType.equals("CompanyName"))) {
                    return input;
                } else if (inputType.equals("PhoneNumber")) {
                    //TODO copy phone number validation methods, and use them here
                } else if (inputType.equals("CompanyName")) {
                    //TODO decide what validation are necessary, write and use them
                }
            } else {
                if (isNameValid(input)) {
                    return input;
                } else {
                    return "middle";
                }
            }
        }
        return FAIL;
    }
    private boolean isNameValid(String name) {
        String validatedName = validateName(name);
        if (name.equals(validatedName)) {
            return true;
        } else if (validatedName.equals("1")) {
            System.err.println("Name too long, restricted to " + limitNumOfChars + " characters");
            return false;
        } else if (validatedName.equals("2")) {
            System.err.println("Name can contain english letters only");
            return false;
        }
        return false;
    }



    /**
     * Clean and validate name property
     *
     * @param name - Name to be validated
     * @return cleaned and validated name, "1" if characters limit invalid or "2" if not in english
     */
    private String validateName(String name) {
        String cleanName = cleanStringInput(name);
        if (isOnlyEnglishLetters(name)) {
            if (numOfCharsRestriction(name, limitNumOfChars)) {
                return cleanName;
            } else {
                return "1";
            }
        } else {
            return "2";
        }
    }

    private boolean numOfCharsRestriction(String str, int limit) {
        return str.length() <= limit;
    }

    /**
     * Validating only english letters, using cleanStringInput method to remove white spaces
     *
     * @param str to validate
     * @return true if only english letters
     */
    private boolean isOnlyEnglishLetters(String str) {
        // ASCII letters "a" = 97, "z" = 122, "A" = 65, "Z" = 90, " " = 32
        String validStr = cleanStringInput(str);
        if (validStr != null) {
            for (int i = 0; i < validStr.length(); i++) {
                int charValue = validStr.charAt(i);
                if (!((charValue >= 65 && charValue <= 90) || (charValue >= 97 && charValue <= 122) || charValue == 32)) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Valid name is not null or empty and has no consecutive spaces
     *
     * @param str - String input to format
     * @return String without consecutive spaces or null
     */
    private String cleanStringInput(String str) {
        String nameNoUselessSpaces = str.replaceAll("\\s+", " ").trim();
        if (nameNoUselessSpaces.length() > 0) {
            return nameNoUselessSpaces;
        } else {
            return null;
        }
    }


}
