Feature: i want to view Seat Map


  @TC01_CalculatorTest
  Scenario Outline: Test the given functions for the calculator
    Given user is on online calculator full screen
    When enter the "<Primary Number>", "<Secondary Number>" details for the "<Function Type>" given
    Then compare the result generated with "<Expected Result>" value

    Examples:
      | Function Type | Primary Number | Secondary Number | Expected Result                           |
     #| Subtraction   | 123456789      | 123456788        | ResultOne                                 |
     #| Subtraction   | 123456.789     | 123455.788       | ResultOnePointZeroZeroOne                 |
     #| Subtraction   | 123456         | 234558           | MinusOneOneOneOneZeroTwo                  |
     #| Division      | 369            | 3                | OneTwoThree                               |
     #| Division      | 369.369        | 3.3              | OneOneOnePointNineThree                   |
     #| Division      | 123            | 456              | ZeroPointTwoSixNineSevenThreeSixEightFour |
     #| CE            | 123            |                  | LaunchZeroResult                          |
      | CE            | 123.456        |                  | LaunchZeroResult                          |
