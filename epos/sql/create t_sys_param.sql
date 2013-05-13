drop table if exists t_sys_param;
create table t_sys_param (
	fid int(4) not null auto_increment,
	fkey VARCHAR(20),
	fvalue VARCHAR(20),
	PRIMARY KEY(fid)
) AUTO_INCREMENT=1;
