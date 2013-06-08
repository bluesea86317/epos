/*
Navicat MySQL Data Transfer

Source Server         : localhost_db
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : epos

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2013-06-08 15:14:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_bill`
-- ----------------------------
DROP TABLE IF EXISTS `t_bill`;
CREATE TABLE `t_bill` (
  `fid` int(10) NOT NULL AUTO_INCREMENT,
  `fbillNo` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '流水单号, 格式年月日+桌台号+时分秒, 例如20130517001001',
  `ftableNo` int(4) DEFAULT NULL,
  `ftotalPrice` decimal(5,2) DEFAULT NULL COMMENT '结账的总金额',
  `fdiscountPrice` decimal(5,2) DEFAULT NULL COMMENT '实际应付的总金额, 既是折扣价',
  `fbillStatus` int(2) DEFAULT NULL COMMENT '流水单状态 0:未结账, 1:结账',
  `fpaymentTime` datetime DEFAULT NULL COMMENT '结账时间',
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='流水单表';

-- ----------------------------
-- Records of t_bill
-- ----------------------------

-- ----------------------------
-- Table structure for `t_department`
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
  `fid` int(4) NOT NULL AUTO_INCREMENT,
  `fdepartmentName` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '部门名',
  `fprinterInfo` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '部门关联的打印机信息',
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_department
-- ----------------------------

-- ----------------------------
-- Table structure for `t_flavor`
-- ----------------------------
DROP TABLE IF EXISTS `t_flavor`;
CREATE TABLE `t_flavor` (
  `fid` int(4) NOT NULL AUTO_INCREMENT,
  `fflavorType` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '口味类型',
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='口味类型表';

-- ----------------------------
-- Records of t_flavor
-- ----------------------------

-- ----------------------------
-- Table structure for `t_item`
-- ----------------------------
DROP TABLE IF EXISTS `t_item`;
CREATE TABLE `t_item` (
  `fid` int(4) NOT NULL AUTO_INCREMENT,
  `fitemName` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fpicName` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fimageUrl` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fprice` decimal(5,2) DEFAULT NULL,
  `fitemTypeId` int(4) DEFAULT NULL,
  `fifCanOrderHalf` int(2) DEFAULT NULL COMMENT '预留字段,目前没用,是否允许点半份, 0:不允许, 1:允许',
  `fflavorIds` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '口味类型',
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='菜品信息表';

-- ----------------------------
-- Records of t_item
-- ----------------------------

-- ----------------------------
-- Table structure for `t_item_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_item_order`;
CREATE TABLE `t_item_order` (
  `fid` int(10) NOT NULL AUTO_INCREMENT,
  `fitemId` int(4) DEFAULT NULL COMMENT '菜品ID',
  `fitemCount` int(2) DEFAULT NULL COMMENT '菜品份数',
  `fflavorId` int(4) DEFAULT NULL COMMENT '口味类型ID',
  `fprice` decimal(5,2) DEFAULT NULL COMMENT '总金额',
  `fprintingStatus` int(2) DEFAULT NULL COMMENT '菜单目录打印状态, 此处为冗余字段, 可忽略',
  `fprovidingStatus` int(2) DEFAULT NULL COMMENT '菜单所点菜品上菜状态, 0:未上菜, 1:已上菜',
  `fpaymentStatus` int(2) DEFAULT NULL COMMENT '菜单所点菜品结账状态, 和关联的流水单结账状态一致',
  `ftableNo` int(4) DEFAULT NULL,
  `fbillNo` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '流水单号',
  `fcreateTime` datetime DEFAULT NULL COMMENT '下单时间',
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='菜单表';

-- ----------------------------
-- Records of t_item_order
-- ----------------------------

-- ----------------------------
-- Table structure for `t_item_order_forprint`
-- ----------------------------
DROP TABLE IF EXISTS `t_item_order_forprint`;
CREATE TABLE `t_item_order_forprint` (
  `fid` int(10) NOT NULL AUTO_INCREMENT,
  `fitemId` int(4) DEFAULT NULL,
  `fitemCount` int(2) DEFAULT NULL,
  `fflavorId` int(4) DEFAULT NULL,
  `fprice` decimal(5,2) DEFAULT NULL,
  `fprintingStatus` int(2) DEFAULT NULL COMMENT '菜单目录打印状态, 0:未打印, 1:已打印',
  `fprovidingStatus` int(2) DEFAULT NULL COMMENT '冗余字段, 可忽略',
  `fpaymentStatus` int(2) DEFAULT NULL COMMENT '冗余字段, 可忽略',
  `ftableNo` int(4) DEFAULT NULL,
  `fbillNo` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '流水单号',
  `fcreateTime` datetime DEFAULT NULL COMMENT '下单时间',
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='菜品打印状态记录表';

-- ----------------------------
-- Records of t_item_order_forprint
-- ----------------------------

-- ----------------------------
-- Table structure for `t_item_type`
-- ----------------------------
DROP TABLE IF EXISTS `t_item_type`;
CREATE TABLE `t_item_type` (
  `fid` int(4) NOT NULL AUTO_INCREMENT,
  `fitemType` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '菜品类型',
  `fdepartmentId` int(4) DEFAULT NULL COMMENT '菜品类型关联的部门ID',
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='菜品类型表';

-- ----------------------------
-- Records of t_item_type
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sys_param`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_param`;
CREATE TABLE `t_sys_param` (
  `fid` int(4) NOT NULL AUTO_INCREMENT,
  `fkey` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '系统参数名, 目前就是用来存储是否让顾客下单的标识',
  `fvalue` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '系统参数值',
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='系统参数设置表';

-- ----------------------------
-- Records of t_sys_param
-- ----------------------------

-- ----------------------------
-- Table structure for `t_table`
-- ----------------------------
DROP TABLE IF EXISTS `t_table`;
CREATE TABLE `t_table` (
  `fid` int(4) NOT NULL AUTO_INCREMENT,
  `ftableNo` int(4) DEFAULT NULL COMMENT '桌台编号',
  `ftableName` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fseatingNum` int(2) DEFAULT NULL COMMENT '桌台座位数',
  `ftableStatus` int(2) DEFAULT NULL COMMENT '桌台状态 0:空闲, 1:开台在用, 2:已买单, 3:已预订',
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_table
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `fid` int(4) NOT NULL AUTO_INCREMENT,
  `fuserName` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fpassword` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `frealName` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fmobile` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fisAdmin` int(2) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '*DA28842831B3C40F4BC1D3C76CF9AD8CBFDAE1CB', '管理员', '15000000000', '1');
