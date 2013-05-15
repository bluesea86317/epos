drop table if exists t_table;
create table t_table (
	fid int(4) not null auto_increment,
	ftableNo int(4),
	ftableName VARCHAR(20),
	fseatingNum int(2),
	ftableStatus int(2),	
	PRIMARY KEY(fid)
) AUTO_INCREMENT=1;
