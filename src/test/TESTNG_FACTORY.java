package test;

import java.io.*;
import java.util.*;
import org.testng.TestNG;
import org.testng.xml.*;

public class TESTNG_FACTORY {
	public void runTestNGTest(Map<String,String> testngParams){
		//Create an instance on TestNG
		TestNG myTestNG = new TestNG();

		//Create an instance of XML Suite and assign a name for it.
		XmlSuite mySuite = new XmlSuite();
		mySuite.setName("Suite");
		mySuite.setParallel(XmlSuite.ParallelMode.NONE);
		
		//Create a list of XmlTests and add the XMLTest you created earlier to it.
		List<XmlTest> myTests = new ArrayList<XmlTest>();
		
		// LOGIN STUFF STARTS HERE
		XmlTest testLogin = createXmlTest("TestLogin", "test.Login", mySuite, testngParams);
		myTests.add(testLogin);
		
		// SIGNUP STUFF STARTS HERE
		XmlTest testSignup = createXmlTest("TestSignup", "test.Signup", mySuite, testngParams);
		myTests.add(testSignup);

		//add the list of tests to your Suite.
		mySuite.setTests(myTests);
		
		//Add the suite to the list of suites.
		List<XmlSuite> mySuites = new ArrayList<XmlSuite>();
		mySuites.add(mySuite);
		
		//Set the list of Suites to the testNG object you created earlier.
		myTestNG.setXmlSuites(mySuites);
		mySuite.setFileName("myTemp.xml");
		myTestNG.run();
		
		//Create physical XML file based on the virtual XML content
		for(XmlSuite suite : mySuites) {
			createXmlFile(suite);
		}
		System.out.println("File created successfully.");
	}
	
	private static XmlTest createXmlTest(String testName, String className, XmlSuite mySuite, Map<String, String> testngParams) {
		//Create an instance of XmlTest and assign a name for it.
		XmlTest myTest = new XmlTest(mySuite);
		myTest.setName(testName);
		
		// Set the thread count for execution
		myTest.setThreadCount(1);
		
		// Execute the test classes in order
		myTest.setPreserveOrder(true);
		
		//Add any parameters that you want to set to the Test.
		myTest.setParameters(testngParams);
		
		//Create a list which can contain the classes that you want to run.
		List<XmlClass> loginClasses = new ArrayList<XmlClass>();
		loginClasses.add(new XmlClass(className));
		
		//Assign that to the XmlTest Object created earlier.
		myTest.setXmlClasses(loginClasses);
		
		return myTest;
	}

	//This method will create an XML file based on the XmlSuite data 
    private static void createXmlFile(XmlSuite mSuite) {
    	FileWriter writer;
    	try {
    		writer = new FileWriter(new File("testng.xml"));
    		writer.write(mSuite.toXml());
    		writer.flush();
    		writer.close();
    	} catch (IOException e) {
    		System.out.println("Error creating XML File");
    		e.printStackTrace();
    	}
    }
   
    //Main Method
    public static void main (String args[]) {
    	TESTNG_FACTORY tf = new TESTNG_FACTORY();

    	//This Map can hold your TestNG Parameters.
    	Map<String,String> testngParams = new HashMap<String,String> ();
    	testngParams.put("browser", "chrome");
    	tf.runTestNGTest(testngParams);
    }
}