drop table if exists t_item_order_forprint;
create table t_item_order_forprint (
	fid int(10) not null auto_increment,
	fitemId int(4),
	fitemCount int(2),
	fflavorId int(4),
	fprice DECIMAL(12,2),
	fprintingStatus int(2) COMMENT '菜单目录打印状态, 0:未打印, 1:已打印',
	fprovidingStatus int(2) COMMENT '冗余字段, 可忽略',
	fpaymentStatus int(2) COMMENT '冗余字段, 可忽略',
	ftableNo int(4),
	fbillNo VARCHAR(20) COMMENT '流水单号',
	fcreateTime datetime COMMENT '下单时间',
	forderType int(2)  COMMENT '点菜类型, 1: 点菜或者加菜  0:退菜',
	PRIMARY KEY(fid)
) AUTO_INCREMENT=1, COMMENT='菜品打印状态记录表';
