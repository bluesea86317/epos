drop table if exists t_item;
create table t_item (
	fid int(4) not null auto_increment,
	fitemName VARCHAR(100),
  fpicName VARCHAR(30),
	fimageUrl VARCHAR(300),
	fprice DECIMAL(5,2),
	fitemTypeId int(4),
	fifCanOrderHalf int(2) COMMENT '预留字段,目前没用,是否允许点半份, 0:不允许, 1:允许',
	fflavorIds VARCHAR(20) COMMENT '口味类型',
	PRIMARY KEY(fid)
) AUTO_INCREMENT=1,COMMENT='菜品信息表';
