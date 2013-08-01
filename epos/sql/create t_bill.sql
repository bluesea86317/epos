drop table if exists t_bill;
create table t_bill (
	fid int(10) not null auto_increment,
	fbillNo VARCHAR(20) COMMENT '流水单号, 格式年月日+桌台号+时分秒, 例如20130517001001',
	ftableNo int(4),	
	ftotalPrice DECIMAL(12,2) COMMENT '结账的总金额',
        fdiscountPrice DECIMAL(5,2) COMMENT '实际应付的总金额, 既是折扣价',
	fbillStatus int(2) COMMENT '流水单状态 0:未结账, 1:结账',
	fpaymentTime datetime COMMENT '结账时间',
	fcustomerNum int(4) COMMENT '总的顾客人数',
	PRIMARY KEY(fid)
) AUTO_INCREMENT=1,COMMENT='流水单表';
