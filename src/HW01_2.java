import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class HW01_2 {

	public static void main(String[] args)
	{
		String usr ="postgres";
		String pwd ="8686";
		String url ="jdbc:postgresql://localhost:5432/postgres";

		try
		{
			Class.forName("org.postgresql.Driver");
			System.out.println("Success loading Driver!");
		}

		catch(Exception e)
		{
			System.out.println("Fail loading Driver!");
			e.printStackTrace();
		}

		try
		{
			Connection conn = DriverManager.getConnection(url, usr, pwd);
			System.out.println("Success connecting server!");

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Sales");
			
			//create a hashmap to store (CUSTOMER,PRODUCT,CT_MAX,DATE,NY_MIN,DATE,NJ_MIN,DATE) info
			//the key is the combination of CUSTOMER and PRODUCT
			HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
			while (rs.next())
			{
				//TODO
				//if hashmap already contain the combination of customer and product, then update the info 
				if(map.containsKey(rs.getString("cust") + rs.getString("prod"))){
					int quant = Integer.parseInt(rs.getString("quant"));
					if(rs.getString("state").equals("CT")){
						if(map.get(rs.getString("cust") + rs.getString("prod")).get(2) == ""  
							|| quant > Integer.parseInt(map.get(rs.getString("cust") + rs.getString("prod")).get(2))){
							map.get(rs.getString("cust") + rs.getString("prod")).set(2, quant + "");
							map.get(rs.getString("cust") + rs.getString("prod")).set(3, toTwoDigits(rs.getString("month")) +"/"
																						+ toTwoDigits(rs.getString("day")) +"/"
																						+ toTwoDigits(rs.getString("year")));
						}
					}else if(rs.getString("state").equals("NY")){
						if(map.get(rs.getString("cust") + rs.getString("prod")).get(4) == ""  
								|| quant < Integer.parseInt(map.get(rs.getString("cust") + rs.getString("prod")).get(4))){
								map.get(rs.getString("cust") + rs.getString("prod")).set(4, quant + "");
								map.get(rs.getString("cust") + rs.getString("prod")).set(5, toTwoDigits(rs.getString("month")) +"/"
																							+ toTwoDigits(rs.getString("day")) +"/"
																							+ toTwoDigits(rs.getString("year")));
						}
					}else if(rs.getString("state").equals("NJ")){
						if(map.get(rs.getString("cust") + rs.getString("prod")).get(6) == ""  
								|| quant < Integer.parseInt(map.get(rs.getString("cust") + rs.getString("prod")).get(6))){
								map.get(rs.getString("cust") + rs.getString("prod")).set(6, quant + "");
								map.get(rs.getString("cust") + rs.getString("prod")).set(7, toTwoDigits(rs.getString("month")) +"/"
																							+ toTwoDigits(rs.getString("day")) +"/"
																							+ toTwoDigits(rs.getString("year")));
						}
					}else
						continue;
				}else{ 
					//if the combination of customer and product is not in the hashmap, then initialize the info
					ArrayList<String> temp = new ArrayList<String>();
					temp.add(rs.getString("cust")); //0  CUSTOMER
					temp.add(rs.getString("prod")); //1  PRODUCT
					if(rs.getString("state").equals("CT")){
						temp.add(rs.getString("quant")); //2  CT_MAX
						temp.add(toTwoDigits(rs.getString("month")) +"/"
								+ toTwoDigits(rs.getString("day")) +"/"
								+ toTwoDigits(rs.getString("year"))); //3  DATE of CT_MAX
						temp.add("");  //4 NY_MIN
						temp.add("");  //5 DATE of NY_MIN
						temp.add("");  //6 NJ_MIN
						temp.add("");  //7 DATE of NJ_MIN
					}else if(rs.getString("state").equals("NY")){
						temp.add("");  //2 CT_MAX
						temp.add("");  //3 DATE of CT_MAX
						temp.add(rs.getString("quant")); //4  NY_MIN
						temp.add(toTwoDigits(rs.getString("month")) +"/"
								+ toTwoDigits(rs.getString("day")) +"/"
								+ toTwoDigits(rs.getString("year"))); //5  DATE of NY_MIN
						temp.add("");  //6 NJ_MIN
						temp.add("");  //7 DATE of NJ_MIN
					}else if(rs.getString("state").equals("NJ")){
						temp.add("");  //2 CT_MAX
						temp.add("");  //3 DATE of CT_MAX
						temp.add("");  //4 NY_MIN
						temp.add("");  //5 DATE of NY_MIN
						temp.add(rs.getString("quant")); //6  NJ_MIN
						temp.add(toTwoDigits(rs.getString("month")) +"/"
								+ toTwoDigits(rs.getString("day")) +"/"
								+ toTwoDigits(rs.getString("year"))); //7  DATE of NJ_MIN
					}else{
						continue;
					}
					map.put(rs.getString("cust") + rs.getString("prod"), temp);
				}
			}
			
			//print the result
//			System.out.println("CUSTOMER" + "\t" + "PRODUCT" + "\t"
//							+ "CT_MAX" + "\t" + "DATE" + "\t" + "NY_MIN" + "\t" 
//							+ "DATE" + "\t" + "NJ_MIN" + "\t" + "DATE");
			Iterator iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				HashMap.Entry entry = (HashMap.Entry) iter.next();
				String key = (String) entry.getKey();
				ArrayList<String> val = (ArrayList<String>) entry.getValue();
//				System.out.print(key);
				for(String str : val){
					System.out.print(str + "\t");
				}
				System.out.println();
			}
		}

		catch(SQLException e)
		{
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}

	}
	
	public static String toTwoDigits(String str){
		if(str.length() == 1){
			return "0" + str;
		}else
			return str;
	}

}
