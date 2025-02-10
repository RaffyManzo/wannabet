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
  `id_account` int NOT NULL AUTO_INCREMENT,
  `codice_fiscale` varchar(45) NOT NULL,
  `tipo` varchar(255) NOT NULL,
  `id_conto` int NOT NULL,
  `id_fedelta` int NOT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `cognome` varchar(45) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `data_nascita` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id_account`,`codice_fiscale`),
  UNIQUE KEY `unq_account_registrato_idaccount` (`id_account`),
  KEY `idConto` (`id_conto`),
  KEY `idFedelta` (`id_fedelta`),
  CONSTRAINT `fk_account_registrato` FOREIGN KEY (`id_fedelta`) REFERENCES `saldo_fedelta` (`id_saldo_fedelta`),
  CONSTRAINT `fk_account_registrato_conto` FOREIGN KEY (`id_conto`) REFERENCES `conto` (`id_conto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_registrato`
--

LOCK TABLES `account_registrato` WRITE;
/*!40000 ALTER TABLE `account_registrato` DISABLE KEYS */;
/*!40000 ALTER TABLE `account_registrato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conto`
--

DROP TABLE IF EXISTS `conto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conto` (
  `id_conto` int NOT NULL AUTO_INCREMENT,
  `data_creazione` datetime(6) NOT NULL,
  `saldo` double DEFAULT (_utf8mb4'0'),
  `indirizzo_fatturazione` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_conto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conto`
--

LOCK TABLES `conto` WRITE;
/*!40000 ALTER TABLE `conto` DISABLE KEYS */;
/*!40000 ALTER TABLE `conto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evento`
--

DROP TABLE IF EXISTS `evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evento` (
  `id_evento` int NOT NULL AUTO_INCREMENT,
  `data_evento` datetime NOT NULL,
  `descrizione` varchar(100) DEFAULT NULL,
  `categoria` varchar(45) DEFAULT NULL,
  `chiuso` tinyint(1) NOT NULL DEFAULT (false),
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`id_evento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evento`
--

LOCK TABLES `evento` WRITE;
/*!40000 ALTER TABLE `evento` DISABLE KEYS */;
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
-- Table structure for table `quota`
--

DROP TABLE IF EXISTS `quota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quota` (
  `id_quota` int NOT NULL AUTO_INCREMENT,
  `moltiplicatore` double NOT NULL,
  `esito` varchar(100) NOT NULL,
  `id_evento` int NOT NULL,
  `stato` varchar(255) NOT NULL,
  `categoria` varchar(100) NOT NULL,
  `referto` varchar(45) DEFAULT NULL,
  `chiusa` tinyint(1) DEFAULT (false),
  PRIMARY KEY (`id_quota`),
  KEY `fk_quota_evento1_idx` (`id_evento`),
  CONSTRAINT `fk_quota_evento` FOREIGN KEY (`id_evento`) REFERENCES `evento` (`id_evento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quota`
--

LOCK TABLES `quota` WRITE;
/*!40000 ALTER TABLE `quota` DISABLE KEYS */;
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
  `id_scommessa` int NOT NULL,
  `id_quota` int NOT NULL,
  `moltiplicatore` double DEFAULT NULL COMMENT 'Questo valore e un valore congelato del moltiplicatore della quota giocata, non soggetto ai cambiamenti della quota',
  PRIMARY KEY (`id_link`),
  KEY `fk_link_quota_scommessa_scommessa1_idx` (`id_scommessa`),
  KEY `fk_link_quota_scommessa_quota1_idx` (`id_quota`),
  CONSTRAINT `fk_link_quota_scommessa_quota1` FOREIGN KEY (`id_quota`) REFERENCES `quota` (`id_quota`),
  CONSTRAINT `fk_quota_giocata_scommessa` FOREIGN KEY (`id_scommessa`) REFERENCES `scommessa` (`id_scommessa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quota_giocata`
--

LOCK TABLES `quota_giocata` WRITE;
/*!40000 ALTER TABLE `quota_giocata` DISABLE KEYS */;
/*!40000 ALTER TABLE `quota_giocata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saldo_fedelta`
--

DROP TABLE IF EXISTS `saldo_fedelta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saldo_fedelta` (
  `id_saldo_fedelta` int NOT NULL AUTO_INCREMENT,
  `punti` int(10) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`id_saldo_fedelta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saldo_fedelta`
--

LOCK TABLES `saldo_fedelta` WRITE;
/*!40000 ALTER TABLE `saldo_fedelta` DISABLE KEYS */;
/*!40000 ALTER TABLE `saldo_fedelta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scommessa`
--

DROP TABLE IF EXISTS `scommessa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scommessa` (
  `id_scommessa` int NOT NULL AUTO_INCREMENT,
  `vincita` double NOT NULL,
  `data_scommessa` datetime(6) NOT NULL,
  `importo` double NOT NULL,
  `stato` varchar(255) NOT NULL,
  `id_account` int NOT NULL,
  PRIMARY KEY (`id_scommessa`),
  KEY `fk_scommessa_aaccount` (`id_account`),
  CONSTRAINT `fk_scommessa_aaccount` FOREIGN KEY (`id_account`) REFERENCES `account_registrato` (`id_account`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scommessa`
--

LOCK TABLES `scommessa` WRITE;
/*!40000 ALTER TABLE `scommessa` DISABLE KEYS */;
INSERT INTO `scommessa` VALUES (1,300,'2025-02-10 12:21:08.797000',100,'Da refertare',9),(2,300,'2025-02-10 12:31:02.154000',100,'Da refertare',9);
/*!40000 ALTER TABLE `scommessa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storico_riscossioni`
--

DROP TABLE IF EXISTS `storico_riscossioni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storico_riscossioni` (
  `id_storico_riscossioni` int NOT NULL,
  `data_riscossione` datetime(6) NOT NULL,
  `id_saldo_fedelta` int NOT NULL,
  `id_premio` bigint NOT NULL,
  PRIMARY KEY (`id_storico_riscossioni`),
  KEY `FK4y8s9sxc9dkgbpvyf9g21k2p0` (`id_premio`),
  KEY `fk_storico_riscossioni` (`id_saldo_fedelta`),
  CONSTRAINT `FK4y8s9sxc9dkgbpvyf9g21k2p0` FOREIGN KEY (`id_premio`) REFERENCES `premi_fedelta` (`id_premio`),
  CONSTRAINT `fk_storico_riscossioni` FOREIGN KEY (`id_saldo_fedelta`) REFERENCES `saldo_fedelta` (`id_saldo_fedelta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storico_riscossioni`
--

LOCK TABLES `storico_riscossioni` WRITE;
/*!40000 ALTER TABLE `storico_riscossioni` DISABLE KEYS */;
/*!40000 ALTER TABLE `storico_riscossioni` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storico_transazioni`
--

DROP TABLE IF EXISTS `storico_transazioni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storico_transazioni` (
  `id_transazione` int NOT NULL,
  `importo` double DEFAULT NULL,
  `data_transazione` datetime DEFAULT NULL,
  `idconto` int DEFAULT NULL,
  PRIMARY KEY (`id_transazione`),
  KEY `fk_storico_transazioni_conto` (`idconto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storico_transazioni`
--

LOCK TABLES `storico_transazioni` WRITE;
/*!40000 ALTER TABLE `storico_transazioni` DISABLE KEYS */;
/*!40000 ALTER TABLE `storico_transazioni` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-10 13:07:35
