# automation-boilerplate
Boilerplate - Automation Testing (Eclipse + Selenium)

**Installations:**
1. Homebrew
2. Eclipse IDE
3. Selenium Server
4. TestNG


**Libraries:**
1. JRE System Library (Should be already there)
2. Test NG
3. Referenced Libraries (Inside `library` folder)


**Setup Instructions:**
1. Clone the repo
2. Get started.
	
**Setting up from scratch:**
1. Install Homebrew

2. Install Eclipse IDE using homebrew:
	> brew cask install eclipse-jee

3. Install Selenium Server. We can again get it installed using homebrew:
	> brew install selenium-server-standalone

4. Open Eclipse IDE and create a new Project.
	> File > New > Java Project > (Provide a project name) Next > Finish

	Our project name is `automation-boilerplate`

5. Create a package in your project `automationFramework`
	> Right click on Project name > New > Package (automationFramework) > Finish

6. Install TestNG. We can again get it installed following the below steps:
	```
	> Help > Install New Software... > Add
	> Enter Name
	> Paste the url https://dl.bintray.com/testng-team/testng-eclipse-release/ to Work with: text field and press enter
	> Next > Follow installation steps
	> Restart Eclipse
	```
And we are done.

7. If not imported, import the libraries manually.
	> Right click on Project name > Properties > JAVA Build Path > Libraries > Add Library > Select TestNG > Next > Finish > Apply and Close

8. Now, we'll be moving ahead towards creating our first test suite. Create a new TestNG Class.
	> Right click on package name (automationFramework) > New > Other > Select TestNG Class > Next > Fill Details > Finish
	
	Source folder: `/automation-boilerplate/src`  
	Package Name: `automationFramework`  
	ClassName: `FirstTestClass`  

9. Create a `testng.xml` in root of project which will be taking care of how our test suite executes with following data.
	```
	<?xml version="1.0" encoding="UTF-8"?>
	<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
	<suite name="Suite">
		<test thread-count="5" name="Test">
			<classes>
				<class name="automationFramework.FirstTestClass"/>
			</classes>
		</test> <!-- Test -->
	</suite> <!-- Suite -->
	```

10. Import the `Referenced Libraries` to the project.
	> Right click on project name (automation-boilerplate) > Properties > JAVA Build Path > Libraries > Add External JARs > Select all in Library folder > Finish > Apply and Close

11. Put a print statement inside the function within our TestNG class `FirstTestClass`
	> System.out.println("FirstTestCase ran successfully");

12. Now, run the test suite.
	> Right click on testng.xml > Run As > TestNG Suite

This should print `FirstTestCase ran successfully` in console. Hence out test suite is setup successfully.  
Now, we need to configure web drivers for `chrome` and `firefox` for the actual testing purpose which are there inside drivers folder.  
	`chromedriver` - For Chrome  
	`geckodriver` - For Firefox  

14. Set the driver property for browser in the function within `FirstTestClass`. Add:
	```
	System.setProperty("webdriver.chrome.driver", "/path/to/driver/chromedriver");
	```

15. Now use the `ChromeDriver` to launch chrome browser.
	```
	WebDriver driver = new ChromeDriver();
	driver.get("https://www.google.com");
	```

	The import statements should look like this:
	```
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.testng.annotations.Test;
	```

	For the `firefox` use `geckodriver`, set property using:
	```
	System.setProperty("webdriver.gecko.driver", "/path/to/driver/geckodriver");
	```

	and use:
	```
	WebDriver driver = new FirefoxDriver();
	driver.get("https://www.google.com");
	```

	the imports should also be updated as:
	```
	import org.openqa.selenium.firefox.FirefoxDriver;
	```

16. Again, run the test suite.
	> Right click on testng.xml > Run As > TestNG Suite

Now it should open chrome broswer with "https://www.google.com" as the url.
		
**Possible Issues:**
1. Driver Is Not Executable:
	Take the ownership of driver file
	> chmod 777 whateverdriver

	or from within Eclipse
		> Right Click the driver file > Properties > Tick Read, Write, Execute for Owner > Apply and Close
		
Let me know for any other issues.
