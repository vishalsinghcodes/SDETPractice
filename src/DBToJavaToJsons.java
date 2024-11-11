import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DBToJavaToJsons {

	public static void main(String args[]) throws ClassNotFoundException, SQLException, StreamWriteException, DatabindException, IOException {
		// Class.forName("com.mysql.cj.jdbc.Driver");
		String host = "localhost";
		String port = "3306";
		Connection dbconn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/Business", "root",
				"Nokia@4230");
		Statement state = dbconn.createStatement();
		ResultSet rs = state
				.executeQuery("select * from CustomerInfo where purchasedDate=CURDATE() and Location ='Asia';");
		ArrayList<CustomerDetails> customerRecords = new ArrayList<CustomerDetails>();
		
		//CustomerDetails cd = new CustomerDetails();
		// See here I have limited the result to 1 for now
		while (rs.next()) {
			CustomerDetails cd = new CustomerDetails();
			cd.setCourseName(rs.getString(1));
			cd.setPurchasedDate(rs.getString(2));
			cd.setAmount(rs.getInt(3));
			cd.setLocation(rs.getString(4));
			customerRecords.add(cd);

//			System.out.println(cd.getCourseName());
//			System.out.println(cd.getAmount());
//			System.out.println(cd.getPurchasedDate());
//			System.out.println(cd.getLocation());

			// System.out.println(rs.getString(1));
//			System.out.println(rs.getString(2));
//			System.out.println(rs.getInt(3));
//			System.out.println(rs.getString(4));

		}
		
		for(int i=0;i<customerRecords.size();i++) {  // This is how you will get multiple jsons one for each record that will be so heavy. 
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(new File(System.getProperty("user.dir") + "//jsonOutput"+i+".json"), customerRecords.get(i));
		}
		
		
		

		dbconn.close();

	}

}
