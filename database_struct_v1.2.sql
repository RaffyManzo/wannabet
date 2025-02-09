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
  `idaccount` int NOT NULL AUTO_INCREMENT,
  `codice_fiscale` varchar(45) NOT NULL,
  `tipo` enum('Utente','Admin','Gestore_fedeltá','Gestore_referti','Gestore_evento') NOT NULL,
  `idConto` int NOT NULL,
  `idFedelta` int NOT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `cognome` varchar(45) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `data_nascita` date DEFAULT NULL,
  PRIMARY KEY (`idaccount`,`codice_fiscale`),
  UNIQUE KEY `unq_account_registrato_idaccount` (`idaccount`),
  KEY `idConto` (`idConto`),
  KEY `idFedelta` (`idFedelta`),
  CONSTRAINT `account_registrato_ibfk_1` FOREIGN KEY (`idConto`) REFERENCES `conto` (`idconto`),
  CONSTRAINT `account_registrato_ibfk_2` FOREIGN KEY (`idFedelta`) REFERENCES `saldo_fedelta` (`idsaldo_fedelta`)
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
  `idconto` int NOT NULL,
  `data_creazione` date NOT NULL,
  `saldo` double DEFAULT (_utf8mb4'0'),
  `indirizzo_fatturazione` varchar(45) NOT NULL,
  PRIMARY KEY (`idconto`)
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
  `idEvento` int NOT NULL,
  `data_evento` datetime NOT NULL,
  `descrizione` varchar(200) DEFAULT NULL COMMENT 'Si intende il titolo dell''evento, come i giocatori coinvolti',
  `categoria` tinytext,
  `chiuso` tinyint(1) NOT NULL DEFAULT (false),
  PRIMARY KEY (`idEvento`)
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
-- Table structure for table `lista_quote`
--

DROP TABLE IF EXISTS `lista_quote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lista_quote` (
  `idLink` int NOT NULL AUTO_INCREMENT,
  `idScommessa` int NOT NULL,
  `idQuota` int NOT NULL,
  PRIMARY KEY (`idLink`),
  KEY `fk_link_quota_scommessa_scommessa1_idx` (`idScommessa`),
  KEY `fk_link_quota_scommessa_quota1_idx` (`idQuota`),
  CONSTRAINT `fk_link_quota_scommessa_quota1` FOREIGN KEY (`idQuota`) REFERENCES `quota` (`id_Quota`),
  CONSTRAINT `fk_link_quota_scommessa_scommessa1` FOREIGN KEY (`idScommessa`) REFERENCES `scommessa` (`idScommessa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lista_quote`
--

LOCK TABLES `lista_quote` WRITE;
/*!40000 ALTER TABLE `lista_quote` DISABLE KEYS */;
/*!40000 ALTER TABLE `lista_quote` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `premi_fedeltà`
--

DROP TABLE IF EXISTS `premi_fedeltà`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `premi_fedeltà` (
  `idpremio` int NOT NULL,
  `costo` int NOT NULL,
  `descrizione` varchar(100) DEFAULT NULL,
  `data_creazione` date DEFAULT NULL,
  `data_scadenza` date DEFAULT NULL,
  `nome` varchar(45) NOT NULL,
  PRIMARY KEY (`idpremio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `premi_fedeltà`
--

LOCK TABLES `premi_fedeltà` WRITE;
/*!40000 ALTER TABLE `premi_fedeltà` DISABLE KEYS */;
/*!40000 ALTER TABLE `premi_fedeltà` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quota`
--

DROP TABLE IF EXISTS `quota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quota` (
  `id_Quota` int NOT NULL AUTO_INCREMENT,
  `moltiplicatore` int NOT NULL DEFAULT '1',
  `descrizione` varchar(45) NOT NULL,
  `idEvento` int NOT NULL,
  `stato` enum('Da Refertare','Vincente','Perdente') NOT NULL DEFAULT (_utf8mb4'Da Refertare'),
  `categoria` tinytext,
  `referto` varchar(45) DEFAULT NULL,
  `chiusa` tinyint(1) DEFAULT (false),
  PRIMARY KEY (`id_Quota`),
  KEY `fk_quota_evento1_idx` (`idEvento`),
  CONSTRAINT `fk_quota_evento1` FOREIGN KEY (`idEvento`) REFERENCES `evento` (`idEvento`)
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
-- Table structure for table `saldo_fedelta`
--

DROP TABLE IF EXISTS `saldo_fedelta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saldo_fedelta` (
  `idsaldo_fedelta` int NOT NULL,
  `punti` int(10) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`idsaldo_fedelta`)
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
  `idScommessa` int NOT NULL,
  `vincita` int DEFAULT '0',
  `data_scommessa` date NOT NULL,
  `idAccount` int NOT NULL,
  `importo` double NOT NULL,
  `stato` enum('Da refertare','Vinta','Persa') DEFAULT NULL,
  PRIMARY KEY (`idScommessa`),
  KEY `fk_scommessa_account_registrato1_idx` (`idAccount`),
  CONSTRAINT `fk_scommessa_account_registrato1` FOREIGN KEY (`idAccount`) REFERENCES `account_registrato` (`idaccount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scommessa`
--

LOCK TABLES `scommessa` WRITE;
/*!40000 ALTER TABLE `scommessa` DISABLE KEYS */;
/*!40000 ALTER TABLE `scommessa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storico_riscossioni`
--

DROP TABLE IF EXISTS `storico_riscossioni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storico_riscossioni` (
  `idstorico_riscossioni` int NOT NULL,
  `data_riscossione` date NOT NULL,
  `idPremio` int NOT NULL,
  `id_saldo_fedelta` int NOT NULL,
  PRIMARY KEY (`idstorico_riscossioni`),
  KEY `fk_storico_riscossioni_premi_fedeltà1_idx` (`idPremio`),
  KEY `fk_storico_riscossioni_account_registrato1` (`id_saldo_fedelta`),
  CONSTRAINT `fk_storico_riscossioni_account_registrato1` FOREIGN KEY (`id_saldo_fedelta`) REFERENCES `saldo_fedelta` (`idsaldo_fedelta`),
  CONSTRAINT `fk_storico_riscossioni_premi_fedeltà1` FOREIGN KEY (`idPremio`) REFERENCES `premi_fedeltà` (`idpremio`)
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
  `idtransazione` int NOT NULL,
  `importo` double DEFAULT NULL,
  `data_transazione` datetime DEFAULT NULL,
  `idconto` int DEFAULT NULL,
  PRIMARY KEY (`idtransazione`),
  KEY `fk_storico_transazioni_conto` (`idconto`),
  CONSTRAINT `fk_storico_transazioni_conto` FOREIGN KEY (`idconto`) REFERENCES `conto` (`idconto`)
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

-- Dump completed on 2025-02-09 13:47:55
