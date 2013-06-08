drop table if exists t_flavor;
create table t_flavor (
	fid int(4) not null auto_increment,
	fflavorType VARCHAR(20) COMMENT '口味类型',
	PRIMARY KEY(fid)
) AUTO_INCREMENT=1, COMMENT='口味类型表';
