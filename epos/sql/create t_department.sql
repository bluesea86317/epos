drop table if exists t_department;
create table t_department (
	fid int(4) not null auto_increment,
	fdepartmentName VARCHAR(20),
	fprinterInfo VARCHAR(200),
	PRIMARY KEY(fid)
) AUTO_INCREMENT=1;
