with theAvg as 
(select cust as CUSTOMER, prod as PRODUCT, round(avg(quant),0) as THE_AVG from sales 
group by cust, prod),
OPA as 
(select l.cust as CUSTOMER, l.prod as PRODUCT, round(avg(r.quant),0) as OTHER_PROD_AVG from sales as l, sales as r 
where l.cust = r.cust and l.prod != r.prod group by l.cust, l.prod),
OCA as 
(select l.cust as CUSTOMER, l.prod as PRODUCT, round(avg(r.quant),0) as OTHER_CUST_AVG from sales as l, sales as r
where l.cust != r.cust and l.prod = r.prod group by l.cust, l.prod)
select * from (theAvg natural join OPA) natural join OCA order by CUSTOMER, PRODUCT;