-- MariaDB dump 10.17  Distrib 10.4.13-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: project
-- ------------------------------------------------------
-- Server version	10.4.13-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bills`
--

DROP TABLE IF EXISTS `bills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bills` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL,
  `issue_date` timestamp NULL DEFAULT current_timestamp(),
  `income` bigint(20) NOT NULL DEFAULT 0,
  `payment` bigint(20) NOT NULL DEFAULT 0,
  `status` enum('PENDING','FINISHED') NOT NULL DEFAULT 'PENDING',
  PRIMARY KEY (`id`),
  KEY `bills_users_id_fk` (`user_id`),
  CONSTRAINT `bills_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bills`
--

LOCK TABLES `bills` WRITE;
/*!40000 ALTER TABLE `bills` DISABLE KEYS */;
INSERT INTO `bills` VALUES (10,'admin','2020-05-11 15:00:00',122880,81920,'FINISHED'),(11,'provider','2020-05-11 15:00:00',10240,153360,'FINISHED'),(12,'subscriber','2020-05-11 15:00:00',0,20240,'FINISHED'),(13,'admin','2020-06-12 09:01:33',245760,163840,'PENDING'),(14,'provider','2020-06-12 09:01:33',20480,306720,'PENDING'),(15,'subscriber','2020-06-12 09:01:33',0,40480,'FINISHED');
/*!40000 ALTER TABLE `bills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Education'),(2,'Hobby'),(3,'Utility'),(4,'Industry');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `constants`
--

DROP TABLE IF EXISTS `constants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `constants` (
  `id` varchar(20) NOT NULL,
  `value` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `constants`
--

LOCK TABLES `constants` WRITE;
/*!40000 ALTER TABLE `constants` DISABLE KEYS */;
INSERT INTO `constants` VALUES ('delete_threshold',1),('download_rate',10),('joining',10000),('subscription',10000),('upload_rate',20);
/*!40000 ALTER TABLE `constants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dependencies`
--

DROP TABLE IF EXISTS `dependencies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dependencies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `require_item_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `dependencies_items_id_fk` (`item_id`),
  KEY `dependencies_items_id_fk2` (`require_item_id`),
  CONSTRAINT `dependencies_items_id_fk` FOREIGN KEY (`item_id`) REFERENCES `items` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `dependencies_items_id_fk2` FOREIGN KEY (`require_item_id`) REFERENCES `items` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dependencies`
--

