/*
Navicat MySQL Data Transfer

Source Server         : localhost_db
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : epos

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2013-05-10 19:33:56
*/

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_item
-- ----------------------------

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
