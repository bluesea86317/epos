/*
Navicat MySQL Data Transfer

Source Server         : localhost_db
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : epos

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2013-05-23 19:28:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_bill`
-- ----------------------------
DROP TABLE IF EXISTS `t_bill`;
CREATE TABLE `t_bill` (
  `fid` int(10) NOT NULL AUTO_INCREMENT,
  `fbillNo` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ftableNo` int(4) DEFAULT NULL,
  `ftotalPrice` decimal(5,2) DEFAULT NULL,
  `fbillStatus` int(2) DEFAULT NULL,
  `fpaymentTime` datetime DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_bill
-- ----------------------------
INSERT INTO `t_bill` VALUES ('9', '20130523005183959', '5', '191.34', '1', '2013-05-23 18:44:14');
INSERT INTO `t_bill` VALUES ('10', '20130523005191028', '5', '71.78', '0', null);

-- ----------------------------
-- Table structure for `t_department`
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
  `fid` int(4) NOT NULL AUTO_INCREMENT,
  `fdepartmentName` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fprinterInfo` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_department
-- ----------------------------
INSERT INTO `t_department` VALUES ('3', '服务部222', '12222');
INSERT INTO `t_department` VALUES ('4', '后勤部', '2');

-- ----------------------------
-- Table structure for `t_flavor`
-- ----------------------------
DROP TABLE IF EXISTS `t_flavor`;
CREATE TABLE `t_flavor` (
  `fid` int(4) NOT NULL AUTO_INCREMENT,
  `fflavorType` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_flavor
-- ----------------------------
INSERT INTO `t_flavor` VALUES ('2', '微辣update');
INSERT INTO `t_flavor` VALUES ('3', '多葱');

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
  `fifCanOrderHalf` int(2) DEFAULT NULL,
  `fflavorIds` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_item
-- ----------------------------
INSERT INTO `t_item` VALUES ('2', '清炒随便update', '1368429920438.jpg', '/images/item/1368429920438.jpg', '20.88', '2', null, '2,3');
INSERT INTO `t_item` VALUES ('3', '爆炒鱿鱼22', '1368429920438.jpg', '/images/item/1368429920438.jpg', '30.90', '1', null, '1,2,3');
INSERT INTO `t_item` VALUES ('4', '清炒随便11', '1368429920438.jpg', '/images/item/1368429920438.jpg', '20.88', '2', null, '2,3');
INSERT INTO `t_item` VALUES ('5', '洋葱炒蛋', '1368429920438.jpg', '/images/item/1368429920438.jpg', '30.90', '1', null, '');
INSERT INTO `t_item` VALUES ('6', '蛋炒洋葱', '1368429920438.jpg', '/images/item/1368429920438.jpg', '20.88', '2', null, '');
INSERT INTO `t_item` VALUES ('7', '茶位费', '', '/images/item/', '2.00', '2', null, '');

-- ----------------------------
-- Table structure for `t_item_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_item_order`;
CREATE TABLE `t_item_order` (
  `fid` int(10) NOT NULL AUTO_INCREMENT,
  `fitemId` int(4) DEFAULT NULL,
  `fitemCount` int(2) DEFAULT NULL,
  `fflavorId` int(4) DEFAULT NULL,
  `fprice` decimal(5,2) DEFAULT NULL,
  `fprintingStatus` int(2) DEFAULT NULL,
  `fprovidingStatus` int(2) DEFAULT NULL,
  `fpaymentStatus` int(2) DEFAULT NULL,
  `ftableNo` int(4) DEFAULT NULL,
  `fbillNo` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fcreateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_item_order
-- ----------------------------
INSERT INTO `t_item_order` VALUES ('19', '7', '4', '0', '8.00', '1', '1', '1', '7', '20130523005183959', '2013-05-23 18:39:48');
INSERT INTO `t_item_order` VALUES ('20', '7', '4', '0', '8.00', '1', '1', '1', '4', '20130523005183959', '2013-05-23 18:39:51');
INSERT INTO `t_item_order` VALUES ('21', '7', '10', '0', '20.00', '1', '1', '1', '5', '20130523005183959', '2013-05-23 18:39:59');
INSERT INTO `t_item_order` VALUES ('22', '2', '2', '2', '41.76', '0', '0', '1', '7', '20130523005183959', '2013-05-23 18:40:55');
INSERT INTO `t_item_order` VALUES ('23', '3', '2', '1', '61.80', '0', '0', '1', '7', '20130523005183959', '2013-05-23 18:40:55');
INSERT INTO `t_item_order` VALUES ('24', '2', '1', '2', '20.88', '0', '0', '1', '4', '20130523005183959', '2013-05-23 18:41:08');
INSERT INTO `t_item_order` VALUES ('25', '3', '1', '1', '30.90', '0', '0', '1', '4', '20130523005183959', '2013-05-23 18:41:08');
INSERT INTO `t_item_order` VALUES ('26', '7', '10', '0', '20.00', '1', '1', '0', '5', '20130523005191028', '2013-05-23 19:10:28');
INSERT INTO `t_item_order` VALUES ('27', '2', '1', '2', '20.88', '0', '1', '0', '5', '20130523005191028', '2013-05-23 19:11:04');
INSERT INTO `t_item_order` VALUES ('28', '3', '1', '1', '30.90', '0', '0', '0', '5', '20130523005191028', '2013-05-23 19:11:04');

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
  `fprintingStatus` int(2) DEFAULT NULL,
  `fprovidingStatus` int(2) DEFAULT NULL,
  `fpaymentStatus` int(2) DEFAULT NULL,
  `ftableNo` int(4) DEFAULT NULL,
  `fbillNo` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fcreateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_item_order_forprint
-- ----------------------------
INSERT INTO `t_item_order_forprint` VALUES ('21', '2', '2', '2', '41.76', '1', '0', '0', '7', '20130523005183959', '2013-05-23 18:40:55');
INSERT INTO `t_item_order_forprint` VALUES ('22', '3', '2', '1', '61.80', '1', '0', '0', '7', '20130523005183959', '2013-05-23 18:40:55');
INSERT INTO `t_item_order_forprint` VALUES ('23', '2', '1', '2', '20.88', '1', '0', '0', '4', '20130523005183959', '2013-05-23 18:41:08');
INSERT INTO `t_item_order_forprint` VALUES ('24', '3', '1', '1', '30.90', '1', '0', '0', '4', '20130523005183959', '2013-05-23 18:41:08');
INSERT INTO `t_item_order_forprint` VALUES ('25', '2', '1', '2', '20.88', '0', '0', '0', '5', '20130523005191028', '2013-05-23 19:11:04');
INSERT INTO `t_item_order_forprint` VALUES ('26', '3', '1', '1', '30.90', '0', '0', '0', '5', '20130523005191028', '2013-05-23 19:11:04');

-- ----------------------------
-- Table structure for `t_item_type`
-- ----------------------------
DROP TABLE IF EXISTS `t_item_type`;
CREATE TABLE `t_item_type` (
  `fid` int(4) NOT NULL AUTO_INCREMENT,
  `fitemType` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fdepartmentId` int(4) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_item_type
-- ----------------------------
INSERT INTO `t_item_type` VALUES ('1', '海鲜update', '4');
INSERT INTO `t_item_type` VALUES ('2', '炖品update', '4');
INSERT INTO `t_item_type` VALUES ('3', '每日推荐update', '3');

-- ----------------------------
-- Table structure for `t_sys_param`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_param`;
CREATE TABLE `t_sys_param` (
  `fid` int(4) NOT NULL AUTO_INCREMENT,
  `fkey` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fvalue` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_sys_param
-- ----------------------------
INSERT INTO `t_sys_param` VALUES ('1', 'ifCanOrder', '0');

-- ----------------------------
-- Table structure for `t_table`
-- ----------------------------
DROP TABLE IF EXISTS `t_table`;
CREATE TABLE `t_table` (
  `fid` int(4) NOT NULL AUTO_INCREMENT,
  `ftableNo` int(4) DEFAULT NULL,
  `ftableName` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fseatingNum` int(2) DEFAULT NULL,
  `ftableStatus` int(2) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_table
-- ----------------------------
INSERT INTO `t_table` VALUES ('3', '4', '4号桌', '10', '0');
INSERT INTO `t_table` VALUES ('4', '5', '5号桌', '4', '1');
INSERT INTO `t_table` VALUES ('5', '7', '7号桌', '10', '0');
INSERT INTO `t_table` VALUES ('6', '8', '8号桌', '4', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '*DA28842831B3C40F4BC1D3C76CF9AD8CBFDAE1CB', '管理员', '15000000000', '1');
