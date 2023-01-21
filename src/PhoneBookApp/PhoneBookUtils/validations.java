package PhoneBookApp.PhoneBookUtils;

public class validations {

    static int limitNumOfChars = 20;
    static final String OK = "OK";
    static final String FAIL = "FAIL";

    /**
     * Setting Contact full name
     * @param firstName - Contact first name
     * @param middleName - Contact middle name
     * @param lastName - Contact last name
     * @return OK if contact name set properly, FAIL if not
     */
    public String setContactFullName(String firstName, String middleName, String lastName) {
        Contact contact = new Contact();
        String result = FAIL;
        if (isNameValid(firstName)) {
            contact.setFirstName(firstName);
            result = OK;
        } else {
            System.err.println("First name must be valid");
            result = FAIL;
        }
        if (isNameValid(middleName)) {
            contact.setMiddleName(middleName);
            result = OK;
        }
        if (isNameValid(lastName)) {
            contact.setLastName(lastName);
            result = OK;
        }
        return result;
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
        } else if (validatedName.equals("") || validatedName == null) { //TODO maybe remove this because of middle name not mandatory
            System.err.println("Name is empty");
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
