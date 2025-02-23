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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_registrato`
--

LOCK TABLES `account_registrato` WRITE;
/*!40000 ALTER TABLE `account_registrato` DISABLE KEYS */;
INSERT INTO `account_registrato` VALUES (1,'ABCDEF12G34H567I','Rossi','2025-02-14 17:12:47.422000','alibaba@email.com','Mario','UTENTE',1,1,'75K3eLr+dx6JJFuJ7LwIpEpOFmwGZZkRiB84PURz6U8='),(3,'JAKOORGIU868937','Baudo','2025-02-14 17:12:47.829000','test@email.com','Pippo','UTENTE',3,3,'NrvlDtloQdEEQ7y2cNZVTwo0t2G+Z+ycSorSwMRMpCw='),(4,'JAKOORGIU848937','Baudo','2025-02-14 17:12:47.845000','test2@email.com','Pippo','UTENTE',3,3,'NrvlDtloQdEEQ7y2cNZVTwo0t2G+Z+ycSorSwMRMpCw='),(10,'JAKOORTIU848937','Baudo','2025-02-14 17:12:48.561000','testSC@email.com','Pippo','UTENTE',9,9,'NrvlDtloQdEEQ7y2cNZVTwo0t2G+Z+ycSorSwMRMpCw='),(11,'XYZ1234','Baudo','2025-02-14 17:12:48.772000','abcde@email.com','Pippo','UTENTE',10,10,'Y/ka5PNmFAK6Q7CGKfMvi5h64q1AKI0xkVgnlkh5oJE='),(12,'JAKOORTIU848637','Baudo','2025-02-14 17:12:49.328000','testS@email.com','Pippo','UTENTE',11,11,'NrvlDtloQdEEQ7y2cNZVTwo0t2G+Z+ycSorSwMRMpCw='),(16,'ABCDEK12G34H567I','Rossi','2025-02-18 18:25:56.415000','alibabu@email.com','Mario','GESTORE_REFERTI',15,15,'75K3eLr+dx6JJFuJ7LwIpEpOFmwGZZkRiB84PURz6U8=');
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conto`
--

LOCK TABLES `conto` WRITE;
/*!40000 ALTER TABLE `conto` DISABLE KEYS */;
INSERT INTO `conto` VALUES (1,'2025-02-14 17:12:47.252000','Via abcde',0),(3,'2025-02-14 17:12:47.817000','Via .',900),(9,'2025-02-14 17:12:48.555000','Via .',1000),(10,'2025-02-14 17:12:48.767000','Via .',1000),(11,'2025-02-14 17:12:49.320000','Via .',40),(12,'2025-02-18 18:19:18.044000','Via abcde',0),(13,'2025-02-18 18:21:31.020000','Via abcde',0),(14,'2025-02-18 18:24:26.072000','Via abcde',0),(15,'2025-02-18 18:25:56.335000','Via abcde',0);
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
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evento`
--

LOCK TABLES `evento` WRITE;
/*!40000 ALTER TABLE `evento` DISABLE KEYS */;
INSERT INTO `evento` VALUES (1,'2025-02-14 17:12:48','ATP Finals','Tennis',0,'Sinner vs Medvedev'),(11,'2025-02-14 17:12:48','ATP Stoccarda','Tennis',0,'Sinner vs Zhang'),(12,'2025-02-14 17:12:49','ATP Stoccarda','Tennis',0,'McDonald\'s vs Zverev'),(13,'2025-02-14 17:12:49','ATP Stoccarda','Tennis',0,'Musetti vs Cerundolo'),(17,'2025-02-14 17:12:49','ATP Stoccarda','Tennis',0,'Nadal vs Nagal'),(27,'2025-03-01 20:45:00','Serie A, 26a giornata','Calcio',0,'Milan vs Inter'),(28,'2025-03-02 18:00:00','Premier League, 27a giornata','Calcio',0,'Man City vs Man Utd'),(29,'2025-03-05 15:00:00','NBA Regular Season','Basket',0,'Lakers vs Celtics'),(30,'2025-03-07 20:00:00','ATP Master 1000','Tennis',0,'Nadal vs Djokovic'),(31,'2025-03-10 21:00:00','Conference League','Calcio',0,'Roma vs Leicester'),(32,'2025-03-11 21:00:00','Serie A, 26a giornata','Calcio',0,'Juventus vs Napoli'),(33,'2025-03-15 20:45:00','Champions League Final','Calcio',0,'Real Madrid vs Liverpool'),(34,'2025-03-16 18:30:00','Europa League Semifinal','Calcio',0,'Atalanta vs Roma'),(35,'2025-03-17 21:00:00','Serie A, 26a giornata','Calcio',0,'Lecce vs Udinese'),(36,'2025-03-18 20:00:00','Premier League Matchday 30','Calcio',0,'Arsenal vs Chelsea'),(37,'2025-03-19 19:00:00','La Liga Matchday 26','Calcio',0,'Barcelona vs Real Madrid'),(38,'2025-03-20 21:00:00','Bundesliga Matchday 25','Calcio',0,'Bayern vs Dortmund'),(39,'2025-03-21 18:00:00','Serie A, 26a giornata','Calcio',0,'Inter vs Milan'),(40,'2025-03-22 20:45:00','Champions League Group Stage','Calcio',0,'Paris SG vs Manchester City'),(41,'2025-03-23 20:00:00','Europa League Final','Calcio',0,'Sevilla vs Arsenal'),(42,'2025-03-24 21:00:00','Serie A, 26a giornata','Calcio',0,'Monza vs Salernitana'),(43,'2025-03-25 20:30:00','NBA Regular Season','Basket',0,'Lakers vs Warriors'),(44,'2025-03-26 19:30:00','NBA Playoffs','Basket',0,'Bulls vs Celtics'),(45,'2025-03-27 20:00:00','EuroLeague Final','Basket',0,'Fenerbahce vs CSKA Moscow'),(46,'2025-03-28 21:00:00','Basket Special','Basket',0,'Olimpia Milano vs Virtus Bologna'),(47,'2025-03-29 19:00:00','ATP 500 Semifinal','Tennis',0,'Federer vs Nadal'),(48,'2025-03-30 18:00:00','WTA Final','Tennis',0,'Serena vs Osaka'),(49,'2025-03-31 20:30:00','ATP Stoccarda','Tennis',0,'Struff vs Darderi'),(50,'2025-04-01 20:00:00','WTA Group Stage','Tennis',0,'Barty vs Halep'),(51,'2025-04-02 21:00:00','Grand Slam Quarterfinal','Tennis',0,'Zverev vs Tsitsipas'),(52,'2025-04-03 20:45:00','Champions League Round of 16','Calcio',0,'Borussia Dortmund vs Atletico Madrid'),(53,'2025-04-05 21:00:00','Serie A, 26a giornata','Calcio',0,'Roma vs Lazio'),(54,'2025-04-06 20:00:00','NBA Regular Season','Basket',0,'Dallas Maverick vs Cleveland Cavaliers');
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prenotazione`
--

