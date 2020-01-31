package com.calc.test.runCukes.calculator;



import com.calc.test.framework.tags.Calculator;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(tags = {"@TC01_CalculatorTest"},
        format = {"pretty","html:target/html_report/TC01_CalculatorTest/",
                "json:target/cucumber-report/TC01_CalculatorTest.json"},
        features = {"src/test/resources/features/Calculator/Calculator.feature"},
        glue = {"com.calc.test"}
)
@Category({Calculator.class})
public class TC01_CalculatorTest {
}
