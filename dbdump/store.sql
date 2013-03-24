-- MySQL dump 10.13  Distrib 5.5.30, for Linux (x86_64)
--
-- Host: localhost    Database: store
-- ------------------------------------------------------
-- Server version	5.5.30-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sc_centro`
--

DROP TABLE IF EXISTS `sc_centro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_centro` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `person` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_centro`
--

LOCK TABLES `sc_centro` WRITE;
/*!40000 ALTER TABLE `sc_centro` DISABLE KEYS */;
INSERT INTO `sc_centro` VALUES (1,'湘潭高新区','湘潭高新仓','史先生','13500000000');
/*!40000 ALTER TABLE `sc_centro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_item`
--

DROP TABLE IF EXISTS `sc_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `userid` bigint(20) DEFAULT NULL,
  `weight` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_userid` (`userid`),
  KEY `idx_code` (`code`),
  KEY `idx_type` (`type`),
  KEY `idx_title` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_item`
--

LOCK TABLES `sc_item` WRITE;
/*!40000 ALTER TABLE `sc_item` DISABLE KEYS */;
INSERT INTO `sc_item` VALUES (1,'DZG-B05E','','天际 DZG-B05E 煮蛋器','',6,1),(2,'SNJ-B10A','','酸奶机','',6,1),(3,'电炖锅DDG-7AD','','电炖锅','',6,2),(5,'122233333','','益智包邮儿童摇马/儿童玩具/摇马木马/藤编藤艺藤条/摇摇马/摇摆','',7,899),(6,'12356781','111','矮凳 宜家 仿古家具藤椅时尚创意凳子小板凳宜家居圆凳梳妆古筝木','',7,11),(7,'121111111','12','厂家直销藤椅藤编餐椅酒店藤家具书房电脑茶楼椅办公包邮宜家休闲-猪肝红','',7,155),(8,'12222222','','优比特 草莓果酱','',6,1),(9,'12121231','14124','我的商品111','',7,124);
/*!40000 ALTER TABLE `sc_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_item_inventory`
--

DROP TABLE IF EXISTS `sc_item_inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_item_inventory` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) DEFAULT NULL,
  `num` int(11) NOT NULL,
  `centro_id` bigint(20) DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK24C208FFD19767EA` (`centro_id`),
  KEY `FK24C208FF8444052A` (`item_id`),
  KEY `FK24C208FFFD61AA2A` (`user_id`),
  KEY `idx_account` (`account`),
  CONSTRAINT `sc_item_inventory_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sc_user` (`id`),
  CONSTRAINT `sc_item_inventory_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `sc_item` (`id`),
  CONSTRAINT `sc_item_inventory_ibfk_3` FOREIGN KEY (`centro_id`) REFERENCES `sc_centro` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_item_inventory`
--

LOCK TABLES `sc_item_inventory` WRITE;
/*!40000 ALTER TABLE `sc_item_inventory` DISABLE KEYS */;
INSERT INTO `sc_item_inventory` VALUES (1,'201',-12,1,1,6),(2,'101',0,1,1,6),(3,'201',-25,1,2,6),(4,'101',0,1,2,6),(5,'201',-10,1,3,6),(6,'101',0,1,3,6),(7,'102',17,1,2,6),(8,'103_001',1,1,2,6),(9,'103_002',3,1,2,6),(10,'103_003',4,1,2,6),(11,'102',4,1,3,6),(12,'103_001',1,1,3,6),(13,'103_002',2,1,3,6),(14,'103_003',3,1,3,6),(15,'102',12,1,1,6),(16,'103_001',0,1,1,6),(17,'103_002',0,1,1,6),(18,'103_003',0,1,1,6),(19,'201',-1000,1,6,7),(20,'101',0,1,6,7),(21,'102',991,1,6,7),(22,'103_001',0,1,6,7),(23,'103_002',2,1,6,7),(24,'103_003',3,1,6,7),(25,'104',4,1,6,7),(26,'201',-100,1,8,6),(27,'101',0,1,8,6),(28,'102',98,1,8,6),(29,'103_001',0,1,8,6),(30,'103_002',0,1,8,6),(31,'103_003',0,1,8,6),(32,'104',2,1,8,6);
/*!40000 ALTER TABLE `sc_item_inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_item_mapping`
--

DROP TABLE IF EXISTS `sc_item_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_item_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `detail_url` varchar(255) DEFAULT NULL,
  `num_iid` bigint(20) DEFAULT NULL,
  `sku_id` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK190B95318444052A` (`item_id`),
  CONSTRAINT `sc_item_mapping_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `sc_item` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_item_mapping`
--

LOCK TABLES `sc_item_mapping` WRITE;
/*!40000 ALTER TABLE `sc_item_mapping` DISABLE KEYS */;
INSERT INTO `sc_item_mapping` VALUES (4,'http://item.taobao.com/item.htm?id=12911692845&spm=2014.21397471.0.0',12911692845,'0','矮凳 宜家 仿古家具藤椅时尚创意凳子小板凳宜家居圆凳梳妆古筝木',6),(6,'http://item.taobao.com/item.htm?id=13336552035&spm=2014.21397471.0.0',13336552035,'15526046255','厂家直销藤椅藤编餐椅酒店藤家具书房电脑茶楼椅办公包邮宜家休闲',6),(9,'http://item.taobao.com/item.htm?id=18650491207&spm=2014.21397471.0.0',18650491207,'0','优比特  草莓果酱   酸奶伴侣  20g',8);
/*!40000 ALTER TABLE `sc_item_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_ship_order`
--

DROP TABLE IF EXISTS `sc_ship_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_ship_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `centro_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `express_company` varchar(255) DEFAULT NULL,
  `express_orderno` varchar(255) DEFAULT NULL,
  `fetch_date` datetime DEFAULT NULL,
  `last_update_date` datetime DEFAULT NULL,
  `orderno` varchar(255) DEFAULT NULL,
  `origin_persion` varchar(255) DEFAULT NULL,
  `origin_phone` varchar(255) DEFAULT NULL,
  `receiver_address` varchar(255) DEFAULT NULL,
  `receiver_city` varchar(255) DEFAULT NULL,
  `receiver_district` varchar(255) DEFAULT NULL,
  `receiver_mobile` varchar(255) DEFAULT NULL,
  `receiver_name` varchar(255) DEFAULT NULL,
  `receiver_phone` varchar(255) DEFAULT NULL,
  `receiver_state` varchar(255) DEFAULT NULL,
  `receiver_zip` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `totalnum` int(11) NOT NULL,
  `trade_id` bigint(20) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `create_userid` bigint(20) DEFAULT NULL,
  `last_update_userid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC0BB5CDA1488148E` (`last_update_userid`),
  KEY `FKC0BB5CDA987076E4` (`create_userid`),
  KEY `idx_centro_id` (`centro_id`),
  KEY `idx_express_order_no` (`express_orderno`),
  KEY `idx_type` (`type`),
  KEY `idx_trade_id` (`trade_id`),
  KEY `idx_status` (`status`),
  CONSTRAINT `sc_ship_order_ibfk_1` FOREIGN KEY (`create_userid`) REFERENCES `sc_user` (`id`),
  CONSTRAINT `sc_ship_order_ibfk_2` FOREIGN KEY (`last_update_userid`) REFERENCES `sc_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_ship_order`
--

LOCK TABLES `sc_ship_order` WRITE;
/*!40000 ALTER TABLE `sc_ship_order` DISABLE KEYS */;
INSERT INTO `sc_ship_order` VALUES (1,1,'2013-03-21 21:55:47','111111111111111','111111111111111111111','2013-03-21 00:00:00','2013-03-21 21:55:47','E2013032100001','卢新星','13787325815','111111111111',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'','ENTRY_FINISH',10,NULL,'entry',6,6),(2,1,'2013-03-21 22:09:10','111111111111111','111111111111111111111','2013-03-21 00:00:00','2013-03-21 22:09:10','E2013032100002','卢新星','13787325815','111111111111',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'','ENTRY_WAIT_SELLER_SEND',0,NULL,'entry',6,6),(3,1,'2013-03-21 22:11:20','111111111111111','111111111111111111111','2013-03-21 00:00:00','2013-03-21 22:11:20','E2013032100003','卢新星','13787325815','111111111111',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'','ENTRY_FINISH',0,NULL,'entry',6,6),(4,1,'2013-03-23 13:28:59','121','123123123','2013-03-23 00:00:00','2013-03-23 13:28:59','E2013032300001','ccccc','13533433333','112',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'111','ENTRY_FINISH',12,NULL,'entry',7,7),(5,1,'2013-03-23 14:01:39','YUNDA','100000020',NULL,'2013-03-23 14:02:06','S2013032300002',NULL,NULL,'水头镇荷泉路138号三楼','泉州市','南安市','18065446789','李亚伟','0595-36280226','福建省','362300','','WAIT_BUYER_RECEIVED',0,1,'send',7,1),(6,1,'2013-03-23 14:16:12',NULL,NULL,NULL,'2013-03-23 14:16:12','S2013032300003',NULL,NULL,'府前路大坎村直上200米金海阁港式火锅店','云浮市','云城区','13927109999','黄世强','','广东省','527300','','WAIT_EXPRESS_RECEIVED',0,2,'send',7,NULL),(7,1,'2013-03-23 17:04:21','111111111111111','111111111111111111111','2013-03-23 00:00:00','2013-03-23 17:04:21','E2013032300004','卢新星','13787325815','111111111111',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'','ENTRY_FINISH',5,NULL,'entry',6,6),(8,1,'2013-03-23 17:29:34','2222222','德邦物流','2013-03-23 00:00:00','2013-03-23 17:29:34','E2013032300005','卢新星','13787325815','222222222',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'','ENTRY_FINISH',20,NULL,'entry',6,6),(9,1,'2013-03-23 17:42:33','YUNDA','1200735714208',NULL,'2013-03-23 17:48:23','S2013032300006',NULL,NULL,'永廻路76号','永州市','江永县','13874626471','王册','','湖南省','425400','','WAIT_BUYER_RECEIVED',0,3,'send',6,1),(10,1,'2013-03-23 18:01:13','YUNDA','1200701541422',NULL,'2013-03-23 18:02:32','S2013032300007',NULL,NULL,'冠华路998号美都花园19棟2单元','盐城市','建湖县','13382639346','臧启路','','江苏省','224700','','WAIT_BUYER_RECEIVED',0,4,'send',6,1);
/*!40000 ALTER TABLE `sc_ship_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_ship_order_detail`
--

DROP TABLE IF EXISTS `sc_ship_order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_ship_order_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `num` bigint(20) NOT NULL,
  `sku_properties_name` varchar(255) DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKBA9F6F568444052A` (`item_id`),
  KEY `FKBA9F6F565DDBA3CE` (`order_id`),
  CONSTRAINT `sc_ship_order_detail_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `sc_ship_order` (`id`),
  CONSTRAINT `sc_ship_order_detail_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `sc_item` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_ship_order_detail`
--

LOCK TABLES `sc_ship_order_detail` WRITE;
/*!40000 ALTER TABLE `sc_ship_order_detail` DISABLE KEYS */;
INSERT INTO `sc_ship_order_detail` VALUES (1,2,NULL,1,1),(2,20,NULL,2,3),(3,10,NULL,3,3),(4,1000,NULL,6,4),(5,4,NULL,6,5),(6,1,NULL,6,6),(7,10,NULL,1,7),(8,5,NULL,2,7),(9,100,NULL,8,8),(10,1,NULL,8,9),(11,1,NULL,8,10);
/*!40000 ALTER TABLE `sc_ship_order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_trade`
--

DROP TABLE IF EXISTS `sc_trade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_trade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `buyer_alipay_no` varchar(255) DEFAULT NULL,
  `buyer_area` varchar(255) DEFAULT NULL,
  `buyer_email` varchar(255) DEFAULT NULL,
  `buyer_memo` varchar(255) DEFAULT NULL,
  `buyer_message` varchar(255) DEFAULT NULL,
  `buyer_nick` varchar(255) DEFAULT NULL,
  `has_buyer_message` tinyint(1) DEFAULT NULL,
  `lg_aging` varchar(255) DEFAULT NULL,
  `lg_aging_type` varchar(255) DEFAULT NULL,
  `pay_time` datetime DEFAULT NULL,
  `receiver_address` varchar(255) DEFAULT NULL,
  `receiver_city` varchar(255) DEFAULT NULL,
  `receiver_district` varchar(255) DEFAULT NULL,
  `receiver_mobile` varchar(255) DEFAULT NULL,
  `receiver_name` varchar(255) DEFAULT NULL,
  `receiver_phone` varchar(255) DEFAULT NULL,
  `receiver_state` varchar(255) DEFAULT NULL,
  `receiver_zip` varchar(255) DEFAULT NULL,
  `shipping_type` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `tid` bigint(20) DEFAULT NULL,
  `trade_from` varchar(255) DEFAULT NULL,
  `centro_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC7E466B5D19767EA` (`centro_id`),
  KEY `FKC7E466B5FD61AA2A` (`user_id`),
  KEY `idx_tid` (`tid`),
  KEY `idx_shipping_type` (`shipping_type`),
  KEY `idx_status` (`status`),
  CONSTRAINT `sc_trade_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sc_user` (`id`),
  CONSTRAINT `sc_trade_ibfk_2` FOREIGN KEY (`centro_id`) REFERENCES `sc_centro` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_trade`
--

LOCK TABLES `sc_trade` WRITE;
/*!40000 ALTER TABLE `sc_trade` DISABLE KEYS */;
INSERT INTO `sc_trade` VALUES (1,NULL,NULL,NULL,'','',NULL,NULL,NULL,NULL,'2013-03-23 11:19:56','水头镇荷泉路138号三楼','泉州市','南安市','18065446789','李亚伟','0595-36280226','福建省','362300','express','TRADE_WAIT_BUYER_RECEIVED',238297558939196,NULL,1,7),(2,NULL,NULL,NULL,'','',NULL,NULL,NULL,NULL,'2013-03-24 02:56:58','府前路大坎村直上200米金海阁港式火锅店','云浮市','云城区','13927109999','黄世强','','广东省','527300','free','TRADE_WAIT_EXPRESS_SHIP',313602602510469,NULL,1,7),(3,NULL,NULL,NULL,'','',NULL,NULL,NULL,NULL,'2013-03-24 07:37:26','永廻路76号','永州市','江永县','13874626471','王册','','湖南省','425400','express','TRADE_WAIT_BUYER_RECEIVED',314525726718250,NULL,1,6),(4,NULL,NULL,NULL,'','',NULL,NULL,NULL,NULL,'2013-03-24 07:43:55','冠华路998号美都花园19棟2单元','盐城市','建湖县','13382639346','臧启路','','江苏省','224700','express','TRADE_WAIT_BUYER_RECEIVED',238561197000883,NULL,1,6);
/*!40000 ALTER TABLE `sc_trade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_trade_mapping`
--

DROP TABLE IF EXISTS `sc_trade_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_trade_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` varchar(255) DEFAULT NULL,
  `tid` bigint(20) DEFAULT NULL,
  `trade_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_tid` (`tid`),
  KEY `idx_trade_id` (`trade_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_trade_mapping`
--

LOCK TABLES `sc_trade_mapping` WRITE;
/*!40000 ALTER TABLE `sc_trade_mapping` DISABLE KEYS */;
INSERT INTO `sc_trade_mapping` VALUES (1,'TRADE_WAIT_BUYER_RECEIVED',238297558939196,1),(2,'TRADE_WAIT_EXPRESS_SHIP',313602602510469,2),(3,'TRADE_WAIT_BUYER_RECEIVED',314525726718250,3),(4,'TRADE_WAIT_BUYER_RECEIVED',238561197000883,4);
/*!40000 ALTER TABLE `sc_trade_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_trade_order`
--

DROP TABLE IF EXISTS `sc_trade_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_trade_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `adjust_fee` varchar(255) DEFAULT NULL,
  `buyer_nick` varchar(255) DEFAULT NULL,
  `discount_fee` varchar(255) DEFAULT NULL,
  `num` bigint(20) NOT NULL,
  `num_iid` bigint(20) DEFAULT NULL,
  `order_from` varchar(255) DEFAULT NULL,
  `pic_path` varchar(255) DEFAULT NULL,
  `sku_id` varchar(255) DEFAULT NULL,
  `sku_properties_name` varchar(255) DEFAULT NULL,
  `timeout_action_time` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `total_fee` varchar(255) DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `trade_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK17B5B544A6780EA` (`trade_id`),
  KEY `FK17B5B5448444052A` (`item_id`),
  CONSTRAINT `sc_trade_order_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `sc_item` (`id`),
  CONSTRAINT `sc_trade_order_ibfk_2` FOREIGN KEY (`trade_id`) REFERENCES `sc_trade` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_trade_order`
--

LOCK TABLES `sc_trade_order` WRITE;
/*!40000 ALTER TABLE `sc_trade_order` DISABLE KEYS */;
INSERT INTO `sc_trade_order` VALUES (1,NULL,NULL,NULL,4,NULL,NULL,NULL,NULL,NULL,NULL,'矮凳 宜家 仿古家具藤椅时尚创意凳子小板凳宜家居圆凳梳妆古筝木',NULL,6,1),(2,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,'矮凳 宜家 仿古家具藤椅时尚创意凳子小板凳宜家居圆凳梳妆古筝木',NULL,6,2),(3,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,'优比特 草莓果酱',NULL,8,3),(4,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,'优比特 草莓果酱',NULL,8,4);
/*!40000 ALTER TABLE `sc_trade_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sc_user`
--

DROP TABLE IF EXISTS `sc_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sc_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `roles` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_roles` (`roles`),
  KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sc_user`
--

LOCK TABLES `sc_user` WRITE;
/*!40000 ALTER TABLE `sc_user` DISABLE KEYS */;
INSERT INTO `sc_user` VALUES (1,'超级管理员','ead9ee96a6b63ae918b771bf673d6a5883df2e5a','admin','114f259c942d520e',NULL,'admin'),(6,'淘宝卖家账号','33e011e887ac4fa111968a6738a443bd428f04fd','user','2a08dc8c690ed87d','懒虫87号小店','luxinxing888'),(7,'淘宝卖家账号','cb88d8ed52229afcd248f704e4c0bda41724777c','user','338310fced75fd2c','◥◣护湘关商城◢◤凉席藤摇椅藤椅厂家直销,藤椅工厂,生产厂商','adminbbs'),(8,'淘宝卖家账号','284cdae009dd12b5d8398e7387fab78e5a4b0e08','user','4dd1258d08a868a7','席伊吖家居旗舰店','席伊吖家居旗舰店');
/*!40000 ALTER TABLE `sc_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-03-24 18:29:13