LOCK TABLES `prenotazione` WRITE;
/*!40000 ALTER TABLE `prenotazione` DISABLE KEYS */;
INSERT INTO `prenotazione` VALUES (2,'KZQOO',5),(4,'E7EPM',10),(5,'GV77W',12);
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
) ENGINE=InnoDB AUTO_INCREMENT=811 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quota`
--

LOCK TABLES `quota` WRITE;
/*!40000 ALTER TABLE `quota` DISABLE KEYS */;
INSERT INTO `quota` VALUES (1,'Risultato Finale',_binary '\0','1',2.5,'Da Refertare',11),(3,'Risultato Finale',_binary '\0','1',2.5,'Da Refertare',12),(4,'Risultato Finale',_binary '\0','1',2.5,'Da Refertare',13),(8,'Risultato Finale',_binary '\0','1',2.5,'Da Refertare',17),(25,'Risultato Finale',_binary '\0','1',1.9,'Da Refertare',27),(26,'Risultato Finale',_binary '\0','X',3.2,'Da Refertare',27),(27,'Risultato Finale',_binary '\0','2',3.9,'Da Refertare',27),(28,'Risultato Finale',_binary '\0','1',2.1,'Da Refertare',28),(29,'Risultato Finale',_binary '\0','X',3.3,'Da Refertare',28),(30,'Risultato Finale',_binary '\0','2',3.6,'Da Refertare',28),(31,'Risultato Finale',_binary '\0','1',1.75,'Da Refertare',30),(32,'Risultato Finale',_binary '\0','2',2.1,'Da Refertare',30),(33,'Risultato Finale',_binary '\0','1',1.95,'Da Refertare',29),(34,'Risultato Finale',_binary '\0','2',2.05,'Da Refertare',29),(35,'Risultato Finale',_binary '\0','1',2.5,'Da Refertare',31),(36,'Risultato Finale',_binary '\0','X',3.2,'Da Refertare',31),(37,'Risultato Finale',_binary '\0','2',2.7,'Da Refertare',31),(38,'Risultato Finale',_binary '\0','1',2.1,'Da Refertare',32),(39,'Risultato Finale',_binary '\0','X',3.1,'Da Refertare',32),(40,'Risultato Finale',_binary '\0','2',3.3,'Da Refertare',32),(41,'Risultato Finale',_binary '\0','1',1.8,'Da Refertare',33),(42,'Risultato Finale',_binary '\0','X',3.5,'Da Refertare',33),(43,'Risultato Finale',_binary '\0','2',4.2,'Da Refertare',33),(44,'Under/Over',_binary '\0','Under',1.95,'Da Refertare',33),(45,'Under/Over',_binary '\0','Over',1.85,'Da Refertare',33),(46,'Risultato Finale',_binary '\0','1',2,'Da Refertare',34),(47,'Risultato Finale',_binary '\0','X',3.4,'Da Refertare',34),(48,'Risultato Finale',_binary '\0','2',3.8,'Da Refertare',34),(49,'Under/Over',_binary '\0','Under',1.9,'Da Refertare',34),(50,'Under/Over',_binary '\0','Over',0,'Da Refertare',34),(51,'Risultato Finale',_binary '\0','1',2.1,'Da Refertare',35),(52,'Risultato Finale',_binary '\0','X',3.6,'Da Refertare',35),(53,'Risultato Finale',_binary '\0','2',3.9,'Da Refertare',35),(54,'Under/Over',_binary '\0','Under',1.85,'Da Refertare',35),(55,'Under/Over',_binary '\0','Over',1.9,'Da Refertare',35),(56,'Risultato Finale',_binary '\0','1',2.2,'Da Refertare',36),(57,'Risultato Finale',_binary '\0','X',3.2,'Da Refertare',36),(58,'Risultato Finale',_binary '\0','2',3.7,'Da Refertare',36),(59,'Under/Over',_binary '\0','Under',1.8,'Da Refertare',36),(60,'Under/Over',_binary '\0','Over',1.9,'Da Refertare',36),(61,'Risultato Finale',_binary '\0','1',2.3,'Da Refertare',37),(62,'Risultato Finale',_binary '\0','X',3.3,'Da Refertare',37),(63,'Risultato Finale',_binary '\0','2',3.8,'Da Refertare',37),(64,'Under/Over',_binary '\0','Under',1.95,'Da Refertare',37),(65,'Under/Over',_binary '\0','Over',1.85,'Da Refertare',37),(66,'Risultato Finale',_binary '\0','1',2.4,'Da Refertare',38),(67,'Risultato Finale',_binary '\0','X',3.4,'Da Refertare',38),(68,'Risultato Finale',_binary '\0','2',3.9,'Da Refertare',38),(69,'Under/Over',_binary '\0','Under',1.9,'Da Refertare',38),(70,'Under/Over',_binary '\0','Over',1.9,'Da Refertare',38),(71,'Risultato Finale',_binary '\0','1',2.5,'Da Refertare',39),(72,'Risultato Finale',_binary '\0','X',3.5,'Da Refertare',39),(73,'Risultato Finale',_binary '\0','2',4,'Da Refertare',39),(74,'Under/Over',_binary '\0','Under',1.95,'Da Refertare',39),(75,'Under/Over',_binary '\0','Over',1.85,'Da Refertare',39),(76,'Risultato Finale',_binary '\0','1',2.6,'Da Refertare',40),(77,'Risultato Finale',_binary '\0','X',3.6,'Da Refertare',40),(78,'Risultato Finale',_binary '\0','2',4.1,'Da Refertare',40),(79,'Under/Over',_binary '\0','Under',1.9,'Da Refertare',40),(80,'Under/Over',_binary '\0','Over',1.8,'Da Refertare',40),(81,'Risultato Finale',_binary '\0','1',2.7,'Da Refertare',41),(82,'Risultato Finale',_binary '\0','X',3.7,'Da Refertare',41),(83,'Risultato Finale',_binary '\0','2',4.2,'Da Refertare',41),(84,'Under/Over',_binary '\0','Under',1.95,'Da Refertare',41),(85,'Under/Over',_binary '\0','Over',1.85,'Da Refertare',41),(86,'Risultato Finale',_binary '\0','1',2.8,'Da Refertare',42),(87,'Risultato Finale',_binary '\0','X',3.8,'Da Refertare',42),(88,'Risultato Finale',_binary '\0','2',4.3,'Da Refertare',42),(89,'Under/Over',_binary '\0','Under',1.9,'Da Refertare',42),(90,'Under/Over',_binary '\0','Over',1.9,'Da Refertare',42),(91,'Risultato Finale',_binary '\0','1',1.7,'Da Refertare',43),(92,'Risultato Finale',_binary '\0','2',2.2,'Da Refertare',43),(93,'Under/Over',_binary '\0','Under',1.85,'Da Refertare',43),(94,'Under/Over',_binary '\0','Over',1.85,'Da Refertare',43),(95,'Risultato Finale',_binary '\0','1',1.75,'Da Refertare',44),(96,'Risultato Finale',_binary '\0','2',2.3,'Da Refertare',44),(97,'Under/Over',_binary '\0','Under',1.8,'Da Refertare',44),(98,'Under/Over',_binary '\0','Over',1.8,'Da Refertare',44),(791,'Risultato Finale',_binary '\0','1',2.1,'Da Refertare',53),(792,'Risultato Finale',_binary '\0','X',3.3,'Da Refertare',53),(793,'Risultato Finale',_binary '\0','2',3.7,'Da Refertare',53),(794,'Under/Over 2.5',_binary '\0','Under',1.95,'Da Refertare',53),(795,'Under/Over 2.5',_binary '\0','Over',1.85,'Da Refertare',53),(796,'GG/NG',_binary '\0','GG',1.8,'Da Refertare',53),(797,'GG/NG',_binary '\0','NG',1.95,'Da Refertare',53),(798,'Doppie Chance',_binary '\0','1X',1.5,'Da Refertare',53),(799,'Doppie Chance',_binary '\0','X2',1.6,'Da Refertare',53),(800,'Doppie Chance',_binary '\0','12',1.4,'Da Refertare',53),(801,'T/T',_binary '\0','1',1.9,'Da Refertare',54),(802,'T/T',_binary '\0','2',1.95,'Da Refertare',54),(803,'Under/Over 220.5',_binary '\0','Under',1.85,'Da Refertare',54),(804,'Under/Over 220.5',_binary '\0','Over',1.95,'Da Refertare',54),(805,'1x2 handicap -5.5',_binary '\0','1',1.75,'Da Refertare',54),(806,'1x2 handicap -5.5',_binary '\0','1',1.75,'Da Refertare',54),(807,'1x2 handicap -5.5',_binary '\0','X',1.75,'Da Refertare',54),(808,'1x2 handicap -5.5',_binary '\0','2',2.2,'Da Refertare',54),(809,'Supplementari',_binary '\0','Si',3.5,'Da Refertare',54),(810,'Supplementari',_binary '\0','No',1.4,'Da Refertare',54);
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
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quota_giocata`
--

