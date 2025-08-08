/*
SQLyog Ultimate v12.14 (64 bit)
MySQL - 5.6.20 : Database - telegramkc
*********************************************************************


/*Table structure for table `infouser` */

DROP TABLE IF EXISTS `infouser`;

CREATE TABLE `infouser` (
  `usernumber` VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户主键、、用户名',
  `username` VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户的昵称',
  `useryaoqing` INT(50) DEFAULT NULL COMMENT '用户的邀请数',
  `userbalance` INT(50) DEFAULT NULL COMMENT '用户余额',
  `yiduihuan` INT(11) DEFAULT NULL COMMENT '已兑换',
  `tokens` VARCHAR(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '唯一id',
  `shangjitok` VARCHAR(500) DEFAULT NULL COMMENT '上级id',
  `isi` INT(50) DEFAULT '0' COMMENT '上级是否已经收益',
  PRIMARY KEY (`usernumber`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `pindaouser` */

DROP TABLE IF EXISTS `pindaouser`;

CREATE TABLE `pindaouser` (
  `usernama` VARCHAR(200) COLLATE utf8_bin DEFAULT NULL,
  `usernumber` VARCHAR(200) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`usernumber`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `shipin` */

DROP TABLE IF EXISTS `shipin`;

CREATE TABLE `shipin` (
  `shipinid` VARCHAR(200) COLLATE utf8_bin NOT NULL COMMENT '唯一id',
  `lengths` INT(100) DEFAULT NULL COMMENT '是否是长视频',
  `misoshu` VARCHAR(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '视频描述',
  PRIMARY KEY (`shipinid`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
