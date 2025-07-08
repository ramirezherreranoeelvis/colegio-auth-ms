CREATE DATABASE  IF NOT EXISTS `auth` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `auth`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: auth
-- ------------------------------------------------------
-- Server version	9.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `access`
--

DROP TABLE IF EXISTS `access`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `access` (
  `id` varchar(255) NOT NULL,
  `access_enabled` bit(1) NOT NULL,
  `description` text,
  `password` varchar(255) NOT NULL,
  `username` varchar(20) NOT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_by` varchar(100) NOT NULL,
  `last_modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKh3rc0ymgxqughjlyuwn4n83ml` (`username`),
  KEY `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `access`
--

LOCK TABLES `access` WRITE;
/*!40000 ALTER TABLE `access` DISABLE KEYS */;
INSERT INTO `access` VALUES ('04cdc7cb-e95f-4f54-bc37-437c73859ab2',_binary '',NULL,'$2a$10$u5uuubAEHa8u3Jnh85w.quzr6/rB5d/kEC/uia7OAAI01aBYNdYpC','gramirezsa','SYSTEM','2025-07-08 05:42:28','SYSTEM','2025-07-08 05:42:28'),('056aeb6d-bbd2-49be-b370-c512e76178bb',_binary '',NULL,'$2a$10$2uo9Zn8NUr7r1pV1PgU62OQ0GEdxIGFgY7PC4RX9avXzscGo1/xw.','gyellowlo','SYSTEM','2025-07-08 04:12:59','SYSTEM','2025-07-08 04:12:59'),('0832550f-48fe-4e69-9edf-51cf24959db7',_binary '',NULL,'$2a$10$0/703gYFcMhs.3twtTccSuDrXeZtEjwFjvXYau9nHtGtVONu5lgXm','agarciape','SYSTEM','2025-07-08 05:42:03','SYSTEM','2025-07-08 05:42:03'),('0d04bd63-e265-4cf4-b4f0-8c7c4bc2677d',_binary '',NULL,'$2a$10$iTiItPQuACqpfBqCSDMaCeFP.cyZYhqWf3bZqq5UcBtNEa16F/Zmm','hredgo','SYSTEM','2025-07-08 04:13:16','SYSTEM','2025-07-08 04:13:16'),('1347d8e8-6aef-49ff-ad5b-3f4cba7d3507',_binary '',NULL,'$2a$10$DwxGHSPNG9f8ODcScCJLleo35YZXFmSV96HLqJu5Qn/fxKomf.4wy','ipurplewi','SYSTEM','2025-07-08 04:13:35','SYSTEM','2025-07-08 04:13:35'),('160a8274-7ea6-4acf-92ec-0147e030181b',_binary '',NULL,'$2a$10$pjVNhzac6HOCI9BRK9jfV.KOfhXQErrgH9P1z3KRLDkRoOz48lsze','jgarciape','SYSTEM','2025-07-08 05:41:48','SYSTEM','2025-07-08 05:41:48'),('16fd9c7e-7dc1-4ec1-a192-5fc56803416b',_binary '',NULL,'$2a$10$m.9S4zlRcleMH7Gp52R/9uA.AsgiVUFTTbTJREBRh6FvXbzJexa7m','jmamanico','SYSTEM','2025-07-08 05:43:08','SYSTEM','2025-07-08 05:43:08'),('1dd0e8f8-f9a2-4c10-adcd-add3c183cecb',_binary '',NULL,'$2a$10$noFoITsiaSTou0qaPz/Hbu5xWZm82LKGaH4cY1qYzU3..8V8BcJSK','dcoronelju','SYSTEM','2025-07-08 04:40:43','SYSTEM','2025-07-08 04:40:43'),('2a495f2c-05f0-4b37-8bc8-7260b6150898',_binary '',NULL,'$2a$10$06GJPLZzw5tcuUtMmiHqK.eWy7dBjESFQn9dVigC6lxz.egil3IR.','jdoesm','SYSTEM','2025-07-08 03:56:31','SYSTEM','2025-07-08 03:56:31'),('3e2cc715-b306-4557-b237-78de37460bad',_binary '',NULL,'$2a$10$HpTIijLz5zyXxGEpnRQkCefAHZ.79Lrmi/.bxN0lTWIpkHIqx6T8m','amamanico','SYSTEM','2025-07-08 05:41:25','SYSTEM','2025-07-08 05:41:25'),('410b9a61-579d-41ae-9f90-0f12c1d02979',_binary '',NULL,'$2a$10$HDzo8N4AfbyJNMQ3KpEe2.haq6Ydx8.TrCknEOdCTyiw.YgbfjNeO','rparedesfa','SYSTEM','2025-07-08 04:41:14','SYSTEM','2025-07-08 04:41:14'),('4839d360-98f0-40f9-839c-42a0aa96efc3',_binary '',NULL,'$2a$10$b6MzB3oQhtvLJCjwRdaDzOdt.u0bjPOfOnDW1Sjy8r6RhmJhcPVtC','csalvatierrato','SYSTEM','2025-07-08 04:40:18','SYSTEM','2025-07-08 04:40:18'),('507cb0d7-7fa2-4cd9-80a2-07327a7ab318',_binary '',NULL,'$2a$10$nLuZ.whD2lLPLGloFvotmOKFrDm3w3hjiKrNhmtZhyheeDxECyMSW','bbrownmi','SYSTEM','2025-07-08 04:11:21','SYSTEM','2025-07-08 04:11:21'),('5d778724-3c1f-499d-bda5-407087751381',_binary '',NULL,'$2a$10$gHhkosL0ZXSLVseuKDYyI.bx8QKhytGZTfaxDD5lZSWVWq.ch1gES','smanriquesa','SYSTEM','2025-07-08 04:41:31','SYSTEM','2025-07-08 04:41:31'),('5e0ff8d7-ca63-4056-9db0-278ff88d4ea7',_binary '',NULL,'$2a$10$Gg/2y.99p8Hi7DtZGhjgVuocaKnmRVuMAdX.upBoB3eYmhbpl3qTS','amamanidi','SYSTEM','2025-07-08 04:26:35','SYSTEM','2025-07-08 04:26:35'),('75200892-ef6b-4dfd-8415-8e90b173cd00',_binary '',NULL,'$2a$10$DZemsQIB0XGnaKR4bBM4geiIzPoKLDkoId3adkKs4mT5j9.1SW2wK','fcoricu','SYSTEM','2025-07-08 03:55:55','SYSTEM','2025-07-08 03:55:55'),('75a0b660-6fd3-47ee-9356-0773466359fe',_binary '',NULL,'$2a$10$l39h/Z6ZryvVPYUOwx2xIezrfqPuVLbBChiYoXPHG5gZXfRNk48ay','jdoesm2','SYSTEM','2025-07-08 04:10:19','SYSTEM','2025-07-08 04:10:19'),('824cd896-eda2-42c4-a4bc-b80f5b31c4dd',_binary '',NULL,'$2a$10$StpJn57/ZRc5uC.fP6XTTe.za0crIRnHNWzUZQRDTTdAIubro558e','agreenjo','SYSTEM','2025-07-08 04:10:55','SYSTEM','2025-07-08 04:10:55'),('8440a8af-818d-4779-9a57-c1c4e41d85c7',_binary '',NULL,'$2a$10$9.fD333Q5RhyN1YSIlduwOdbmx0CKXcjhdlbxN5sXENVsP980o3Lu','iperaltaca','SYSTEM','2025-07-08 04:40:59','SYSTEM','2025-07-08 04:40:59'),('85a3388d-83b9-4b58-91b3-e421afadb022',_binary '',NULL,'$2a$10$FtIAHLUZCeefyZ3Qi0sA6.3dCeK00DXybrvmaHSP9oY5fIDpbjNb.','cwhiteda','SYSTEM','2025-07-08 04:11:38','SYSTEM','2025-07-08 04:11:38'),('9403472b-7aec-4a38-96b4-25cd5aabc936',_binary '',NULL,'$2a$10$W5Am2WKx.XJNx9KrxF17hukDp7uCJqLMJu4o19Y4i.VYXa.BCa8tC','aherrerame','SYSTEM','2025-07-08 04:28:21','SYSTEM','2025-07-08 04:28:21'),('9792ae2c-44d5-458f-834a-ce70a2a72111',_binary '',NULL,'$2a$10$bLDY0tPA3Jq2n3bebK5D4.UMtui98bhdzBsMom90ySRGEF8dcAEp2','jherrerapa','SYSTEM','2025-07-08 05:43:33','SYSTEM','2025-07-08 05:43:33'),('9acc7245-7d49-4551-ad1c-2ed69aca4753',_binary '',NULL,'$2a$10$lCRXWfJhWc3sLqiL69eT4eahCyKgXRadtrsliR721lFen4aePPdfu','jdoesm1','SYSTEM','2025-07-08 04:09:24','SYSTEM','2025-07-08 04:09:24'),('9cb8d6f1-d89f-4f92-800f-eff083176699',_binary '',NULL,'$2a$10$/bDusdnNLRrD5IH0RP.kselyQcAWjEnkWxeu6Yv7WDqKZuyCRU4dG','agarciape1','SYSTEM','2025-07-08 05:42:43','SYSTEM','2025-07-08 05:42:43'),('9e3d6fee-f3ae-4b3d-b674-08c00cc3e892',_binary '',NULL,'$2a$10$DUs.VTazPyxLgmbFEVgcMOL423wLinwdEijaLLL0QiNfSJaUO/jxy','falanyado','SYSTEM','2025-07-08 04:27:52','SYSTEM','2025-07-08 04:27:52'),('a3bcb52d-c805-48d5-b281-57689f233660',_binary '',NULL,'$2a$10$rWVSrsXQwDDWJZaUoiRgb.lvm7xf0QVDVsysJXJkrckwYKYCVaC.S','dblackga','SYSTEM','2025-07-08 04:11:58','SYSTEM','2025-07-08 04:11:58'),('a66c0d2c-8a41-4095-af4e-db4c15401499',_binary '',NULL,'$2a$10$BAm2/gUIX/4LJDp2Q3xR3OkKcbX6eTwlpyKG3AaekI.rb1C.myntG','nalanyama','SYSTEM','2025-07-08 05:43:44','SYSTEM','2025-07-08 05:43:44'),('b48cc4df-2255-4b1b-82a1-ba9fa5e8d8d2',_binary '',NULL,'$2a$10$CODEoMhv.Ik/d1voL8W5VOmlkbCMcW9PQ3hY9CeY3ruByNv1N9XuS','fbluehe','SYSTEM','2025-07-08 04:12:40','SYSTEM','2025-07-08 04:12:40'),('c26ea1a7-0ec6-4d00-8ec4-5516a98a3526',_binary '',NULL,'$2a$10$eQkBQclvY8wNu.Hzx.zT2.vV2P2HUnZ5uwNna36vlzlE0H8xdDFAS','egrayma','SYSTEM','2025-07-08 04:12:16','SYSTEM','2025-07-08 04:12:16'),('d3834775-2e7c-4213-a6e3-64a47b41c5a2',_binary '',NULL,'$2a$10$f4IOzrr0nHT2p8QUr81nZO23U7.q86pSmH1UT/8lIbr.JvOepBdS.','lbarbozara','SYSTEM','2025-07-08 03:41:20','SYSTEM','2025-07-08 03:41:20'),('e21bc4c1-79b9-4797-9174-1338cf36dd4b',_binary '',NULL,'$2a$10$tf.FT7C3Txrcve3kjJUzV.7YczD6Qy9gFQoKhEW3sus.BfSTodu16','agarciaap','SYSTEM','2025-07-08 04:28:07','SYSTEM','2025-07-08 04:28:07'),('e5ac1ca7-1765-4314-8a90-e22e3dadbd8f',_binary '',NULL,'$2a$10$RjpRmqi1u4B4wtceBKla6.n69.qnhOwXoA7J4cZCQao25alZkfdja','lramirezsa','SYSTEM','2025-07-08 05:42:14','SYSTEM','2025-07-08 05:42:14'),('ed15dce6-c8dd-4d39-b213-a068c4884d06',_binary '',NULL,'$2a$10$aErGVJNihrukFLcGAtRtGeW35/WEBS/5QRTiRU3frI6QfjZQO3JJu','jorangean','SYSTEM','2025-07-08 04:13:53','SYSTEM','2025-07-08 04:13:53'),('f1f85c35-fa3b-4829-9e34-bf2dc722bd27',_binary '',NULL,'$2a$10$MyvBwc4MToQDdn6GpDBl5.QZhDK.ed6yyPZIArZGeTjG3Lkcj3.Wi','aramirezch','SYSTEM','2025-07-08 04:28:36','SYSTEM','2025-07-08 04:28:36');
/*!40000 ALTER TABLE `access` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-08  5:51:46
