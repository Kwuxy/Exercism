class PhoneNumber {
    private String number;

    PhoneNumber(String number) {
        this.number = number;
        cleanNumber();
        checkValidity();
        removeFirstDigitIfNecessary();
    }

    private void checkValidity() {
        containsValidChar();
        lengthIsValid();
        number11DigitsLongDoesntStartWith1();
        areaCodeIsValid();
        exchangeCodeIsValid();
    }

    private void containsValidChar() {
        containsLetters();
        containsPunctuations();
    }

    private void containsLetters() {
        if(number.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("letters not permitted");
        }
    }

    private void containsPunctuations() {
        if(number.matches(".*[^\\w].*")) {
            throw new IllegalArgumentException("punctuations not permitted");
        }
    }

    private void tooShortNumber() {
        if(number.length() < 10) {
            throw new IllegalArgumentException("incorrect number of digits");
        }
    }

    private void tooLongNumber() {
        if(number.length() > 11) {
            throw new IllegalArgumentException("more than 11 digits");
        }
    }

    private void lengthIsValid() {
        tooShortNumber();
        tooLongNumber();
    }

    private void number11DigitsLongDoesntStartWith1() {
        if(number.length() == 11 && number.charAt(0) != '1') {
            throw new IllegalArgumentException("11 digits must start with 1");
        }
    }

    private void areaCodeStartsWith0() {
        if(number.length() == 10 && number.charAt(0) == '0'
                || number.length() == 11 && number.charAt(1) == '0') {
            throw new IllegalArgumentException("area code cannot start with zero");
        }
    }

    private void areaCodeStartsWith1() {
        if(number.length() == 10 && number.charAt(0) == '1'
                || number.length() == 11 && number.charAt(1) == '1') {
            throw new IllegalArgumentException("area code cannot start with one");
        }
    }

    private void areaCodeIsValid() {
        areaCodeStartsWith0();
        areaCodeStartsWith1();
    }

    private void exchangeCodeIsValid() {
        exchangeCodeStartsWith0();
        exchangeCodeStartsWith1();
    }

    private void exchangeCodeStartsWith0() {
        if(number.length() == 10 && number.charAt(3) == '0'
                || number.length() == 11 && number.charAt(4) == '0') {
            throw new IllegalArgumentException("exchange code cannot start with zero");
        }
    }

    private void exchangeCodeStartsWith1() {
        if(number.length() == 10 && number.charAt(3) == '1'
                || number.length() == 11 && number.charAt(4) == '1') {
            throw new IllegalArgumentException("exchange code cannot start with one");
        }
    }

    private void cleanNumber() {
        number = number.replaceAll("[-()\\s.+]", "");
    }

    private void removeFirstDigitIfNecessary() {
        if(number.charAt(0) == '1'){
            number = number.substring(1);
        }
    }

    public String getNumber() {
        return number;
    }
}