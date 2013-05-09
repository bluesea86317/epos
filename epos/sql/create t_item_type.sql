drop table if exists t_item_type;
create table t_item_type (
	fid int(4) not null auto_increment,
	fitemType VARCHAR(20),
	fdepartmentId int(4),
	PRIMARY KEY(fid)
) AUTO_INCREMENT=1;
