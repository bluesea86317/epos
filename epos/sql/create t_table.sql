drop table if exists t_table;
create table t_table (
	fid int(4) not null auto_increment,
	ftableNo int(4) COMMENT '桌台编号',
	ftableName VARCHAR(20),
	fseatingNum int(2) COMMENT '桌台座位数',
	ftableStatus int(2) COMMENT '桌台状态 0:空闲, 1:开台在用, 2:已买单, 3:已结账,4:已预订',	
	PRIMARY KEY(fid)
) AUTO_INCREMENT=1;
