package com.calc.test.pages.calculator;

import com.calc.test.framework.context.calculator.CalculatorContext;
import com.calc.test.framework.helpers.WaitHelper;
import com.calc.test.pages.BasePage;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.sikuli.script.*;

public class CalculatorPage extends BasePage {


    public void enterTheDetailsForTheGiven(String primaryNumber, String secondaryNumber, String functionType) throws Exception {
        try {
            populateCalculatorContext(primaryNumber, secondaryNumber, functionType);
            validateGivenCalculatorInputs();
            verifyAllTheKeysDisplayed();
            if (functionType.equalsIgnoreCase("Subtraction")) {
                doSubtractionOfGivenNumbers();
            } else if (functionType.equalsIgnoreCase("Division")) {
                doDivisionOfGivenNumbers();
            } else if (functionType.equalsIgnoreCase("CE")) {
                doCEOfGivenNumbers();
            } else {
                Assert.assertFalse("Given function type : " + functionType + " is not handled", true);
            }
        } catch (Exception ex) {
            Assert.assertFalse("Entering the given details Primary Number : " + primaryNumber +
                    " with Secondary Number : " + secondaryNumber + " for the Function Type : " + functionType +
                    " failed due to : " + ex.getMessage(), true);
        }
    }

    private void doSubtractionOfGivenNumbers() {
        clickNumberKeysOnCalculator(CalculatorContext.primaryNumber);
        clickActionKey("Minus");
        clickNumberKeysOnCalculator(CalculatorContext.secondaryNumber);
        clickActionKey("Equals");
    }

    private void doDivisionOfGivenNumbers() {
        clickNumberKeysOnCalculator(CalculatorContext.primaryNumber);
        clickActionKey("Divide");
        clickNumberKeysOnCalculator(CalculatorContext.secondaryNumber);
        clickActionKey("Equals");
    }

    private void doCEOfGivenNumbers() {
        clickNumberKeysOnCalculator(CalculatorContext.primaryNumber);
        clickActionKey("CE");
    }

    private void clickNumberKeysOnCalculator(String givenNumber) {
        if (givenNumber.contains(".")) {
            String[] splitNumber = givenNumber.split("\\.");
            clickDirectKeysWithoutDot(splitNumber[0]);
            clickActionKey("Dot");
            clickDirectKeysWithoutDot(splitNumber[1]);
        } else {
            clickDirectKeysWithoutDot(givenNumber);
        }
    }

    private void clickDirectKeysWithoutDot(String numberGiven) {
        for (int index = 0; index < numberGiven.length(); index++) {
            String key = String.valueOf(numberGiven.charAt(index));
            Region region = new Screen(CalculatorContext.SCREEN_ID);
            Pattern keyPattern = null;
            Match keyMatch = null;
            try {
                keyPattern = new Pattern(CalculatorContext.IMAGE_PATH + key + ".PNG").similar((float) 0.40);
                keyMatch = region.find(keyPattern);
                keyMatch.click();
            } catch (FindFailed ff) {
                Assert.assertFalse("clicking key : " + key + " for given number : " + numberGiven + " failed", true);
            }
        }
    }

    private void clickActionKey(String actionName) {
        Region region = new Screen(CalculatorContext.SCREEN_ID);
        try {
            Pattern actionPattern = new Pattern(CalculatorContext.IMAGE_PATH + actionName + ".PNG").similar((float) 0.40);
            Match keyMatch = region.find(actionPattern);
            keyMatch.click();
        } catch (FindFailed ff) {
            Assert.assertFalse("clicking action key : " + actionName + " failed", true);
        }
    }


    public void compareTheResultGeneratedWithValue(String expectedResult) throws Exception {
        try {
            if (!CalculatorContext.executionSkip) {
                verifyTheResultDisplayed(expectedResult);
            }
        } catch (Exception ex) {
            Assert.assertFalse("ComParing the given Result generated : " + expectedResult +
                    " for the Function Type : " + CalculatorContext.functionType + " failed due to : " + ex.getMessage(), true);
        }
    }


