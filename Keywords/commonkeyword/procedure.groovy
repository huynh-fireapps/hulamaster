package commonkeyword

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import org.openqa.selenium.Keys as Keys
import java.nio.file.Path
import java.nio.file.Paths
import internal.GlobalVariable

public class procedure {

	//Check save path exist or not an return true/false value. Create new folder if value = true
	@Keyword
	void checkSavePath (String save_path) {
		File folder = new File(save_path)

		if (!folder.exists()) {
			boolean success = folder.mkdir()
		} else {
			//No need to create folder
		}
	}

	//Choose account shopify if it need
	@Keyword
	void checkAccountSelection () {
		WebUI.switchToDefaultContent()
		WebUI.delay(2)
		String current_link = WebUI.getUrl()
		if (current_link.contains("https://accounts.shopify.com/select") == true) {
			WebUI.click(findTestObject('Object Repository/Shopify/account_shopify', ['account_email' : GlobalVariable.account_email]))
		} else {
			//Do Not Thing
		}
	}


	//Check 500 error screen then reload page if it exist
	@Keyword
	void checkReloadPage () {
		if (WebUI.verifyElementPresent(findTestObject('Object Repository/Shopify/div_reload_shopify_page'), 2,FailureHandling.OPTIONAL) == true) {
			WebUI.refresh()
		} else {
			//do not thing
		}

	}

	//Check store front password. If the page displays then input password and login
	@Keyword
	void checkPassword (TestObject input_password, String store_password) {
		WebUI.delay(3)
		String current_link = WebUI.getUrl()
		if (current_link.contains("password") == true) {
			WebUI.setText(input_password, store_password)
//			WebUI.sendKeys(input_password, Keys.chord(Keys.ENTER))
			WebUI.click(findTestObject('Object Repository/StoreFront/btn_enter'))
		} else {
			//Do not thing
		}
	}

	//Turn off chat box
	@Keyword
	void turnOffCrispChat () {
		WebUI.delay(2)
		WebUI.switchToDefaultContent()
		if (WebUI.verifyElementPresent(findTestObject('Object Repository/Shopify/span_chat_box'), 3,FailureHandling.OPTIONAL) == true) {
			WebUI.sendKeys(findTestObject('Object Repository/Shopify/span_chat_box'), Keys.chord(Keys.ESCAPE))
			WebUI.switchToDefaultContent()
		} else {
			//Do not thing
			WebUI.switchToDefaultContent()
		}
	}

	//Wait for element PRESENT
	@Keyword
	void waitForElementPresent (TestObject test_object, int retry) {
		def success = false
		int i = 0
		while (!success && i<retry) {
			try {
				WebUI.verifyElementPresent(test_object, 5)
				success = true
			} catch (Exception e) {
				i++
				WebUI.delay(5)
			}
		}
	}

	//Wait for element CLICKABLE
	@Keyword
	void waitForElementClickable (TestObject test_object, int retry) {
		def success = false
		int i = 0
		while (!success && i<retry) {
			try {
				WebUI.verifyElementClickable(test_object, 5)
				success = true
			} catch (Exception e) {
				i++
				WebUI.delay(5)
			}
		}
	}

	//Wait for element NOT Present
	@Keyword
	void waitForElementNotPresent (TestObject test_object, int retry) {
		def success = false
		int i = 0
		while (!success && i<retry) {
			try {
				WebUI.verifyElementNotPresent(test_object, 3)
				success = true
			} catch (Exception e) {
				i++
				WebUI.delay(5)
			}
		}
	}
	
	@Keyword
	//Check import button by Extension - In case product is maximum of review imported
	void checkImportButtonExtension (int retry) {
		def success = false
		int i = 2
		while (!success && i<retry) {
			try {
				WebUI.switchToDefaultContent()
				WebUI.click(findTestObject('Object Repository/AliReview/ImportReview/btn_import_dynamic_index', ["index": i]))
				WebUI.verifyElementPresent(findTestObject('Object Repository/AliReview/ImportReview/input_product_url_ali'), 5)
				success = true
			} catch (Exception e) {
				i++
			}
		}
	}
	
	@Keyword
	//Check import button by API - In case product is maximum of review imported
	void checkImportButtonAPI (int retry) {
		def success = false
		int i = 2
		while (!success && i<retry) {
			try {
				WebUI.switchToDefaultContent()
				WebUI.click(findTestObject('Object Repository/AliReview/ImportReview/btn_import_dynamic_index', ["index": i]))
				WebUI.verifyElementPresent(findTestObject('Object Repository/AliReview/ImportReview/btn_skip_now'), 5)
				success = true
			} catch (Exception e) {
				i++
			}
		}
	}
}
