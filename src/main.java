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
