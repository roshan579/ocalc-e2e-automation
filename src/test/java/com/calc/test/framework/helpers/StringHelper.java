package com.calc.test.framework.helpers;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

public class StringHelper {

    public static boolean equalLists(List<String> listOne, List<String> listTwo) {
        if (listOne == null && listTwo == null) {
            return true;
        }

        if ((listOne == null && listTwo != null)
                || listOne != null && listTwo == null
                || listOne.size() != listTwo.size()) {
            return false;
        }
        listOne = new ArrayList<String>(listOne);
        listTwo = new ArrayList<String>(listTwo);

        Collections.sort(listOne);
        Collections.sort(listTwo);
        return listOne.equals(listTwo);
    }

    public static int getRandomNumber(int max, int min) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public static String getRandomStringWithInput(String input) throws Exception {
        String startingOrder = StringUtils.isEmpty(input) ? "0" : input;
        return DateHelper.getDateInRequiredFormat(DateHelper.getCurrentDate(), DateHelper.TIMESTAMP_DATE_FORMAT_MILLISEC) + startingOrder + UUID.randomUUID().toString();
    }

    public static String getRandomString() {
        return UUID.randomUUID().toString();
    }

    public static String[] splitText(String strPassed, String regExp) {
        String stringArray[] = strPassed.split(regExp);
        return stringArray;
    }

    public static boolean stringContainsNumber(String s) {
        return Pattern.compile("[0-9]").matcher(s).find();
    }


    public static String getRandomNumberOnlyStringOfGivenLength(int length) {
        if (length > 16) {
            return null;
        } else {
            String randomValue = String.valueOf(Math.random());
            randomValue = randomValue.substring(2);
            randomValue = randomValue.substring(0, length);
            return randomValue;
        }
    }


    public static void main(String[] args) {

    }

}
