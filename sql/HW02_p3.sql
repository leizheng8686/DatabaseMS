with avg_max as
(select prod, month, avg(quant) as avgq, max(quant) as maxq from sales group by prod, month),
b_tot as
(select am.prod, am.month, count(s.quant) as BEFORE_TOT from avg_max am, sales s 
where am.prod = s.prod and s.month = am.month-1 and s.quant between am.avgq and am.maxq group by am.prod, am.month),
a_tot as
(select am.prod, am.month, count(s.quant) as AFTER_TOT from avg_max am, sales s 
where am.prod = s.prod and s.month = am.month+1 and s.quant between am.avgq and am.maxq group by am.prod, am.month)
select * from b_tot natural full join a_tot order by prod, month