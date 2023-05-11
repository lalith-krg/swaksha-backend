-- MySQL dump 10.13  Distrib 8.0.18, for macos10.14 (x86_64)
--
-- Host: localhost    Database: swaksha
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
-- Table structure for table `api_keys`
--

DROP TABLE IF EXISTS `api_keys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `api_keys` (
  `ssid` varchar(255) NOT NULL,
  `api_key` varchar(255) NOT NULL,
  PRIMARY KEY (`api_key`,`ssid`),
  UNIQUE KEY `UK_246uaqox6u47n1xnimj348yq5` (`ssid`),
  UNIQUE KEY `UK_sgdkngj9u2lb7u4ar5guwnpx2` (`api_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_keys`
--

LOCK TABLES `api_keys` WRITE;
/*!40000 ALTER TABLE `api_keys` DISABLE KEYS */;
/*!40000 ALTER TABLE `api_keys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consent`
--

DROP TABLE IF EXISTS `consent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consent` (
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
-- Dumping data for table `consent`
--

LOCK TABLES `consent` WRITE;
/*!40000 ALTER TABLE `consent` DISABLE KEYS */;
/*!40000 ALTER TABLE `consent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor_credentials`
--

DROP TABLE IF EXISTS `doctor_credentials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor_credentials` (
  `ssid` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`ssid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor_credentials`
--

LOCK TABLES `doctor_credentials` WRITE;
/*!40000 ALTER TABLE `doctor_credentials` DISABLE KEYS */;
INSERT INTO `doctor_credentials` VALUES ('234338969157','$2a$10$OmzqaorgwBBtXqEoYXXHU.0jhQJs29JxvQoqzwIeTmsF8KbAxiBrC'),('282461903071','$2a$10$eXYRRYLNU3/vUsw5LJAHb.KROjkEcsABv1va3ACfs8rHyjHhrfvry'),('471773985418','$2a$10$zLZ.VfW8QN7pieOC4XsfjeiEcwvsP.jSmIsFweCy9prRTV.zkH.C6'),('530394400016','$2a$10$AioG6SDHDkwe0oue8SgRYOeXEAItx3KuKNTwU7o/ikjArz0TQjs5u'),('669311467711','$2a$10$965hLbRV5YvmhAhPO3Jmwe0OEiyaIMwvLcvet05Tzy5OZcP/66bhS'),('796577613824','$2a$10$XN/adPKwp3XSrco3GQXuwukSXaq5OTXyicJZBycUPByXaAlECF1q6'),('937512048865','$2a$10$pkMYx13TmR.y.0wxYfm17eNThRDlg9ZbK/kaBEr9d9kSXFw34eLpe'),('990471985726','$2a$10$a7lC5idV9ddEAiGP1ISOMe2eUssqwNe4UgvcYWgzguPsCs8pd5D4e');
/*!40000 ALTER TABLE `doctor_credentials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor_details`
--

DROP TABLE IF EXISTS `doctor_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor_details` (
  `ssid` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) NOT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) NOT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ssid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor_details`
--

