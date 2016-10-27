
create view ct_temp as
(select cust, prod, max(quant) as ct_max from sales 
where state = 'CT' and year between 2000 and 2005 group by cust, prod ); 

create view ct as
(select ct_temp.*, sales.month as ct_mon, sales.day as ct_day, sales.year  as ct_yr 
from ct_temp, sales where ct_temp.prod = sales.prod and ct_temp.cust = sales.cust 
and ct_temp.ct_max = sales.quant and sales.year between 2000 and 2005);

create view ny_temp as
(select cust, prod, min(quant) as ny_min from sales where state = 'NY' group by cust, prod ); 

create view ny as
(select ny_temp.*, sales.month as ny_mon, sales.day as ny_day, sales.year as ny_yr from ny_temp, sales 
where ny_temp.prod = sales.prod and ny_temp.cust = sales.cust and ny_temp.ny_min = sales.quant);

create view nj_temp as
(select cust, prod, min(quant) as nj_min from sales where state = 'NJ' group by cust, prod ); 

create view nj as
(select nj_temp.*, sales.month as nj_mon, sales.day as nj_day, sales.year as nj_yr from nj_temp, sales 
where nj_temp.prod = sales.prod and nj_temp.cust = sales.cust and nj_temp.nj_min = sales.quant);

select * from
((ct natural full outer join ny) natural full outer join nj) order by cust, prod;
