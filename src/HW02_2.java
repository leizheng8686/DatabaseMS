import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class HW02_2 extends basicFunc {
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
			
			//create a HashMap the key is the combination of CUSTOMER and PRODUCT, the value is class record
			//record store CUSTOMER PRODUCT  MONTH  BEFORE_AVG  AFTER_AVG
			HashMap<String,record> map = new HashMap<String,record>();

			while (rs.next())
			{
				String cust = rs.getString("cust"), prod = rs.getString("prod");
				String comb = cust+prod;
				int month = rs.getInt("month");
				int quant = rs.getInt("quant");
				//update if there exists this cust-prod combination in map
				if(map.containsKey(comb)){
					map.get(comb).update(month, quant);
				}else{//initialize if there is no this cust-prod combination in map
					map.put(comb, new record(cust, prod, month, quant));
				}
			}
			
			//print the result
			if(isPrint){
				System.out.println("CUSTOMER  PRODUCT  MONTH  BEFORE_AVG  AFTER_AVG");
				System.out.println("========  =======  =====  ==========  =========");
				int i = 0;  //count rows
				for(record r : map.values()){
					for(int m = 1; m < 13; m++){
						if(r.count.containsKey(m-1)||r.count.containsKey(m+1)){
							i++;
							System.out.print(strFormat(r.cust, "LEFT", 8));  //CUSTOMER
							System.out.print(strFormat(r.prod, "LEFT", 7));  //PRODUCT
							System.out.print(strFormat(m + "", "RIGHT", 5));  //MONTH
							System.out.print(strFormat(r.count.containsKey(m-1)?r.count.get(m-1)[0]/r.count.get(m-1)[1]+"":"NULL", "RIGHT", 10));  //BEFORE_AVG
							System.out.println(strFormat(r.count.containsKey(m+1)?r.count.get(m+1)[0]/r.count.get(m+1)[1]+"":"NULL", "RIGHT", 9));  //AFTER_AVG
						}else{}
					}
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
	
	/**
	 * record CUSTOMER, PRODUCT, MONTH, SUM of THE MONTH, COUNT of THE MONTH 
	 */
	public class record{
		protected String cust;
		protected String prod;
		protected HashMap<Integer,Integer[]> count = new HashMap<Integer,Integer[]>();
		
		/**
		 * input customer, product, month , quantity 
		 */
		public record(String c, String p, int month, int quant){
			cust = c;
			prod = p;
			Integer []i = new Integer[2];
			i[0] = quant; i[1] = 1;
			count.put(month, i);
		}
		
		/**
		 * input month and quantity 
		 */
		public void update(int month, int quant){
			if(count.containsKey(month)){
				count.get(month)[0] += quant;
				count.get(month)[1]++;
			}else{
				Integer []i = new Integer[2];
				i[0] = quant; i[1] = 1;
				count.put(month, i);
			}
		}
	}
}
