package PhoneBookApp.PhoneBookUtils;

import java.util.HashMap;

/**
 * Phone book contact object.
 * Contains first, middle, and last name, phone number, and company name for each contact object
 */
public class Contact {
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String companyName;


    /**
     * Setting contact first name and validating the use of english letters
     * In this case name is limited to 20 characters
     * @param firstName - set contact first name
     * @throws IllegalArgumentException if input invalid
     */
    public void setFirstName(String firstName) {
//        int charLimit = 20;
//        String validatedName = validateName(firstName, charLimit);
//        if (!validatedName.equals("2")) {
//            if (!validatedName.equals("1")) {
//                this.firstName = validatedName;
//            } else {
//                throw new ArithmeticException("First name length invalid");
//            }
//        } else {
//            throw new IllegalArgumentException("First name input is invalid");
//        }
        this.firstName = firstName;
    }

    /**
     * Setting contact middle name and validating the use of english letters
     * In this case name is limited to 20 characters
     * @param middleName - set contact middle name
     * @throws IllegalArgumentException if input invalid
     */
    public void setMiddleName(String middleName) {
//        int charLimit = 20;
//        String validatedName = validateName(middleName, charLimit);
//        if (!validatedName.equals("2")) {
//            if (!validatedName.equals("1")) {
//                this.middleName = validatedName;
//            } else {
//                throw new ArithmeticException("Middle name length invalid");
//            }
//        } else {
//            throw new IllegalArgumentException("Middle name input is invalid");
//        }
        this.middleName = middleName;
    }

    /**
     * Setting contact last name and validating the use of english letters
     * In this case name is limited to 20 characters
     * @param lastName - set contact last name
     * @throws IllegalArgumentException if input invalid
     */
    public void setLastName(String lastName) {
//        int charLimit = 20;
//        String validatedName = validateName(lastName, charLimit);
//        if (!validatedName.equals("2")) {
//            if (!validatedName.equals("1")) {
//                this.lastName = validatedName;
//            } else {
//                throw new ArithmeticException("Last name length invalid");
//            }
//        } else {
//            throw new IllegalArgumentException("Last name input is invalid");
//        }
        this.lastName = lastName;
    }

    /**
     * Setting contact company name and validating the limited 20 characters
     * @param companyName - set contact company name
     * @throws IllegalArgumentException if input invalid
     */
    public void setCompanyName(String companyName) {
        int charLimit = 20;
        if (companyName.length() <= charLimit) {
            this.companyName = companyName;
        } else {
            throw new ArithmeticException("Company name length invalid");
        }
    }

    public String getFirstName() {
        return this.firstName;
    }
    public String getMiddleName() { return this.middleName;}
    public String getLastName() { return this.lastName;}
    public String getCompanyName() {return this.companyName;}
    public String getFullName() {
        if (this.middleName != null) {
            return this.firstName + " " + this.middleName + " " + this.lastName;
        } else {
            return this.firstName + " " + this.lastName;
        }
    }
    public HashMap<String,String> getFullContactInfo() {
        HashMap<String,String> contatcInfo = new HashMap<>();
        contatcInfo.put("firstName", this.firstName);
        if(this.middleName != null) { contatcInfo.put("middleName", this.middleName); }
        contatcInfo.put("lastName", this.lastName);
        contatcInfo.put("companyName", this.companyName);
        contatcInfo.put("phoneNumber", this.phoneNumber);

        return contatcInfo;
    }

    /**
     * Setting and validating contact phone number
     * @param phoneNumber to set
     * @throws NumberFormatException if input invalid
     */
    public void setPhoneNumber(String phoneNumber) {
        String validatedPhone = validatePhone(phoneNumber);
        if (validatedPhone != null) {
            if (!validatedPhone.equals("length invalid")) {
            this.phoneNumber = validatedPhone;
            } else {
                throw new IllegalArgumentException("Phone input length invalid");
            }
        } else {
            throw new NumberFormatException("Phone input invalid");
        }
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }


    private boolean numOfCharsRestriction(String str, int limit) {
        return str.length() <= limit;
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
        return "length invalid";
    }

    /**
     * Determines phone number type
     *
     * @param phone - Phone number
     * @return cellphone, landline or null
     */
    public String phoneNumType(String phone) {
        // ASCII for 0 = 48, 5 = 53, 7 = 55
        if (phone.charAt(0) == 48) {
            if (phone.charAt(1) == 53) {
                return "cellphone";
            } else if (phone.charAt(1) == 55){
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
  /*      String validNum;
        try {
            validNum = cleanStringInput(num);
            for (int i = 0; i < validNum.length(); i++) {
                int charValue = validNum.charAt(i);
                if (charValue == 45 || charValue == 32) {
                    validNum = removeHyphen(validNum);
                }
            }
        } catch (NullPointerException npe) { */
            return null;
 //       }
 //       return validNum;
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
