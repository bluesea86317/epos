drop table if exists t_department;
create table t_department (
	fid int(4) not null auto_increment,
	fdepartmentName VARCHAR(20) COMMENT '部门名',
	fprinterInfo VARCHAR(200) COMMENT '部门关联的打印机信息',
	PRIMARY KEY(fid)
) AUTO_INCREMENT=1;
