/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50612
Source Host           : localhost:3306
Source Database       : student_manager

Target Server Type    : MYSQL
Target Server Version : 50612
File Encoding         : 65001

Date: 2014-06-16 01:48:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `class`
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `classno` varchar(6) NOT NULL,
  `faculty` varchar(50) NOT NULL,
  `chief_tno` varchar(4) DEFAULT NULL,
  `stuNum` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`classno`),
  KEY `FK_Class_cheifTecher` (`chief_tno`),
  KEY `FK_Class_faculty` (`faculty`),
  CONSTRAINT `FK_Class_cheifTecher` FOREIGN KEY (`chief_tno`) REFERENCES `teacher` (`tno`),
  CONSTRAINT `FK_Class_faculty` FOREIGN KEY (`faculty`) REFERENCES `faculty` (`fname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES ('Rj1701', '软件学院', '3002', '26');
INSERT INTO `class` VALUES ('Rj1702', '软件学院', '3005', '27');
INSERT INTO `class` VALUES ('Rj1703', '软件学院', '3002', '30');
INSERT INTO `class` VALUES ('Rj1705', '软件学院', '3002', '43');
INSERT INTO `class` VALUES ('Rj1701', '软件学院', '3003', '28');
INSERT INTO `class` VALUES ('Rj1702', '软件学院', '3003', '28');
INSERT INTO `class` VALUES ('Rj1703', '软件学院', '3002', '26');
INSERT INTO `class` VALUES ('Rj1704', '软件学院', '3005', '28');

-- ----------------------------
-- Table structure for `course`
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `cno` varchar(8) NOT NULL,
  `cname` varchar(30) DEFAULT NULL,
  `credit` smallint(1) DEFAULT NULL,
  `property` enum('Elective','Required') DEFAULT 'Required',
  `assess_method` enum('Check','Test') DEFAULT 'Test',
  PRIMARY KEY (`cno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('RJ083001', '软件工程概论', '2', 'Required', 'Test');
INSERT INTO `course` VALUES ('RJ083002', '数据结构', '4', 'Required', 'Test');
INSERT INTO `course` VALUES ('RJ083003', '算法导论', '2', 'Required', 'Test');

-- ----------------------------
-- Table structure for `faculty`
-- ----------------------------
DROP TABLE IF EXISTS `faculty`;
CREATE TABLE `faculty` (
  `fname` varchar(50) NOT NULL,
  `dean` varchar(4) DEFAULT NULL,
  `buildingno` varchar(5) NOT NULL,
  `liaison` varchar(4) DEFAULT NULL,
  `telNo` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`fname`),
  KEY `FK_Faculty_dean` (`dean`),
  KEY `FK_Faculty_liaison` (`liaison`),
  CONSTRAINT `FK_Faculty_dean` FOREIGN KEY (`dean`) REFERENCES `teacher` (`tno`),
  CONSTRAINT `FK_Faculty_liaison` FOREIGN KEY (`liaison`) REFERENCES `teacher` (`tno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of faculty
-- ----------------------------
INSERT INTO `faculty` VALUES ('建筑与艺术学院', '2901', '29', '3001', '010-5152742');
INSERT INTO `faculty` VALUES ('计算机信息技术学院', '2801', '28', '2801', '010-5152894');
INSERT INTO `faculty` VALUES ('软件学院', '3001', '30', '3003', '010-5152432');

-- ----------------------------
-- Table structure for `sc`
-- ----------------------------
DROP TABLE IF EXISTS `sc`;
CREATE TABLE `sc` (
  `sno` varchar(8) NOT NULL,
  `cno` varchar(8) NOT NULL,
  `sequence` smallint(1) NOT NULL,
  `grade` smallint(3) DEFAULT NULL,
  PRIMARY KEY (`sno`,`cno`,`sequence`),
  KEY `FK_SC_cno` (`cno`),
  CONSTRAINT `FK_SC_cno` FOREIGN KEY (`cno`) REFERENCES `course` (`cno`),
  CONSTRAINT `FK_SC_sno` FOREIGN KEY (`sno`) REFERENCES `student` (`sno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sc
-- ----------------------------

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `sno` varchar(8) NOT NULL,
  `classno` varchar(6) NOT NULL,
  `sname` varchar(30) NOT NULL,
  `ssex` varchar(1) DEFAULT 'm',
  `sbirth` date DEFAULT NULL,
  `nationality` varchar(20) DEFAULT NULL,
  `faculty` varchar(50) DEFAULT NULL,
  `major` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`sno`),
  KEY `FK_Student_classno` (`classno`),
  KEY `FK_Student_faculty` (`faculty`),
  CONSTRAINT `FK_Student_classno` FOREIGN KEY (`classno`) REFERENCES `class` (`classno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Student_faculty` FOREIGN KEY (`faculty`) REFERENCES `faculty` (`fname`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('17301158', 'Rj1706', '李旭', 'm', '2000-07-08', '汉', '软件学院', '软件工程');

-- ----------------------------
-- Table structure for `teacher`
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `tno` varchar(4) NOT NULL,
  `tname` varchar(30) NOT NULL,
  `tsex` varchar(1) DEFAULT 'm',
  `tbirth` date DEFAULT NULL,
  `nationality` varchar(20) DEFAULT '汉',
  `faculty` varchar(50) NOT NULL,
  `tmajor` varchar(40) DEFAULT NULL,
  `title` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`tno`),
  KEY `FK_Teacher_faculty` (`faculty`),
  CONSTRAINT `FK_Teacher_faculty` FOREIGN KEY (`faculty`) REFERENCES `faculty` (`fname`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('2801', '张毅', 'm', '2014-04-09', '汉', '计算机信息技术学院', '人工智能', '院长');
INSERT INTO `teacher` VALUES ('2901', '李华', 'm', '1984-07-20', '汉', '建筑与艺术学院', '城市规划', '院长');
INSERT INTO `teacher` VALUES ('3001', '芦苇', 'm', '1980-07-12', '汉', '软件学院', '软件工程', '院长');
INSERT INTO `teacher` VALUES ('3002', '赵宏', 'f', '1982-07-08', '汉', '软件学院', '软件工程', '副院长');
INSERT INTO `teacher` VALUES ('3003', '陈旭东', 'm', '1985-07-11', '汉', '软件学院', '软件工程', '系主任');
INSERT INTO `teacher` VALUES ('3005', '郭洋宏', 'm', '2014-04-12', '汉', '软件学院', '软件工程', '班主任');

-- ----------------------------
-- Table structure for `teaching`
-- ----------------------------
DROP TABLE IF EXISTS `teaching`;
CREATE TABLE `teaching` (
  `tno` varchar(4) NOT NULL,
  `cno` varchar(8) NOT NULL,
  `time` varchar(15) NOT NULL,
  `roomNo` varchar(10) NOT NULL,
  `sequence` smallint(1) NOT NULL,
  `language` varchar(8) DEFAULT 'Chinese',
  PRIMARY KEY (`time`,`roomNo`),
  KEY `FK_Teaching_cno` (`cno`),
  KEY `FK_Teaching_tno` (`tno`),
  CONSTRAINT `FK_Teaching_cno` FOREIGN KEY (`cno`) REFERENCES `course` (`cno`),
  CONSTRAINT `FK_Teaching_tno` FOREIGN KEY (`tno`) REFERENCES `teacher` (`tno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teaching
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `username` varchar(8) NOT NULL,
  `password` varchar(10) NOT NULL,
  `role` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `FK_User_sno` FOREIGN KEY (`username`) REFERENCES `student` (`sno`),
  CONSTRAINT `FK_User_tno` FOREIGN KEY (`username`) REFERENCES `teacher` (`tno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
