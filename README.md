# DatabaseMS

###First of all:  
Install PostgreSQL, and run load_sales_table.sql to initialize the table.  

###How to execute the sql:  
1. Open your database where the load_sales_table.sql loaded.  
2. Open and run HW01_part1.sql to get part1 table, HW01_part2.sql to get part2 table.  

###How to execute the java program:  
1. Create a java project and include files(Main.java, HW01.java, HW01_2.java, commFunc.java) in one package.  
2. Add postgresql driver to Libraries. The driver used for this program is postgresql-9.4.1211.jre6.jar.  
2.1 For Eclipse, you can download the whole project from my github and run it directly.  
3. According to your configuration, set PostgreSQL username, password, url in commFunc.java.   
4. Then compile and run the main.java. You will get 2 tables in the Console.  
4.1 You can only print one table once by change the boolean parameter in getResult method.true: print result, false: nothing print.  
