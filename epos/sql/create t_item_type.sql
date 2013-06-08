drop table if exists t_item_type;
create table t_item_type (
	fid int(4) not null auto_increment,
	fitemType VARCHAR(20) COMMENT '菜品类型',
	fdepartmentId int(4) COMMENT '菜品类型关联的部门ID',
	PRIMARY KEY(fid)
) AUTO_INCREMENT=1,COMMENT='菜品类型表';
