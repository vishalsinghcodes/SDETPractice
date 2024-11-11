package dockerRelatedStuff;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DockerTestSelenium {

	public static void main(String args[]) throws MalformedURLException {

		DesiredCapabilities dc = new DesiredCapabilities();
		//dc.setCapability(CapabilityType.BROWSER_NAME, "chrome");
		dc.setCapability(CapabilityType.BROWSER_NAME, "firefox");
		//dc.setCapability(CapabilityType.BROWSER_NAME, "edge");

		WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
		// We have created the Docker Container in such a way that any request made to
		// port 4444 will be directed to the Docker container 4444 (Refer command of
		// docker 4444:4444)
		driver.get("http://www.google.com");
		System.out.println(driver.getTitle());
		driver.quit(); // It is important to use driver.quit, else your test will be in running mode in
						// the container and you will get some error upon the execution after the first
						// trigger and you will need to restart the container again and again

	}

}
