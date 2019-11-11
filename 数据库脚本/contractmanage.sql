/*
Navicat MySQL Data Transfer

Source Server         : 172.31.34.50_admin
Source Server Version : 50612
Source Host           : 172.31.34.50:3306
Source Database       : contractmanage

Target Server Type    : MYSQL
Target Server Version : 50612
File Encoding         : 65001

Date: 2014-05-14 14:42:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `attachment`
-- ----------------------------
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contractno` int(11) NOT NULL,
  `filename` varchar(50) NOT NULL,
  `filepath` varchar(200) NOT NULL,
  `filetype` varchar(20) DEFAULT NULL,
  `submitTime` timestamp NULL DEFAULT NULL,
  `del` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_attach` (`contractno`),
  CONSTRAINT `FK_attach` FOREIGN KEY (`contractno`) REFERENCES `contract` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of attachment
-- ----------------------------

-- ----------------------------
-- Table structure for `client`
-- ----------------------------
DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  `fax` varchar(30) DEFAULT NULL,
  `tel` varchar(20) NOT NULL,
  `postcode` varchar(20) DEFAULT NULL,
  `bankname` varchar(50) DEFAULT NULL,
  `bankaccount` varchar(20) DEFAULT NULL,
  `del` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of client
-- ----------------------------
INSERT INTO `client` VALUES ('1', '张三', '', '0', '17515742153', '100044', '中国银行', '1234567890', '0');
INSERT INTO `client` VALUES ('2', '李四', '', '1', '1881144****', '100044', '中国银行', 'null', '0');
INSERT INTO `client` VALUES ('3', '王五', '', '223', '1881144****', '100044', '中国银行', '1234567890', '0');
INSERT INTO `client` VALUES ('4', '钱六', '北京海淀', '334', '1881144****', '100044', '中国银行', '', '0');
INSERT INTO `client` VALUES ('5', '李七', '北京海淀', '445', '1881144****', '100044', '中国银行', '', '0');
INSERT INTO `client` VALUES ('6', 'Client01', '北京海淀', '5', '1881144****', '100044', '', '', '0');
INSERT INTO `client` VALUES ('7', 'Client02', '北京海淀', '10', '1881144****', '100044', '', '', '1');

-- ----------------------------
-- Table structure for `contract`
-- ----------------------------
DROP TABLE IF EXISTS `contract`;
CREATE TABLE `contract` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `client` int(11) NOT NULL,
  `startTime` date DEFAULT NULL,
  `finishTime` date DEFAULT NULL,
  `content` varchar(500) NOT NULL,
  `drafter` int(11) NOT NULL,
  `countersigner` int(11) DEFAULT NULL,
  `approver` int(11) DEFAULT NULL,
  `signer` int(11) DEFAULT NULL,
  `del` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_10` (`countersigner`),
  KEY `FK_Reference_11` (`drafter`),
  KEY `FK_Reference_12` (`signer`),
  KEY `FK_Reference_9` (`approver`),
  KEY `FK_own` (`client`),
  CONSTRAINT `FK_own` FOREIGN KEY (`client`) REFERENCES `client` (`id`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`countersigner`) REFERENCES `system_user` (`id`),
  CONSTRAINT `FK_Reference_11` FOREIGN KEY (`drafter`) REFERENCES `system_user` (`id`),
  CONSTRAINT `FK_Reference_12` FOREIGN KEY (`signer`) REFERENCES `system_user` (`id`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`approver`) REFERENCES `system_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of contract
-- ----------------------------
INSERT INTO `contract` VALUES ('1', '合同01', '1', '2014-04-01', '2014-04-09', '这是合同内容这是合同内容这是合同内容这是合同内容这是合同内容', '1', null, null, null, '0');
INSERT INTO `contract` VALUES ('2', '合同02', '1', '2014-04-23', '2014-04-27', '合同内容合同内容合同内容合同内容合同内容合同内容', '1', null, null, null, '0');
INSERT INTO `contract` VALUES ('6', '合同03', '1', '2014-04-01', '2014-04-16', '', '3', null, null, null, '0');
INSERT INTO `contract` VALUES ('8', '合同04', '1', '2014-04-24', '2014-04-29', '这是合同内容', '1', null, null, null, '0');
INSERT INTO `contract` VALUES ('9', 'contract0011', '1', '2013-08-02', '2014-10-25', 'qwertyu', '1', null, null, null, '1');
INSERT INTO `contract` VALUES ('10', 'contract0034', '1', '2013-08-02', '2014-08-02', 'qwertyu', '3', null, null, null, '0');
INSERT INTO `contract` VALUES ('11', 'contract0012', '1', '2014-02-02', '2014-08-06', 'qsbbcsjxcnckjxcbn', '1', null, null, null, '0');
INSERT INTO `contract` VALUES ('12', 'contract0021', '1', '2014-02-02', '2014-08-06', 'csjxcnckjxcbn', '3', null, null, null, '0');
INSERT INTO `contract` VALUES ('13', 'contract0024', '1', '2014-02-02', '2014-08-06', 'csjhkjcdbkj xlkfnoshljgho', '3', null, null, null, '0');
INSERT INTO `contract` VALUES ('14', 'contract0065', '1', '2014-02-02', '2014-08-06', 'csjhkjcdbkj xlkfnoshljgho', '1', null, null, null, '0');
INSERT INTO `contract` VALUES ('15', 'contract0054', '1', '2014-02-02', '2014-08-06', 'csjhkjcdbkj xlkfnoshljgho', '2', null, null, null, '0');
INSERT INTO `contract` VALUES ('16', 'contract0023', '1', '2014-02-02', '2014-08-06', 'csjhkjcdbkj xlkfnoshljgho', '2', null, null, null, '0');
INSERT INTO `contract` VALUES ('17', 'contract0014', '1', '2014-02-02', '2014-08-06', 'csjhkjcdbkj xlkfnoshljgho', '2', null, null, null, '0');
INSERT INTO `contract` VALUES ('18', 'contract0053', '1', '2014-02-02', '2014-08-06', 'csjhkjcdbkj xlkfnoshljgho', '2', null, null, null, '0');
INSERT INTO `contract` VALUES ('19', 'contract2357', '2', '2014-05-13', '2014-05-25', '这是又一份合同', '5', '6', null, null, '0');
INSERT INTO `contract` VALUES ('21', '北京交通大学住房协议', '1', '2012-04-07', '2013-04-07', '都是白色底深V', '6', null, null, null, '0');
INSERT INTO `contract` VALUES ('22', '宿舍管理', '1', '2012-04-08', '2013-04-23', '宿舍管理', '6', null, null, null, '0');
INSERT INTO `contract` VALUES ('23', '合同05', '1', '2012-04-08', '2013-04-23', '合同合同合同合同合同合同合同合同合同合同合同合同合同合同合同合同合同合同合同合同合同合同合同合同合同合同合同合同', '1', null, null, null, '0');
INSERT INTO `contract` VALUES ('26', '合同06', '2', '2012-04-07', '2013-04-23', '合同合同合同合同66666666', '1', '6', null, null, '0');
INSERT INTO `contract` VALUES ('27', '合同07', '3', '2012-04-08', '2013-04-23', '宋震SB宋震SB宋震SB', '1', null, null, null, '0');
INSERT INTO `contract` VALUES ('28', 'hetong0001', '2', '2012-04-07', '2013-04-23', '26984326985236987542', '6', '5', null, null, '1');
INSERT INTO `contract` VALUES ('29', 'contract0088', '3', '2012-04-10', '2012-08-10', '1.合伙因以下事由之一得终止：①合伙期届满；②全体合伙人同意终止合伙关系；③合伙事业完成或不能完成；④合伙事\r\n业违反法律被撤销；⑤法院根据有关当事人请求判决解散。\r\n\r\n2.合伙终止后的事项：①即行推举清算人，并邀请____________中间人(或公证员)参与清算；②清算后如有盈余，则按收\r\n取债权、清偿债务、返还出资、按比例分配剩余财产的顺序进行。固定资产和不可分物，可作价卖给合伙人或第三人，其\r\n价款参与分配；③清算后如有亏损，不论合伙人出资多少，先以合伙共同财产偿还，合伙财产不足清偿的部分，由合伙人\r\n按出资比例承担。\r\n', '6', '6', null, null, '1');
INSERT INTO `contract` VALUES ('30', 'contract0002', '2', '2012-04-08', '2012-08-10', 'contract0002contract0002contract0002contract0002contract0002contract0002contract0002contract0002contract0002contract0002contract0002', '6', '5', null, null, '1');
INSERT INTO `contract` VALUES ('31', 'contract0063', '2', '2012-04-10', '2013-04-07', '好 我改好了', '6', '5', '6', '6', '1');
INSERT INTO `contract` VALUES ('32', 'contract0004', '3', '2014-05-01', '2014-05-11', '测试，新的合同，再来一发！', '6', '6', '6', '1', '1');
INSERT INTO `contract` VALUES ('33', 'contract0123', '3', '2012-04-07', '2013-04-23', '第二次修改后的内容！一定给过啊！！contract0123contract0123contract0123contract0123contract0123contract0123contract0123contract0123contract0123contract0123contract0123contract0123contract0123contract0123contract0123contract0123contract0123contract0123', '6', '2', '6', null, '0');
INSERT INTO `contract` VALUES ('34', 'contract0100', '4', '2014-05-22', '2014-05-31', 'OK 定稿contract0100contract0100contract0100contract0100contract0100contract0100contract0100contract0100contract0100contract0100contract0100contract0100contract0100contract0100contract0100contract0100contract0100contract0100', '6', '6', '6', '6', '0');
INSERT INTO `contract` VALUES ('35', 'contract0111', '3', '2014-05-12', '2014-05-23', 'co粉色房顶上空降兵', '6', '6', '6', null, '0');
INSERT INTO `contract` VALUES ('36', 'contract0002', '2', '2014-05-23', '2014-05-28', 'cxsjbsabb', '6', '6', '6', '6', '0');
INSERT INTO `contract` VALUES ('37', 'contract0003', '4', '2014-05-13', '2014-05-17', '12也89298', '6', '6', '6', '6', '0');
INSERT INTO `contract` VALUES ('38', '要大声', '1', '2014-05-08', '2014-05-23', 'feasfgukjeshfiklshkresl', '2', '6', '2', '6', '0');

-- ----------------------------
-- Table structure for `log`
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operator` int(11) NOT NULL,
  `content` varchar(100) NOT NULL,
  `time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_13` (`operator`),
  CONSTRAINT `FK_Reference_13` FOREIGN KEY (`operator`) REFERENCES `system_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=514 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES ('1', '6', '登录系统', '2014-05-03 23:44:47');
INSERT INTO `log` VALUES ('2', '6', '退出系统', '2014-05-03 23:45:02');
INSERT INTO `log` VALUES ('3', '1', '登录系统', '2014-05-04 00:17:22');
INSERT INTO `log` VALUES ('4', '1', '退出系统', '2014-05-04 00:26:22');
INSERT INTO `log` VALUES ('5', '2', '登录系统', '2014-05-04 00:27:47');
INSERT INTO `log` VALUES ('6', '6', '登录系统', '2014-05-04 09:27:13');
INSERT INTO `log` VALUES ('7', '6', '登录系统', '2014-05-04 15:27:07');
INSERT INTO `log` VALUES ('8', '6', '退出系统', '2014-05-04 15:31:26');
INSERT INTO `log` VALUES ('9', '5', '登录系统', '2014-05-04 15:31:32');
INSERT INTO `log` VALUES ('10', '5', '退出系统', '2014-05-04 15:33:32');
INSERT INTO `log` VALUES ('11', '6', '登录系统', '2014-05-04 15:34:49');
INSERT INTO `log` VALUES ('12', '6', '登录系统', '2014-05-04 20:34:24');
INSERT INTO `log` VALUES ('13', '6', '登录系统', '2014-05-05 18:09:14');
INSERT INTO `log` VALUES ('14', '6', '登录系统', '2014-05-05 20:58:49');
INSERT INTO `log` VALUES ('15', '6', '登录系统', '2014-05-05 22:20:00');
INSERT INTO `log` VALUES ('16', '6', '登录系统', '2014-05-05 22:22:33');
INSERT INTO `log` VALUES ('17', '5', '登录系统', '2014-05-05 22:35:10');
INSERT INTO `log` VALUES ('18', '6', '登录系统', '2014-05-05 22:36:33');
INSERT INTO `log` VALUES ('19', '1', '登录系统', '2014-05-05 22:42:14');
INSERT INTO `log` VALUES ('20', '6', '登录系统', '2014-05-05 22:58:39');
INSERT INTO `log` VALUES ('21', '6', '登录系统', '2014-05-05 23:01:35');
INSERT INTO `log` VALUES ('22', '6', '登录系统', '2014-05-05 23:01:37');
INSERT INTO `log` VALUES ('23', '6', '登录系统', '2014-05-05 23:02:42');
INSERT INTO `log` VALUES ('24', '6', '登录系统', '2014-05-05 23:03:46');
INSERT INTO `log` VALUES ('25', '6', '登录系统', '2014-05-05 23:04:36');
INSERT INTO `log` VALUES ('26', '6', '登录系统', '2014-05-05 23:08:43');
INSERT INTO `log` VALUES ('27', '6', '退出系统', '2014-05-05 23:09:11');
INSERT INTO `log` VALUES ('28', '6', '登录系统', '2014-05-05 23:09:23');
INSERT INTO `log` VALUES ('29', '6', '登录系统', '2014-05-05 23:12:04');
INSERT INTO `log` VALUES ('30', '6', '登录系统', '2014-05-05 23:12:56');
INSERT INTO `log` VALUES ('31', '5', '登录系统', '2014-05-05 23:34:03');
INSERT INTO `log` VALUES ('32', '5', '退出系统', '2014-05-05 23:35:03');
INSERT INTO `log` VALUES ('33', '1', '退出系统', '2014-05-06 00:23:39');
INSERT INTO `log` VALUES ('34', '6', '登录系统', '2014-05-06 00:23:44');
INSERT INTO `log` VALUES ('35', '6', '登录系统', '2014-05-06 00:25:57');
INSERT INTO `log` VALUES ('36', '6', '登录系统', '2014-05-06 00:30:20');
INSERT INTO `log` VALUES ('37', '6', '登录系统', '2014-05-06 00:34:38');
INSERT INTO `log` VALUES ('38', '6', '登录系统', '2014-05-06 00:35:49');
INSERT INTO `log` VALUES ('39', '6', '登录系统', '2014-05-06 00:47:01');
INSERT INTO `log` VALUES ('40', '6', '登录系统', '2014-05-06 01:21:54');
INSERT INTO `log` VALUES ('41', '6', '登录系统', '2014-05-06 01:32:56');
INSERT INTO `log` VALUES ('42', '6', '退出系统', '2014-05-06 01:34:13');
INSERT INTO `log` VALUES ('43', '2', '登录系统', '2014-05-06 01:35:01');
INSERT INTO `log` VALUES ('44', '2', '退出系统', '2014-05-06 01:35:30');
INSERT INTO `log` VALUES ('45', '2', '登录系统', '2014-05-06 01:35:56');
INSERT INTO `log` VALUES ('46', '2', '退出系统', '2014-05-06 01:36:25');
INSERT INTO `log` VALUES ('47', '6', '登录系统', '2014-05-06 10:01:25');
INSERT INTO `log` VALUES ('48', '6', '登录系统', '2014-05-06 11:32:11');
INSERT INTO `log` VALUES ('49', '6', '登录系统', '2014-05-06 12:55:23');
INSERT INTO `log` VALUES ('50', '6', '登录系统', '2014-05-06 13:53:52');
INSERT INTO `log` VALUES ('51', '6', '登录系统', '2014-05-06 15:13:42');
INSERT INTO `log` VALUES ('52', '6', '登录系统', '2014-05-06 15:14:03');
INSERT INTO `log` VALUES ('53', '6', '登录系统', '2014-05-06 15:21:22');
INSERT INTO `log` VALUES ('54', '6', '登录系统', '2014-05-06 15:42:49');
INSERT INTO `log` VALUES ('55', '6', '登录系统', '2014-05-06 15:44:03');
INSERT INTO `log` VALUES ('56', '6', '登录系统', '2014-05-06 16:12:02');
INSERT INTO `log` VALUES ('57', '6', '登录系统', '2014-05-06 17:27:46');
INSERT INTO `log` VALUES ('58', '6', '登录系统', '2014-05-06 17:39:04');
INSERT INTO `log` VALUES ('59', '6', '登录系统', '2014-05-06 18:03:51');
INSERT INTO `log` VALUES ('60', '6', '登录系统', '2014-05-06 19:03:11');
INSERT INTO `log` VALUES ('61', '6', '登录系统', '2014-05-06 20:04:33');
INSERT INTO `log` VALUES ('62', '6', '登录系统', '2014-05-06 21:03:49');
INSERT INTO `log` VALUES ('63', '6', '退出系统', '2014-05-06 21:14:20');
INSERT INTO `log` VALUES ('64', '6', '登录系统', '2014-05-06 21:41:35');
INSERT INTO `log` VALUES ('65', '6', '登录系统', '2014-05-06 22:15:00');
INSERT INTO `log` VALUES ('66', '6', '登录系统', '2014-05-06 22:49:04');
INSERT INTO `log` VALUES ('67', '1', '登录系统', '2014-05-06 23:06:29');
INSERT INTO `log` VALUES ('68', '6', '登录系统', '2014-05-06 23:13:32');
INSERT INTO `log` VALUES ('69', '1', '登录系统', '2014-05-06 23:19:20');
INSERT INTO `log` VALUES ('70', '6', '登录系统', '2014-05-06 23:22:18');
INSERT INTO `log` VALUES ('71', '6', '登录系统', '2014-05-06 23:30:53');
INSERT INTO `log` VALUES ('72', '6', '登录系统', '2014-05-06 23:37:59');
INSERT INTO `log` VALUES ('73', '6', '登录系统', '2014-05-06 23:42:35');
INSERT INTO `log` VALUES ('74', '6', '登录系统', '2014-05-06 23:49:13');
INSERT INTO `log` VALUES ('75', '6', '登录系统', '2014-05-07 00:02:07');
INSERT INTO `log` VALUES ('76', '6', '登录系统', '2014-05-07 00:03:57');
INSERT INTO `log` VALUES ('77', '6', '登录系统', '2014-05-07 00:09:50');
INSERT INTO `log` VALUES ('78', '6', '登录系统', '2014-05-07 00:12:23');
INSERT INTO `log` VALUES ('79', '6', '登录系统', '2014-05-07 00:17:51');
INSERT INTO `log` VALUES ('80', '1', '登录系统', '2014-05-07 00:21:13');
INSERT INTO `log` VALUES ('81', '6', '登录系统', '2014-05-07 00:23:37');
INSERT INTO `log` VALUES ('82', '6', '登录系统', '2014-05-07 00:26:06');
INSERT INTO `log` VALUES ('83', '6', '登录系统', '2014-05-07 00:29:18');
INSERT INTO `log` VALUES ('84', '6', '登录系统', '2014-05-07 00:30:51');
INSERT INTO `log` VALUES ('85', '6', '登录系统', '2014-05-07 00:34:54');
INSERT INTO `log` VALUES ('86', '6', '登录系统', '2014-05-07 00:37:26');
INSERT INTO `log` VALUES ('87', '6', '登录系统', '2014-05-07 00:55:57');
INSERT INTO `log` VALUES ('88', '6', '登录系统', '2014-05-07 00:58:50');
INSERT INTO `log` VALUES ('89', '6', '登录系统', '2014-05-07 01:01:29');
INSERT INTO `log` VALUES ('90', '6', '登录系统', '2014-05-07 01:02:38');
INSERT INTO `log` VALUES ('91', '6', '登录系统', '2014-05-07 01:39:06');
INSERT INTO `log` VALUES ('92', '6', '分配了一份合同，合同编号：1', '2014-05-07 01:39:23');
INSERT INTO `log` VALUES ('93', '6', '分配了一份合同，合同编号：19', '2014-05-07 01:41:16');
INSERT INTO `log` VALUES ('94', '6', '登录系统', '2014-05-07 10:26:40');
INSERT INTO `log` VALUES ('95', '6', '登录系统', '2014-05-07 10:35:33');
INSERT INTO `log` VALUES ('96', '6', '登录系统', '2014-05-07 11:04:37');
INSERT INTO `log` VALUES ('97', '6', '登录系统', '2014-05-07 11:14:05');
INSERT INTO `log` VALUES ('98', '6', '登录系统', '2014-05-07 11:23:11');
INSERT INTO `log` VALUES ('99', '1', '登录系统', '2014-05-07 12:10:11');
INSERT INTO `log` VALUES ('100', '6', '登录系统', '2014-05-07 12:23:38');
INSERT INTO `log` VALUES ('101', '1', '登录系统', '2014-05-07 12:36:11');
INSERT INTO `log` VALUES ('102', '1', '起草了一份合同，合同编号：27', '2014-05-07 13:07:12');
INSERT INTO `log` VALUES ('103', '1', '退出系统', '2014-05-07 13:08:16');
INSERT INTO `log` VALUES ('104', '1', '登录系统', '2014-05-07 13:08:22');
INSERT INTO `log` VALUES ('105', '6', '登录系统', '2014-05-07 13:15:07');
INSERT INTO `log` VALUES ('106', '6', '登录系统', '2014-05-07 13:38:08');
INSERT INTO `log` VALUES ('107', '6', '登录系统', '2014-05-07 14:13:15');
INSERT INTO `log` VALUES ('108', '6', '分配了一份合同，合同编号：2', '2014-05-07 14:15:31');
INSERT INTO `log` VALUES ('109', '6', '登录系统', '2014-05-07 14:22:18');
INSERT INTO `log` VALUES ('110', '2', '登录系统', '2014-05-07 14:25:35');
INSERT INTO `log` VALUES ('111', '6', '登录系统', '2014-05-07 14:33:37');
INSERT INTO `log` VALUES ('112', '6', '登录系统', '2014-05-07 14:49:52');
INSERT INTO `log` VALUES ('113', '6', '登录系统', '2014-05-07 15:03:09');
INSERT INTO `log` VALUES ('114', '6', '登录系统', '2014-05-07 15:03:17');
INSERT INTO `log` VALUES ('115', '6', '登录系统', '2014-05-07 15:11:04');
INSERT INTO `log` VALUES ('116', '6', '登录系统', '2014-05-07 15:16:00');
INSERT INTO `log` VALUES ('117', '6', '退出系统', '2014-05-07 15:27:51');
INSERT INTO `log` VALUES ('118', '6', '登录系统', '2014-05-07 15:28:28');
INSERT INTO `log` VALUES ('119', '6', '起草了一份合同，合同编号：28', '2014-05-07 15:28:48');
INSERT INTO `log` VALUES ('120', '6', '分配了一份合同，合同编号：28', '2014-05-07 15:29:41');
INSERT INTO `log` VALUES ('121', '2', '登录系统', '2014-05-07 15:37:27');
INSERT INTO `log` VALUES ('122', '6', '登录系统', '2014-05-07 16:42:58');
INSERT INTO `log` VALUES ('123', '6', '登录系统', '2014-05-07 16:56:02');
INSERT INTO `log` VALUES ('124', '6', '登录系统', '2014-05-07 17:12:03');
INSERT INTO `log` VALUES ('125', '6', '登录系统', '2014-05-07 17:37:47');
INSERT INTO `log` VALUES ('126', '6', '分配了一份合同，合同编号：6', '2014-05-07 17:59:31');
INSERT INTO `log` VALUES ('127', '6', '登录系统', '2014-05-07 18:05:23');
INSERT INTO `log` VALUES ('128', '6', '退出系统', '2014-05-07 18:06:54');
INSERT INTO `log` VALUES ('129', '6', '退出系统', '2014-05-07 18:08:03');
INSERT INTO `log` VALUES ('130', '6', '登录系统', '2014-05-07 18:08:13');
INSERT INTO `log` VALUES ('131', '2', '登录系统', '2014-05-07 18:08:51');
INSERT INTO `log` VALUES ('132', '2', '登录系统', '2014-05-07 18:11:28');
INSERT INTO `log` VALUES ('133', '2', '登录系统', '2014-05-07 18:57:08');
INSERT INTO `log` VALUES ('134', '2', '登录系统', '2014-05-07 18:57:32');
INSERT INTO `log` VALUES ('135', '2', '登录系统', '2014-05-07 18:57:37');
INSERT INTO `log` VALUES ('136', '2', '登录系统', '2014-05-07 18:57:48');
INSERT INTO `log` VALUES ('137', '2', '登录系统', '2014-05-07 18:57:50');
INSERT INTO `log` VALUES ('138', '2', '登录系统', '2014-05-07 18:58:46');
INSERT INTO `log` VALUES ('139', '2', '登录系统', '2014-05-07 18:59:02');
INSERT INTO `log` VALUES ('140', '6', '登录系统', '2014-05-07 18:59:10');
INSERT INTO `log` VALUES ('141', '2', '登录系统', '2014-05-07 19:12:14');
INSERT INTO `log` VALUES ('142', '2', '登录系统', '2014-05-07 19:12:16');
INSERT INTO `log` VALUES ('143', '6', '登录系统', '2014-05-08 19:04:29');
INSERT INTO `log` VALUES ('144', '6', '登录系统', '2014-05-08 21:04:27');
INSERT INTO `log` VALUES ('145', '6', '分配了一份合同，合同编号：27', '2014-05-08 21:07:55');
INSERT INTO `log` VALUES ('146', '2', '登录系统', '2014-05-08 22:00:08');
INSERT INTO `log` VALUES ('147', '6', '登录系统', '2014-05-08 22:18:50');
INSERT INTO `log` VALUES ('148', '2', '登录系统', '2014-05-08 22:50:09');
INSERT INTO `log` VALUES ('149', '6', '登录系统', '2014-05-08 23:09:22');
INSERT INTO `log` VALUES ('150', '6', '登录系统', '2014-05-08 23:12:46');
INSERT INTO `log` VALUES ('151', '6', '登录系统', '2014-05-08 23:17:46');
INSERT INTO `log` VALUES ('152', '6', '登录系统', '2014-05-08 23:20:01');
INSERT INTO `log` VALUES ('153', '6', '登录系统', '2014-05-08 23:29:53');
INSERT INTO `log` VALUES ('154', '6', '登录系统', '2014-05-08 23:32:50');
INSERT INTO `log` VALUES ('155', '1', '登录系统', '2014-05-08 23:43:21');
INSERT INTO `log` VALUES ('156', '6', '登录系统', '2014-05-08 23:56:35');
INSERT INTO `log` VALUES ('157', '6', '登录系统', '2014-05-09 00:03:03');
INSERT INTO `log` VALUES ('158', '6', '登录系统', '2014-05-09 00:09:46');
INSERT INTO `log` VALUES ('159', '6', '登录系统', '2014-05-09 00:18:19');
INSERT INTO `log` VALUES ('160', '6', '登录系统', '2014-05-09 00:19:22');
INSERT INTO `log` VALUES ('161', '6', '登录系统', '2014-05-09 00:25:09');
INSERT INTO `log` VALUES ('162', '6', '登录系统', '2014-05-09 00:25:19');
INSERT INTO `log` VALUES ('163', '6', '登录系统', '2014-05-09 00:32:32');
INSERT INTO `log` VALUES ('164', '6', '登录系统', '2014-05-09 00:42:46');
INSERT INTO `log` VALUES ('165', '6', '登录系统', '2014-05-09 00:46:44');
INSERT INTO `log` VALUES ('166', '6', '分配了一份合同，合同编号：12', '2014-05-09 00:47:30');
INSERT INTO `log` VALUES ('167', '1', '登录系统', '2014-05-09 01:12:28');
INSERT INTO `log` VALUES ('168', '6', '登录系统', '2014-05-09 01:16:32');
INSERT INTO `log` VALUES ('169', '6', '登录系统', '2014-05-09 01:25:44');
INSERT INTO `log` VALUES ('170', '6', '登录系统', '2014-05-09 01:29:48');
INSERT INTO `log` VALUES ('171', '6', '登录系统', '2014-05-09 10:54:14');
INSERT INTO `log` VALUES ('172', '6', '登录系统', '2014-05-09 10:58:10');
INSERT INTO `log` VALUES ('173', '6', '登录系统', '2014-05-09 11:26:46');
INSERT INTO `log` VALUES ('174', '6', '登录系统', '2014-05-09 11:28:09');
INSERT INTO `log` VALUES ('175', '6', '登录系统', '2014-05-09 11:42:38');
INSERT INTO `log` VALUES ('176', '6', '分配了一份合同，合同编号：26', '2014-05-09 12:17:39');
INSERT INTO `log` VALUES ('177', '6', '登录系统', '2014-05-09 12:40:39');
INSERT INTO `log` VALUES ('178', '6', '登录系统', '2014-05-09 13:12:26');
INSERT INTO `log` VALUES ('179', '6', '登录系统', '2014-05-09 16:54:24');
INSERT INTO `log` VALUES ('180', '6', '退出系统', '2014-05-09 17:02:45');
INSERT INTO `log` VALUES ('181', '6', '登录系统', '2014-05-09 17:02:54');
INSERT INTO `log` VALUES ('182', '6', '退出系统', '2014-05-09 17:02:59');
INSERT INTO `log` VALUES ('183', '6', '登录系统', '2014-05-09 17:03:26');
INSERT INTO `log` VALUES ('184', '2', '登录系统', '2014-05-09 17:07:53');
INSERT INTO `log` VALUES ('185', '2', '登录系统', '2014-05-09 17:08:35');
INSERT INTO `log` VALUES ('186', '2', '登录系统', '2014-05-09 17:12:42');
INSERT INTO `log` VALUES ('187', '6', '登录系统', '2014-05-09 22:52:11');
INSERT INTO `log` VALUES ('188', '6', '登录系统', '2014-05-09 22:58:58');
INSERT INTO `log` VALUES ('189', '6', '登录系统', '2014-05-09 23:17:36');
INSERT INTO `log` VALUES ('190', '6', '登录系统', '2014-05-09 23:39:10');
INSERT INTO `log` VALUES ('191', '6', '登录系统', '2014-05-10 00:38:51');
INSERT INTO `log` VALUES ('192', '1', '登录系统', '2014-05-10 01:11:24');
INSERT INTO `log` VALUES ('193', '1', '退出系统', '2014-05-10 01:32:30');
INSERT INTO `log` VALUES ('194', '1', '登录系统', '2014-05-10 01:36:42');
INSERT INTO `log` VALUES ('195', '1', '退出系统', '2014-05-10 01:37:02');
INSERT INTO `log` VALUES ('196', '1', '退出系统', '2014-05-10 01:38:53');
INSERT INTO `log` VALUES ('197', '6', '登录系统', '2014-05-10 01:39:18');
INSERT INTO `log` VALUES ('198', '6', '退出系统', '2014-05-10 01:40:54');
INSERT INTO `log` VALUES ('199', '6', '登录系统', '2014-05-10 10:06:43');
INSERT INTO `log` VALUES ('200', '6', '登录系统', '2014-05-10 12:58:31');
INSERT INTO `log` VALUES ('201', '6', '登录系统', '2014-05-10 13:05:03');
INSERT INTO `log` VALUES ('202', '2', '登录系统', '2014-05-10 13:28:12');
INSERT INTO `log` VALUES ('203', '2', '登录系统', '2014-05-10 13:29:47');
INSERT INTO `log` VALUES ('204', '6', '登录系统', '2014-05-10 13:31:10');
INSERT INTO `log` VALUES ('205', '6', '登录系统', '2014-05-10 13:32:13');
INSERT INTO `log` VALUES ('206', '2', '登录系统', '2014-05-10 13:37:33');
INSERT INTO `log` VALUES ('207', '6', '登录系统', '2014-05-10 13:39:09');
INSERT INTO `log` VALUES ('208', '6', '登录系统', '2014-05-10 13:44:04');
INSERT INTO `log` VALUES ('209', '2', '登录系统', '2014-05-10 13:44:13');
INSERT INTO `log` VALUES ('210', '6', '登录系统', '2014-05-10 13:45:11');
INSERT INTO `log` VALUES ('211', '2', '登录系统', '2014-05-10 14:11:15');
INSERT INTO `log` VALUES ('212', '2', '登录系统', '2014-05-10 14:14:37');
INSERT INTO `log` VALUES ('213', '2', '登录系统', '2014-05-10 14:17:53');
INSERT INTO `log` VALUES ('214', '2', '登录系统', '2014-05-10 14:34:16');
INSERT INTO `log` VALUES ('215', '2', '登录系统', '2014-05-10 14:38:03');
INSERT INTO `log` VALUES ('216', '2', '登录系统', '2014-05-10 14:39:05');
INSERT INTO `log` VALUES ('217', '2', '登录系统', '2014-05-10 14:40:32');
INSERT INTO `log` VALUES ('218', '2', '登录系统', '2014-05-10 14:44:41');
INSERT INTO `log` VALUES ('219', '2', '登录系统', '2014-05-10 14:49:29');
INSERT INTO `log` VALUES ('220', '2', '登录系统', '2014-05-10 15:15:54');
INSERT INTO `log` VALUES ('221', '2', '登录系统', '2014-05-10 15:25:39');
INSERT INTO `log` VALUES ('222', '2', '登录系统', '2014-05-10 15:27:46');
INSERT INTO `log` VALUES ('223', '6', '登录系统', '2014-05-10 15:33:54');
INSERT INTO `log` VALUES ('224', '6', '登录系统', '2014-05-10 15:39:42');
INSERT INTO `log` VALUES ('225', '6', '登录系统', '2014-05-10 15:44:42');
INSERT INTO `log` VALUES ('226', '6', '起草了一份合同，合同编号：29', '2014-05-10 15:49:22');
INSERT INTO `log` VALUES ('227', '2', '登录系统', '2014-05-10 15:52:01');
INSERT INTO `log` VALUES ('228', '2', '登录系统', '2014-05-10 15:53:12');
INSERT INTO `log` VALUES ('229', '2', '登录系统', '2014-05-10 15:53:13');
INSERT INTO `log` VALUES ('230', '2', '登录系统', '2014-05-10 15:56:13');
INSERT INTO `log` VALUES ('231', '2', '登录系统', '2014-05-10 15:56:49');
INSERT INTO `log` VALUES ('232', '6', '分配了一份合同，合同编号：29', '2014-05-10 17:01:05');
INSERT INTO `log` VALUES ('233', '6', '登录系统', '2014-05-10 18:28:48');
INSERT INTO `log` VALUES ('234', '6', '登录系统', '2014-05-10 18:36:06');
INSERT INTO `log` VALUES ('235', '6', '登录系统', '2014-05-10 18:48:02');
INSERT INTO `log` VALUES ('236', '6', '登录系统', '2014-05-10 18:50:53');
INSERT INTO `log` VALUES ('237', '6', '登录系统', '2014-05-10 19:19:55');
INSERT INTO `log` VALUES ('238', '2', '登录系统', '2014-05-10 19:45:04');
INSERT INTO `log` VALUES ('239', '6', '登录系统', '2014-05-10 20:53:53');
INSERT INTO `log` VALUES ('240', '2', '登录系统', '2014-05-10 22:23:33');
INSERT INTO `log` VALUES ('241', '2', '登录系统', '2014-05-10 22:24:25');
INSERT INTO `log` VALUES ('242', '2', '登录系统', '2014-05-10 22:26:15');
INSERT INTO `log` VALUES ('243', '2', '登录系统', '2014-05-10 22:26:36');
INSERT INTO `log` VALUES ('244', '2', '登录系统', '2014-05-10 22:28:10');
INSERT INTO `log` VALUES ('245', '2', '登录系统', '2014-05-10 22:31:41');
INSERT INTO `log` VALUES ('246', '1', '登录系统', '2014-05-10 22:34:28');
INSERT INTO `log` VALUES ('247', '2', '登录系统', '2014-05-10 22:34:31');
INSERT INTO `log` VALUES ('248', '2', '登录系统', '2014-05-10 22:52:11');
INSERT INTO `log` VALUES ('249', '2', '登录系统', '2014-05-10 22:55:33');
INSERT INTO `log` VALUES ('250', '2', '登录系统', '2014-05-10 22:56:32');
INSERT INTO `log` VALUES ('251', '2', '登录系统', '2014-05-10 23:02:52');
INSERT INTO `log` VALUES ('252', '2', '登录系统', '2014-05-10 23:05:37');
INSERT INTO `log` VALUES ('253', '2', '登录系统', '2014-05-10 23:06:14');
INSERT INTO `log` VALUES ('254', '2', '登录系统', '2014-05-10 23:08:08');
INSERT INTO `log` VALUES ('255', '6', '登录系统', '2014-05-10 23:42:35');
INSERT INTO `log` VALUES ('256', '6', '退出系统', '2014-05-10 23:52:55');
INSERT INTO `log` VALUES ('257', '5', '登录系统', '2014-05-10 23:53:05');
INSERT INTO `log` VALUES ('258', '5', '退出系统', '2014-05-11 00:12:14');
INSERT INTO `log` VALUES ('259', '1', '登录系统', '2014-05-11 00:12:20');
INSERT INTO `log` VALUES ('260', '1', '退出系统', '2014-05-11 00:12:31');
INSERT INTO `log` VALUES ('261', '6', '登录系统', '2014-05-11 00:12:36');
INSERT INTO `log` VALUES ('262', '6', '起草了一份合同，合同编号：30', '2014-05-11 00:13:58');
INSERT INTO `log` VALUES ('263', '6', '分配了一份合同，合同编号：30', '2014-05-11 00:14:33');
INSERT INTO `log` VALUES ('264', '6', '退出系统', '2014-05-11 00:15:09');
INSERT INTO `log` VALUES ('265', '5', '登录系统', '2014-05-11 00:15:15');
INSERT INTO `log` VALUES ('266', '5', '退出系统', '2014-05-11 00:15:39');
INSERT INTO `log` VALUES ('267', '6', '登录系统', '2014-05-11 00:16:24');
INSERT INTO `log` VALUES ('268', '2', '登录系统', '2014-05-11 00:35:56');
INSERT INTO `log` VALUES ('269', '2', '登录系统', '2014-05-11 00:40:28');
INSERT INTO `log` VALUES ('270', '2', '登录系统', '2014-05-11 00:41:24');
INSERT INTO `log` VALUES ('271', '2', '登录系统', '2014-05-11 00:49:42');
INSERT INTO `log` VALUES ('272', '2', '退出系统', '2014-05-11 00:52:16');
INSERT INTO `log` VALUES ('273', '2', '登录系统', '2014-05-11 00:54:26');
INSERT INTO `log` VALUES ('274', '2', '登录系统', '2014-05-11 01:00:16');
INSERT INTO `log` VALUES ('275', '2', '登录系统', '2014-05-11 01:01:10');
INSERT INTO `log` VALUES ('276', '6', '登录系统', '2014-05-11 11:00:18');
INSERT INTO `log` VALUES ('277', '6', '登录系统', '2014-05-11 11:00:54');
INSERT INTO `log` VALUES ('278', '6', '登录系统', '2014-05-11 11:12:12');
INSERT INTO `log` VALUES ('279', '6', '登录系统', '2014-05-11 13:04:37');
INSERT INTO `log` VALUES ('280', '6', '登录系统', '2014-05-11 14:42:03');
INSERT INTO `log` VALUES ('281', '6', '登录系统', '2014-05-11 14:44:31');
INSERT INTO `log` VALUES ('282', '6', '登录系统', '2014-05-11 15:17:16');
INSERT INTO `log` VALUES ('283', '2', '登录系统', '2014-05-11 16:31:45');
INSERT INTO `log` VALUES ('284', '6', '登录系统', '2014-05-11 16:31:53');
INSERT INTO `log` VALUES ('285', '6', '起草了一份合同，合同编号：31', '2014-05-11 16:32:21');
INSERT INTO `log` VALUES ('286', '6', '分配了一份合同，合同编号：31', '2014-05-11 16:32:58');
INSERT INTO `log` VALUES ('287', '6', '会签了一份合同，合同编号：31', '2014-05-11 16:33:11');
INSERT INTO `log` VALUES ('288', '6', '退出系统', '2014-05-11 16:33:16');
INSERT INTO `log` VALUES ('289', '5', '登录系统', '2014-05-11 16:33:22');
INSERT INTO `log` VALUES ('290', '5', '会签了一份合同，合同编号：31', '2014-05-11 16:33:33');
INSERT INTO `log` VALUES ('291', '5', '退出系统', '2014-05-11 16:33:39');
INSERT INTO `log` VALUES ('292', '6', '登录系统', '2014-05-11 16:33:47');
INSERT INTO `log` VALUES ('293', '6', '定稿了一份合同，合同编号：31', '2014-05-11 16:34:13');
INSERT INTO `log` VALUES ('294', '6', '审批拒绝了一份合同，合同编号：31', '2014-05-11 16:34:31');
INSERT INTO `log` VALUES ('295', '6', '重新定稿了一份合同，合同编号：31', '2014-05-11 16:34:53');
INSERT INTO `log` VALUES ('296', '6', '审批通过了一份合同，合同编号：31', '2014-05-11 16:35:17');
INSERT INTO `log` VALUES ('297', '6', '签订了一份合同，合同编号：31', '2014-05-11 16:35:34');
INSERT INTO `log` VALUES ('298', '6', '定稿了一份合同，合同编号：30', '2014-05-11 16:39:31');
INSERT INTO `log` VALUES ('299', '6', '退出系统', '2014-05-11 16:40:53');
INSERT INTO `log` VALUES ('300', '1', '登录系统', '2014-05-11 16:40:58');
INSERT INTO `log` VALUES ('301', '1', '退出系统', '2014-05-11 16:42:09');
INSERT INTO `log` VALUES ('302', '2', '登录系统', '2014-05-11 23:03:11');
INSERT INTO `log` VALUES ('303', '2', '登录系统', '2014-05-11 23:05:30');
INSERT INTO `log` VALUES ('304', '2', '登录系统', '2014-05-11 23:19:12');
INSERT INTO `log` VALUES ('305', '2', '登录系统', '2014-05-11 23:23:41');
INSERT INTO `log` VALUES ('306', '6', '登录系统', '2014-05-12 01:27:38');
INSERT INTO `log` VALUES ('307', '6', '退出系统', '2014-05-12 01:29:32');
INSERT INTO `log` VALUES ('308', '6', '登录系统', '2014-05-12 01:29:38');
INSERT INTO `log` VALUES ('309', '6', '登录系统', '2014-05-12 10:17:20');
INSERT INTO `log` VALUES ('310', '2', '登录系统', '2014-05-12 12:02:51');
INSERT INTO `log` VALUES ('311', '2', '登录系统', '2014-05-12 12:18:05');
INSERT INTO `log` VALUES ('312', '2', '登录系统', '2014-05-12 13:22:12');
INSERT INTO `log` VALUES ('313', '2', '登录系统', '2014-05-12 13:23:47');
INSERT INTO `log` VALUES ('314', '2', '登录系统', '2014-05-12 13:26:08');
INSERT INTO `log` VALUES ('315', '2', '登录系统', '2014-05-12 13:27:13');
INSERT INTO `log` VALUES ('316', '2', '登录系统', '2014-05-12 13:28:11');
INSERT INTO `log` VALUES ('317', '2', '登录系统', '2014-05-12 13:32:01');
INSERT INTO `log` VALUES ('318', '6', '登录系统', '2014-05-12 14:12:22');
INSERT INTO `log` VALUES ('319', '2', '登录系统', '2014-05-12 14:14:12');
INSERT INTO `log` VALUES ('320', '2', '登录系统', '2014-05-12 14:15:20');
INSERT INTO `log` VALUES ('321', '2', '登录系统', '2014-05-12 14:17:36');
INSERT INTO `log` VALUES ('322', '2', '登录系统', '2014-05-12 14:20:56');
INSERT INTO `log` VALUES ('323', '2', '登录系统', '2014-05-12 14:27:15');
INSERT INTO `log` VALUES ('324', '2', '登录系统', '2014-05-12 14:27:47');
INSERT INTO `log` VALUES ('325', '2', '登录系统', '2014-05-12 14:30:02');
INSERT INTO `log` VALUES ('326', '2', '登录系统', '2014-05-12 14:32:51');
INSERT INTO `log` VALUES ('327', '2', '登录系统', '2014-05-12 14:42:08');
INSERT INTO `log` VALUES ('328', '2', '登录系统', '2014-05-12 14:42:48');
INSERT INTO `log` VALUES ('329', '2', '登录系统', '2014-05-12 14:44:54');
INSERT INTO `log` VALUES ('330', '2', '登录系统', '2014-05-12 14:45:09');
INSERT INTO `log` VALUES ('331', '2', '登录系统', '2014-05-12 14:46:44');
INSERT INTO `log` VALUES ('332', '2', '登录系统', '2014-05-12 14:48:42');
INSERT INTO `log` VALUES ('333', '2', '登录系统', '2014-05-12 14:54:58');
INSERT INTO `log` VALUES ('334', '2', '登录系统', '2014-05-12 14:58:25');
INSERT INTO `log` VALUES ('335', '2', '登录系统', '2014-05-12 15:07:08');
INSERT INTO `log` VALUES ('336', '2', '登录系统', '2014-05-12 15:09:36');
INSERT INTO `log` VALUES ('337', '2', '登录系统', '2014-05-12 15:10:33');
INSERT INTO `log` VALUES ('338', '2', '登录系统', '2014-05-12 15:33:15');
INSERT INTO `log` VALUES ('339', '2', '登录系统', '2014-05-12 15:34:19');
INSERT INTO `log` VALUES ('340', '2', '登录系统', '2014-05-12 15:41:01');
INSERT INTO `log` VALUES ('341', '6', '登录系统', '2014-05-12 16:50:04');
INSERT INTO `log` VALUES ('342', '6', '起草了一份合同，合同编号：32', '2014-05-12 16:53:04');
INSERT INTO `log` VALUES ('343', '6', '分配了一份合同，合同编号：32', '2014-05-12 16:53:54');
INSERT INTO `log` VALUES ('344', '6', '会签了一份合同，合同编号：32', '2014-05-12 16:54:12');
INSERT INTO `log` VALUES ('345', '6', '定稿了一份合同，合同编号：32', '2014-05-12 16:54:32');
INSERT INTO `log` VALUES ('346', '6', '审批通过了一份合同，合同编号：32', '2014-05-12 16:55:06');
INSERT INTO `log` VALUES ('347', '6', '退出系统', '2014-05-12 16:55:11');
INSERT INTO `log` VALUES ('348', '1', '登录系统', '2014-05-12 16:55:19');
INSERT INTO `log` VALUES ('349', '1', '签订了一份合同，合同编号：32', '2014-05-12 16:55:46');
INSERT INTO `log` VALUES ('350', '1', '退出系统', '2014-05-12 16:57:02');
INSERT INTO `log` VALUES ('351', '6', '登录系统', '2014-05-12 21:12:26');
INSERT INTO `log` VALUES ('352', '6', '登录系统', '2014-05-12 22:47:51');
INSERT INTO `log` VALUES ('353', '6', '退出系统', '2014-05-13 00:33:43');
INSERT INTO `log` VALUES ('354', '6', '登录系统', '2014-05-13 00:42:10');
INSERT INTO `log` VALUES ('355', '6', '退出系统', '2014-05-13 01:10:01');
INSERT INTO `log` VALUES ('356', '6', '登录系统', '2014-05-13 01:44:30');
INSERT INTO `log` VALUES ('357', '6', '登录系统', '2014-05-13 11:47:23');
INSERT INTO `log` VALUES ('358', '6', '登录系统', '2014-05-13 14:11:40');
INSERT INTO `log` VALUES ('359', '6', '退出系统', '2014-05-13 14:11:59');
INSERT INTO `log` VALUES ('360', '6', '登录系统', '2014-05-13 14:39:17');
INSERT INTO `log` VALUES ('361', '6', '登录系统', '2014-05-13 16:32:44');
INSERT INTO `log` VALUES ('362', '6', '登录系统', '2014-05-13 17:23:46');
INSERT INTO `log` VALUES ('363', '6', '登录系统', '2014-05-13 19:45:06');
INSERT INTO `log` VALUES ('364', '6', '登录系统', '2014-05-13 20:19:16');
INSERT INTO `log` VALUES ('365', '6', '登录系统', '2014-05-13 20:19:37');
INSERT INTO `log` VALUES ('366', '6', '登录系统', '2014-05-13 20:32:24');
INSERT INTO `log` VALUES ('367', '6', '登录系统', '2014-05-13 20:35:11');
INSERT INTO `log` VALUES ('369', '6', '登录系统', '2014-05-13 20:55:53');
INSERT INTO `log` VALUES ('370', '6', '修改客户信息,客户号：1', '2014-05-13 21:10:35');
INSERT INTO `log` VALUES ('371', '6', '添加新客户', '2014-05-13 21:11:31');
INSERT INTO `log` VALUES ('372', '6', '登录系统', '2014-05-13 21:13:14');
INSERT INTO `log` VALUES ('373', '6', '修改客户信息,客户号：5', '2014-05-13 21:13:25');
INSERT INTO `log` VALUES ('374', '6', '修改客户信息,客户号：2', '2014-05-13 21:13:43');
INSERT INTO `log` VALUES ('375', '6', '修改客户信息,客户号：5', '2014-05-13 21:14:03');
INSERT INTO `log` VALUES ('376', '6', '修改客户信息,客户号：3', '2014-05-13 21:14:22');
INSERT INTO `log` VALUES ('377', '6', '修改客户信息,客户号：4', '2014-05-13 21:17:50');
INSERT INTO `log` VALUES ('378', '6', '修改客户信息,客户号：2', '2014-05-13 21:19:00');
INSERT INTO `log` VALUES ('379', '6', '登录系统', '2014-05-13 21:24:16');
INSERT INTO `log` VALUES ('380', '6', '修改客户信息,客户号：3', '2014-05-13 21:24:27');
INSERT INTO `log` VALUES ('381', '6', '修改客户信息,客户号：2', '2014-05-13 21:24:45');
INSERT INTO `log` VALUES ('382', '6', '修改客户信息,客户号：3', '2014-05-13 21:24:58');
INSERT INTO `log` VALUES ('383', '6', '修改客户信息,客户号：1', '2014-05-13 21:25:25');
INSERT INTO `log` VALUES ('384', '6', '删除客户信息,客户号：5', '2014-05-13 21:25:50');
INSERT INTO `log` VALUES ('385', '6', '修改客户信息,客户号：4', '2014-05-13 21:29:38');
INSERT INTO `log` VALUES ('386', '6', '修改客户信息,客户号：5', '2014-05-13 21:30:33');
INSERT INTO `log` VALUES ('387', '6', '登录系统', '2014-05-13 21:36:14');
INSERT INTO `log` VALUES ('388', '6', '添加新客户', '2014-05-13 21:36:43');
INSERT INTO `log` VALUES ('389', '6', '修改客户信息,客户号：6', '2014-05-13 21:37:14');
INSERT INTO `log` VALUES ('390', '6', '添加新客户', '2014-05-13 21:37:44');
INSERT INTO `log` VALUES ('391', '6', '修改客户信息,客户号：7', '2014-05-13 21:38:03');
INSERT INTO `log` VALUES ('392', '6', '删除客户信息,客户号：7', '2014-05-13 21:41:31');
INSERT INTO `log` VALUES ('393', '6', '修改客户信息,客户号：3', '2014-05-13 21:44:45');
INSERT INTO `log` VALUES ('394', '6', '修改客户信息,客户号：5', '2014-05-13 21:51:11');
INSERT INTO `log` VALUES ('395', '6', '修改客户信息,客户号：3', '2014-05-13 21:51:55');
INSERT INTO `log` VALUES ('396', '6', '修改客户信息,客户号：5', '2014-05-13 21:52:48');
INSERT INTO `log` VALUES ('397', '6', '登录系统', '2014-05-13 21:54:03');
INSERT INTO `log` VALUES ('398', '6', '修改客户信息,客户号：3', '2014-05-13 21:55:08');
INSERT INTO `log` VALUES ('399', '6', '修改客户信息,客户号：3', '2014-05-13 21:57:16');
INSERT INTO `log` VALUES ('400', '6', '修改客户信息,客户号：3', '2014-05-13 21:57:17');
INSERT INTO `log` VALUES ('401', '6', '修改客户信息,客户号：4', '2014-05-13 21:59:31');
INSERT INTO `log` VALUES ('402', '6', '修改客户信息,客户号：4', '2014-05-13 22:01:02');
INSERT INTO `log` VALUES ('403', '6', '登录系统', '2014-05-13 22:27:02');
INSERT INTO `log` VALUES ('404', '6', '登录系统', '2014-05-13 22:34:48');
INSERT INTO `log` VALUES ('405', '6', '登录系统', '2014-05-13 22:41:25');
INSERT INTO `log` VALUES ('406', '6', '登录系统', '2014-05-13 22:59:42');
INSERT INTO `log` VALUES ('407', '6', '登录系统', '2014-05-13 23:35:46');
INSERT INTO `log` VALUES ('408', '6', '退出系统', '2014-05-13 23:37:42');
INSERT INTO `log` VALUES ('409', '6', '登录系统', '2014-05-13 23:38:25');
INSERT INTO `log` VALUES ('410', '6', '删除合同及相关信息,合同编号：28', '2014-05-13 23:39:49');
INSERT INTO `log` VALUES ('411', '6', '修改合同信息，合同编号：27', '2014-05-13 23:42:17');
INSERT INTO `log` VALUES ('412', '6', '修改合同信息，合同编号：1', '2014-05-13 23:43:37');
INSERT INTO `log` VALUES ('413', '6', '修改合同信息，合同编号：2', '2014-05-13 23:44:00');
INSERT INTO `log` VALUES ('414', '6', '登录系统', '2014-05-13 23:59:39');
INSERT INTO `log` VALUES ('415', '6', '登录系统', '2014-05-14 00:04:57');
INSERT INTO `log` VALUES ('416', '6', '退出系统', '2014-05-14 00:06:02');
INSERT INTO `log` VALUES ('417', '6', '登录系统', '2014-05-14 00:06:22');
INSERT INTO `log` VALUES ('418', '6', '登录系统', '2014-05-14 00:18:54');
INSERT INTO `log` VALUES ('419', '6', '登录系统', '2014-05-14 00:40:06');
INSERT INTO `log` VALUES ('420', '6', '退出系统', '2014-05-14 00:41:11');
INSERT INTO `log` VALUES ('421', '16', '登录系统', '2014-05-14 00:41:18');
INSERT INTO `log` VALUES ('422', '16', '退出系统', '2014-05-14 00:42:27');
INSERT INTO `log` VALUES ('423', '2', '登录系统', '2014-05-14 00:42:37');
INSERT INTO `log` VALUES ('424', '2', '删除一个角色，角色编号：6', '2014-05-14 00:42:52');
INSERT INTO `log` VALUES ('425', '2', '退出系统', '2014-05-14 00:44:11');
INSERT INTO `log` VALUES ('426', '17', '登录系统', '2014-05-14 00:44:28');
INSERT INTO `log` VALUES ('427', '17', '退出系统', '2014-05-14 00:46:21');
INSERT INTO `log` VALUES ('428', '6', '登录系统', '2014-05-14 00:46:28');
INSERT INTO `log` VALUES ('429', '6', '登录系统', '2014-05-14 00:53:43');
INSERT INTO `log` VALUES ('430', '6', '退出系统', '2014-05-14 00:57:14');
INSERT INTO `log` VALUES ('431', '17', '登录系统', '2014-05-14 00:57:22');
INSERT INTO `log` VALUES ('432', '17', '退出系统', '2014-05-14 01:03:40');
INSERT INTO `log` VALUES ('433', '6', '登录系统', '2014-05-14 01:03:44');
INSERT INTO `log` VALUES ('434', '6', '登录系统', '2014-05-14 01:24:37');
INSERT INTO `log` VALUES ('435', '6', '登录系统', '2014-05-14 02:01:48');
INSERT INTO `log` VALUES ('436', '6', '登录系统', '2014-05-14 09:52:58');
INSERT INTO `log` VALUES ('437', '6', '起草了一份合同，合同编号：33', '2014-05-14 10:32:16');
INSERT INTO `log` VALUES ('438', '6', '退出系统', '2014-05-14 10:37:04');
INSERT INTO `log` VALUES ('439', '6', '登录系统', '2014-05-14 10:37:09');
INSERT INTO `log` VALUES ('440', '6', '分配了一份合同，合同编号：33', '2014-05-14 10:38:30');
INSERT INTO `log` VALUES ('441', '6', '会签了一份合同，合同编号：33', '2014-05-14 10:38:57');
INSERT INTO `log` VALUES ('442', '6', '退出系统', '2014-05-14 10:39:06');
INSERT INTO `log` VALUES ('443', '6', '登录系统', '2014-05-14 10:39:13');
INSERT INTO `log` VALUES ('444', '6', '退出系统', '2014-05-14 10:39:21');
INSERT INTO `log` VALUES ('445', '1', '登录系统', '2014-05-14 10:39:26');
INSERT INTO `log` VALUES ('446', '1', '会签了一份合同，合同编号：33', '2014-05-14 10:39:41');
INSERT INTO `log` VALUES ('447', '1', '退出系统', '2014-05-14 10:39:45');
INSERT INTO `log` VALUES ('448', '5', '登录系统', '2014-05-14 10:39:54');
INSERT INTO `log` VALUES ('449', '5', '退出系统', '2014-05-14 10:40:17');
INSERT INTO `log` VALUES ('450', '2', '登录系统', '2014-05-14 10:40:23');
INSERT INTO `log` VALUES ('451', '2', '会签了一份合同，合同编号：33', '2014-05-14 10:40:39');
INSERT INTO `log` VALUES ('452', '2', '退出系统', '2014-05-14 10:40:44');
INSERT INTO `log` VALUES ('453', '6', '登录系统', '2014-05-14 10:40:51');
INSERT INTO `log` VALUES ('454', '6', '定稿了一份合同，合同编号：33', '2014-05-14 10:41:18');
INSERT INTO `log` VALUES ('455', '6', '审批拒绝了一份合同，合同编号：33', '2014-05-14 10:41:45');
INSERT INTO `log` VALUES ('456', '6', '重新定稿了一份合同，合同编号：33', '2014-05-14 10:42:15');
INSERT INTO `log` VALUES ('457', '6', '删除一个角色，角色编号：7', '2014-05-14 10:50:24');
INSERT INTO `log` VALUES ('458', '6', '删除一个角色，角色编号：4', '2014-05-14 10:50:47');
INSERT INTO `log` VALUES ('459', '6', '登录系统', '2014-05-14 11:48:35');
INSERT INTO `log` VALUES ('460', '6', '起草了一份合同，合同编号：34', '2014-05-14 12:15:17');
INSERT INTO `log` VALUES ('461', '6', '分配了一份合同，合同编号：34', '2014-05-14 12:15:43');
INSERT INTO `log` VALUES ('462', '6', '会签了一份合同，合同编号：34', '2014-05-14 12:16:01');
INSERT INTO `log` VALUES ('463', '6', '定稿了一份合同，合同编号：34', '2014-05-14 12:16:23');
INSERT INTO `log` VALUES ('464', '6', '审批通过了一份合同，合同编号：34', '2014-05-14 12:16:34');
INSERT INTO `log` VALUES ('465', '6', '签订了一份合同，合同编号：34', '2014-05-14 12:16:44');
INSERT INTO `log` VALUES ('466', '6', '起草了一份合同，合同编号：35', '2014-05-14 12:36:36');
INSERT INTO `log` VALUES ('467', '6', '分配了一份合同，合同编号：35', '2014-05-14 12:36:51');
INSERT INTO `log` VALUES ('468', '6', '会签了一份合同，合同编号：35', '2014-05-14 12:37:03');
INSERT INTO `log` VALUES ('469', '6', '定稿了一份合同，合同编号：35', '2014-05-14 12:37:13');
INSERT INTO `log` VALUES ('470', '6', '审批拒绝了一份合同，合同编号：35', '2014-05-14 12:37:24');
INSERT INTO `log` VALUES ('471', '6', '重新定稿了一份合同，合同编号：35', '2014-05-14 12:37:39');
INSERT INTO `log` VALUES ('472', '6', '登录系统', '2014-05-14 12:47:20');
INSERT INTO `log` VALUES ('473', '6', '起草了一份合同，合同编号：36', '2014-05-14 12:55:10');
INSERT INTO `log` VALUES ('474', '6', '分配了一份合同，合同编号：36', '2014-05-14 12:55:34');
INSERT INTO `log` VALUES ('475', '6', '会签了一份合同，合同编号：36', '2014-05-14 12:55:53');
INSERT INTO `log` VALUES ('476', '6', '定稿了一份合同，合同编号：36', '2014-05-14 12:56:00');
INSERT INTO `log` VALUES ('477', '6', '审批拒绝了一份合同，合同编号：36', '2014-05-14 12:56:12');
INSERT INTO `log` VALUES ('478', '6', '重新定稿了一份合同，合同编号：36', '2014-05-14 12:56:18');
INSERT INTO `log` VALUES ('479', '6', '审批通过了一份合同，合同编号：36', '2014-05-14 12:56:35');
INSERT INTO `log` VALUES ('480', '6', '登录系统', '2014-05-14 13:07:18');
INSERT INTO `log` VALUES ('481', '6', '起草了一份合同，合同编号：37', '2014-05-14 13:07:38');
INSERT INTO `log` VALUES ('482', '6', '分配了一份合同，合同编号：37', '2014-05-14 13:07:56');
INSERT INTO `log` VALUES ('483', '6', '会签了一份合同，合同编号：37', '2014-05-14 13:08:17');
INSERT INTO `log` VALUES ('484', '6', '定稿了一份合同，合同编号：37', '2014-05-14 13:08:28');
INSERT INTO `log` VALUES ('485', '6', '审批拒绝了一份合同，合同编号：37', '2014-05-14 13:08:37');
INSERT INTO `log` VALUES ('486', '6', '重新定稿了一份合同，合同编号：37', '2014-05-14 13:09:06');
INSERT INTO `log` VALUES ('487', '6', '审批通过了一份合同，合同编号：37', '2014-05-14 13:09:15');
INSERT INTO `log` VALUES ('488', '6', '签订了一份合同，合同编号：36', '2014-05-14 13:09:35');
INSERT INTO `log` VALUES ('489', '6', '签订了一份合同，合同编号：37', '2014-05-14 13:10:10');
INSERT INTO `log` VALUES ('490', '6', '会签了一份合同，合同编号：26', '2014-05-14 13:15:19');
INSERT INTO `log` VALUES ('491', '6', '登录系统', '2014-05-14 13:39:32');
INSERT INTO `log` VALUES ('492', '2', '登录系统', '2014-05-14 13:42:01');
INSERT INTO `log` VALUES ('493', '2', '起草了一份合同，合同编号：38', '2014-05-14 13:42:21');
INSERT INTO `log` VALUES ('494', '6', '分配了一份合同，合同编号：38', '2014-05-14 13:42:54');
INSERT INTO `log` VALUES ('495', '2', '会签了一份合同，合同编号：38', '2014-05-14 13:43:04');
INSERT INTO `log` VALUES ('496', '6', '会签了一份合同，合同编号：38', '2014-05-14 13:43:15');
INSERT INTO `log` VALUES ('497', '2', '定稿了一份合同，合同编号：38', '2014-05-14 13:43:26');
INSERT INTO `log` VALUES ('498', '2', '审批拒绝了一份合同，合同编号：38', '2014-05-14 13:43:36');
INSERT INTO `log` VALUES ('499', '2', '重新定稿了一份合同，合同编号：38', '2014-05-14 13:43:45');
INSERT INTO `log` VALUES ('500', '2', '审批拒绝了一份合同，合同编号：38', '2014-05-14 13:44:00');
INSERT INTO `log` VALUES ('501', '2', '重新定稿了一份合同，合同编号：38', '2014-05-14 13:44:06');
INSERT INTO `log` VALUES ('502', '2', '审批通过了一份合同，合同编号：38', '2014-05-14 13:44:10');
INSERT INTO `log` VALUES ('503', '6', '签订了一份合同，合同编号：38', '2014-05-14 13:44:34');
INSERT INTO `log` VALUES ('504', '5', '登录系统', '2014-05-14 14:14:17');
INSERT INTO `log` VALUES ('505', '2', '登录系统', '2014-05-14 14:14:40');
INSERT INTO `log` VALUES ('506', '5', '退出系统', '2014-05-14 14:14:49');
INSERT INTO `log` VALUES ('507', '6', '登录系统', '2014-05-14 14:14:53');
INSERT INTO `log` VALUES ('508', '2', '登录系统', '2014-05-14 14:15:59');
INSERT INTO `log` VALUES ('509', '2', '登录系统', '2014-05-14 14:17:43');
INSERT INTO `log` VALUES ('510', '2', '登录系统', '2014-05-14 14:26:37');
INSERT INTO `log` VALUES ('511', '6', '登录系统', '2014-05-14 14:26:37');
INSERT INTO `log` VALUES ('512', '6', '登录系统', '2014-05-14 14:29:16');
INSERT INTO `log` VALUES ('513', '6', '登录系统', '2014-05-14 14:36:06');

-- ----------------------------
-- Table structure for `operateflow`
-- ----------------------------
DROP TABLE IF EXISTS `operateflow`;
CREATE TABLE `operateflow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contractno` int(11) NOT NULL,
  `operatorno` int(11) NOT NULL,
  `operatetype` smallint(6) NOT NULL,
  `operatestatus` smallint(6) DEFAULT NULL,
  `content` varchar(100) DEFAULT NULL,
  `operateTime` timestamp NULL DEFAULT NULL,
  `del` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_Relationship_5` (`contractno`),
  KEY `FK_Relationship_6` (`operatorno`),
  CONSTRAINT `FK_Relationship_5` FOREIGN KEY (`contractno`) REFERENCES `contract` (`id`),
  CONSTRAINT `FK_Relationship_6` FOREIGN KEY (`operatorno`) REFERENCES `system_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operateflow
-- ----------------------------
INSERT INTO `operateflow` VALUES ('4', '1', '5', '3', '0', null, '2014-05-07 01:39:23', '0');
INSERT INTO `operateflow` VALUES ('5', '1', '1', '5', '-1', null, '2014-05-07 01:39:23', '0');
INSERT INTO `operateflow` VALUES ('6', '1', '6', '6', '-1', null, '2014-05-07 01:39:23', '0');
INSERT INTO `operateflow` VALUES ('7', '19', '2', '3', '0', null, '2014-05-07 01:41:16', '0');
INSERT INTO `operateflow` VALUES ('8', '19', '1', '3', '0', null, '2014-05-07 01:41:16', '0');
INSERT INTO `operateflow` VALUES ('9', '19', '6', '3', '1', '', '2014-05-11 11:06:02', '0');
INSERT INTO `operateflow` VALUES ('10', '19', '5', '3', '0', null, '2014-05-07 01:41:16', '0');
INSERT INTO `operateflow` VALUES ('11', '19', '1', '5', '-1', null, '2014-05-07 01:41:16', '0');
INSERT INTO `operateflow` VALUES ('12', '19', '1', '6', '-1', null, '2014-05-07 01:41:16', '0');
INSERT INTO `operateflow` VALUES ('18', '26', '1', '1', '1', null, '2014-05-07 12:37:21', '0');
INSERT INTO `operateflow` VALUES ('19', '27', '1', '1', '1', null, '2014-05-07 13:07:12', '0');
INSERT INTO `operateflow` VALUES ('21', '2', '2', '5', '-1', null, '2014-05-07 14:15:31', '0');
INSERT INTO `operateflow` VALUES ('22', '2', '6', '6', '-1', null, '2014-05-07 14:15:31', '0');
INSERT INTO `operateflow` VALUES ('23', '28', '6', '1', '1', null, '2014-05-07 15:28:48', '1');
INSERT INTO `operateflow` VALUES ('24', '28', '5', '3', '1', '只有我会签哦', '2014-05-11 00:05:37', '1');
INSERT INTO `operateflow` VALUES ('25', '28', '6', '5', '-1', null, '2014-05-07 15:29:41', '1');
INSERT INTO `operateflow` VALUES ('26', '28', '6', '6', '-1', null, '2014-05-07 15:29:41', '1');
INSERT INTO `operateflow` VALUES ('27', '6', '1', '3', '0', null, '2014-05-07 17:59:31', '0');
INSERT INTO `operateflow` VALUES ('28', '6', '5', '3', '0', null, '2014-05-07 17:59:31', '0');
INSERT INTO `operateflow` VALUES ('29', '6', '2', '3', '0', null, '2014-05-07 17:59:31', '0');
INSERT INTO `operateflow` VALUES ('30', '6', '6', '3', '0', null, '2014-05-07 17:59:31', '0');
INSERT INTO `operateflow` VALUES ('31', '6', '2', '5', '-1', null, '2014-05-07 17:59:31', '0');
INSERT INTO `operateflow` VALUES ('32', '6', '6', '6', '-1', null, '2014-05-07 17:59:31', '0');
INSERT INTO `operateflow` VALUES ('33', '27', '2', '3', '0', null, '2014-05-08 21:07:55', '0');
INSERT INTO `operateflow` VALUES ('34', '27', '5', '3', '0', null, '2014-05-08 21:07:55', '0');
INSERT INTO `operateflow` VALUES ('35', '27', '1', '3', '0', null, '2014-05-08 21:07:55', '0');
INSERT INTO `operateflow` VALUES ('36', '27', '6', '5', '-1', null, '2014-05-08 21:07:55', '0');
INSERT INTO `operateflow` VALUES ('37', '27', '6', '6', '-1', null, '2014-05-08 21:07:55', '0');
INSERT INTO `operateflow` VALUES ('38', '12', '5', '3', '0', null, '2014-05-09 00:47:30', '0');
INSERT INTO `operateflow` VALUES ('39', '12', '1', '3', '0', null, '2014-05-09 00:47:30', '0');
INSERT INTO `operateflow` VALUES ('40', '12', '2', '5', '-1', null, '2014-05-09 00:47:30', '0');
INSERT INTO `operateflow` VALUES ('41', '12', '1', '5', '-1', null, '2014-05-09 00:47:30', '0');
INSERT INTO `operateflow` VALUES ('42', '12', '6', '6', '-1', null, '2014-05-09 00:47:30', '0');
INSERT INTO `operateflow` VALUES ('43', '26', '6', '3', '1', '会签', '2014-05-14 13:15:19', '0');
INSERT INTO `operateflow` VALUES ('44', '26', '2', '3', '0', null, '2014-05-09 12:17:39', '0');
INSERT INTO `operateflow` VALUES ('45', '26', '5', '3', '0', null, '2014-05-09 12:17:39', '0');
INSERT INTO `operateflow` VALUES ('46', '26', '1', '3', '0', null, '2014-05-09 12:17:39', '0');
INSERT INTO `operateflow` VALUES ('47', '26', '2', '5', '-1', null, '2014-05-09 12:17:39', '0');
INSERT INTO `operateflow` VALUES ('48', '26', '6', '5', '-1', null, '2014-05-09 12:17:39', '0');
INSERT INTO `operateflow` VALUES ('49', '26', '1', '5', '-1', null, '2014-05-09 12:17:39', '0');
INSERT INTO `operateflow` VALUES ('50', '26', '2', '6', '-1', null, '2014-05-09 12:17:39', '0');
INSERT INTO `operateflow` VALUES ('51', '26', '6', '6', '-1', null, '2014-05-09 12:17:39', '0');
INSERT INTO `operateflow` VALUES ('52', '29', '6', '1', '1', null, '2014-05-10 15:49:22', '1');
INSERT INTO `operateflow` VALUES ('53', '29', '5', '3', '0', null, '2014-05-10 17:01:05', '1');
INSERT INTO `operateflow` VALUES ('54', '29', '6', '3', '1', '我会签好了！！！', '2014-05-10 23:43:20', '1');
INSERT INTO `operateflow` VALUES ('55', '29', '2', '3', '0', null, '2014-05-10 17:01:05', '1');
INSERT INTO `operateflow` VALUES ('56', '29', '1', '3', '0', null, '2014-05-10 17:01:05', '1');
INSERT INTO `operateflow` VALUES ('57', '29', '2', '5', '-1', null, '2014-05-10 17:01:05', '1');
INSERT INTO `operateflow` VALUES ('58', '29', '1', '5', '-1', null, '2014-05-10 17:01:05', '1');
INSERT INTO `operateflow` VALUES ('59', '29', '6', '5', '-1', null, '2014-05-10 17:01:05', '1');
INSERT INTO `operateflow` VALUES ('60', '29', '6', '6', '-1', null, '2014-05-10 17:01:05', '1');
INSERT INTO `operateflow` VALUES ('61', '29', '2', '6', '-1', null, '2014-05-10 17:01:05', '1');
INSERT INTO `operateflow` VALUES ('62', '30', '6', '1', '1', null, '2014-05-11 00:13:58', '1');
INSERT INTO `operateflow` VALUES ('63', '30', '6', '4', '1', null, '2014-05-11 16:39:31', '1');
INSERT INTO `operateflow` VALUES ('64', '30', '6', '3', '1', '这是李四会签的', '2014-05-11 00:15:04', '1');
INSERT INTO `operateflow` VALUES ('65', '30', '5', '3', '1', '这是李四会签的', '2014-05-11 00:15:35', '1');
INSERT INTO `operateflow` VALUES ('66', '30', '6', '5', '0', null, '2014-05-11 00:14:33', '1');
INSERT INTO `operateflow` VALUES ('67', '30', '6', '6', '-1', null, '2014-05-11 00:14:33', '1');
INSERT INTO `operateflow` VALUES ('68', '31', '6', '1', '1', null, '2014-05-11 16:32:21', '1');
INSERT INTO `operateflow` VALUES ('69', '31', '6', '4', '1', null, '2014-05-11 16:34:53', '1');
INSERT INTO `operateflow` VALUES ('70', '31', '6', '3', '1', 'zhshdskhpfnpkvdmpfd[l e[ninrp', '2014-05-11 16:33:11', '1');
INSERT INTO `operateflow` VALUES ('71', '31', '5', '3', '1', 'songsong', '2014-05-11 16:33:33', '1');
INSERT INTO `operateflow` VALUES ('72', '31', '6', '5', '1', '通过', '2014-05-11 16:35:17', '1');
INSERT INTO `operateflow` VALUES ('73', '31', '6', '6', '1', '签的第一个合同！！！！！哈哈哈', '2014-05-11 16:35:34', '1');
INSERT INTO `operateflow` VALUES ('74', '32', '6', '1', '1', null, '2014-05-12 16:53:04', '1');
INSERT INTO `operateflow` VALUES ('75', '32', '6', '4', '1', null, '2014-05-12 16:54:32', '1');
INSERT INTO `operateflow` VALUES ('76', '32', '6', '3', '1', '会签一下下！！', '2014-05-12 16:54:12', '1');
INSERT INTO `operateflow` VALUES ('77', '32', '6', '5', '1', '通过！！', '2014-05-12 16:55:06', '1');
INSERT INTO `operateflow` VALUES ('78', '32', '1', '6', '1', '还是签了吧', '2014-05-12 16:55:46', '1');
INSERT INTO `operateflow` VALUES ('79', '33', '6', '1', '1', null, '2014-05-14 10:32:16', '0');
INSERT INTO `operateflow` VALUES ('80', '33', '6', '4', '1', null, '2014-05-14 10:42:15', '0');
INSERT INTO `operateflow` VALUES ('81', '33', '6', '3', '1', '李四会签完毕！！哈哈哈', '2014-05-14 10:38:57', '0');
INSERT INTO `operateflow` VALUES ('82', '33', '2', '3', '1', '张三会签完毕', '2014-05-14 10:40:39', '0');
INSERT INTO `operateflow` VALUES ('83', '33', '1', '3', '1', 'Boss会签完毕', '2014-05-14 10:39:41', '0');
INSERT INTO `operateflow` VALUES ('84', '33', '6', '5', '2', '第一次，不通过！！哈哈哈', '2014-05-14 10:41:45', '0');
INSERT INTO `operateflow` VALUES ('85', '33', '6', '6', '-1', null, '2014-05-14 10:38:30', '0');
INSERT INTO `operateflow` VALUES ('86', '34', '6', '1', '1', null, '2014-05-14 12:15:17', '0');
INSERT INTO `operateflow` VALUES ('87', '34', '6', '4', '1', null, '2014-05-14 12:16:23', '0');
INSERT INTO `operateflow` VALUES ('88', '34', '6', '3', '1', '会签完毕', '2014-05-14 12:16:01', '0');
INSERT INTO `operateflow` VALUES ('89', '34', '6', '5', '1', '审批通过啦', '2014-05-14 12:16:34', '0');
INSERT INTO `operateflow` VALUES ('90', '34', '6', '6', '1', '签订完成', '2014-05-14 12:16:44', '0');
INSERT INTO `operateflow` VALUES ('91', '35', '6', '1', '1', null, '2014-05-14 12:36:35', '0');
INSERT INTO `operateflow` VALUES ('92', '35', '6', '4', '1', null, '2014-05-14 12:37:39', '0');
INSERT INTO `operateflow` VALUES ('93', '35', '6', '3', '1', '会签', '2014-05-14 12:37:03', '0');
INSERT INTO `operateflow` VALUES ('94', '35', '6', '5', '2', '不通过', '2014-05-14 12:37:24', '0');
INSERT INTO `operateflow` VALUES ('95', '35', '6', '6', '-1', null, '2014-05-14 12:36:51', '0');
INSERT INTO `operateflow` VALUES ('96', '36', '6', '1', '1', null, '2014-05-14 12:55:10', '0');
INSERT INTO `operateflow` VALUES ('97', '36', '6', '4', '1', null, '2014-05-14 12:56:18', '0');
INSERT INTO `operateflow` VALUES ('98', '36', '6', '3', '1', 'huiqian', '2014-05-14 12:55:53', '0');
INSERT INTO `operateflow` VALUES ('99', '36', '6', '5', '1', '第二次啦', '2014-05-14 12:56:35', '0');
INSERT INTO `operateflow` VALUES ('100', '36', '6', '6', '1', 'S的呢过', '2014-05-14 13:09:35', '0');
INSERT INTO `operateflow` VALUES ('101', '37', '6', '1', '1', null, '2014-05-14 13:07:38', '0');
INSERT INTO `operateflow` VALUES ('102', '37', '6', '4', '1', null, '2014-05-14 13:09:06', '0');
INSERT INTO `operateflow` VALUES ('103', '37', '6', '3', '1', '妈呀SBBSB', '2014-05-14 13:08:17', '0');
INSERT INTO `operateflow` VALUES ('104', '37', '6', '5', '1', 'sbbbbbbbbb', '2014-05-14 13:09:15', '0');
INSERT INTO `operateflow` VALUES ('105', '37', '6', '6', '1', '这个没问题的', '2014-05-14 13:10:10', '0');
INSERT INTO `operateflow` VALUES ('106', '38', '2', '1', '1', null, '2014-05-14 13:42:21', '0');
INSERT INTO `operateflow` VALUES ('107', '38', '2', '4', '1', null, '2014-05-14 13:44:06', '0');
INSERT INTO `operateflow` VALUES ('108', '38', '6', '3', '1', '李四', '2014-05-14 13:43:15', '0');
INSERT INTO `operateflow` VALUES ('109', '38', '2', '3', '1', '和科威特就哦；了他', '2014-05-14 13:43:04', '0');
INSERT INTO `operateflow` VALUES ('110', '38', '2', '5', '1', 'tfyd', '2014-05-14 13:44:10', '0');
INSERT INTO `operateflow` VALUES ('111', '38', '6', '6', '1', '好了，签订完成', '2014-05-14 13:44:34', '0');

-- ----------------------------
-- Table structure for `permission`
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `del` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', 'HTGL', '合同管理', '包括起草合同、会签合同、定稿合同、审核合同、签订合同，但是不一定拥有此模块全部权限', '0');
INSERT INTO `permission` VALUES ('2', 'XTGL', '系统管理', '包括合同分配、角色管理、日志管理，但是不一定拥有此模块全部权限', '0');
INSERT INTO `permission` VALUES ('3', 'DLXT', '登录系统', '登录到合同管理系统', '0');
INSERT INTO `permission` VALUES ('4', 'GRGL', '个人管理', '用户自己的信息管理', '0');
INSERT INTO `permission` VALUES ('5', 'CXTJ', '查询统计', '包括合同信息查询、合同流程查询，但是不一定拥有此模块全部权限', '0');
INSERT INTO `permission` VALUES ('6', 'JCSJGL', '基础数据管理', '包括合同信息管理、客户信息管理，但是不一定拥有此模块全部权限', '0');
INSERT INTO `permission` VALUES ('7', 'QCHT', '起草合同', '填写合同的全部信息', '0');
INSERT INTO `permission` VALUES ('8', 'HQHT', '会签合同', '指定的会签人会签合同，提交意见', '0');
INSERT INTO `permission` VALUES ('9', 'DGHT', '定稿合同', '起草人根据会签意见，整理定稿合同', '0');
INSERT INTO `permission` VALUES ('10', 'SPHT', '审批合同', '具有一定话语权的领导或者其他特定人员根据定稿的合同，审核该合同，给予通过或否决', '0');
INSERT INTO `permission` VALUES ('11', 'QDHT', '签订合同', '根据审核结果，与客户签订合同', '0');
INSERT INTO `permission` VALUES ('12', 'HTXXCX', '合同信息查询', '根据查询条件显示合同基础信息', '0');
INSERT INTO `permission` VALUES ('13', 'HTLCCX', '合同流程查询', '根据查询条件显示合同流程信息', '0');
INSERT INTO `permission` VALUES ('14', 'HTXXGL', '合同信息管理', '修改合同基本信息和删除合同', '0');
INSERT INTO `permission` VALUES ('15', 'KHXXGL', '客户信息管理', '查询客户信息，增加一个客户，客户基本信息的修改，删除一个客户', '0');
INSERT INTO `permission` VALUES ('16', 'FPHT', '分配合同', '指定合同的每个流程的操作员', '0');
INSERT INTO `permission` VALUES ('17', 'JSGL', '角色管理', '角色的增删改查', '0');
INSERT INTO `permission` VALUES ('18', 'YHGL', '用户管理', '用户的增删改查', '0');
INSERT INTO `permission` VALUES ('19', 'RZGL', '日志管理', '涉及到数据的增加、删除或更改，建立日志，记录其姓名、用户名、操作日期时间', '0');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `del` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'XYH', '新用户', '没有任何权限', '0');
INSERT INTO `role` VALUES ('2', 'XTGLY', '系统管理员', '拥有系统最高权限', '0');
INSERT INTO `role` VALUES ('3', 'HTCZY', '合同操作员', '对合同进行管理', '0');
INSERT INTO `role` VALUES ('4', 'ZSXTGLY', '专属系统管理员', '只有系统管理的权限', '0');
INSERT INTO `role` VALUES ('5', 'hfjd', 'freserw', 'raeds', '1');
INSERT INTO `role` VALUES ('6', 'JL', '经理', '测试用', '0');
INSERT INTO `role` VALUES ('7', 'JL', 'SB', 'namjdsbfbco', '1');

-- ----------------------------
-- Table structure for `rolepermission`
-- ----------------------------
DROP TABLE IF EXISTS `rolepermission`;
CREATE TABLE `rolepermission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleno` int(11) NOT NULL,
  `permissionno` int(11) NOT NULL,
  `del` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_Relationship_10` (`permissionno`),
  KEY `FK_Relationship_9` (`roleno`),
  CONSTRAINT `FK_Relationship_10` FOREIGN KEY (`permissionno`) REFERENCES `permission` (`id`),
  CONSTRAINT `FK_Relationship_9` FOREIGN KEY (`roleno`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rolepermission
-- ----------------------------
INSERT INTO `rolepermission` VALUES ('1', '2', '1', '0');
INSERT INTO `rolepermission` VALUES ('2', '2', '2', '0');
INSERT INTO `rolepermission` VALUES ('3', '2', '3', '0');
INSERT INTO `rolepermission` VALUES ('4', '2', '4', '0');
INSERT INTO `rolepermission` VALUES ('5', '2', '5', '0');
INSERT INTO `rolepermission` VALUES ('6', '2', '6', '0');
INSERT INTO `rolepermission` VALUES ('7', '3', '1', '0');
INSERT INTO `rolepermission` VALUES ('8', '3', '3', '0');
INSERT INTO `rolepermission` VALUES ('9', '3', '4', '0');
INSERT INTO `rolepermission` VALUES ('10', '3', '6', '0');
INSERT INTO `rolepermission` VALUES ('11', '2', '7', '0');
INSERT INTO `rolepermission` VALUES ('12', '2', '8', '0');
INSERT INTO `rolepermission` VALUES ('13', '2', '9', '0');
INSERT INTO `rolepermission` VALUES ('14', '2', '10', '0');
INSERT INTO `rolepermission` VALUES ('15', '2', '11', '0');
INSERT INTO `rolepermission` VALUES ('16', '2', '12', '0');
INSERT INTO `rolepermission` VALUES ('17', '2', '13', '0');
INSERT INTO `rolepermission` VALUES ('18', '2', '14', '0');
INSERT INTO `rolepermission` VALUES ('20', '2', '15', '0');
INSERT INTO `rolepermission` VALUES ('21', '2', '16', '0');
INSERT INTO `rolepermission` VALUES ('22', '2', '17', '0');
INSERT INTO `rolepermission` VALUES ('23', '2', '18', '0');
INSERT INTO `rolepermission` VALUES ('24', '2', '19', '0');
INSERT INTO `rolepermission` VALUES ('25', '3', '7', '1');
INSERT INTO `rolepermission` VALUES ('26', '3', '8', '1');
INSERT INTO `rolepermission` VALUES ('27', '3', '9', '1');
INSERT INTO `rolepermission` VALUES ('28', '3', '10', '1');
INSERT INTO `rolepermission` VALUES ('29', '3', '11', '1');
INSERT INTO `rolepermission` VALUES ('30', '3', '15', '0');
INSERT INTO `rolepermission` VALUES ('31', '6', '1', '0');
INSERT INTO `rolepermission` VALUES ('32', '6', '2', '1');
INSERT INTO `rolepermission` VALUES ('33', '6', '3', '0');
INSERT INTO `rolepermission` VALUES ('34', '6', '4', '0');
INSERT INTO `rolepermission` VALUES ('35', '6', '5', '1');
INSERT INTO `rolepermission` VALUES ('36', '6', '6', '1');
INSERT INTO `rolepermission` VALUES ('37', '6', '7', '1');
INSERT INTO `rolepermission` VALUES ('38', '6', '8', '1');
INSERT INTO `rolepermission` VALUES ('39', '6', '9', '1');
INSERT INTO `rolepermission` VALUES ('40', '6', '10', '1');
INSERT INTO `rolepermission` VALUES ('41', '6', '11', '1');
INSERT INTO `rolepermission` VALUES ('42', '6', '12', '1');
INSERT INTO `rolepermission` VALUES ('43', '6', '13', '1');
INSERT INTO `rolepermission` VALUES ('44', '6', '14', '1');
INSERT INTO `rolepermission` VALUES ('45', '6', '15', '1');
INSERT INTO `rolepermission` VALUES ('46', '6', '16', '0');
INSERT INTO `rolepermission` VALUES ('47', '6', '17', '1');
INSERT INTO `rolepermission` VALUES ('48', '6', '18', '1');
INSERT INTO `rolepermission` VALUES ('49', '6', '19', '1');
INSERT INTO `rolepermission` VALUES ('50', '7', '1', '0');
INSERT INTO `rolepermission` VALUES ('51', '7', '2', '0');
INSERT INTO `rolepermission` VALUES ('52', '7', '3', '0');
INSERT INTO `rolepermission` VALUES ('53', '7', '4', '0');
INSERT INTO `rolepermission` VALUES ('54', '7', '5', '0');
INSERT INTO `rolepermission` VALUES ('55', '7', '6', '0');
INSERT INTO `rolepermission` VALUES ('56', '7', '7', '0');
INSERT INTO `rolepermission` VALUES ('57', '7', '8', '0');
INSERT INTO `rolepermission` VALUES ('58', '7', '9', '0');
INSERT INTO `rolepermission` VALUES ('59', '7', '10', '0');
INSERT INTO `rolepermission` VALUES ('60', '7', '11', '0');
INSERT INTO `rolepermission` VALUES ('61', '7', '12', '0');
INSERT INTO `rolepermission` VALUES ('62', '7', '13', '0');
INSERT INTO `rolepermission` VALUES ('63', '7', '14', '0');
INSERT INTO `rolepermission` VALUES ('64', '7', '15', '1');
INSERT INTO `rolepermission` VALUES ('65', '7', '16', '0');
INSERT INTO `rolepermission` VALUES ('66', '7', '17', '0');
INSERT INTO `rolepermission` VALUES ('67', '7', '18', '1');
INSERT INTO `rolepermission` VALUES ('68', '7', '19', '1');

-- ----------------------------
-- Table structure for `status`
-- ----------------------------
DROP TABLE IF EXISTS `status`;
CREATE TABLE `status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contractno` int(11) NOT NULL,
  `contractStatus` smallint(6) NOT NULL,
  `finishTime` timestamp NULL DEFAULT NULL,
  `del` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_include` (`contractno`),
  CONSTRAINT `FK_include` FOREIGN KEY (`contractno`) REFERENCES `contract` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of status
-- ----------------------------
INSERT INTO `status` VALUES ('1', '1', '21', '2014-05-07 01:39:23', '0');
INSERT INTO `status` VALUES ('2', '2', '21', '2014-05-07 14:15:31', '0');
INSERT INTO `status` VALUES ('4', '11', '21', '2014-04-17 21:35:33', '0');
INSERT INTO `status` VALUES ('5', '6', '21', '2014-05-07 17:59:31', '0');
INSERT INTO `status` VALUES ('6', '10', '21', '2014-04-30 09:52:27', '0');
INSERT INTO `status` VALUES ('7', '16', '31', '2014-04-30 09:52:40', '0');
INSERT INTO `status` VALUES ('8', '17', '41', '2014-04-19 09:52:51', '0');
INSERT INTO `status` VALUES ('9', '8', '50', '2014-05-03 21:33:35', '0');
INSERT INTO `status` VALUES ('10', '9', '21', '2014-05-03 21:33:52', '1');
INSERT INTO `status` VALUES ('11', '12', '21', '2014-05-09 00:47:30', '0');
INSERT INTO `status` VALUES ('12', '13', '11', '2014-05-24 21:34:46', '0');
INSERT INTO `status` VALUES ('13', '14', '41', '2014-05-03 21:35:53', '0');
INSERT INTO `status` VALUES ('14', '15', '50', '2014-05-03 21:36:05', '0');
INSERT INTO `status` VALUES ('15', '16', '51', '2014-05-23 21:36:20', '0');
INSERT INTO `status` VALUES ('16', '18', '61', '2014-05-03 21:36:48', '0');
INSERT INTO `status` VALUES ('17', '19', '21', '2014-05-07 01:41:16', '0');
INSERT INTO `status` VALUES ('18', '21', '11', '2014-05-07 11:29:52', '0');
INSERT INTO `status` VALUES ('19', '22', '11', '2014-05-07 11:56:45', '0');
INSERT INTO `status` VALUES ('20', '23', '11', '2014-05-07 12:10:44', '0');
INSERT INTO `status` VALUES ('22', '26', '21', '2014-05-09 12:17:39', '0');
INSERT INTO `status` VALUES ('23', '27', '21', '2014-05-08 21:07:55', '0');
INSERT INTO `status` VALUES ('24', '28', '31', '2014-05-11 00:05:37', '1');
INSERT INTO `status` VALUES ('25', '29', '21', '2014-05-10 17:01:05', '1');
INSERT INTO `status` VALUES ('26', '30', '41', '2014-05-11 16:39:31', '1');
INSERT INTO `status` VALUES ('27', '31', '61', '2014-05-11 16:35:34', '1');
INSERT INTO `status` VALUES ('28', '32', '61', '2014-05-12 16:55:46', '1');
INSERT INTO `status` VALUES ('29', '33', '41', '2014-05-14 10:42:15', '0');
INSERT INTO `status` VALUES ('30', '34', '61', '2014-05-14 12:16:44', '0');
INSERT INTO `status` VALUES ('31', '35', '41', '2014-05-14 12:37:39', '0');
INSERT INTO `status` VALUES ('32', '36', '61', '2014-05-14 13:09:35', '0');
INSERT INTO `status` VALUES ('33', '37', '61', '2014-05-14 13:10:10', '0');
INSERT INTO `status` VALUES ('34', '38', '61', '2014-05-14 13:44:34', '0');

-- ----------------------------
-- Table structure for `system_user`
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `role` int(11) NOT NULL,
  `nickname` varchar(20) DEFAULT NULL,
  `password` varchar(20) NOT NULL,
  `email` varchar(30) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `del` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_system_user_role` (`role`),
  CONSTRAINT `FK_system_user_role` FOREIGN KEY (`role`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES ('1', 'Tracy', '2', '终极Boss', '841203', '***@bjtu.edu.cn', '15652953396', '0');
INSERT INTO `system_user` VALUES ('2', 'YYC', '2', '张三', '123456', '', '', '0');
INSERT INTO `system_user` VALUES ('3', 'WX', '1', '何二', '123456', 'wx@hotmail.com', '', '0');
INSERT INTO `system_user` VALUES ('5', 'Song', '3', '宋震', '123456', '', '', '0');
INSERT INTO `system_user` VALUES ('6', 'lpx', '2', '李四', 'lpx', 'lpx0804@hotmail.com', '15652953396', '0');
INSERT INTO `system_user` VALUES ('8', '123', '1', '123', '123', '', '', '1');
INSERT INTO `system_user` VALUES ('14', '321', '1', '342', '432', '432', '423', '1');
INSERT INTO `system_user` VALUES ('15', 'qwer', '1', '公共的', 'qwer', '', '', '1');
INSERT INTO `system_user` VALUES ('16', 'Song01', '6', 'A', '123', '', '', '1');
INSERT INTO `system_user` VALUES ('17', 'qwe', '3', 'SSSSBBBBB', '123', 'lpx0804@hotmail.com', '', '0');
INSERT INTO `system_user` VALUES ('18', 'zq', '2', '王五', '123456', '', '', '0');