LOCK TABLES `dependencies` WRITE;
/*!40000 ALTER TABLE `dependencies` DISABLE KEYS */;
INSERT INTO `dependencies` VALUES (3,7,4);
/*!40000 ALTER TABLE `dependencies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `dependencies_ext`
--

DROP TABLE IF EXISTS `dependencies_ext`;
/*!50001 DROP VIEW IF EXISTS `dependencies_ext`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `dependencies_ext` (
  `item_id` tinyint NOT NULL,
  `require_item_id` tinyint NOT NULL,
  `require_item_name` tinyint NOT NULL,
  `require_item_size` tinyint NOT NULL,
  `require_item_hardware` tinyint NOT NULL,
  `require_item_os` tinyint NOT NULL,
  `require_item_url` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `downloads`
--

DROP TABLE IF EXISTS `downloads`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `downloads` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL,
  `item_id` int(11) NOT NULL,
  `download_time` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `downloads_users_id_fk` (`user_id`),
  KEY `downloads_items_id_fk` (`item_id`),
  CONSTRAINT `downloads_items_id_fk` FOREIGN KEY (`item_id`) REFERENCES `items` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `downloads_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `downloads`
--

LOCK TABLES `downloads` WRITE;
/*!40000 ALTER TABLE `downloads` DISABLE KEYS */;
INSERT INTO `downloads` VALUES (4,'subscriber',4,'2020-06-10 14:03:42'),(5,'provider',4,'2020-06-11 13:34:22'),(11,'provider',4,'2020-06-11 14:08:52'),(13,'provider',4,'2020-06-11 14:12:13'),(14,'provider',4,'2020-06-11 15:02:40'),(15,'subscriber',7,'2020-06-11 17:49:04'),(18,'subscriber',4,'2020-06-12 09:30:51'),(19,'subscriber',4,'2020-06-12 09:33:41'),(20,'subscriber',4,'2020-06-12 11:31:02'),(21,'subscriber',7,'2020-06-12 11:32:07'),(22,'subscriber',4,'2020-06-12 11:32:19');
/*!40000 ALTER TABLE `downloads` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 trigger downloads_insert_payment_trigger
    after insert
    on downloads
    for each row
begin
    declare item_size int(11) default 0;
    declare download_rate bigint(20) default 0;
    declare uploader_id varchar(20) default NULL;
    begin
        select value into download_rate from constants where id = 'download_rate';
        select size, author_id into item_size, uploader_id from items where id = new.item_id;

        insert into payments (user_id, type, amount, due_date, comment)
        values (new.user_id,
                'PAYMENT',
                item_size * download_rate,
                adddate(last_day(curdate()), 1),
                'DOWNLOAD');
        insert into payments (user_id, type, amount, due_date, comment)
        values (uploader_id,
                'INCOME',
                item_size * download_rate,
                adddate(last_day(curdate()), 1),
                'DOWNLOAD');
    end;

end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Temporary table structure for view `downloads_ext`
--

DROP TABLE IF EXISTS `downloads_ext`;
/*!50001 DROP VIEW IF EXISTS `downloads_ext`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `downloads_ext` (
  `id` tinyint NOT NULL,
  `user_id` tinyint NOT NULL,
  `user_name` tinyint NOT NULL,
  `item_id` tinyint NOT NULL,
  `item_name` tinyint NOT NULL,
  `download_time` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author_id` varchar(20) NOT NULL,
  `category_id` int(11) NOT NULL,
  `type` enum('PROGRAM','IMAGE','VIDEO','AUDIO','DOCUMENT') NOT NULL,
  `name` varchar(100) NOT NULL,
  `size` int(11) NOT NULL,
  `url` varchar(200) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT current_timestamp(),
  `hardware` enum('MAC','PC','WORKSTATION','ALL') DEFAULT 'ALL',
  `os` enum('MAC','WINDOWS','LINUX','ALL') DEFAULT 'ALL',
  `description` varchar(1000) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `items_categories_id_fk` (`category_id`),
  KEY `items_users_id_fk` (`author_id`),
  CONSTRAINT `items_categories_id_fk` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `items_users_id_fk` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
INSERT INTO `items` VALUES (4,'admin',1,'PROGRAM','VLC Media Player',4096,'https://icampus.skku.edu/index.do','2020-06-10 13:45:57','ALL','ALL','VLC is god program. wow!',0),(7,'provider',4,'PROGRAM','Util',1024,'https://junbread.win/file-sharing/download?id=801d2a48-cf15-4f4e-9aa8-481e12ee1420','2020-06-12 11:36:11','ALL','ALL','Wow Wow Wow',0),(8,'provider',1,'IMAGE','DDL Cheat Sheet',10,'https://junbread.win/file-sharing/download?id=8a9a8fc9-e5e8-486d-9697-59b34d22cbd7','2020-06-12 11:34:06','ALL','ALL','Very valuable cheat sheet. you must see it.',1);
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 trigger items_update_time_trigger
    before update
    on items
    for each row
begin
    if old.deleted = new.deleted then
        set new.update_time = current_timestamp();
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Temporary table structure for view `items_ext`
--

DROP TABLE IF EXISTS `items_ext`;
/*!50001 DROP VIEW IF EXISTS `items_ext`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `items_ext` (
  `id` tinyint NOT NULL,
  `type` tinyint NOT NULL,
  `name` tinyint NOT NULL,
  `size` tinyint NOT NULL,
  `url` tinyint NOT NULL,
  `update_time` tinyint NOT NULL,
  `hardware` tinyint NOT NULL,
  `os` tinyint NOT NULL,
  `description` tinyint NOT NULL,
  `download_cnt` tinyint NOT NULL,
  `category_id` tinyint NOT NULL,
  `category_name` tinyint NOT NULL,
  `author_id` tinyint NOT NULL,
  `author_name` tinyint NOT NULL,
  `deleted` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL,
  `type` enum('INCOME','PAYMENT') NOT NULL DEFAULT 'PAYMENT',
  `amount` bigint(20) NOT NULL,
  `due_date` date NOT NULL,
  `comment` varchar(20) DEFAULT NULL,
  `issue_bill_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `payments_users_id_fk` (`user_id`),
  KEY `payments_bills_id_fk` (`issue_bill_id`),
  CONSTRAINT `payments_bills_id_fk` FOREIGN KEY (`issue_bill_id`) REFERENCES `bills` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `payments_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,'provider','PAYMENT',10000,'2020-07-01','JOINING',14),(11,'provider','PAYMENT',40960,'2020-07-01','DOWNLOAD',14),(12,'admin','INCOME',40960,'2020-07-01','DOWNLOAD',13),(14,'provider','PAYMENT',40960,'2020-07-01','DOWNLOAD',14),(15,'admin','INCOME',40960,'2020-07-01','DOWNLOAD',13),(16,'provider','PAYMENT',40960,'2020-07-01','DOWNLOAD',14),(17,'admin','INCOME',40960,'2020-07-01','DOWNLOAD',13),(18,'subscriber','PAYMENT',10240,'2020-07-01','DOWNLOAD',15),(19,'provider','INCOME',10240,'2020-07-01','DOWNLOAD',14),(30,'admin','PAYMENT',81920,'2020-07-01','SUBSCRIPTION',13),(31,'provider','PAYMENT',20480,'2020-07-01','SUBSCRIPTION',14),(33,'subscriber','PAYMENT',10000,'2020-07-01','SUBSCRIPTION',15),(34,'subscriber','PAYMENT',40960,'2020-07-01','DOWNLOAD',NULL),(35,'admin','INCOME',40960,'2020-07-01','DOWNLOAD',NULL),(36,'subscriber','PAYMENT',40960,'2020-07-01','DOWNLOAD',NULL),(37,'admin','INCOME',40960,'2020-07-01','DOWNLOAD',NULL),(38,'subscriber','PAYMENT',40960,'2020-07-01','DOWNLOAD',NULL),(39,'admin','INCOME',40960,'2020-07-01','DOWNLOAD',NULL),(40,'subscriber','PAYMENT',10240,'2020-07-01','DOWNLOAD',NULL),(41,'provider','INCOME',10240,'2020-07-01','DOWNLOAD',NULL),(42,'subscriber','PAYMENT',40960,'2020-07-01','DOWNLOAD',NULL),(43,'admin','INCOME',40960,'2020-07-01','DOWNLOAD',NULL);
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `providers_stat`
--

DROP TABLE IF EXISTS `providers_stat`;
/*!50001 DROP VIEW IF EXISTS `providers_stat`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `providers_stat` (
  `user_id` tinyint NOT NULL,
  `user_name` tinyint NOT NULL,
  `user_download_cnt` tinyint NOT NULL,
  `item_id` tinyint NOT NULL,
  `item_name` tinyint NOT NULL,
  `item_download_cnt` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `subscribers_stat`
--

DROP TABLE IF EXISTS `subscribers_stat`;
/*!50001 DROP VIEW IF EXISTS `subscribers_stat`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `subscribers_stat` (
  `user_id` tinyint NOT NULL,
  `user_name` tinyint NOT NULL,
  `user_download_cnt` tinyint NOT NULL,
  `item_id` tinyint NOT NULL,
  `item_name` tinyint NOT NULL,
  `item_download_cnt` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` varchar(20) NOT NULL,
  `pw` char(40) NOT NULL,
  `name` varchar(20) NOT NULL,
  `role` enum('SUBSCRIBER','PROVIDER','ADMIN') NOT NULL DEFAULT 'SUBSCRIBER',
  `account_number` varchar(20) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `phone` varchar(13) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `join_date` date NOT NULL DEFAULT current_timestamp(),
  `subscription` tinyint(1) NOT NULL DEFAULT 0,
  `last_login_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('admin','5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8','admin','ADMIN','1002-250-541995','Suwon','010-8838-2951','1996-11-01','2020-06-08',1,'2020-06-12 11:25:52'),('provider','5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8','provider','PROVIDER','1002-250-541995','Suwon','010-8838-2951','1996-11-01','2020-06-11',1,'2020-06-12 11:32:52'),('subscriber','5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8','subscriber','SUBSCRIBER','1002-250-541995','Suwon','010-8838-2951','1996-11-01','2020-06-10',1,'2020-06-12 11:28:01');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 trigger users_insert_joining_fee_trigger
    after update
    on users
    for each row
begin
    declare joining_fee bigint(20) default 0;

    if new.role = 'PROVIDER' and old.last_login_time is null and new.last_login_time is not null then
        select value into joining_fee from constants where id = 'joining_fee';
        insert into payments (user_id, type, amount, due_date, comment)
        values (new.id, 'PAYMENT', joining_fee, adddate(last_day(curdate()), 1), 'JOINING');
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `dependencies_ext`
--

/*!50001 DROP TABLE IF EXISTS `dependencies_ext`*/;
/*!50001 DROP VIEW IF EXISTS `dependencies_ext`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `dependencies_ext` AS select `d`.`item_id` AS `item_id`,`i`.`id` AS `require_item_id`,`i`.`name` AS `require_item_name`,`i`.`size` AS `require_item_size`,`i`.`hardware` AS `require_item_hardware`,`i`.`os` AS `require_item_os`,`i`.`url` AS `require_item_url` from (`dependencies` `d` join `items` `i` on(`d`.`require_item_id` = `i`.`id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `downloads_ext`
--

/*!50001 DROP TABLE IF EXISTS `downloads_ext`*/;
/*!50001 DROP VIEW IF EXISTS `downloads_ext`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `downloads_ext` AS select `d`.`id` AS `id`,`u`.`id` AS `user_id`,`u`.`name` AS `user_name`,`i`.`id` AS `item_id`,`i`.`name` AS `item_name`,`d`.`download_time` AS `download_time` from ((`downloads` `d` join `users` `u` on(`d`.`user_id` = `u`.`id`)) join `items` `i` on(`d`.`item_id` = `i`.`id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `items_ext`
--

/*!50001 DROP TABLE IF EXISTS `items_ext`*/;
/*!50001 DROP VIEW IF EXISTS `items_ext`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `items_ext` AS select `i`.`id` AS `id`,`i`.`type` AS `type`,`i`.`name` AS `name`,`i`.`size` AS `size`,`i`.`url` AS `url`,`i`.`update_time` AS `update_time`,`i`.`hardware` AS `hardware`,`i`.`os` AS `os`,`i`.`description` AS `description`,(select count(0) from `downloads` where `downloads`.`item_id` = `i`.`id`) AS `download_cnt`,`c`.`id` AS `category_id`,`c`.`name` AS `category_name`,`u`.`id` AS `author_id`,`u`.`name` AS `author_name`,`i`.`deleted` AS `deleted` from ((`items` `i` join `categories` `c` on(`i`.`category_id` = `c`.`id`)) join `users` `u` on(`i`.`author_id` = `u`.`id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `providers_stat`
--

/*!50001 DROP TABLE IF EXISTS `providers_stat`*/;
/*!50001 DROP VIEW IF EXISTS `providers_stat`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `providers_stat` AS select `items_ext`.`author_id` AS `user_id`,`items_ext`.`author_name` AS `user_name`,ifnull(sum(`items_ext`.`download_cnt`) over ( partition by `items_ext`.`author_id`),0) AS `user_download_cnt`,`items_ext`.`id` AS `item_id`,`items_ext`.`name` AS `item_name`,`items_ext`.`download_cnt` AS `item_download_cnt` from `items_ext` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `subscribers_stat`
--

/*!50001 DROP TABLE IF EXISTS `subscribers_stat`*/;
/*!50001 DROP VIEW IF EXISTS `subscribers_stat`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `subscribers_stat` AS select `u`.`id` AS `user_id`,`u`.`name` AS `user_name`,ifnull(sum(`d`.`download_cnt`) over ( partition by `d`.`user_id`),0) AS `user_download_cnt`,`d`.`item_id` AS `item_id`,`d`.`item_name` AS `item_name`,`d`.`download_cnt` AS `item_download_cnt` from (`project`.`users` `u` join (select `downloads_ext`.`user_id` AS `user_id`,`downloads_ext`.`item_id` AS `item_id`,`downloads_ext`.`item_name` AS `item_name`,count(0) AS `download_cnt` from `project`.`downloads_ext` group by `downloads_ext`.`user_id`,`downloads_ext`.`item_id`) `d` on(`u`.`id` = `d`.`user_id`)) where `u`.`role` = 'SUBSCRIBER' */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-12 23:27:03
