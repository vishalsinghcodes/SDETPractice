import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JSONToJavaObject {

	public static void main(String args[]) throws ClassNotFoundException, SQLException, StreamWriteException,
			DatabindException, IOException, InterruptedException {
		// Class.forName("com.mysql.cj.jdbc.Driver");
		String host = "localhost";
		String port = "3306";
		Connection dbconn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/Business", "root",
				"Nokia@4230");
		Statement state = dbconn.createStatement();
		ResultSet rs = state
				.executeQuery("select * from CustomerInfo where purchasedDate=CURDATE() and Location ='Asia';");
		ArrayList<CustomerDetails> customerRecords = new ArrayList<CustomerDetails>();

		// CustomerDetails cd = new CustomerDetails();
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

//		for(int i=0;i<customerRecords.size();i++) {  // This is how you will get multiple jsons one for each record that will be so heavy. 
//			ObjectMapper mapper = new ObjectMapper();
//			mapper.writeValue(new File(System.getProperty("user.dir") + "//jsonOutput"+i+".json"), customerRecords.get(i));
//		}

		// Below is the mthod, how you will be retriving the Data from DB and create a
		// single JSON for all those records

		JSONArray ja = new JSONArray();
		for (int i = 0; i < customerRecords.size(); i++) {
			Gson g = new Gson();
			String JsonString = g.toJson(customerRecords.get(i));
			ja.add(JsonString);
		}

		JSONObject jo = new JSONObject();
		jo.put("data", ja);

		System.out.println(StringEscapeUtils.unescapeJava(jo.toString()));
		String properString01 = StringEscapeUtils.unescapeJava(jo.toString());
		// System.out.println(properString01);
		String properString02 = properString01.replace("\"{", "{");
		// System.out.println(properString02);
		String properString03 = properString02.replace("}\"", "}");
		// System.out.println(properString03);

//		ObjectMapper mapper = new ObjectMapper();
//		mapper.writeValue(new File(System.getProperty("user.dir") + "//CombinedjsonOutput.json"),
//				properString.toString());

		try (FileWriter writer = new FileWriter(new File(System.getProperty("user.dir") + "//ComboJsonRecords.json"))) {
			writer.write(properString03);
		} catch (IOException e) {
			e.printStackTrace(); // Handle exception
		}

		Thread.sleep(3000);
		// Till Here we have the File with us ComboJsonRecords.json now again we are
		// trying to convert it back in to Java Object array

		// 1st Logic using Object Mapper
//		String pathToJsonFile = System.getProperty("user.dir") + "//ComboJsonRecords.json";
//		String StringOfJson = FileUtils.readFileToString(new File(pathToJsonFile), StandardCharsets.UTF_8);
//		JSONObject joo = new JSONObject(StringOfJson);
//		org.json.JSONArray jsonarray = joo.getJSONArray("data");
//		String arrayString = jsonarray.toString();
//
//		ArrayList<CustomerDetails> jsontoList = new ArrayList<CustomerDetails>();
//		ObjectMapper fileToStringMapper = new ObjectMapper();
//		List<CustomerDetails> custList = fileToStringMapper.readValue(arrayString,
//				new TypeReference<List<CustomerDetails>>() {
//				});
//		System.out.println(custList);

		// 2nd Logic by using File Utils and JSON Object

		String pathToJsonFile = System.getProperty("user.dir") + "//ComboJsonRecords.json";
		String StringOfJson = FileUtils.readFileToString(new File(pathToJsonFile), StandardCharsets.UTF_8);
		JSONObject joo = new JSONObject(StringOfJson);
		System.out.println(
				"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Here we are fwtching the DATA from JSON File and Trying to create a ArrayList of Java class");
		System.out.println(joo);
		org.json.JSONArray jsonarray = joo.getJSONArray("data");
		System.out.println(jsonarray);
		System.out.println(jsonarray.length());
		ArrayList<CustomerDetails> jsontoList2 = new ArrayList<CustomerDetails>();
		for (int i = 0; i < jsonarray.length(); i++) {
			CustomerDetails cd = new CustomerDetails();
			cd.setCourseName(jsonarray.getJSONObject(i).getString("CourseName"));
			cd.setPurchasedDate(jsonarray.getJSONObject(i).getString("PurchasedDate"));
			cd.setAmount(jsonarray.getJSONObject(i).getInt("Amount"));
			cd.setLocation(jsonarray.getJSONObject(i).getString("Location"));
			jsontoList2.add(cd);
		}

		System.out.println(jsontoList2.size());

		for (int i = 0; i < jsontoList2.size(); i++) {
			System.out.println("Course Name: " + jsontoList2.get(i).getCourseName());
			System.out.println("Purchased Date: " + jsontoList2.get(i).getPurchasedDate());
			System.out.println("Amount: " + jsontoList2.get(i).getAmount());
			System.out.println("Location: " + jsontoList2.get(i).getLocation());
			System.out.println("-----------------------------"); // separator for readability
		}

		dbconn.close();

	}

}
