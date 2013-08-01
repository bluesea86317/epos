drop table if exists t_item_order;
create table t_item_order (
	fid int(10) not null auto_increment,
	fitemId int(4) COMMENT '菜品ID',
	fitemCount int(2) COMMENT '菜品份数',
	fflavorId int(4) COMMENT '口味类型ID',
	fprice DECIMAL(12,2) COMMENT '总金额',
	fprintingStatus int(2) COMMENT '菜单目录打印状态, 此处为冗余字段, 可忽略',
	fprovidingStatus int(2) COMMENT '菜单所点菜品上菜状态, 0:未上菜, 1:已上菜',
	fpaymentStatus int(2) COMMENT '菜单所点菜品结账状态, 和关联的流水单结账状态一致',
	ftableNo int(4),
	fbillNo VARCHAR(20) COMMENT '流水单号',
	fcreateTime datetime COMMENT '下单时间',
	PRIMARY KEY(fid)
) AUTO_INCREMENT=1,COMMENT='菜单表';
