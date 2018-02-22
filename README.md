# Selenium labs

To run tests in a browser

`mvn test -Dtest=RunnerClassOfYourBrowser`

Current browsers: 
 - Chrome `mvn test -Dtest=ChromeRunner`
 - ChromeIncognito `mvn test -Dtest=ChromeIncognitoRunner`


To run tests with Cucumber tags run:

`mvn test -Dtest=RunnerClassOfYourBrowser -Dcucumber.options="--tags @yourTag"`


To run tests in diffrent browsers run: 

`mvn test -Dtest=RunnerClassOfYourFirstBrowser; mvn test -Dtest=RunnerClassOfYourSecondBrowser`

Evidences are taken after a scenario fails or calling `EvidenceHandler.getInstance().takeEvidence("EvidenceName");` during test script.
Evidences are generated at /target/evidences/[browser-name]/[scenario-tags]/[executiontime]
