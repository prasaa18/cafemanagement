-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: cafemanagement
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill` (
  `id` int NOT NULL AUTO_INCREMENT,
  `contactnumber` varchar(255) DEFAULT NULL,
  `createdby` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `paymentmethod` varchar(255) DEFAULT NULL,
  `productdetails` json DEFAULT NULL,
  `total` int DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (1,NULL,'prasadsnaik18@gmail.com','1234567890','test','Cash','[{\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}, {\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}]',279,'Bill1695447108412'),(2,NULL,'prasadsnaik18@gmail.com','1234567890','test','Cash','[{\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}, {\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}]',279,'Bill1695447967185'),(3,NULL,'prasadsnaik18@gmail.com','1234567890','test','Cash','[{\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}, {\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}]',279,'Bill1695450366998'),(4,NULL,'prasadsnaik18@gmail.com','1234567890','test','Cash','[{\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}, {\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}]',279,'Bill1695451170995'),(5,'1234567890','prasadsnaik18@gmail.com','test@gmail.com','test','Cash','[{\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}, {\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}]',279,'Bill1695451289722'),(6,'1234567890','prasadsnaik18@gmail.com','test@gmail.com','test','Cash','[{\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffeeeeeee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}, {\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}]',279,'Bill1695454135179'),(8,'1234567890','prasadsnaik18@gmail.com','test@gmail.com','test','Cash','[{\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffeeeeeee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}, {\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}]',279,'Bill1695454206163'),(9,'1234567890','prasadsnaik18@gmail.com','test@gmail.com','test','Cash','[{\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffeeeeeee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}, {\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}]',279,'Bill1695454261126'),(10,'1234567890','prasadsnaik18@gmail.com','test@gmail.com','test','Cash','[{\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffeeeeeee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}, {\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}]',279,'Bill1695454617842'),(11,'1234567890','techprasad96@gmail.com','test@gmail.com','test','Cash','[{\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffeeeeeee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}, {\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}]',279,'Bill1698675507217'),(12,'1234567890','techprasad96@gmail.com','test@gmail.com','test','Cash','[{\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffeeeeeee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}, {\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}]',279,'Bill1698675538728'),(13,'1234567890','techprasad96@gmail.com','test@gmail.com','test','Cash','[{\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffeeeeeee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}, {\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}]',279,'Bill1698675559593'),(14,'1234567890','techprasad96@gmail.com','test@gmail.com','test','Cash','[{\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffeeeeeee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}, {\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}]',279,'Bill1699007788900'),(15,'08979879999','techprasad96@gmail.com','prasaaik18@gmail.com','w','Cash','[{\"id\": 7, \"name\": \"pepsi\", \"price\": 89, \"total\": 89, \"category\": \"hotdrink\", \"quantity\": \"1\"}]',89,'Bill1699028741726'),(16,'8979879999','techprasad96@gmail.com','prasaaik18@gmail.com','nnn','Credit Card','[{\"id\": 7, \"name\": \"pepsi\", \"price\": 89, \"total\": 89, \"category\": \"hotdrink\", \"quantity\": \"1\"}]',89,'Bill1699028992111'),(17,'1234567890','techprasad96@gmail.com','test@gmail.com','test','Cash','[{\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffeeeeeee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}, {\"id\": 18, \"name\": \"Doppio Coffee\", \"price\": 120, \"total\": 120, \"category\": \"Coffee\", \"quantity\": \"1\"}, {\"id\": 5, \"name\": \"Chocolate Frosted Doughnut\", \"price\": 159, \"total\": 159, \"category\": \"Doughnut\", \"quantity\": \"1\"}]',279,'Bill1699029028800');
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'piza'),(2,'dosa'),(3,'hotdrink'),(4,'Burger'),(5,'nmm'),(6,'drinks');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `category_fk` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK275nu1ncohhfur6qhxiwrm3go` (`category_fk`),
  CONSTRAINT `FK275nu1ncohhfur6qhxiwrm3go` FOREIGN KEY (`category_fk`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (4,'goood','dosa6',45,'true',2),(5,'goood','rava Dosa',45,'true',2),(7,'nmm','pepsi',89,'true',3),(8,'nice','Chiken Burger',700,'true',4),(9,'gud','nmkl',87,'false',5);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `contact_number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'8789990','prasadsnaik18@gmail.com','prasad','admin','admin','true'),(2,'7289939','techprasad96@gmail.com','prasadnaik','admin','admin','true'),(3,'903030','kil','lik@gmail.com','873','user','true'),(4,'9867678','kil#gmail,com','jdjdk','jak','user','false'),(5,'9867678','ki@gmail,com','jdjdk','jak','user','false'),(6,'8979879999','prasaaik18@gmail.com','nnn','nbn','user','false'),(7,'0897987999','prasaai@gmail.com','nnn','mm','user','false'),(8,'8979879999','pra18@gmail.com','nmkl','nn','user','false'),(9,'8788831446','n@gmail.com','Prasad Naik','Xj76@xHE2hcWyT3','user','false');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-16 18:58:47
