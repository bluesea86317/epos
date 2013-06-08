drop table if exists t_sys_param;
create table t_sys_param (
	fid int(4) not null auto_increment,
	fkey VARCHAR(20) COMMENT '系统参数名, 目前就是用来存储是否让顾客下单的标识',
	fvalue VARCHAR(20) COMMENT '系统参数值',
	PRIMARY KEY(fid)
) AUTO_INCREMENT=1,COMMENT='系统参数设置表';
