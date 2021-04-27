package Runner;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.codoid.products.exception.FilloException;

import IB.Test_Functions.Function;
import Reusable_Methods.Utility;
import managers.ExtentManager;

public class Executor {

	
	private static ExtentReports extent=null;
	public static  Hashtable<String,String> Testcase_Details=new Hashtable<String,String>();
	public static  Hashtable<String,String> KeywordSheet_values=new Hashtable<String,String>();
	
	
	public static void main(String[] args) throws FilloException, IOException {
		

		Utility util = new Utility();
		String App = "";
		try {
			App = util.readProperty("Application").toString();
			//System.out.println("Application name : "+App);
			
		}catch(Exception e) {
			App = ""; 
			System.out.println("Please check project setting and give any application name to proceed..");
		}
		if(!App.trim().isEmpty()) {
			
			ArrayList<String> TestCases = new ArrayList<String>();
			TestCases = util.get_Driver_TestCaseID("TestCase_Control");
			try {
				for(String each_TestCases_ID:TestCases) {
					util.Data.clear();
					extent = ExtentManager.ExtentReports_CreateInstance();
					ArrayList<String> Testkeyword_Seq = new ArrayList<String>();
					Testcase_Details = util.get_Driver_Details(each_TestCases_ID);
					ExtentTest parent= extent.createTest("TesCase ID : "+Testcase_Details.get("TestCase_ID") , "TesCase Description : "+Testcase_Details.get("TestCase_Description"));
					util.parentTest.set(parent);
					//Testkeyword_Seq = util.getTestData_SeqNos(each_TestCases_ID, "Keywords");
						//for(String each_Seq:Testkeyword_Seq) {
							
							//KeywordSheet_values = util.get_Keywords_Databindings(each_TestCases_ID, "Keywords", each_Seq);
							
							System.out.println("Method Name : "+Testcase_Details.get("MethodName"));
							System.out.println("DataBinding is : "+Testcase_Details.get("DataBinding"));
							System.out.println("SheetName is : "+Testcase_Details.get("SheetName"));
							
							//Test Data***************************************************************
							util.Data = util.get_TestData(Testcase_Details.get("DataBinding"), Testcase_Details.get("SheetName"));
							util.DataLogin = util.get_TestData(Testcase_Details.get("Environment_DataBinding"), Testcase_Details.get("Environment_SheetName"));
							
							try {
								
								Class<?> DynamicClass=Class.forName(App+".Test_Functions.Function");
								
								Method DynamicMethod = DynamicClass.newInstance().getClass().getDeclaredMethod(Testcase_Details.get("MethodName"));
								DynamicMethod.invoke(DynamicClass.newInstance());
								
								extent.flush();
								
							}catch(Exception e) {
								e.printStackTrace();
								extent.flush();
								System.out.println("Exception occure");
							}
							
							
						//}
					
				}
			}catch(Exception e) {
				e.printStackTrace();
				extent.flush();
			}
			
			
		}else {
			System.out.println("Please check project setting and give any application name to proceed..");
		}
		
	}
	
	
	
	
	
	
	
	
}
