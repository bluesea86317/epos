drop table if exists t_user;
create table t_user (
	fid int(4) not null auto_increment,
	fuserName VARCHAR(20),
	fpassword VARCHAR(50),
	PRIMARY KEY(fid)
) AUTO_INCREMENT=1;