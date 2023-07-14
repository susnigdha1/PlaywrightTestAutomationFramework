**Setting up the framework**:
   
   Simply clone the project from GitHub URL using SSH. Then import it as a maven project
   in your preferred java IDE. Depending upon the choice of your IDE, you may need to 
   install below plugins:

   1. Maven
   2. Junit
   3. Spring

   **JDK-11 or above is required for the Playwright version used in this project**

   Remember to set Java_Home variable in your machine, and add that
   JDK path to your project.

**Running the tests in your local environment:**

   You can run the tests using Maven (Surefire plugin) command: `mvn clean test`

**Running tests in docker:**

   **Pre-requisites:**

      1. Docker desktop should be installed and configured in your local machine
      2. Docker daemon process should be running in your local machine

   **Steps to execute the tests in docker:**

      1. From the root directory of the project launch terminal (for MAC) or powershell (for Windows)
      2. Build a docker image using this command: `docker build -t playwrighttests --target test .`
      3. Before running this step, get the full path of your project directory, for MAC use
         pwd command from terminal and for windows right-click on the project root folder and
         copy the path reference - absolute path. You need to replace `C:\PlaywrightTestAutomationFramework`
         with your project root path, when running the docker image container using this command:
         `docker run -it --rm -v C:\PlaywrightTestAutomationFramework\target:/playwrightTests/target --name playwrighttests playwrighttests:latest` 

         The above command will directly execute the tests using mvn (surefire) inside the docker
         container and then will copy the target folder from the container to your local folder.
         This is achieved by this flag in the above command:
         `-v C:\PlaywrightTestAutomationFramework\target:/playwrightTests/target`

**Brief overview of the current offerings of this framework:**

   1. This project structure is derived from Maven test project structure. If you have earlier worked with Maven,
   then you are already familiar with the high level folder structure of this project.
   2. Refer to the pom.xml file to understand the dependencies required to build the project.
   3. The `src.test.java.springboot.playwright.cucumber` package organizes the code base
   4. The `configurations` package keeps the needed configuration files for Spring-boot test context
   5. The `hooks` package contains the hooks class file. It is responsible for mapping the cucumber hooks, the PlaywrightBrowser dependency
      is injected using constructor
   6. The `pageObjects` package contains the Page Object classes. For simplified example, only one such classfile is kept. The PlaywrightBrowser dependency
      is injected using constructor. If you carefully read this file, you will get good idea about how to use different playwright locator strategies
   7. The `playwright` package contains the implementation of factory-design pattern using spring-boot. This helps in initializing
      and supplying the playwright browser, browser context and page, depending upon the test parameter passed through `TestData.properties` file 
   8. The `runners` package contains the Junit5 style cucumber runner, responsible for executing the tests
   9. The stepDefinitions package contains the Cucumber step definition classes. For simplified example, only one such classfile is kept.
   10. All Cucumber feature files are kept under the `resources/features` folder. Feature files are duplicated with simple tweak
       in the file names, feature names and scenario names on purpose, to prove that parallel test execution is working. This folder also have `junit-platform.properties`
       file which contains configuration details for both Junit5 and Cucumber. This folder also contains another properties file
       `testconfigurations.properties`, in this file some common test data are kept, which will be accessed as runtime, and it serves as a bit of
       an abstraction layer for the Cucumber feature file, to maintain better hygiene.
   12. The `dockerfile` is kept as a simple example of how you can run the tests of this framework within a container.
        In subsequent updates to this framework, new capabilities will be added to scale up the test infrastructure.

**Planned future offerings of this framework:**
   1. The present implementation run tests in parallel in one browser. But it does not run tests across multiple browser.
      Will address this concern in the next release
   2. More coding examples to handle alerts, browser tabs, shadow dom's etc
   3. API testing