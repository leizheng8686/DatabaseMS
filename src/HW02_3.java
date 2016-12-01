import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;


public class HW02_3 extends basicFunc {
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
			Statement stmt2 = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Sales");
			ResultSet rs2 = stmt2.executeQuery("SELECT * FROM Sales");
			//create a HashMap, the key is PRODUCT, the value is class record
			//record store PRODUCT, MONTH, BEFORE_TOT, AFTER_TOT
			HashMap<String,record> map = new HashMap<String,record>();

			//create a HashMap, the key is the combination of PRODUCT and month
			//the value is Integer[], [0] for avg, [1] for max, [2] for sum, [3] for count
			HashMap<String,Integer[]> a_m = new HashMap<String,Integer[]>();
			//compute avg and max of each month for each product
			while(rs2.next()){
				String prod = rs2.getString("prod");
				int month = rs2.getInt("month");
				int quant = rs2.getInt("quant");
				String comb = prod + month;
				if(a_m.containsKey(comb)){
					a_m.get(comb)[2] += quant;
					a_m.get(comb)[3]++;
					a_m.get(comb)[0] = a_m.get(comb)[2]/a_m.get(comb)[3];
					if(quant > a_m.get(comb)[1])
						a_m.get(comb)[1] = quant;
				}else{
					Integer []val = new Integer[4];
					val[0] = quant; val[1] = quant; val[2] = quant; val[3] = 1;
					a_m.put(comb, val);
				}
			}
			
			//compute before_tot and after_tot
			while (rs.next())
			{
				String prod = rs.getString("prod");
				int month = rs.getInt("month");
				int quant = rs.getInt("quant");
				String comb = prod + month;
				//update if there exists this cust-prod combination in map
				if(month > 1 && a_m.containsKey(prod + (month - 1))){
					if(map.containsKey(prod + (month - 1))){
						map.get(prod + (month - 1)).update(month-1, month, quant);
					}else{//initialize if there is no this cust-prod combination in map
						map.put(prod + (month - 1), new record(prod, month-1, month, quant, a_m.get(prod + (month - 1))[0], a_m.get(prod + (month - 1))[1]));
					}
				}
				if(month < 12 && a_m.containsKey(prod + (month + 1))){
					if(map.containsKey(prod + (month + 1))){
						map.get(prod + (month + 1)).update(month+1, month, quant);
					}else{//initialize if there is no this cust-prod combination in map
						map.put(prod + (month + 1), new record(prod, month+1, month, quant, a_m.get(prod + (month + 1))[0], a_m.get(prod + (month + 1))[1]));
					}
				}
			}
			
			//print the result
			if(isPrint){
				System.out.println("PRODUCT  MONTH  BEFORE_TOT  AFTER_TOT");
				System.out.println("=======  =====  ==========  =========");
				int i = 0;  //count rows
				for(record r : map.values()){
					for(int m = 1; m < 13; m++){
						if(r.count.containsKey(m) && r.count.get(m)[0]+r.count.get(m)[1] != 0){
							i++;
							System.out.print(strFormat(r.prod, "LEFT", 7));  //PRODUCT
							System.out.print(strFormat(m + "", "RIGHT", 5));  //MONTH
							System.out.print(strFormat(r.count.get(m)[0]==0?"NULL":r.count.get(m)[0] + "", "RIGHT", 10));  //BEFORE_AVG
							System.out.println(strFormat(r.count.get(m)[1]==0?"NULL":r.count.get(m)[1] + "", "RIGHT", 9));  //AFTER_AVG
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
	 * record PRODUCT, MONTH, BEFORE_TOT, AFTER_TOT 
	 */
	public class record{
		protected String prod;
		protected HashMap<Integer,Integer[]> count = new HashMap<Integer,Integer[]>();
		protected int avg = 0, max = 0;
		/**
		 * input product, month, previous or following month, its quantity, current month's avg, current month's max
		 */
		public record(String p, int cur_m, int m, int quant, int avg, int max){
			prod = p;
			this.avg = avg;
			this.max = max;
			//[0] for previous, [1] for following
			Integer []i = new Integer[2];
			i[0] = 0; i[1] = 0;
			if(quant <= max && quant >= avg){
				if(cur_m > m)
					i[0] = 1;
				else
					i[1] = 1;
			}
			count.put(cur_m, i);
		}
		
		/**
		 * input month, previous or following month, its quantity
		 */
		public void update(int cur_m, int m, int quant){
			if(quant <= max && quant >= avg){
				if(count.containsKey(cur_m)){
						if(cur_m > m)
							count.get(cur_m)[0]++;
						else
							count.get(cur_m)[1]++;
					
				}else{
					Integer []i = new Integer[2];
					if(cur_m > m){
						i[0] = 1; i[1] = 0;
					}else{
						i[0] = 0; i[1] = 1;
					}
					count.put(cur_m, i);
				}
			}
		}
	}
}