$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("src/test/resources/features/Calculator/Calculator.feature");
formatter.feature({
  "line": 1,
  "name": "i want to view Seat Map",
  "description": "",
  "id": "i-want-to-view-seat-map",
  "keyword": "Feature"
});
formatter.scenarioOutline({
  "line": 5,
  "name": "Test the given functions for the calculator",
  "description": "",
  "id": "i-want-to-view-seat-map;test-the-given-functions-for-the-calculator",
  "type": "scenario_outline",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 4,
      "name": "@TC01_CalculatorTest"
    }
  ]
});
formatter.step({
  "line": 6,
  "name": "user is on online calculator full screen",
  "keyword": "Given "
});
formatter.step({
  "line": 7,
  "name": "enter the \"\u003cPrimary Number\u003e\", \"\u003cSecondary Number\u003e\" details for the \"\u003cFunction Type\u003e\" given",
  "keyword": "When "
});
formatter.step({
  "line": 8,
  "name": "compare the result generated with \"\u003cExpected Result\u003e\" value",
  "keyword": "Then "
});
formatter.examples({
  "line": 10,
  "name": "",
  "description": "",
  "id": "i-want-to-view-seat-map;test-the-given-functions-for-the-calculator;",
  "rows": [
    {
      "cells": [
        "Function Type",
        "Primary Number",
        "Secondary Number",
        "Expected Result"
      ],
      "line": 11,
      "id": "i-want-to-view-seat-map;test-the-given-functions-for-the-calculator;;1"
    },
    {
      "comments": [
        {
          "line": 12,
          "value": "#| Subtraction   | 123456789      | 123456788        | ResultOne                                 |"
        },
        {
          "line": 13,
          "value": "#| Subtraction   | 123456.789     | 123455.788       | ResultOnePointZeroZeroOne                 |"
        },
        {
          "line": 14,
          "value": "#| Subtraction   | 123456         | 234558           | MinusOneOneOneOneZeroTwo                  |"
        },
        {
          "line": 15,
          "value": "#| Division      | 369            | 3                | OneTwoThree                               |"
        },
        {
          "line": 16,
          "value": "#| Division      | 369.369        | 3.3              | OneOneOnePointNineThree                   |"
        },
        {
          "line": 17,
          "value": "#| Division      | 123            | 456              | ZeroPointTwoSixNineSevenThreeSixEightFour |"
        },
        {
          "line": 18,
          "value": "#| CE            | 123            |                  | LaunchZeroResult                          |"
        }
      ],
      "cells": [
        "CE",
        "123.456",
        "",
        "LaunchZeroResult"
      ],
      "line": 19,
      "id": "i-want-to-view-seat-map;test-the-given-functions-for-the-calculator;;2"
    }
  ],
  "keyword": "Examples"
});
formatter.before({
  "duration": 264100,
  "status": "passed"
});
formatter.before({
  "duration": 7712400,
  "status": "passed"
});
formatter.before({
  "duration": 24371920200,
  "status": "passed"
});
formatter.scenario({
  "comments": [
    {
      "line": 12,
      "value": "#| Subtraction   | 123456789      | 123456788        | ResultOne                                 |"
    },
    {
      "line": 13,
      "value": "#| Subtraction   | 123456.789     | 123455.788       | ResultOnePointZeroZeroOne                 |"
    },
    {
      "line": 14,
      "value": "#| Subtraction   | 123456         | 234558           | MinusOneOneOneOneZeroTwo                  |"
    },
    {
      "line": 15,
      "value": "#| Division      | 369            | 3                | OneTwoThree                               |"
    },
    {
      "line": 16,
      "value": "#| Division      | 369.369        | 3.3              | OneOneOnePointNineThree                   |"
    },
    {
      "line": 17,
      "value": "#| Division      | 123            | 456              | ZeroPointTwoSixNineSevenThreeSixEightFour |"
    },
    {
      "line": 18,
      "value": "#| CE            | 123            |                  | LaunchZeroResult                          |"
    }
  ],
  "line": 19,
  "name": "Test the given functions for the calculator",
  "description": "",
  "id": "i-want-to-view-seat-map;test-the-given-functions-for-the-calculator;;2",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 4,
      "name": "@TC01_CalculatorTest"
    }
  ]
});
formatter.step({
  "line": 6,
  "name": "user is on online calculator full screen",
  "keyword": "Given "
});
formatter.step({
  "line": 7,
  "name": "enter the \"123.456\", \"\" details for the \"CE\" given",
  "matchedColumns": [
    0,
    1,
    2
  ],
  "keyword": "When "
});
formatter.step({
  "line": 8,
  "name": "compare the result generated with \"LaunchZeroResult\" value",
  "matchedColumns": [
    3
  ],
  "keyword": "Then "
});
formatter.match({
  "location": "CalculatorStepsImpl.userIsOnOnlineCalculatorFullScreen()"
});
formatter.result({
  "duration": 1566772300,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "123.456",
      "offset": 11
    },
    {
      "val": "",
      "offset": 22
    },
    {
      "val": "CE",
      "offset": 41
    }
  ],
  "location": "CalculatorStepsImpl.enterTheDetailsForTheGiven(String,String,String)"
});
formatter.result({
  "duration": 18027847600,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "LaunchZeroResult",
      "offset": 35
    }
  ],
  "location": "CalculatorStepsImpl.compareTheResultGeneratedWithValue(String)"
});
formatter.result({
  "duration": 20779825400,
  "status": "passed"
});
formatter.after({
  "duration": 507600,
  "status": "passed"
});
formatter.after({
  "duration": 2468322700,
  "status": "passed"
});
});