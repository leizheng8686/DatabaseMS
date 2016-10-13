import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class HW01 {

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
			
			//create a hashmap to store (PRODUCT(as key),MAX_Q,CUSTOMER,DATE,ST,MIN_Q,CUSTOMER,DATE,ST,AVG_Q,SUM,COUNT) info
			HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();

			//read line by line
			while (rs.next())
			{
				//if hashmap already contain the prod, then update the info 
				if(map.containsKey(rs.getString("prod"))){
					int quant = Integer.parseInt(rs.getString("quant"));
					//if quant > current MAX_Q, update info
					if(quant > Integer.parseInt(map.get(rs.getString("prod")).get(0))){
						map.get(rs.getString("prod")).set(0, quant + "");
						map.get(rs.getString("prod")).set(1, rs.getString("cust"));
						map.get(rs.getString("prod")).set(2, toTwoDigits(rs.getString("month")) +"/"
															+ toTwoDigits(rs.getString("day")) +"/"
															+ toTwoDigits(rs.getString("year")));
						map.get(rs.getString("prod")).set(3, rs.getString("state"));
					}else if(quant < Integer.parseInt(map.get(rs.getString("prod")).get(4))){
						map.get(rs.getString("prod")).set(4, quant + "");
						map.get(rs.getString("prod")).set(5, rs.getString("cust"));
						map.get(rs.getString("prod")).set(6, toTwoDigits(rs.getString("month")) +"/"
															+ toTwoDigits(rs.getString("day")) +"/"
															+ toTwoDigits(rs.getString("year")));
						map.get(rs.getString("prod")).set(7, rs.getString("state"));
					}
					//update AVG_Q,SUM,COUNT
					map.get(rs.getString("prod")).set(9, Integer.parseInt(map.get(rs.getString("prod")).get(9)) + quant + "");
					map.get(rs.getString("prod")).set(10, Integer.parseInt(map.get(rs.getString("prod")).get(10)) + 1 + "");
					map.get(rs.getString("prod")).set(8, Integer.parseInt(map.get(rs.getString("prod")).get(9))/Integer.parseInt(map.get(rs.getString("prod")).get(10)) + "");
				}else{
					//if the prod is not in the hashmap, then initialize the info
					ArrayList<String> temp = new ArrayList<String>();
					temp.add(rs.getString("quant")); //0  MAX_Q
					temp.add(rs.getString("cust")); //1  CUSTOMER
					temp.add(toTwoDigits(rs.getString("month")) +"/"
							+ toTwoDigits(rs.getString("day")) +"/"
							+ toTwoDigits(rs.getString("year"))); //2  DATE
					temp.add(rs.getString("state")); //3  ST
					temp.add(rs.getString("quant")); //4  MIN_Q
					temp.add(rs.getString("cust")); //5  CUSTOMER
					temp.add(toTwoDigits(rs.getString("month")) +"/"
							+ toTwoDigits(rs.getString("day")) +"/"
							+ toTwoDigits(rs.getString("year"))); //6 DATE
					temp.add(rs.getString("state")); //7  ST
					temp.add(rs.getString("quant")); //8  AVG_Q
					temp.add(rs.getString("quant")); //9  SUM
					temp.add("1"); //10  COUNT
					map.put(rs.getString("prod"), temp);
				}
			}
			
			//print the result
			Iterator iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				HashMap.Entry entry = (HashMap.Entry) iter.next();
				String key = (String) entry.getKey();
				ArrayList<String> val = (ArrayList<String>) entry.getValue();
				System.out.print(key);
				for(String str : val){
					System.out.print("\t" + str);
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
