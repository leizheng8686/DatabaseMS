/*
		cust	varchar(20),
		prod	varchar(20),
		day	integer,
		month	integer,
		year	integer,
		state	char(2),
		quant	integer

PRODUCT   MAX_Q  CUSTOMER  DATE        ST  MIN_Q  CUSTOMER  DATE        ST  AVG_Q      
========  =====  ========  ==========  ==  =====  ========  ==========  ==  =====      
Pepsi      2893  Bloom     01/01/2006  NJ     12  Sam       09/25/2001  NY   1435      
Milk        159  Sam       02/15/2002  NJ      1  Emily     03/23/2004  CT     56      
Bread      3087  Emily     07/01/2005  NY      2  Helen     02/02/2001  NJ   1512 


CUSTOMER  PRODUCT  CT_MAX  DATE        NY_MIN  DATE        NJ_MIN  DATE          
========  =======  ======  ==========  ======  ==========  ======  ==========      
Sam       Egg        1908  01/11/2001     234  07/24/2005       2  11/03/2008      
Helen     Cookies     392  03/31/2002    2342  09/14/2000      11  07/23/2002      
Bloom     Butter     7045  09/22/2003     923  03/10/2004       8  09/11/2006 

*/
select * from sales;