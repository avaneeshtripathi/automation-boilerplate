package test;

import java.io.*;
import java.util.*;

import org.testng.*;
import org.testng.xml.*;

import helper.*;

public class TESTNG_FACTORY {
	@SuppressWarnings("deprecation")
	public void runTestNGTest(String browser){
		// Create instance of TestNG listener
		TestListenerAdapter tla = new TestListenerAdapter();
		
		//Create an instance on TestNG
		TestNG myTestNG = new TestNG();

		//Create an instance of XML Suite and assign a name for it.
		XmlSuite mySuite = new XmlSuite();
		mySuite.setName("Suite");
		mySuite.setParallel(XmlSuite.ParallelMode.NONE);
		mySuite.setPreserveOrder(true);
		
		//Create a list of XmlTests and add the XMLTest you created earlier to it.
		List<XmlTest> myTests = new ArrayList<XmlTest>();
		
		List <LinkedHashMap<String, String>> testCaseList = ExcelUtils.getTestCaseData(0);

		for (LinkedHashMap<String, String> testCase:testCaseList) {
			if (!testCase.get("execute").equalsIgnoreCase("yes")) {
				continue;
			}
			Map<String, String> testngParams = new HashMap<String, String> ();
			testngParams.put("browser", browser);
			testngParams = setDynamicTestNgParams(testCase, testngParams);
			XmlTest testName = createXmlTest(testCase.get("flow"), testCase.get("className"), mySuite, testngParams);
			testName.setParallel(XmlSuite.ParallelMode.NONE);
			myTests.add(testName);
		}

		//add the list of tests to your Suite.
		mySuite.setTests(myTests);
		
		//Add the suite to the list of suites.
		List<XmlSuite> mySuites = new ArrayList<XmlSuite>();
		mySuites.add(mySuite);
		
		//Set the list of Suites to the testNG object you created earlier.
		myTestNG.setXmlSuites(mySuites);
		mySuite.setFileName("testng.xml");
		
		//Create physical XML file based on the virtual XML content
		for(XmlSuite suite : mySuites) {
			createXmlFile(suite);
		}
		Utils.logger("File created successfully.");
		myTestNG.addListener(tla);
//		myTestNG.run();
	}
	
	private static XmlTest createXmlTest(String testName, String className, XmlSuite mySuite, Map<String, String> testngParams) {
		//Create an instance of XmlTest and assign a name for it.
		XmlTest myTest = new XmlTest(mySuite);
		myTest.setName(testName);
		
		// Set the thread count for execution
//		myTest.setThreadCount(1);
		
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
    		Utils.logger("Error creating XML File");
    		e.printStackTrace();
    	}
    }
    
    private static Map<String, String> setDynamicTestNgParams (LinkedHashMap<String, String> testCase, Map<String, String> testngParams) {
    	Set<String> keys = testCase.keySet();
    	for (String key:keys) {
    		if(!key.startsWith("param:") || testCase.get(key) == null || testCase.get(key).toString() == "") {
    			continue;
    		}
    		testngParams.put(key.replace("param:", ""), testCase.get(key));
    	}
    	return testngParams;
    }
   
    //Main Method
    public static void main (String args[]) {
    	TESTNG_FACTORY tf = new TESTNG_FACTORY();
    	tf.runTestNGTest("chrome");
    }
}