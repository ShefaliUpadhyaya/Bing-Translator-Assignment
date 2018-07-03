package bingtranslatortest;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BingTranslatorTest {
	
	WebDriver driver;
	JavascriptExecutor js;
	BingTranslator bingTranslator;
	
	@BeforeClass
	public void launchBrowser() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
        js = (JavascriptExecutor)driver;	
		driver.get("https://www.bing.com/translator");
		bingTranslator = new BingTranslator(driver, js);
    }
	
	@Test(priority = 1)
	public void checkPageIsReady() {
		bingTranslator.urlLoadsCompletely();
	}
	
	@Test(priority = 2)
	public void check_Whether_AutoDetect_DropDown_Automatically_Detects_Language() {
		bingTranslator.automatically_Detect_Language_In_AutoDetect_DropDown();
	}
	
	@Test(priority = 3)
	public void clearButton_Should_Clear_Both_TextAreas() {
		bingTranslator.clearButton_Clears_Both_TextAreas();
	}
	
	@Test(priority = 4)
	public void check_Whether_AutoDetect_DropDown_Selects_A_Language() {
		bingTranslator.select_A_Language_From_DropDown_Options_In_AutoDetect();
	}
	
	@Test(priority = 5)
	public void check_Whether_Translating_DropDown_Selects_A_Language() {
		bingTranslator.select_A_Language_From_Translating_DropDown();
	}
	
	@Test(priority = 6)
	public void check_Whether_Swap_Functions_Correctly() {
		bingTranslator.swap_Languages_In_DropDowns();
	}
	
	@Test(priority = 7)
	public void translated_TextArea_Should_Not_Be_Editable() {
		bingTranslator.translated_TextArea_Not_Editable();
	}
	
	@Test(priority = 8)
	public void translated_Text_Copy_Button_Should_Copy_Text() {
		bingTranslator.copy_Translated_Text();
	}
	
	@Test(priority = 9)
	public void exceeding_Character_Limit_Should_Display_Error_Message() {
		bingTranslator.exceeding_Text_Limit_Gives_Error_Message();
	}
	
	@AfterClass
	public void closeBrowser() {
		driver.quit();
	}
}
