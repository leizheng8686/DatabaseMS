import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class HW01_2 extends commFunc{

	public static void main(String[] args)
	{
		String usr ="postgres";
		String pwd ="8686";
		String url ="jdbc:postgresql://localhost:5432/postgres";

		try
		{
			Class.forName("org.postgresql.Driver");
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

		try
		{
			Connection conn = DriverManager.getConnection(url, usr, pwd);

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
					// update CT info if the year betweens 2000 and 2005
					if(rs.getString("state").equals("CT")
						&& Integer.parseInt(rs.getString("year")) >= 2000
						&& Integer.parseInt(rs.getString("year")) <= 2005){
						if(map.get(rs.getString("cust") + rs.getString("prod")).get(2) == "NULL"  
							|| quant > Integer.parseInt(map.get(rs.getString("cust") + rs.getString("prod")).get(2))){
							map.get(rs.getString("cust") + rs.getString("prod")).set(2, quant + "");
							map.get(rs.getString("cust") + rs.getString("prod")).set(3, toTwoDigits(rs.getString("month")) +"/"
																						+ toTwoDigits(rs.getString("day")) +"/"
																						+ toTwoDigits(rs.getString("year")));
						}
					}else if(rs.getString("state").equals("NY")){
						if(map.get(rs.getString("cust") + rs.getString("prod")).get(4) == "NULL"  
								|| quant < Integer.parseInt(map.get(rs.getString("cust") + rs.getString("prod")).get(4))){
								map.get(rs.getString("cust") + rs.getString("prod")).set(4, quant + "");
								map.get(rs.getString("cust") + rs.getString("prod")).set(5, toTwoDigits(rs.getString("month")) +"/"
																							+ toTwoDigits(rs.getString("day")) +"/"
																							+ toTwoDigits(rs.getString("year")));
						}
					}else if(rs.getString("state").equals("NJ")){
						if(map.get(rs.getString("cust") + rs.getString("prod")).get(6) == "NULL"  
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
					//initialize CT info if the year betweens 2000 and 2005
					if(rs.getString("state").equals("CT") 
							&& Integer.parseInt(rs.getString("year")) >= 2000
							&& Integer.parseInt(rs.getString("year")) <= 2005){
						temp.add(rs.getString("quant")); //2  CT_MAX
						temp.add(toTwoDigits(rs.getString("month")) +"/"
								+ toTwoDigits(rs.getString("day")) +"/"
								+ toTwoDigits(rs.getString("year"))); //3  DATE of CT_MAX
						temp.add("NULL");  //4 NY_MIN
						temp.add("NULL");  //5 DATE of NY_MIN
						temp.add("NULL");  //6 NJ_MIN
						temp.add("NULL");  //7 DATE of NJ_MIN
					}else if(rs.getString("state").equals("NY")){
						temp.add("NULL");  //2 CT_MAX
						temp.add("NULL");  //3 DATE of CT_MAX
						temp.add(rs.getString("quant")); //4  NY_MIN
						temp.add(toTwoDigits(rs.getString("month")) +"/"
								+ toTwoDigits(rs.getString("day")) +"/"
								+ toTwoDigits(rs.getString("year"))); //5  DATE of NY_MIN
						temp.add("NULL");  //6 NJ_MIN
						temp.add("NULL");  //7 DATE of NJ_MIN
					}else if(rs.getString("state").equals("NJ")){
						temp.add("NULL");  //2 CT_MAX
						temp.add("NULL");  //3 DATE of CT_MAX
						temp.add("NULL");  //4 NY_MIN
						temp.add("NULL");  //5 DATE of NY_MIN
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
			System.out.println("CUSTOMER  PRODUCT  CT_MAX  DATE        NY_MIN  DATE        NJ_MIN  DATE      ");
			System.out.println("========  =======  ======  ==========  ======  ==========  ======  ==========");
			Iterator iter = map.entrySet().iterator();
			int i = 0;  //count rows
			while (iter.hasNext()) {
				HashMap.Entry entry = (HashMap.Entry) iter.next();
				String key = (String) entry.getKey();
				ArrayList<String> val = (ArrayList<String>) entry.getValue();
				System.out.print(strFormat(val.get(0), "LEFT", 8));  //customer
				System.out.print(strFormat(val.get(1), "LEFT", 7));  //product
				System.out.print(strFormat(val.get(2), "RIGHT", 6));  //CT_MAX
				System.out.print(strFormat(val.get(3), "RIGHT", 10));  //DATE
				System.out.print(strFormat(val.get(4), "RIGHT", 6));  //NY_MIN
				System.out.print(strFormat(val.get(5), "RIGHT", 10));  //DATE
				System.out.print(strFormat(val.get(6), "RIGHT", 6));  //NJ_MIN
				System.out.print(strFormat(val.get(7), "RIGHT", 10));  //DATE
				System.out.println();
				i++;
			}
			System.out.println("(" + i + " records)");
		}

		catch(SQLException e)
		{
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}

	}

}
