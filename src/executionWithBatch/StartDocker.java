package executionWithBatch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.junit.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class StartDocker {

	@BeforeSuite
	public void startDockerServer() throws IOException, InterruptedException {
		System.out.println("----Starting Server----");
		boolean flag = false;
		Runtime.getRuntime().exec("cmd /c start Dockerup.bat");
		Thread.sleep(3000); // This is to let the output file to be created
		String filenameString = "output.txt";
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 30);
		Long millisecStopNow = cal.getTimeInMillis();
		
		while(System.currentTimeMillis()<millisecStopNow) {
			if(flag==true)
				break;
			BufferedReader breader = new BufferedReader(new FileReader(filenameString));
			String currentLine = breader.readLine();
			while (currentLine != null && !flag) {
				if (currentLine.contains("All containers are up")) {
					flag = true;
					break;
				}
				currentLine = breader.readLine();
			}
			breader.close();
		}
		
		
		Assert.assertTrue(flag);
		if(flag == true)
		System.out.println("----Server Started-----");
		
		System.out.println("----Scaling up the browser instances----");
		Runtime.getRuntime().exec("cmd /c start instanceScaleup.bat");
		Thread.sleep(5000);
		System.out.println("----Browser instances scaled up----");
		
		
	}

}
