drop table if exists t_item_order;
create table t_item_order (
	fid int(10) not null auto_increment,
	fitemId int(4),
	fitemCount int(2),
	fprice DECIMAL(5,2),
	fprintingStatus int(2),
	fprovidingStatus int(2),
	fpaymentStatus int(2),
	ftableNo int(4),
	fbillNo VARCHAR(20), /*20130517001001*/
	fcreateTime timestamp,
	PRIMARY KEY(fid)
) AUTO_INCREMENT=1;