LOCK TABLES `quota_giocata` WRITE;
/*!40000 ALTER TABLE `quota_giocata` DISABLE KEYS */;
INSERT INTO `quota_giocata` VALUES (5,2.5,8,5),(27,2.5,3,1),(28,2.1,32,1),(31,3.8,87,7),(32,3.7,793,7),(42,3.3,29,8),(52,3.1,39,9),(53,2.1,32,9),(54,3.2,36,9),(55,1.9,25,6),(56,3.1,39,6),(57,2.8,86,10),(58,2.1,38,11),(59,3.6,52,11),(60,4,73,11),(61,3.3,792,11),(62,3.9,27,11),(63,2.8,86,12),(64,3.5,72,12),(65,3.3,40,12),(66,1.9,25,12),(67,1.95,794,13);
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saldo_fedelta`
--

LOCK TABLES `saldo_fedelta` WRITE;
/*!40000 ALTER TABLE `saldo_fedelta` DISABLE KEYS */;
INSERT INTO `saldo_fedelta` VALUES (1,0),(3,100),(9,100),(10,100),(11,100),(12,0),(13,0),(14,0),(15,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scommessa`
--

LOCK TABLES `scommessa` WRITE;
/*!40000 ALTER TABLE `scommessa` DISABLE KEYS */;
INSERT INTO `scommessa` VALUES (1,'2025-02-14 17:12:48.582000',100,'Da refertare',525,12,'GIOCATA'),(5,'2025-02-14 17:12:49.364000',100,'Da refertare',250,12,'PRENOTATA'),(6,'2025-02-23 10:54:34.242000',3,'Da refertare',17.67,12,'GIOCATA'),(7,'2025-02-23 10:55:15.056000',9,'Da refertare',126.54,12,'GIOCATA'),(8,'2025-02-23 11:01:25.232000',10,'Da refertare',33,12,'GIOCATA'),(9,'2025-02-23 11:04:16.443000',2.5,'Da refertare',52.08,12,'GIOCATA'),(10,'2025-02-23 11:20:45.567000',3,'Da refertare',8.4,12,'PRENOTATA'),(11,'2025-02-23 11:33:28.443000',3,'Da refertare',1167.57,12,'GIOCATA'),(12,'2025-02-23 12:22:34.519000',3,'Da refertare',184.33799999999997,12,'PRENOTATA'),(13,'2025-02-23 17:48:29.919000',5,'Da refertare',9.75,12,'GIOCATA');
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

--
-- Dumping events for database 'dbis'
--

--
-- Dumping routines for database 'dbis'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-23 21:38:44
