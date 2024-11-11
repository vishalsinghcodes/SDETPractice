package dockerRelatedStuff;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import bsh.Capabilities;

public class ForParallelTest2 {

	@Test
	public void Test01() throws MalformedURLException {

		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(CapabilityType.BROWSER_NAME, "chrome");
		RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
		driver.get("http://www.google.com");
		System.out.println(driver.getTitle());
		org.openqa.selenium.Capabilities capabilities = driver.getCapabilities();
		System.out.println(capabilities.getCapability("platformName"));
		System.out.println(Thread.currentThread().getId());
	//	System.out.println(capabilities.getCapability("selenium.node.name"));
		driver.quit();

	}
	
	@Test
	public void Test02() throws MalformedURLException {

		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(CapabilityType.BROWSER_NAME, "firefox");
		RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
		driver.get("http://www.instagram.com");
		System.out.println(driver.getTitle());
		org.openqa.selenium.Capabilities capabilities = driver.getCapabilities();
		System.out.println(capabilities.getCapability("platformName"));
		System.out.println(Thread.currentThread().getId());
	//	System.out.println(capabilities.getCapability("selenium.node.name"));
		driver.quit();

	}
	
	@Test
	public void Test03() throws MalformedURLException {

		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(CapabilityType.BROWSER_NAME, "firefox");
		RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
		driver.get("http://www.facebook.com");
		System.out.println(driver.getTitle());
		org.openqa.selenium.Capabilities capabilities = driver.getCapabilities();
		System.out.println(capabilities.getCapability("platformName"));
		System.out.println(Thread.currentThread().getId());
	//	System.out.println(capabilities.getCapability("selenium.node.name"));
		driver.quit();

	}
	
	@AfterMethod
	public void aftermethod() {
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	}

}
