/* 
 * Name: Lei Zheng
 * CWID: 10399614
 * 
 * How to execute the program:
 * 1. Install PostgreSQL, and run load_sales_table.sql to initialize the table.
 * 2. Create a java project and include files(Main.java, HW01.java, HW01_2.java, commFunc.java) in default package.
 * 3. Add postgresql driver to Libraries. The driver used for this program is postgresql-9.4.1211.jre6.jar.
 * 3.1 For Eclipse, you can download the whole project from my github and run it directly.
 *     URL:https://github.com/leizheng8686/DatabaseMS)
 * 4. According to your configuration, set PostgreSQL username, password, url in basicFunc.java. 
 * 5. Then compile and run the main2.java. You will get 3 tables in the Console.
 * 5.1 You can choose any table to print or not by change the boolean parameter in getResult method.
 * 	   true: print, false: nothing print.
 * 
 * Data structure:
 * 1. For part 1:
 * 		The data structure is Hashmap<String,ArrayList<String>>. 
 * 		It is convenient to map Primary Keys in sql to Keys in hashmap, and store content in arraylist. 
 * 2. For part 2:
 * 		The data structure is Hashmap<String,record>.
 * 		record is a class I create that can store different type datas,
 * 		for this case, it store CUSTOMER as String data, PRODUCT as String data, 
 * 		MONTH as int data, BEFORE_AVG as int data, AFTER_AVG as int data.
 * 3. For part 3:
 * 		The data structure is Hashmap<String,record> map and Hashmap<String,Integer[]> a_m.
 * 		The record in map store PRODUCT as String data, 
 * 		MONTH as int data, BEFORE_TOT as int data, AFTER_TOT as int data.
 * 		The Integer[] in a_m store specific month's avg, max, sum, count.
 * 
 * Algorithm:
 * Part1: 
 * When reading in a line, fetch the customer and product, quantity
 * 1.If hashmap does not contain the Key(customer and product), initialize information, 
 * add customer, product, sum, count, set THE_AVG as this quantity.
 * 2.If hashmap contains the Key, update the existing info.
 * In the end, set OTHER_PROD_AVG = (the customer' sum - that product's sum)/(the customer' count - that product's count),
 * set OTHER_CUST_AVG = (the prod' sum - that cust's sum)/(the prod' count - that cust's count).
 * print out records.
 * Part2:
 * For every combination of customer and product, compute each month's average quantity(avg = sum/count).
 * In the end, for every combination of customer and product, enumerate month, 
 * if the month's previous month or following month's avg is not null, then print this record. 
 * Part3:
 * Go through the table to get avg and max for each combination of product and month.
 * Go through the table again, for every coming month, compare to its previous and following month,
 * if its quantity is between its previous or following month's avg and max, 
 * then increase the BEFORE_TOT or/and AFTER_TOT.
 * print result
 */

public class main2 {
	public static void main(String[] args)
	{
		HW02_1 part1 = new HW02_1();
		HW02_2 part2 = new HW02_2();
		HW02_3 part3 = new HW02_3();
		
		part1.getResult(true);
		part2.getResult(true);
		part3.getResult(true);
	}
}
