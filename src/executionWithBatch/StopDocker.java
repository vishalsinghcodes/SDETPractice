package executionWithBatch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.junit.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

public class StopDocker {

	@AfterSuite
	public void startDockerServer() throws IOException, InterruptedException {
		System.out.println("----Stopping Server----");
		boolean flag = false;
		Runtime.getRuntime().exec("cmd /c start StopDocker.bat");
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
				if (currentLine.contains("All containers have been stopped")) { // This I have got from Chat GPT, I have modified the bat file to echo this sentence upon 
					//completing the stop operation it will print this line in the logs
					flag = true;
					break;
				}
				currentLine = breader.readLine();
			}
			breader.close();
		}
		
		
		Assert.assertTrue(flag);
		if(flag == true)
		System.out.println("----Server Stopped----");
		
		File log = new File( System.getProperty("user.dir")+"//output.txt");
		if(log.exists()) {
			log.delete();
			System.out.println("Log File deleted successfully");
		}
			
		
		
	}

}