LOCK TABLES `doctor_details` WRITE;
/*!40000 ALTER TABLE `doctor_details` DISABLE KEYS */;
INSERT INTO `doctor_details` VALUES ('234338969157','abcefg','blr',NULL,'ayush@gmail.com','yashomati','male','tiwari','9327232276','karnataka'),('282461903071','abcefg','blr',NULL,'ayush@gmail.com','yashomati','male','tiwari','9327232276','karnataka'),('471773985418','abcefg','blr',NULL,'ayush@gmail.com','yashomati','male','tiwari','9327232276','karnataka'),('530394400016','abcefg','blr',NULL,'ayush@gmail.com','yashomati','male','tiwari','8830753227','karnataka'),('669311467711','abcefg','blr',NULL,'ayush@gmail.com','yashomati','male','tiwari','9327232276','karnataka'),('796577613824','abcefg','blr',NULL,'ayush@gmail.com','yashomati','male','tiwari','9327232276','karnataka'),('937512048865','abcefg','blr',NULL,'ayush@gmail.com','yashomati','male','tiwari','9327232276','karnataka'),('987654321','IIITB','blr',NULL,'shrey@gmail.com','shrey','male','tripathi','9327232276','karnataka'),('990471985726','abcefg','blr',NULL,'ayush@gmail.com','yashomati','male','tiwari','9327232276','karnataka');
/*!40000 ALTER TABLE `doctor_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ehr`
--

DROP TABLE IF EXISTS `ehr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ehr` (
  `ehr_id` varchar(255) NOT NULL,
  PRIMARY KEY (`ehr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ehr`
--

LOCK TABLES `ehr` WRITE;
/*!40000 ALTER TABLE `ehr` DISABLE KEYS */;
/*!40000 ALTER TABLE `ehr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hiu_url`
--

DROP TABLE IF EXISTS `hiu_url`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hiu_url` (
  `hospital_ssid` varchar(255) NOT NULL,
  `hospital_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`hospital_ssid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hiu_url`
--

LOCK TABLES `hiu_url` WRITE;
/*!40000 ALTER TABLE `hiu_url` DISABLE KEYS */;
INSERT INTO `hiu_url` VALUES ('1234','http://localhost:9009');
/*!40000 ALTER TABLE `hiu_url` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hospital`
--

DROP TABLE IF EXISTS `hospital`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hospital` (
  `ssid` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `hospital_name` varchar(255) NOT NULL,
  `phone_num1` varchar(255) NOT NULL,
  PRIMARY KEY (`ssid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hospital`
--

LOCK TABLES `hospital` WRITE;
/*!40000 ALTER TABLE `hospital` DISABLE KEYS */;
INSERT INTO `hospital` VALUES ('123456789','house address','vegas@ho.co.in','Gyaan Baato Hospitals','9999999999');
/*!40000 ALTER TABLE `hospital` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hospital_credentials`
--

DROP TABLE IF EXISTS `hospital_credentials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hospital_credentials` (
  `ssid` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`ssid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hospital_credentials`
--

LOCK TABLES `hospital_credentials` WRITE;
/*!40000 ALTER TABLE `hospital_credentials` DISABLE KEYS */;
/*!40000 ALTER TABLE `hospital_credentials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hospital_details`
--

DROP TABLE IF EXISTS `hospital_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hospital_details` (
  `ssid` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `hospital_name` varchar(255) NOT NULL,
  `phone_num1` varchar(255) NOT NULL,
  `phone_num2` varchar(255) NOT NULL,
  `state` varchar(255) DEFAULT NULL,
  `data_post_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ssid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hospital_details`
--

LOCK TABLES `hospital_details` WRITE;
/*!40000 ALTER TABLE `hospital_details` DISABLE KEYS */;
INSERT INTO `hospital_details` VALUES ('1234','new','blr','gh@jkjk.co','hip','989898','98977','ka','http://localhost:9009/hospital/requests/getRequestedData'),('123456789','house','vegas','vegas@ho.co.in','Gyaan Baato Hospitals','9999999999','8988888888','gujju','http://localhost:9002/hospital/requests/getRequestedData');
/*!40000 ALTER TABLE `hospital_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hospital_details_seq`
--

DROP TABLE IF EXISTS `hospital_details_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hospital_details_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hospital_details_seq`
--

LOCK TABLES `hospital_details_seq` WRITE;
/*!40000 ALTER TABLE `hospital_details_seq` DISABLE KEYS */;
INSERT INTO `hospital_details_seq` VALUES (1);
/*!40000 ALTER TABLE `hospital_details_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hospital_seq`
--

DROP TABLE IF EXISTS `hospital_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hospital_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hospital_seq`
--

LOCK TABLES `hospital_seq` WRITE;
/*!40000 ALTER TABLE `hospital_seq` DISABLE KEYS */;
INSERT INTO `hospital_seq` VALUES (1);
/*!40000 ALTER TABLE `hospital_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `ssid` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `age` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) NOT NULL,
  `phone_num` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`ssid`),
  UNIQUE KEY `UK_avbwwnxo3348e62y2reiv3fko` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES ('1','my house',21,'usgd','hehe myname','9327232276','nayapatient');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_credentials`
--

DROP TABLE IF EXISTS `patient_credentials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient_credentials` (
  `ssid` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ssid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_credentials`
--

LOCK TABLES `patient_credentials` WRITE;
/*!40000 ALTER TABLE `patient_credentials` DISABLE KEYS */;
INSERT INTO `patient_credentials` VALUES ('225658878566','$2a$10$6oNUKYgLRzVrq/.vYNwwlu1Wb5SPd.aoIG.MmiVEi70gZ8tuHAu6u',NULL),('622323235180','$2a$10$ZaperFNRe7UvyNsMg7HzXetPG3t5Gi83EHyevDrg/11OrFEUkUjPy',NULL),('652152210913','$2a$10$KN9fosIZSzv3VLc6GvHctO0ExZtswkqV3hkIiNkicmh.rE0RIHMWm',NULL),('703347509821','$2a$10$hJo4RF1rbc5c26SVNE9Qhu5.1VsdenVIblGdvrSD6ncawDMoXpCj2',NULL),('800993161242','$2a$10$dPqqhkhBSGmLqmilLLmLJujboPuIc34ZPmxY6cAowbryjioyIw2IG','USER');
/*!40000 ALTER TABLE `patient_credentials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_details`
--

DROP TABLE IF EXISTS `patient_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient_details` (
  `ssid` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `cm_pin_password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ssid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_details`
--

LOCK TABLES `patient_details` WRITE;
/*!40000 ALTER TABLE `patient_details` DISABLE KEYS */;
INSERT INTO `patient_details` VALUES ('1','my house','blr','3333','usgd','hehemyname','m','jgykg','9327232276','gujju'),('622323235180','hdgiosuhishbfghdisughusdfhoiuh','psdgiufviuedhsgiuhu',NULL,'shrey.tripathi@iiitb.ac.in','dwgjgj','Prefer Not to say','ugsudfgg','9327232276','pidfhgiuedfkljhsljd'),('652152210913','iiitb','blr',NULL,'pratik@gmail.com','pratik','Female','ahirrao','8830753227','ka'),('703347509821','bali','baali',NULL,'first@last.example.com','first','Male','last','9327232276','UP'),('800993161242','IIITB','BLR',NULL,'shrey.tripathi@iiitb.ac.in','Shrey','Male','Tripathi','9327232276','Karnataka');
/*!40000 ALTER TABLE `patient_details` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-11  0:42:58
