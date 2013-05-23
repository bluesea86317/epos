drop table if exists t_bill;
create table t_bill (
	fid int(10) not null auto_increment,
	fbillNo VARCHAR(20), /*20130517001001*/
	ftableNo int(4),	
	ftotalPrice DECIMAL(5,2),
	fbillStatus int(2),
	fpaymentTime datetime,
	PRIMARY KEY(fid)
) AUTO_INCREMENT=1;
