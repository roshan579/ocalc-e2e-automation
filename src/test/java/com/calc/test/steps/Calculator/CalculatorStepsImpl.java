package com.calc.test.steps.Calculator;

import com.calc.test.pages.PageFactory;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CalculatorStepsImpl {


    @Given("^user is on online calculator full screen$")
    public void userIsOnOnlineCalculatorFullScreen() throws Exception{
        PageFactory.getCalculatorPage().loadPage();
    }

    @When("^enter the \"([^\"]*)\", \"([^\"]*)\" details for the \"([^\"]*)\" given$")
    public void enterTheDetailsForTheGiven(String primaryNumber, String secondaryNumber, String functionType) throws Exception {
        PageFactory.getCalculatorPage().enterTheDetailsForTheGiven(primaryNumber, secondaryNumber, functionType);
    }

    @Then("^compare the result generated with \"([^\"]*)\" value$")
    public void compareTheResultGeneratedWithValue(String expectedResult) throws Exception {
        PageFactory.getCalculatorPage().compareTheResultGeneratedWithValue(expectedResult);
    }




}
