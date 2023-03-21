package PhoneBookApp;

import Main.PhoneData;

import static PhoneBookApp.ContactVars.*;

public class setContact {

    static PhoneData mPhoneData = PhoneData.getInstance();
    static int limitNumOfChars = 20;
    static final String OK = "OK";
    static final String FAIL = "FAIL";


    /**
     * Setting Contact full name
     *
     * @return contact object with full name set
     * @throws NullPointerException if first name is invalid
     */
    public Contact setContactFullInfo() {
        String firstName, middleName, lastName, phoneNumber, companyName, result = FAIL;
        Contact contact = new Contact();
        firstName = infoPerTypeInput(FIRST_NAME);
        if (isNamePerTypeValid(FIRST_NAME.strValue, firstName)) {
            contact.setFirstName(firstName);
            result = OK;
        }
        if (!setContactEnd(FIRST_NAME, result).equals(OK)) {
            return null;
        }
        middleName = infoPerTypeInput(MIDDLE_NAME);
        if (isNamePerTypeValid(MIDDLE_NAME.strValue, middleName) && !middleName.equals("middle")) {
            contact.setMiddleName(middleName);
            result = OK;
        } else {
            result = FAIL;
        }
        if (!setContactEnd(MIDDLE_NAME, result).equals(OK)) {
            return null;
        }
        lastName = infoPerTypeInput(LAST_NAME);
        if (isNamePerTypeValid(LAST_NAME.strValue, lastName)) {
            contact.setLastName(lastName);
        }
        if (!setContactEnd(LAST_NAME, result).equals(OK)) {
            return null;
        }
        companyName = infoPerTypeInput(COMPANY_NAME);
        if (isNamePerTypeValid(COMPANY_NAME.strValue, companyName)) {
            contact.setCompanyName(companyName);
        }
        if (!setContactEnd(COMPANY_NAME, result).equals(OK)) {
            return null;
        }
        phoneNumber = infoPerTypeInput(PHONE_NUMBER);
        if (isNamePerTypeValid(PHONE_NUMBER.strValue, phoneNumber)) {
            contact.setPhoneNumber(phoneNumber);
            result = OK;
        } else {
            result = FAIL;
        }
        if (!setContactEnd(PHONE_NUMBER, result).equals(OK)) {
            return null;
        }
        return contact;
    }

     private String setContactEnd(ContactVars stage, String result) {
        switch (stage) {
            case FIRST_NAME ->  {
                if (result.equals(FAIL)) {
                    System.err.println("UNABLE TO SET CONTACT DETAILS, FIRST NAME IS MANDATORY");
                    return FAIL;
                }
            }
            case MIDDLE_NAME -> {
                if (result.equals(FAIL)) {
                    System.out.println("UNABLE TO SET MIDDLE NAME, BUT THAT'S FINE IT'S NOT MANDATORY");
                    return OK;
                }
            }
            case LAST_NAME -> {
                if (result.equals(FAIL)) {
                    System.out.println("UNABLE TO SET LAST NAME, BUT THAT'S FINE IT'S NOT MANDATORY");
                    return OK;
                }
            }
            case COMPANY_NAME -> {
                if (result.equals(FAIL)) {
                    System.out.println("UNABLE TO SET COMPANY NAME, BUT THAT'S FINE IT'S NOT MANDATORY");
                    return OK;
                }
            }
            case PHONE_NUMBER -> {
                if (result.equals(FAIL)) {
                    System.err.println("UNABLE TO SET CONTACT DETAILS, PHONE NUMBER IS MANDATORY");
                    return FAIL;
                }
            }
            default -> {
                System.out.println(stage.strValue + " SET SUCCESSFUL");
                return OK;
            }
        }
        System.out.println(stage.strValue + " SET SUCCESSFUL");
        return OK;
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
     * @param inputType Values of ContactVars enum
     * @return User input if valid, "middle" if middle name invalid, FAIL if input invalid
     */
    private String infoPerTypeInput(ContactVars inputType) {
        String input;
        for (int i = 0; i < 3; i++) {
            input = userInputString("Enter contact " + inputType.strValue);
            switch (inputType) {
                case PHONE_NUMBER -> {
                    String validatedPhone = isPhoneNumberValid(input);
                    if (!(validatedPhone.equals(FAIL))) {
                        return validatedPhone;
                    }
                }
                case COMPANY_NAME -> {
                    String cleanInput = cleanStringInput(input);
                    if (numOfCharsRestriction(cleanInput, limitNumOfChars)) {
                        return cleanInput;
                    } else if (cleanInput == null || cleanInput.length() == 0) {
                        System.err.println("Empty input");
                    } else {
                        System.err.println("Input too long, only " + limitNumOfChars + " characters allowed here");
                    }
                }
                case FIRST_NAME, LAST_NAME ->  {
                    String nameValidation = isNameValid(input);
                    if (!nameValidation.equals(FAIL)) {
                        return nameValidation;
                    }
                }
                case MIDDLE_NAME ->  {
                    String nameValidation = isNameValid(input);
                    if (!(input == null) && !input.equals("")) {
                        if (!nameValidation.equals(FAIL)) {
                            return nameValidation;
                        }
                    } else if (i == 2) {
                        return "middle";
                    }
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
        try {
            return str.length() <= limit;
        } catch (NullPointerException npe) {
            return false;
        }
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
        String phoneFormatError = "ERROR - Phone number format invalid (should start with 0, and have 9-10 numbers)";
        switch (postValidation) {
            case FAIL -> {
                System.err.println(phoneFormatError + ", or some characters are not numbers");
                return FAIL;
            }
            case null -> {
                System.err.println(phoneFormatError + ", or numbers length is invalid");
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
