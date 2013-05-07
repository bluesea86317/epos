drop table if exists t_user;
create table t_user (
	fid int(4) not null auto_increment,
	fuserName VARCHAR(20),
	fpassword VARCHAR(50),
	frealName VARCHAR(50),
	fmobile VARCHAR(20),
	fisAdmin int(2),
	PRIMARY KEY(fid)
) AUTO_INCREMENT=1;
