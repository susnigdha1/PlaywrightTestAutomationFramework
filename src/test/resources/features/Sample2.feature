Feature: Second Shopping cart display
  @Regression
  Scenario: Second Add an item to the shopping cart then verify the selected quantity and item name
    When I launch "https://www.saucedemo.com/" the application
    And I log in with valid credentials
    And I land on product selection page
    And I right click and open "Sauce Labs Bike Light" item in another browser tab
    And I select "Sauce Labs Bike Light" item from the list of available items for purchase
    And I click on the shopping cart link
    And I verify that the following product and quantity are selected
    |ProductName          |Quantity|
    |Sauce Labs Bike Light|1      |
    And I perform checkout




