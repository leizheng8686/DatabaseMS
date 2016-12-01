import java.sql.*;
import java.util.*;

public class HW02_1 extends basicFunc{

	/**
	 * get the result. If isPrint is true, then print in the Console. Otherwise, print nothing 
	 */
	public void getResult(boolean isPrint)
	{

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
			
			//create a hashmap to store (CUSTOMER, PRODUCT, THE_AVG, OTHER_PROD_AVG, OTHER_CUST_AVG) info
			//the key is the combination of CUSTOMER and PRODUCT
			//Arraylist<String> store CUSTOMER, PRODUCT, THE_AVG, OTHER_PROD_AVG, OTHER_CUST_AVG, SUM_THIS_COMB, COUNT_THIS_COMB
			HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
			//quantity and count of specific customer, quantity and count of specific product
			HashMap<String, Integer[]> cust_sum = new HashMap<String,Integer[]>();
			HashMap<String, Integer[]> prod_sum = new HashMap<String,Integer[]>();
			while (rs.next())
			{
				//update or initialize cust_sum
				if(cust_sum.containsKey(rs.getString("cust"))){
					cust_sum.get(rs.getString("cust"))[0] += Integer.parseInt(rs.getString("quant"));
					cust_sum.get(rs.getString("cust"))[1] += 1;
				}else{
					Integer[] i = new Integer[2];
					i[0] = Integer.parseInt(rs.getString("quant")); //0 sum
					i[1] = 1;										//1 count
					cust_sum.put(rs.getString("cust"), i);
				}
				//update or initialize prod_sum
				if(prod_sum.containsKey(rs.getString("prod"))){
					prod_sum.get(rs.getString("prod"))[0] += Integer.parseInt(rs.getString("quant"));
					prod_sum.get(rs.getString("prod"))[1] += 1;
				}else{
					Integer[] i = new Integer[2];
					i[0] = Integer.parseInt(rs.getString("quant")); //0 sum
					i[1] = 1;										//1 count
					prod_sum.put(rs.getString("prod"), i);
				}
				
				//if map already contain the combination of customer and product, then update the info 
				if(map.containsKey(rs.getString("cust") + rs.getString("prod"))){
					String comb = rs.getString("cust") + rs.getString("prod");
					int quant = Integer.parseInt(rs.getString("quant"));
					// update SUM_THIS_COMB and COUNT_THIS_COMB
					map.get(comb).set(5, Integer.parseInt(map.get(comb).get(5)) + quant + "");
					map.get(comb).set(6, Integer.parseInt(map.get(comb).get(6)) + 1 + "");
					// update THE_AVG 2
					map.get(comb).set(2, Integer.parseInt(map.get(comb).get(5))/Integer.parseInt(map.get(comb).get(6)) + "");
				}else{
					//if the combination of customer and product is not in the hashmap, then initialize the info
					ArrayList<String> temp = new ArrayList<String>();
					temp.add(rs.getString("cust")); //0  CUSTOMER
					temp.add(rs.getString("prod")); //1  PRODUCT
					temp.add(rs.getString("quant")); //2  THE_AVG
					//3 OTHER_PROD_AVG
					if(cust_sum.containsKey(rs.getString("cust"))){
						if(cust_sum.get(rs.getString("cust"))[1] <= 1){
							temp.add(0 + "");
						}else
							temp.add((cust_sum.get(rs.getString("cust"))[0] - Integer.parseInt(temp.get(2)))/
								(cust_sum.get(rs.getString("cust"))[1] - 1) + "");
					}else {
						temp.add(0 + "");
					}
					//4 OTHER_CUST_AVG
					if(prod_sum.containsKey(rs.getString("prod"))){
						if(prod_sum.get(rs.getString("prod"))[1] <= 1){
							temp.add(0 + "");
						}else
							temp.add((prod_sum.get(rs.getString("prod"))[0] - Integer.parseInt(temp.get(2)))/
								(prod_sum.get(rs.getString("prod"))[1] - 1) + "");
					}else {
						temp.add(0 + "");
					}
					temp.add(rs.getString("quant")); //5 SUM_THIS_COMB
					temp.add(1 + ""); //6 COUNT_THIS_COMB
					
					map.put(rs.getString("cust") + rs.getString("prod"), temp);
				}
			}
			
			//print the result
			if(isPrint){
				System.out.println("CUSTOMER  PRODUCT  THE_AVG  OTHER_PROD_AVG  OTHER_CUST_AVG");
				System.out.println("========  =======  =======  ==============  ==============");
				Iterator iter = map.entrySet().iterator();
				int i = 0;  //count rows
				while (iter.hasNext()) {
					HashMap.Entry entry = (HashMap.Entry) iter.next();
					String key = (String) entry.getKey();
					ArrayList<String> val = (ArrayList<String>) entry.getValue();
					System.out.print(strFormat(val.get(0), "LEFT", 8));  //CUSTOMER
					System.out.print(strFormat(val.get(1), "LEFT", 7));  //PRODUCT
					System.out.print(strFormat(val.get(2), "RIGHT", 7));  //THE_AVG
					int v6 = Integer.parseInt(val.get(6)), v5 = Integer.parseInt(val.get(5));
					int cs1 = cust_sum.get(val.get(0))[1], cs0 = cust_sum.get(val.get(0))[0];
					int opa = cs1<=v6?0:(cs0-v5)/(cs1-v6);
					System.out.print(strFormat(opa+"", "RIGHT", 14));  //OTHER_PROD_AVG
					int ps1 = prod_sum.get(val.get(1))[1], ps0 = prod_sum.get(val.get(1))[0];
					int oca = ps1<=v6?0:(ps0-v5)/(ps1-v6);
					System.out.print(strFormat(oca + "", "RIGHT", 14));  //OTHER_CUST_AVG
					System.out.println();
					i++;
				}
				System.out.println("(" + i + " records)" + "\n");
			}
		}

		catch(SQLException e)
		{
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}

	}
}
