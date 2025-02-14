CREATE DATABASE  IF NOT EXISTS `dbis` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `dbis`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: dbis
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `account_registrato`
--

DROP TABLE IF EXISTS `account_registrato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_registrato` (
  `id_account` bigint NOT NULL AUTO_INCREMENT,
  `codice_fiscale` varchar(45) NOT NULL,
  `cognome` varchar(45) DEFAULT NULL,
  `data_nascita` datetime(6) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `tipo` varchar(255) NOT NULL,
  `id_conto` bigint NOT NULL,
  `id_fedelta` bigint NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id_account`),
  UNIQUE KEY `UK3vr2qyy8qq00hdloxljeaeo56` (`codice_fiscale`),
  KEY `FKsmqrxktvkbjsqk5jobvw45cpm` (`id_conto`),
  KEY `FKjsago76xr8f7k34c91dgpkqjy` (`id_fedelta`),
  CONSTRAINT `FKjsago76xr8f7k34c91dgpkqjy` FOREIGN KEY (`id_fedelta`) REFERENCES `saldo_fedelta` (`id_saldo_fedelta`),
  CONSTRAINT `FKsmqrxktvkbjsqk5jobvw45cpm` FOREIGN KEY (`id_conto`) REFERENCES `conto` (`id_conto`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_registrato`
--

LOCK TABLES `account_registrato` WRITE;
/*!40000 ALTER TABLE `account_registrato` DISABLE KEYS */;
INSERT INTO `account_registrato` VALUES (1,'ABCDEF12G34H567I','Rossi','2025-02-14 01:04:22.743000','alibaba@email.com','Mario','UTENTE',1,1,'75K3eLr+dx6JJFuJ7LwIpEpOFmwGZZkRiB84PURz6U8='),(3,'JAKOORGIU868937','Baudo','2025-02-14 01:04:24.397000','test@email.com','Pippo','UTENTE',3,3,'NrvlDtloQdEEQ7y2cNZVTwo0t2G+Z+ycSorSwMRMpCw='),(4,'JAKOORGIU848937','Baudo','2025-02-14 01:04:24.409000','test2@email.com','Pippo','UTENTE',3,3,'NrvlDtloQdEEQ7y2cNZVTwo0t2G+Z+ycSorSwMRMpCw='),(10,'JAKOORTIU848937','Baudo','2025-02-14 01:04:25.061000','testSC@email.com','Pippo','UTENTE',9,9,'NrvlDtloQdEEQ7y2cNZVTwo0t2G+Z+ycSorSwMRMpCw='),(11,'XYZ1234','Baudo','2025-02-14 01:04:25.245000','abcde@email.com','Pippo','UTENTE',10,10,'Y/ka5PNmFAK6Q7CGKfMvi5h64q1AKI0xkVgnlkh5oJE='),(12,'JAKOORTIU848637','Baudo','2025-02-14 01:04:25.827000','testS@email.com','Pippo','UTENTE',11,11,'NrvlDtloQdEEQ7y2cNZVTwo0t2G+Z+ycSorSwMRMpCw=');
/*!40000 ALTER TABLE `account_registrato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conto`
--

DROP TABLE IF EXISTS `conto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conto` (
  `id_conto` bigint NOT NULL AUTO_INCREMENT,
  `data_creazione` datetime(6) NOT NULL,
  `indirizzo_fatturazione` varchar(100) DEFAULT NULL,
  `saldo` double NOT NULL,
  PRIMARY KEY (`id_conto`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conto`
--

LOCK TABLES `conto` WRITE;
/*!40000 ALTER TABLE `conto` DISABLE KEYS */;
INSERT INTO `conto` VALUES (1,'2025-02-14 01:04:22.669000','Via abcde',0),(3,'2025-02-14 01:04:24.389000','Via .',900),(9,'2025-02-14 01:04:25.056000','Via .',1000),(10,'2025-02-14 01:04:25.239000','Via .',1000),(11,'2025-02-14 01:04:25.818000','Via .',1000);
/*!40000 ALTER TABLE `conto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evento`
--

DROP TABLE IF EXISTS `evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evento` (
  `id_evento` bigint NOT NULL AUTO_INCREMENT,
  `data_evento` datetime NOT NULL,
  `descrizione` varchar(100) DEFAULT NULL,
  `categoria` varchar(45) DEFAULT NULL,
  `chiuso` tinyint(1) NOT NULL DEFAULT (false),
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`id_evento`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evento`
--

LOCK TABLES `evento` WRITE;
/*!40000 ALTER TABLE `evento` DISABLE KEYS */;
INSERT INTO `evento` VALUES (1,'2025-02-14 01:04:25','','Tennis',0,'Sinner vs Medvedev'),(11,'2025-02-14 01:04:25','','Tennis',0,'Sinner vs Medvedev'),(12,'2025-02-14 01:04:25','','Tennis',0,'Sinner vs Medvedev'),(13,'2025-02-14 01:04:25','','Tennis',0,'Sinner vs Medvedev'),(17,'2025-02-14 01:04:26','','Tennis',0,'Sinner vs Medvedev');
/*!40000 ALTER TABLE `evento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `premi_fedelta`
--

DROP TABLE IF EXISTS `premi_fedelta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `premi_fedelta` (
  `id_premio` bigint NOT NULL AUTO_INCREMENT,
  `data_creazione` datetime(6) NOT NULL,
  `data_scadenza` datetime(6) NOT NULL,
  `descrizione` varchar(255) DEFAULT NULL,
  `nome` varchar(100) NOT NULL,
  `punti_richiesti` int NOT NULL,
  PRIMARY KEY (`id_premio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `premi_fedelta`
--

LOCK TABLES `premi_fedelta` WRITE;
/*!40000 ALTER TABLE `premi_fedelta` DISABLE KEYS */;
/*!40000 ALTER TABLE `premi_fedelta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prenotazione`
--

DROP TABLE IF EXISTS `prenotazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prenotazione` (
  `id_prenotazione` bigint NOT NULL AUTO_INCREMENT,
  `codice` varchar(5) NOT NULL,
  `id_scommessa` bigint NOT NULL,
  PRIMARY KEY (`id_prenotazione`),
  UNIQUE KEY `codice_unique` (`codice`),
  KEY `fk_prenotazione_scommessa` (`id_scommessa`),
  CONSTRAINT `fk_prenotazione_scommessa` FOREIGN KEY (`id_scommessa`) REFERENCES `scommessa` (`id_scommessa`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prenotazione`
--

LOCK TABLES `prenotazione` WRITE;
/*!40000 ALTER TABLE `prenotazione` DISABLE KEYS */;
INSERT INTO `prenotazione` VALUES (2,'JILIL',5),(3,'B25HE',5);
/*!40000 ALTER TABLE `prenotazione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quota`
--

DROP TABLE IF EXISTS `quota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quota` (
  `id_quota` bigint NOT NULL AUTO_INCREMENT,
  `categoria` varchar(100) NOT NULL,
  `chiusa` bit(1) NOT NULL,
  `esito` varchar(100) NOT NULL,
  `moltiplicatore` double NOT NULL,
  `stato` varchar(255) NOT NULL,
  `id_evento` bigint NOT NULL,
  PRIMARY KEY (`id_quota`),
  KEY `FK6hw5waydlqixci785kiny019i` (`id_evento`),
  CONSTRAINT `FK6hw5waydlqixci785kiny019i` FOREIGN KEY (`id_evento`) REFERENCES `evento` (`id_evento`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quota`
--

LOCK TABLES `quota` WRITE;
/*!40000 ALTER TABLE `quota` DISABLE KEYS */;
INSERT INTO `quota` VALUES (1,'Risultato Finale',_binary '\0','1',2.5,'Da Refertare',11),(3,'Risultato Finale',_binary '\0','1',2.5,'Da Refertare',12),(4,'Risultato Finale',_binary '\0','1',2.5,'Da Refertare',13),(8,'Risultato Finale',_binary '\0','1',2.5,'Da Refertare',17);
/*!40000 ALTER TABLE `quota` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quota_giocata`
--

DROP TABLE IF EXISTS `quota_giocata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quota_giocata` (
  `id_link` bigint NOT NULL AUTO_INCREMENT,
  `moltiplicatore` double NOT NULL,
  `id_quota` bigint NOT NULL,
  `id_scommessa` bigint NOT NULL,
  PRIMARY KEY (`id_link`),
  KEY `FK9o01eecqc0mrs5gsjgrwq42rh` (`id_quota`),
  KEY `FKqd8baagrckqy2hrgjsnns9c8q` (`id_scommessa`),
  CONSTRAINT `FK9o01eecqc0mrs5gsjgrwq42rh` FOREIGN KEY (`id_quota`) REFERENCES `quota` (`id_quota`),
  CONSTRAINT `FKqd8baagrckqy2hrgjsnns9c8q` FOREIGN KEY (`id_scommessa`) REFERENCES `scommessa` (`id_scommessa`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quota_giocata`
--

LOCK TABLES `quota_giocata` WRITE;
/*!40000 ALTER TABLE `quota_giocata` DISABLE KEYS */;
INSERT INTO `quota_giocata` VALUES (1,2.5,3,1),(5,2.5,8,5);
/*!40000 ALTER TABLE `quota_giocata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saldo_fedelta`
--

DROP TABLE IF EXISTS `saldo_fedelta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saldo_fedelta` (
  `id_saldo_fedelta` bigint NOT NULL AUTO_INCREMENT,
  `punti` int NOT NULL,
  PRIMARY KEY (`id_saldo_fedelta`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saldo_fedelta`
--

LOCK TABLES `saldo_fedelta` WRITE;
/*!40000 ALTER TABLE `saldo_fedelta` DISABLE KEYS */;
INSERT INTO `saldo_fedelta` VALUES (1,0),(3,100),(9,100),(10,100),(11,100);
/*!40000 ALTER TABLE `saldo_fedelta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scommessa`
--

DROP TABLE IF EXISTS `scommessa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scommessa` (
  `id_scommessa` bigint NOT NULL AUTO_INCREMENT,
  `data_scommessa` datetime(6) NOT NULL,
  `importo` double NOT NULL,
  `stato` varchar(255) NOT NULL,
  `vincita` double NOT NULL,
  `id_account` bigint NOT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_scommessa`),
  KEY `FK4qd9nnxf0vnffftt5gji1uu9x` (`id_account`),
  CONSTRAINT `FK4qd9nnxf0vnffftt5gji1uu9x` FOREIGN KEY (`id_account`) REFERENCES `account_registrato` (`id_account`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scommessa`
--

LOCK TABLES `scommessa` WRITE;
/*!40000 ALTER TABLE `scommessa` DISABLE KEYS */;
INSERT INTO `scommessa` VALUES (1,'2025-02-14 01:04:25.078000',100,'Da refertare',250,10,'GIOCATA'),(5,'2025-02-14 01:04:25.860000',100,'Da refertare',250,12,'PRENOTATA');
/*!40000 ALTER TABLE `scommessa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storico_riscossioni`
--

DROP TABLE IF EXISTS `storico_riscossioni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storico_riscossioni` (
  `id_riscossione` bigint NOT NULL AUTO_INCREMENT,
  `data_riscossione` datetime(6) NOT NULL,
  `id_premio` bigint NOT NULL,
  `id_saldo_fedelta` bigint NOT NULL,
  PRIMARY KEY (`id_riscossione`),
  KEY `FK4y8s9sxc9dkgbpvyf9g21k2p0` (`id_premio`),
  KEY `FK2s0ee7yi246e9o711dnmbk0rh` (`id_saldo_fedelta`),
  CONSTRAINT `FK2s0ee7yi246e9o711dnmbk0rh` FOREIGN KEY (`id_saldo_fedelta`) REFERENCES `saldo_fedelta` (`id_saldo_fedelta`),
  CONSTRAINT `FK4y8s9sxc9dkgbpvyf9g21k2p0` FOREIGN KEY (`id_premio`) REFERENCES `premi_fedelta` (`id_premio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storico_riscossioni`
--

LOCK TABLES `storico_riscossioni` WRITE;
/*!40000 ALTER TABLE `storico_riscossioni` DISABLE KEYS */;
/*!40000 ALTER TABLE `storico_riscossioni` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-14  1:44:46
