with b_avg as
(select cust as CUSTOMER, prod as PRODUCT, month+1 as MONTH, ROUND(avg(quant),0) as BEFORE_AVG from sales where month<12 group by cust, prod, month),
a_avg as
(select cust as CUSTOMER, prod as PRODUCT, month-1 as MONTH, ROUND(avg(quant),0) as AFTER_AVG from sales where month>1 group by cust, prod, month)
select * from b_avg natural full outer join a_avg order by CUSTOMER, PRODUCT, MONTH