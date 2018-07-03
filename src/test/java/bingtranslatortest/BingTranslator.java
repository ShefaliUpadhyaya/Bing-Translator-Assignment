package bingtranslatortest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class BingTranslator {
	
	WebDriver driver;
	JavascriptExecutor js;
	Select autoDetectSelect, translatingToSelect;
	String selection1 = null, selection2 = null;
	
	@FindBy(id = "t_sl")
	WebElement autoDetectDropDown;
	
	@FindBy(id = "t_tl")
	WebElement translatingToDropDown;
	
	@FindBy(id = "t_sv")
	WebElement textArea;
	
	@FindBy(id = "t_tv")
	WebElement translatedTextArea;
	
	@FindBy(id = "t_revIcon")
	WebElement swap;
	
	@FindBy(id = "t_edc")
	WebElement clearButton;
	
	@FindBy(xpath = "//*[@id=\"t_copyIcon\"]")
	WebElement copyButton;
	
	@FindBy(xpath = "//*[@id=\"copy_result\"]")
	WebElement copy;
	
	@FindBy(xpath = "//*[@id=\"t_long\"]")
	WebElement error_msg;
	
	public BingTranslator(WebDriver driver, JavascriptExecutor js) {
		this.driver = driver;
		this.js = js;
		PageFactory.initElements(driver, this);
		autoDetectSelect = new Select(autoDetectDropDown);
		translatingToSelect = new Select(translatingToDropDown);
	}
	
	public void urlLoadsCompletely() {
		if (js.executeScript("return document.readyState").toString().equals("complete")){ 
			   System.out.println("Page loaded.");
			   return; 
		} 
	}
	
	public void automatically_Detect_Language_In_AutoDetect_DropDown() {
		textArea.sendKeys("Merci.");
		try {
		    Thread.sleep(5000);
	    }
		catch (InterruptedException e) {} 
		selection1 = autoDetectSelect.getFirstSelectedOption().getText();
		assertEquals(translatedTextArea.getAttribute("value").toString(), "Thank you.");
		//assertEquals(translatedTextArea.getText(), "Thank you.");
		assertTrue(selection1.contains("French"));
	}
	
	public void clearButton_Clears_Both_TextAreas() {
		clearButton.click();
		assertTrue(textArea.getText().equals(""));
		assertTrue(translatedTextArea.getText().equals(""));
	}
	
	public void select_A_Language_From_DropDown_Options_In_AutoDetect() {
		autoDetectSelect.selectByVisibleText("Hindi");
		selection1 = autoDetectSelect.getFirstSelectedOption().getText();
		assertEquals(selection1, "Hindi");
	}
	
	public void select_A_Language_From_Translating_DropDown() {
		translatingToSelect.selectByVisibleText("English");
		selection2 = translatingToSelect.getFirstSelectedOption().getText();
		assertEquals(selection2, "English");
	}
	
	public void swap_Languages_In_DropDowns() {
		swap.click();
		selection1 = autoDetectSelect.getFirstSelectedOption().getText();
		selection2 = translatingToSelect.getFirstSelectedOption().getText();
		assertEquals(selection1, "English");
		assertEquals(selection2, "Hindi");
	}
	
	public void translated_TextArea_Not_Editable() {
		assertEquals(translatedTextArea.getAttribute("readonly"), "true");
	}
	
	public void copy_Translated_Text() {
		translatingToSelect.selectByVisibleText("French");
		textArea.sendKeys("Welcome");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			System.out.println(e1.getMessage());
		}
		copyButton.click();
		assertEquals(copy.getText(), "Copied!");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		String result;
		try {
			result = (String) clipboard.getData(DataFlavor.stringFlavor);
			assertEquals(result, translatedTextArea.getAttribute("value").toString());
			assertEquals(result, "Bienvenue");
		} catch (UnsupportedFlavorException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		clearButton.click();
	}
	
	public void exceeding_Text_Limit_Gives_Error_Message() {
		String str = "We have to be there. ";
		for(int i=1;i<=250;i++)
			textArea.sendKeys(str);
		assertEquals(error_msg.getText(), "That’s too much text to translate at once. Try entering less");
	}
	
}
