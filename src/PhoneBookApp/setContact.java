package PhoneBookApp;

import Main.PhoneData;

public class setContact {

    static PhoneData mPhoneData = PhoneData.getInstance();;
    static int limitNumOfChars = 20;
    static final String OK = "OK";
    static final String FAIL = "FAIL";

//    public setContact() {
//        mPhoneData = PhoneData.getInstance();
//    }

    /**
     * Setting Contact full name
     *
     * @return contact object with full name set
     * @throws NullPointerException if first name is invalid
     */
    public Contact setContactFullInfo() {
        String firstName, middleName, lastName, phoneNumber, companyName, result = FAIL;
        String first = "First Name", middle = "Middle Name" , last = "Last Name", company = "Company Name", phone = "Phone Number";
        Contact contact = new Contact();
        firstName = infoPerTypeInput(first);
        if (isNamePerTypeValid(first, firstName)) {
            contact.setFirstName(firstName);
            result = OK;
        }
        if (!setContactMessages(first.toUpperCase(), result).equals(OK)) {
            return null;
        }
        middleName = infoPerTypeInput(middle);
        if (isNamePerTypeValid(middle, middleName) && !middleName.equals("middle")) {
            contact.setMiddleName(middleName);
        }
        if (!setContactMessages(middle.toUpperCase(), result).equals(OK)) {
            return null;
        }
        lastName = infoPerTypeInput(last);
        if (isNamePerTypeValid(last, lastName)) {
            contact.setLastName(lastName);
        }
        if (!setContactMessages(last.toUpperCase(), result).equals(OK)) {
            return null;
        }
        companyName = infoPerTypeInput(company);
        if (isNamePerTypeValid(company, companyName)) {
            contact.setCompanyName(companyName);
        }
        if (!setContactMessages(company.toUpperCase(), result).equals(OK)) {
            return null;
        }
        phoneNumber = infoPerTypeInput(phone);
        if (isNamePerTypeValid(phone, phoneNumber)) {
            contact.setPhoneNumber(phoneNumber);
            result = OK;
        }
        if (!setContactMessages(phone.toUpperCase(), result).equals(OK)) {
            return null;
        }
        return contact;
    }

     private String setContactMessages(String stage, String result) {
        if (stage.equals("FIRST NAME") && result.equals(FAIL)) {
            System.err.println("UNABLE TO SET CONTACT DETAILS, FIRST NAME IS MANDATORY");
            return FAIL;
        } else if (stage.equals("PHONE NUMBER") && result.equals(FAIL)) {
            System.err.println("UNABLE TO SET CONTACT DETAILS, PHONE NUMBER IS MANDATORY");
            return FAIL;
        } else {
            System.out.println(stage + " SET SUCCESSFUL");
            return OK;
        }

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
            if (inputType.equals("Phone Number")) {
                String validatedPhone = isPhoneNumberValid(input);
                if (!(validatedPhone.equals(FAIL))) {
                    return validatedPhone;
                }
            } else if (inputType.equals("Company Name")) {
                String cleanInput = cleanStringInput(input);
                if (numOfCharsRestriction(cleanInput, limitNumOfChars)) {
                    return cleanInput;
                }
            } else if (!inputType.equals("Middle Name")) {
                String nameValidation = isNameValid(input);
                if (!nameValidation.equals(FAIL)) {
                    return nameValidation;
                }
            } else {
                String nameValidation = isNameValid(input);
                if (!input.equals("") && !(input == null)) {
                    if (!nameValidation.equals(FAIL)) {
                        return nameValidation;
                    }
                } else if (i == 2) {
                    return "middle";
                }
            }
        }
        return FAIL;
    }

    private String userInputString(String promptMessage) {
        System.out.println(promptMessage);
        return mPhoneData.scan.nextLine();
    }

    /**
     * Validate name using the validateName method
     * Prints Name too long and english letters restriction errors
     *
     * @param name - Name to be validated
     * @return true if name is valid, false if not
     */
    private String isNameValid(String name) {
        String validatedName = validateName(name);
        if (!(validatedName.equals("1") || validatedName.equals("2"))) {
            return validatedName;
        } else if (validatedName.equals("1")) {
            System.err.println("Name too long, restricted to " + limitNumOfChars + " characters");
            return FAIL;
        } else if (validatedName.equals("2")) {
            System.err.println("Name can contain english letters only");
            return FAIL;
        }
        return FAIL;
    }


    /**
     * Clean and validate name property
     *
     * @param name - Name to be validated
     * @return cleaned and validated name, "1" if characters limit invalid or "2" if not in english
     */
    private String validateName(String name) {
        String cleanName = cleanStringInput(name);
        if (isOnlyEnglishLetters(cleanName)) {
            if (numOfCharsRestriction(cleanName, limitNumOfChars)) {
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
    public boolean isOnlyEnglishLetters(String str) {
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
    private static String cleanStringInput(String str) {
        try {
            String nameNoUselessSpaces = str.replaceAll("\\s+", " ").trim();
            if (nameNoUselessSpaces.length() > 0) {
                return nameNoUselessSpaces;
            } else {
                return null;
            }
        } catch (NullPointerException npe) {
            return null;
        }
    }

    private String isPhoneNumberValid(String phoneNumber) {
        String postValidation = validatePhone(phoneNumber);
        switch (postValidation) {
            case FAIL -> {
                System.err.println("ERROR - Phone number format invalid (should start with 0, and have 9-10 numbers), or some characters are not numbers");
                return FAIL;
            }
            case null -> {
                System.err.println("ERROR - Phone number format invalid (should start with 0, and have 9-10 numbers), or numbers length is invalid");
                return FAIL;
            }
            default -> {
                return postValidation;
            }
        }

    }

    /**
     * Clean, validate and parse phone number property
     *
     * @param phone - String of phone number to be validated
     * @return formatted and validated phone number or null if phone number is invalid
     */
    public String validatePhone(String phone) {
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
    public static String removeHyphen(String hyphenedStr) {
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
    public static String cleanNumber(String num) {
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
    public static boolean isNumbersOnly(String num) {
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
