drop table if exists t_item;
create table t_item (
	fid int(4) not null auto_increment,
	fitemName VARCHAR(100),
  fpicName VARCHAR(30),
	fimageUrl VARCHAR(300),
	fprice DECIMAL(5,2),
	fitemTypeId int(4),
	fifCanOrderHalf int(2),
	fflavorIds VARCHAR(20),
	PRIMARY KEY(fid)
) AUTO_INCREMENT=1;
