# thrall
Thrall is a testing framework that uses [Selenium WebDriver](https://www.seleniumhq.org/projects/webdriver/). 

The current possible browsers are Chrome and Firefox.

#### Motivation 
Thrall encapsulates part of Selenium classes, so that creating test scripts becomes easier. 

Also, Thrall provides an easy way to capture screenshots and provides some features to manage the test scenario. 

## Usage
Unfortunately, Thrall is not in [Maven Central](https://mvnrepository.com/repos/central) yet. So, is necessary to clone the project, using the command: 

    git clone https://github.com/lgigek/thrall
Then, is necessary to have the `.jar` file from Thrall in local Maven repository. You can get it from command:

    mvn clean install
After that, it is possible to use Thrall as a dependency on a project, adding the following code in the project's `pom.xml`:

    <dependency>
        <groupId>br.com.lgigek</groupId>
        <artifactId>thrall</artifactId>
        <version>1.0.0</version>
    </dependency>
