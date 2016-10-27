/* 
 * Name: Lei Zheng
 * CWID: 10399614
 * 
 * How to execute the program:
 * 1. Install PostgreSQL, and run load_sales_table.sql to initialize the table.
 * 2. Create a java project and include files(Main.java, HW01.java, HW01_2.java, commFunc.java) in one package.
 * 3. Add postgresql driver to Libraries. The driver used for this program is postgresql-9.4.1211.jre6.jar.
 * 3.1 For Eclipse, you can download the whole project from my github and run it directly.
 *     URL:https://github.com/leizheng8686/DatabaseMS)
 * 4. According to your configuration, set PostgreSQL username, password, url in commFunc.java. 
 * 5. Then compile and run the main.java. You will get 2 tables in the Console.
 * 5.1 You can only print one table once by change the boolean parameter in getResult method.
 * 	   true: print result, false: nothing print.
 * 
 * Data structure:
 * The data structure is Hashmap<String,ArrayList<String>>. 
 * It is convenient to map Primary Keys in sql to Keys in hashmap, and store content in arraylist. 
 * In part1, the Key is product. In part2, the Key is combination of customer and product.
 * 
 * Algorithm:
 * Part1: 
 * When reading in a line, fetch the product name,
 * 1.If hashmap does not contain the Key(product name), initialize information, 
 * add product, customer, state, date and set MAX_Q, MIN_Q, Sum, AVG_Q as this quantity, set count as 1.
 * 2.If hashmap contains the Key, add quantity to sum and increase count by 1, set the AVG_Q equals to sum/count.
 * Then compare the new coming quantity with existing MAX_Q and MIN_Q,
 * and update the value and relevant product infos if greater than MAX_Q, less than MIN_Q.
 * Part2:
 * When reading in a line, fetch the customer and product name,
 * 1.If hashmap does not contain the Key(combination of customer and product), 
 * then check if the state is one of CT, NY and NJ(if it is CT, the year should be between 2000 and 2005, 
 * skip if the year is not right), skip this line if not, otherwise, initialize information, 
 * add product, customer, date, corresponding state and quantity and set other infos as "NULL".
 * 2.If hashmap contains the Key, 
 * check if the state is required, skip this line if not, otherwise, 
 * initialize it if the state's info is NULL. If the state's info is not NULL, 
 * compare the new coming quantity with existing state's quantity, update state's info if need.
 */

public class main {
	public static void main(String[] args)
	{
		HW01 part1 = new HW01();
		HW01_2 part2 = new HW01_2();
		
		part1.getResult(true);
		part2.getResult(true);
	}
}
