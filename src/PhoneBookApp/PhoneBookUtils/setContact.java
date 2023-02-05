package PhoneBookApp.PhoneBookUtils;

import java.util.Scanner;

public class setContact {

    static int limitNumOfChars = 20;
    static final String OK = "OK";
    static final String FAIL = "FAIL";
    static Scanner scan = new Scanner(System.in);

    /**
     * Setting Contact full name
     *
     * @return contact object with full name set
     * @throws NullPointerException if first name is invalid
     */
    public Contact setContactFullInfo() {
        String firstName, middleName, lastName, phoneNumber, companyName, result = FAIL;
        Contact contact = new Contact();
        firstName = infoPerTypeInput("First Name");
        if (isNamePerTypeValid("First Name", firstName)) {
            contact.setFirstName(firstName);
            result = OK;
        }
        if (result.equals(FAIL)) {
            System.err.println("UNABLE TO SET CONTACT DETAILS, FIRST NAME IS MANDATORY");
            return null;
        }
        middleName = infoPerTypeInput("Middle Name");
        if (isNamePerTypeValid("Middle Name", middleName) && !middleName.equals("middle")) {
            contact.setMiddleName(middleName);
        }

        lastName = infoPerTypeInput("Last Name");
        if (isNamePerTypeValid("Last Name", lastName)) {
            contact.setLastName(lastName);
        }
        companyName = infoPerTypeInput("Company Name");
        if (isNamePerTypeValid("Company Name", companyName)) {
            contact.setCompanyName(companyName);
        }
        phoneNumber = infoPerTypeInput("Phone Number");
        if (isNamePerTypeValid("Phone Number", phoneNumber)) {
            contact.setPhoneNumber(phoneNumber);
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
     *
     * @param inputType First Name, Middle Name, Last Name, Company Name (only limits number of characters), Phone Number
     * @return User input if valid, "middle" if middle name invalid, FAIL if input invalid
     */
    private String infoPerTypeInput(String inputType) {
        String input;
        for (int i = 0; i < 3; i++) {
            input = userInputString("Enter contact " + inputType);
            if (!inputType.equals("Middle Name")) {
                if (inputType.equals("Company Name")) {
                    if (numOfCharsRestriction(input, limitNumOfChars)) {
                        return input;
                    }
                } else if (inputType.equals("Phone Number")) {
                    if (isPhoneNumberValid(input)) {
                        return input;
                    }

                } else if (isNameValid(input)) {
                    return input;
                }
            } else {
                if (!input.equals("") && !(input == null)) {
                    if (isNameValid(input)) {
                        return input;
                    }
                } else {
                    return "middle";
                }
            }
        }
        return FAIL;
    }

    private String userInputString(String promptMessage) {
        System.out.println(promptMessage);
        return scan.nextLine();
    }

    /**
     * Validate name using the validateName method
     * Prints Name too long and english letters restriction errors
     *
     * @param name - Name to be validated
     * @return true if name is valid, false if not
     */
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

    private boolean isPhoneNumberValid(String phoneNumber) {
        switch (validatePhone(phoneNumber)) {
            case FAIL -> {
                System.err.println("ERROR - Phone number format invalid, or some characters are not numbers");
                return false;
            }
            case null -> {
                System.err.println("Phone number length is invalid");
                return false;
            }
            default -> {
                return true;
            }
        }

    }

    /**
     * Clean, validate and parse phone number property
     *
     * @param phone - String of phone number to be validated
     * @return formatted and validated phone number or null if phone number is invalid
     */
    public String validatePhone(String phone) { // TODO change method to support invalid phone number format
        String formattedPhone = cleanNumber(phone);
        if (isNumbersOnly(formattedPhone)) {
            if (phoneNumType(formattedPhone) == null) {
                return null;
            } else if (phoneNumType(formattedPhone).equals("cellphone") || phoneNumType(formattedPhone).equals("long_landline")) {
                if (formattedPhone.length() == 10) {
                    return formattedPhone;
                }
            } else if (phoneNumType(formattedPhone).equals("short_landline")) {
                if (formattedPhone.length() == 9) {
                    return formattedPhone;
                }
            }
        }
        return FAIL;
    }

    /**
     * Determines phone number type
     *
     * @param phone - Phone number
     * @return cellphone, long_landline, short_landline or null
     */
    public String phoneNumType(String phone) {
        // ASCII for 0 = 48, 5 = 53, 7 = 55
        if (phone.charAt(0) == 48) {
            if (phone.charAt(1) == 53) {
                return "cellphone";
            } else if (phone.charAt(1) == 55) {
                return "long_landline";
            } else {
                return "short_landline";
            }
        } else {
            return null;
        }

    }


    /**
     * Removing hyphens from string
     *
     * @param hyphenedStr String with hyphen
     * @return String without hyphen
     */
    public String removeHyphen(String hyphenedStr) {
        String[] splittedStr = hyphenedStr.split("[- +]");
        String result = "";
        for (String s : splittedStr) {
            result = result.concat(s);
        }
        return result;
    }

    /**
     * Cleaning string from white spaces and hyphens
     *
     * @param num - String numbers to be cleaned
     * @return Cleaned number string
     */
    public String cleanNumber(String num) {
        String validNum;
        try {
            validNum = cleanStringInput(num);
            for (int i = 0; i < validNum.length(); i++) {
                int charValue = validNum.charAt(i);
                if (charValue == 45 || charValue == 32) {
                    validNum = removeHyphen(validNum);
                }
            }
        } catch (NullPointerException npe) {
            return null;
        }
        return validNum;
    }

    /**
     * Cleaning and validating phone number chars
     *
     * @param num - Number to be validated
     * @return true if string contains only numbers
     */
    public boolean isNumbersOnly(String num) {
        String cleanNum = cleanNumber(num);
        try {
            for (int i = 0; i < cleanNum.length(); i++) {
                int charValue = cleanNum.charAt(i);
                if (!(charValue >= 48 && charValue <= 57)) {
                    return false;
                }
            }
        } catch (NullPointerException npe) {
            return false;
        }
        return true;
    }

}
