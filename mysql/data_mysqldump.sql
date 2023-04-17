-- MySQL dump 10.13  Distrib 8.0.18, for macos10.14 (x86_64)
--
-- Host: localhost    Database: swaksha_db
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
-- Dumping data for table `Doctor_Details`
--

LOCK TABLES `Doctor_Details` WRITE;
/*!40000 ALTER TABLE `Doctor_Details` DISABLE KEYS */;
INSERT INTO `Doctor_Details` VALUES ('987654321987','Ashish','Nehra','987654321','1990-08-21 00:00:00','M','ashish@gmail.com','Bhopal','Madhya Pradesh'),('987654677556','Pratik','Mishra','987654871','1991-09-21 00:00:00','F','nehra@gmail.com','Bhusawal','Maharashtra');
/*!40000 ALTER TABLE `Doctor_Details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Doctor_Hospital_Association`
--

LOCK TABLES `Doctor_Hospital_Association` WRITE;
/*!40000 ALTER TABLE `Doctor_Hospital_Association` DISABLE KEYS */;
INSERT INTO `Doctor_Hospital_Association` VALUES ('987567679876','987654321987'),('987567676767','987654677556');
/*!40000 ALTER TABLE `Doctor_Hospital_Association` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Doctor_Waiting_List`
--

LOCK TABLES `Doctor_Waiting_List` WRITE;
/*!40000 ALTER TABLE `Doctor_Waiting_List` DISABLE KEYS */;
/*!40000 ALTER TABLE `Doctor_Waiting_List` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Hospital_Credentials`
--

LOCK TABLES `Hospital_Credentials` WRITE;
/*!40000 ALTER TABLE `Hospital_Credentials` DISABLE KEYS */;
INSERT INTO `Hospital_Credentials` VALUES ('987567676767','apollopwd'),('987567679876','kumkumpswdd');
/*!40000 ALTER TABLE `Hospital_Credentials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Hospital_Details`
--

LOCK TABLES `Hospital_Details` WRITE;
/*!40000 ALTER TABLE `Hospital_Details` DISABLE KEYS */;
INSERT INTO `Hospital_Details` VALUES ('987567676767','Apollo Hospital','987654321','9898989898','apollo@gmail.com','Near Neeladri Circle, Electronics City','Bangalore','Karnataka'),('987567679876','Kumkum Hospital','987654864','9898776898','kumkum@gmail.com','Near Neeladri Circle, Electronic City','Bangalore','Karnataka');
/*!40000 ALTER TABLE `Hospital_Details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Hospital_Waiting_List`
--

LOCK TABLES `Hospital_Waiting_List` WRITE;
/*!40000 ALTER TABLE `Hospital_Waiting_List` DISABLE KEYS */;
/*!40000 ALTER TABLE `Hospital_Waiting_List` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Patient_Credentials`
--

LOCK TABLES `Patient_Credentials` WRITE;
/*!40000 ALTER TABLE `Patient_Credentials` DISABLE KEYS */;
INSERT INTO `Patient_Credentials` VALUES ('123455432169','hehe69lmao'),('987654321696','hehepwd');
/*!40000 ALTER TABLE `Patient_Credentials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Patient_Details`
--

LOCK TABLES `Patient_Details` WRITE;
/*!40000 ALTER TABLE `Patient_Details` DISABLE KEYS */;
INSERT INTO `Patient_Details` VALUES ('123455432169','Ishaan','Shanware','123457652','2016-06-30 00:00:00','M','goa@email.com','Rich Colony, Pune','Pune','Maharashtra'),('987654321696','Harsh','Gujral','1234567891','2001-04-30 00:00:00','F','harsh@email.com','Chor Bazaar, New Delhi','New Delhi','Delhi');
/*!40000 ALTER TABLE `Patient_Details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Patient_Doctor_Association`
--

LOCK TABLES `Patient_Doctor_Association` WRITE;
/*!40000 ALTER TABLE `Patient_Doctor_Association` DISABLE KEYS */;
INSERT INTO `Patient_Doctor_Association` VALUES ('987654321696','987654321987'),('123455432169','987654677556');
/*!40000 ALTER TABLE `Patient_Doctor_Association` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-30 17:09:00
