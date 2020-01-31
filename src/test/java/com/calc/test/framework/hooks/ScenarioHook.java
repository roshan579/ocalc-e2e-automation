package com.calc.test.framework.hooks;

import cucumber.api.Scenario;
import cucumber.api.java.Before;

/**
 * Created by Roshan
 */
public class ScenarioHook {

    public static Scenario scenario;

    public static Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    @Before(order = 1)
    public void KeepSceario(Scenario scenario) {
        this.scenario = scenario;
        this.setScenario(scenario);
    }
}
