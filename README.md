# Selenium labs

This repository is for study and explore Selenium with other tools.


Current tools:
 - Selenium
 - Maven
 - Cucumber
 - JUnit
 


To run tests in a browser

`mvn -Dtest=RunnerClassOfYourDesiredBrowser# test`

Current browsers: 
 - Chrome `mvn -Dtest=ChromeRunner# test`
 - ChromeIncognito `mvn -Dtest=ChromeIncognitoRunner# test`


To run tests with Cucumber tags run:

`mvn -Dtest=RunnerClassOfYourDesiredBrowser# test -Dcucumber.options="--tags @yourDesiredTag"`


To run tests in diffrent browsers run: 

`mvn -Dtest=RunnerClassOfYourFirstBrowser# test; mvn -Dtest=RunnerClassOfYourSecondBrowser# test`
