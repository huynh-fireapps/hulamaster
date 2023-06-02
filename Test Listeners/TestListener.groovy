import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable as GlobalVariable

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import com.kms.katalon.core.configuration.RunConfiguration
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.chrome.ChromeOptions

class TestListener {
	
    @BeforeTestSuite
    def openBrowser() {
        WebUI.openBrowser('')
		WebUI.maximizeWindow()
    }
//	@BeforeTestSuite
	def selectProfile() {
		System.setProperty("webdriver.chrome.driver", DriverFactory.getChromeDriverPath());
		String chromeProfilePath = RunConfiguration.getProjectDir() + "/Chrome Profile/";
		ChromeOptions chromeProfile = new ChromeOptions();
		chromeProfile.addArguments("--user-data-dir=" + chromeProfilePath);
		chromeProfile.addArguments("--profile-directory="+GlobalVariable.chrome_profile_name);
//		chromeProfile.addExtensions(new File("D:\\Project\\AR-Basic\\Data\\alireviews_extension.crx"))
		WebDriver driver = new ChromeDriver(chromeProfile);
		driver.get("https://www.google.com");
		DriverFactory.changeWebDriver(driver)
		WebUI.maximizeWindow()
	}
//	@BeforeTestCase
	def skipStatus(TestCaseContext testCaseContext) {
		WebUI.maximizeWindow()
		Map<String, Object> map = testCaseContext.getTestCaseVariables()
		String setting_status = map.get("setting_status")
		if(setting_status=='Passed') {
			testCaseContext.skipThisTestCase()
		}
	}
//	@BeforeTestCase
	def skipChargePlan(TestCaseContext testCaseContext) {
		Map<String, Object> map = testCaseContext.getTestCaseVariables()
		String app_plan = map.get("app_plan")
		if(app_plan=='Free') {
			testCaseContext.skipThisTestCase()
		}
	}
}