import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.testobject.TestObject
import org.openqa.selenium.WebElement
import commonkeyword.procedure
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.Files
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import com.kms.katalon.core.webui.driver.DriverFactory


def store_front_url = getStoreFrontUrl(store_name, raw_shopify_domain)
def collection_page = getPage(store_front_url, "collections/all")
String save_path = Paths.get("img", "storefront", store_name)
def check_dir = Paths.get(save_path)
def source_image_count = 10
String save_path_diff = Paths.get("img", "storefront", store_name, "diff")

WebUI.navigateToUrl(store_front_url)
CustomKeywords.'commonkeyword.procedure.checkPassword'(input_password, store_password)
CustomKeywords.'commonkeyword.procedure.checkSavePath'(save_path)
CustomKeywords.'commonkeyword.procedure.checkSavePath'(save_path_diff)

if (GlobalVariable.is_first_valid_storefront == true) {

	screenShotStoreFront(store_front_url, save_path, source_name_format, source_image_count)
	
	screenShotAllProduct(collection_page, save_path, source_name_format, 3, source_image_count)
	
	CustomKeywords.'excelkeyword.excel.updateValueToSheet'(GlobalVariable.excel_file_path, GlobalVariable.sheet_name, 4, store_name, 31, save_path)
	
} else if (GlobalVariable.is_first_valid_storefront == false) {
	
	screenShotStoreFront(store_front_url, save_path, current_name_format, source_image_count)
	
	screenShotAllProduct(collection_page, save_path, current_name_format, 3, source_image_count)
	
	CustomKeywords.'excelkeyword.excel.updateValueToSheet'(GlobalVariable.excel_file_path, GlobalVariable.sheet_name, 4, store_name, 31, save_path)
}


//PROCEDURE - This section will contains actions for scripts above
//Screen short all product page
void screenShotAllProduct (String collection_page, String save_path, String format_file, int run_time, def source_image_count) {
	WebUI.navigateToUrl(collection_page)
	List<String> url = WebUI.getAllLinksOnCurrentPage(false, null)
	List<String> product_crawed = url.findAll{ it =~ /.*products.*/ }
	def check_item = product_crawed.size()
	if (check_item != 0) {
		if (check_item <= run_time == true) {
			for (int i= 0; i < check_item; i++) {
				String product_url = product_crawed.get(i)
				navigateToAllProduct(product_url, save_path, format_file, source_image_count)
			}
		} else if (check_item > run_time == true) {
			for (int i= 0; i < run_time; i++) {
				String product_url = product_crawed.get(i)
				navigateToAllProduct(product_url, save_path, format_file, source_image_count)
			}
		}
	} else if (check_item == 0) {
		println('No products')
	}
}
//Screen shot common page
void screenShotStoreFront (String store_front_url, String save_path, String format_file, def source_image_count) {
	WebUI.navigateToUrl(store_front_url)
	WebUI.maximizeWindow(FailureHandling.OPTIONAL)
	for (String store_url : store_url_list) {
		WebUI.navigateToUrl(store_front_url+store_url)
		image_name = addPrefix(store_name ,store_url.replaceAll("[^a-zA-Z0-9_]|[:]", "_"))
		performActionWhileScrolling(save_path, image_name, format_file, source_image_count)
	}
}

//Navigate to product link and screencapture
void navigateToAllProduct (String product_url, String save_path, String format_file, def source_image_count) {
	WebUI.navigateToUrl(product_url)
	image_name = product_url.replace("https://", "").replaceAll("[^a-zA-Z0-9_]|[:]", "_")
	performActionWhileScrolling(save_path, image_name, format_file, source_image_count)
}

//Scroll and capture image until scroll bar reached the end of page
void performActionWhileScrolling(String save_path, String image_name, String format_file, def source_image_count) {
	WebUI.delay(delay_time_for_widget)
	int maxScrollPosition = WebUI.executeJavaScript("return Math.max( (document.documentElement.scrollHeight || document.body.scrollHeight) - window.innerHeight );", null)
    int scrollPosition = 0
    int i = 1
	if (scrollPosition == maxScrollPosition) {
		save_image = getImage(save_path, image_name, format_file)
		source_image = getImage(save_path, image_name, source_name_format)
		current_image = getImage(save_path, image_name, current_name_format)
		screenShotAndCompare(save_image, source_image, current_image, save_path, source_image_count)
	} else {
		while (scrollPosition < maxScrollPosition) {
			scrollPosition = WebUI.executeJavaScript("return window.pageYOffset || document.documentElement.scrollTop;", null)
			save_image = getImage(save_path, image_name+"_"+i, format_file)
			source_image = getImage(save_path, image_name+"_"+i, source_name_format)
			current_image = getImage(save_path, image_name+"_"+i, current_name_format)
			screenShotAndCompare(save_image, source_image, current_image, save_path, source_image_count)
			i++
			if (scrollPosition == maxScrollPosition || i == 10) {
				println('Scrollbar has reached the end of the page.')
				break;
			} else {
				WebUI.executeJavaScript("window.scrollBy(0, window.innerHeight);", null)
			}
		}
	}
}

void screenShotAndCompare (String save_image, String source_image, String current_image, String save_path, def source_image_count) {
	WebUI.delay(delay_time_for_screen_shot)
	CustomKeywords.'kms.turing.katalon.plugins.visualtesting.ScreenCapture.takeScreenshot'(save_image, FailureHandling.OPTIONAL)
	if ( GlobalVariable.is_first_valid_storefront == false) {
		if (CustomKeywords.'kms.turing.katalon.plugins.visualtesting.ImageComparison.areMatched'(source_image, current_image, save_path, FailureHandling.OPTIONAL) == false) {
			a++
			CustomKeywords.'excelkeyword.excel.updateValueToSheet'(GlobalVariable.excel_file_path, GlobalVariable.sheet_name, 4, store_name, 35, a.toString()+"/${source_image_count} Failed")
		} else {
			//NOT THING
		}
	} else if ( GlobalVariable.is_first_valid_storefront == true) {
		// DO NOT THING - No need to compare images
	}
}

def getStoreFrontUrl(String store_name, String raw_shopify_domain) {
	return "https://${store_name}${raw_shopify_domain}"
}

def getImage(String save_path, String image_name, String destination) {
	return "${save_path}/${image_name}${destination}"
}

def getPage(String store_front_url, String page) {
	return "${store_front_url}/${page}"
}

def addPrefix(String common_page_prefix, String url) {
	return "${common_page_prefix}${url}"
}