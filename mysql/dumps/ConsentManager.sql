-- MySQL dump 10.13  Distrib 8.0.18, for macos10.14 (x86_64)
--
-- Host: localhost    Database: ConsentManager
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `consents`
--

DROP TABLE IF EXISTS `consents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consents` (
  `consentid` varchar(255) NOT NULL,
  `consent_approved_date` date DEFAULT NULL,
  `consent_end_date` date DEFAULT NULL,
  `data_access_end_date` date DEFAULT NULL,
  `data_access_start_date` date DEFAULT NULL,
  `doctorssid` varchar(255) DEFAULT NULL,
  `hipssid` varchar(255) DEFAULT NULL,
  `hiussid` varchar(255) DEFAULT NULL,
  `is_approved` bit(1) DEFAULT NULL,
  `patientssid` varchar(255) DEFAULT NULL,
  `request_initiated_date` date DEFAULT NULL,
  `self_consent` bit(1) DEFAULT NULL,
  PRIMARY KEY (`consentid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consents`
--

LOCK TABLES `consents` WRITE;
/*!40000 ALTER TABLE `consents` DISABLE KEYS */;
INSERT INTO `consents` VALUES ('187605f2',NULL,NULL,NULL,NULL,'987654321','1234','123456789',_binary '','800993161242',NULL,_binary '\0'),('381de73e',NULL,NULL,NULL,NULL,'987654321','1234','123456789',_binary '','800993161242',NULL,_binary '\0'),('57706768',NULL,NULL,NULL,NULL,NULL,NULL,NULL,_binary '',NULL,NULL,_binary '\0'),('828228f3',NULL,NULL,NULL,NULL,'987654321','1234','123456789',_binary '','800993161242',NULL,_binary '\0'),('9ec82f30',NULL,NULL,NULL,NULL,'987654321','','123456789',_binary '','800993161242',NULL,_binary '\0'),('c33b2d12',NULL,NULL,NULL,NULL,'987654321','','123456789',_binary '','800993161242',NULL,_binary '\0');
/*!40000 ALTER TABLE `consents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_auth`
--

DROP TABLE IF EXISTS `patient_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient_auth` (
  `ssid` varchar(255) NOT NULL,
  `auth_pin` varchar(255) DEFAULT NULL,
  `phone_num` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ssid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_auth`
--

LOCK TABLES `patient_auth` WRITE;
/*!40000 ALTER TABLE `patient_auth` DISABLE KEYS */;
INSERT INTO `patient_auth` VALUES ('1','6912','42069'),('800993161242','1234','9327232276');
/*!40000 ALTER TABLE `patient_auth` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-11  0:44:27
