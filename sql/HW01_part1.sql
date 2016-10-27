
create view prod_max as
(select prod, max(quant) as max_q from sales group by prod);

create view prod_max_info as
(select prod_max.prod, prod_max.max_q, sales.cust as cust_max,
 sales.month as mon_max, sales.day as day_max, sales.year as year_max, sales.state as st_max
from prod_max, sales where prod_max.prod = sales.prod and prod_max.max_q = sales.quant);

create view prod_min as
(select prod, min(quant) as min_q from sales group by prod);

create view prod_min_info as
(select prod_min.prod, prod_min.min_q, sales.cust as cust_min,
 sales.month as mon_min, sales.day as day_min, sales.year as year_min, sales.state as st_min
from prod_min, sales where prod_min.prod = sales.prod and prod_min.min_q = sales.quant);

create view prod_avg as
(select prod, round(avg(quant), 0) as avg_q from sales group by prod);

create view part1 as
(select prod_max_info.prod, prod_max_info.max_q, prod_max_info.cust_max, 
prod_max_info.mon_max, prod_max_info.day_max, prod_max_info.year_max, prod_max_info.st_max,
prod_min_info.min_q, prod_min_info.cust_min, prod_min_info.mon_min, 
prod_min_info.day_min, prod_min_info.year_min, prod_min_info.st_min,
prod_avg.avg_q
from prod_max_info, prod_min_info, prod_avg
where prod_max_info.prod = prod_min_info.prod and prod_max_info.prod = prod_avg.prod);

select * from part1;