    private void validateGivenCalculatorInputs() {
        if (StringUtils.isBlank(CalculatorContext.functionType)) {
            Assert.assertFalse("Function Type given from scenario : " + CalculatorContext.primaryNumber + " is blank", true);
        }

        Assert.assertFalse("Primary Number given from scenario : " + CalculatorContext.primaryNumber + " is blank",
                (StringUtils.isBlank(CalculatorContext.primaryNumber)));

        if (CalculatorContext.primaryNumber.contains(".")) {
            String[] primarySplit = CalculatorContext.primaryNumber.split("\\.");
            Assert.assertFalse("Given Primary Number Length cannot Exceed 9 Digit", (primarySplit[0].length() > 10));
            if (primarySplit.length > 1) {
                Assert.assertFalse("Given Primary Number dot Length cannot Exceed 8 Digit", (primarySplit[1].length() > 8));
            }
        } else {
            Assert.assertFalse("Given Primary Number Length cannot Exceed 9 Digit", (CalculatorContext.primaryNumber.length() > 9));
        }

        try {
            Float.valueOf(CalculatorContext.primaryNumber);
        } catch (Exception ex) {
            Assert.assertFalse("Given Primary Number should be a number", true);
        }

        if (CalculatorContext.functionType.equalsIgnoreCase("Subtraction") || CalculatorContext.functionType.equalsIgnoreCase("Division")) {

            Assert.assertFalse("Secondary Number given from scenario : " + CalculatorContext.primaryNumber + " is blank",
                    (StringUtils.isBlank(CalculatorContext.secondaryNumber)));
            try {
                Float.valueOf(CalculatorContext.secondaryNumber);
            } catch (Exception ex) {
                Assert.assertFalse("Given Secondary Number should be a number", true);
            }
            if (CalculatorContext.secondaryNumber.contains(".")) {
                String[] secondarySplit = CalculatorContext.secondaryNumber.split("\\.");
                Assert.assertFalse("Given Secondary Number Length cannot Exceeds 9 Digit", (secondarySplit[0].length() > 10));
                if (secondarySplit.length > 1) {
                    Assert.assertFalse("Given Secondary Number dot Length cannot Exceed 8 Digit", (secondarySplit[1].length() > 8));
                }
            } else {
                Assert.assertFalse("Given Secondary Number Length cannot Exceeds 9 Digit", (CalculatorContext.secondaryNumber.length() > 9));
            }
        }
    }

    private void populateCalculatorContext(String primaryNumber, String secondaryNumber, String functionType) {
        CalculatorContext.executionSkip = false;
        CalculatorContext.primaryNumber = primaryNumber;
        CalculatorContext.secondaryNumber = secondaryNumber;
        CalculatorContext.functionType = functionType;
    }

    private void verifyTheResultDisplayed(String resultImage) {
        try {
            Region region = new Screen(CalculatorContext.SCREEN_ID);
            Pattern resultPattern = new Pattern(CalculatorContext.IMAGE_PATH + resultImage + ".PNG").similar((float) 0.40);
            region.find(resultPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("result is not matching the expected result : " + resultImage, true);
        }
    }

    private void verifyAllTheKeysDisplayed()  {
        Region region = new Screen(CalculatorContext.SCREEN_ID);
        Pattern calcPattern = null;

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "LaunchZeroResult.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("Zero default result is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "0.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("Zero key is not shown", true);
        }


        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "Dot.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("Dot key is not shown is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "Plus-Minus.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("Plus-Minus key is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "Plus.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("Plus key is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "Equals.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("Equals key is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "1.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("1 key is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "2.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("2 key is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "3.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("3 key is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "Minus.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("Minus key is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "OneByX.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("OneByX key is not shown", true);
        }


        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "4.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("4 key is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "5.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("5 key is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "6.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("6 key is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "Multiply.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("Multiply key is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "Percentage.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("Percentage key is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "7.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("7 key is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "8.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("8 key is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "9.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("9 key is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "Divide.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("Divide key is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "SquareRoot.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("SquareRoot key is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "MC.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("MC key is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "MR.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("MR key is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "MPlus.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("MPlus key is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "MMinus.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("MMinus key is not shown", true);
        }

        try {
            calcPattern = new Pattern(CalculatorContext.IMAGE_PATH + "CE.PNG").similar((float) 0.40);
            region.find(calcPattern);
        } catch (FindFailed ff) {
            Assert.assertFalse("CE key is not shown", true);
        }
    }


}
