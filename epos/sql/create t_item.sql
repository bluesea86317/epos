drop table if exists t_item;
create table t_item (
	fid int(4) not null auto_increment,
	fitemName VARCHAR(100),
        fpicName VARCHAR(30) COMMENT '大图名称',
	fimageUrl VARCHAR(300) COMMENT '大图地址',
        fsmallPicName VARCHAR(30) COMMENT '小图名称',
	fsmallImageUrl VARCHAR(300) COMMENT '小图地址',
	fprice DECIMAL(12,2),
	fitemTypeId int(4),
	fifCanOrderHalf int(2) COMMENT '预留字段,目前没用,是否允许点半份, 0:不允许, 1:允许',
	fflavorIds VARCHAR(20) COMMENT '口味类型',
	fitemReserveStatus int(2) default 1 COMMENT '菜品存量状态,0: 估清, 1: 正常',
	PRIMARY KEY(fid)
) AUTO_INCREMENT=1,COMMENT='菜品信息表';
