-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.7.44-log


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema cardops
--

CREATE DATABASE IF NOT EXISTS cardops;
USE cardops;

--
-- Definition of table `admins`
--

DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins` (
  `adminId` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(1000) DEFAULT NULL,
  `emailAdd` varchar(100) DEFAULT NULL,
  `middlename` varchar(45) DEFAULT NULL,
  `dateCreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL,
  `otp` varchar(45) DEFAULT NULL,
  `groupID` int(11) NOT NULL,
  PRIMARY KEY (`adminId`),
  KEY `fk_admins_groups` (`groupID`),
  CONSTRAINT `fk_admins_groups` FOREIGN KEY (`groupID`) REFERENCES `groups` (`idgroups`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admins`
--

/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;


--
-- Definition of table `audittrails`
--

DROP TABLE IF EXISTS `audittrails`;
CREATE TABLE `audittrails` (
  `idaudittrails` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `dateperformed` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `action` varchar(200) NOT NULL,
  PRIMARY KEY (`idaudittrails`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `audittrails`
--

/*!40000 ALTER TABLE `audittrails` DISABLE KEYS */;
/*!40000 ALTER TABLE `audittrails` ENABLE KEYS */;


--
-- Definition of table `contacts`
--

DROP TABLE IF EXISTS `contacts`;
CREATE TABLE `contacts` (
  `idcontacts` int(11) NOT NULL AUTO_INCREMENT,
  `contact` varchar(200) NOT NULL,
  `createdBy` varchar(45) NOT NULL,
  `createdOn` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idcontacts`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `contacts`
--

/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;


--
-- Definition of table `emp`
--

DROP TABLE IF EXISTS `emp`;
CREATE TABLE `emp` (
  `pan` varchar(45) DEFAULT NULL,
  `ffid` varchar(45) DEFAULT NULL,
  `terminalID` varchar(10) DEFAULT NULL,
  `reasonCode` varchar(5) DEFAULT NULL,
  `bankAccount` varchar(40) DEFAULT NULL,
  `originalMessageType` varchar(10) DEFAULT NULL,
  `messageType` varchar(45) DEFAULT NULL,
  `processingCode` varchar(17) DEFAULT NULL,
  `billingCurrency` varchar(5) DEFAULT NULL,
  `billingCureencyDecimals` varchar(2) DEFAULT NULL,
  `billingAmount` int(11) DEFAULT NULL,
  `amountIndicator` varchar(10) DEFAULT NULL,
  `postingDate` varchar(40) DEFAULT NULL,
  `transactionDate` varchar(40) DEFAULT NULL,
  `tranCurrency` varchar(45) DEFAULT NULL,
  `tranCurrencyDecimals` varchar(45) DEFAULT NULL,
  `transactionAmount` int(11) DEFAULT NULL,
  `clearingCurrency` varchar(4) DEFAULT NULL,
  `clearingCurrencyDecimals` varchar(2) DEFAULT NULL,
  `clearingAmount` varchar(45) DEFAULT NULL,
  `rate` varchar(45) DEFAULT NULL,
  `messageDescription` varchar(100) DEFAULT NULL,
  `terminalLocation` varchar(200) DEFAULT NULL,
  `country` varchar(5) DEFAULT NULL,
  `mcc` varchar(5) DEFAULT NULL,
  `approval` varchar(7) DEFAULT NULL,
  `arn` varchar(45) DEFAULT NULL,
  `relation` varchar(10) DEFAULT NULL,
  `bankRef` varchar(32) DEFAULT NULL,
  `uniqueID` varchar(22) NOT NULL,
  `entryCode` varchar(55) DEFAULT NULL,
  `clientID` varchar(22) DEFAULT NULL,
  `externalClientCode` varchar(22) DEFAULT NULL,
  `contractNumber` varchar(33) DEFAULT NULL,
  `cardBranch` varchar(4) DEFAULT NULL,
  `acctbranch` varchar(4) DEFAULT NULL,
  `internalAcctNumber` varchar(40) DEFAULT NULL,
  `webmotoID` varchar(5) DEFAULT NULL,
  `rrn` varchar(14) DEFAULT NULL,
  `stan` varchar(7) DEFAULT NULL,
  `cpd` varchar(45) DEFAULT NULL,
  `acquiereID` varchar(8) DEFAULT NULL,
  `extStan` varchar(8) DEFAULT NULL,
  `reserved` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`uniqueID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `emp`
--

/*!40000 ALTER TABLE `emp` DISABLE KEYS */;
/*!40000 ALTER TABLE `emp` ENABLE KEYS */;


--
-- Definition of table `groups`
--

DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups` (
  `idgroups` int(11) NOT NULL AUTO_INCREMENT,
  `groupName` varchar(45) NOT NULL,
  `responsibilities` varchar(45) NOT NULL,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`idgroups`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `groups`
--

/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;


--
-- Definition of table `iprs`
--

DROP TABLE IF EXISTS `iprs`;
CREATE TABLE `iprs` (
  `idiprs` int(11) NOT NULL AUTO_INCREMENT,
  `idnumber` varchar(45) DEFAULT NULL,
  `serialNumber` varchar(45) DEFAULT NULL,
  `firstName` varchar(100) DEFAULT NULL,
  `secondName` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `dob` varchar(45) DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `districtOfBirth` varchar(45) DEFAULT NULL,
  `placeOfIssue` varchar(45) DEFAULT NULL,
  `dateOfIssue` varchar(45) DEFAULT NULL,
  `holdersSign` varchar(45) DEFAULT NULL,
  `district` varchar(45) DEFAULT NULL,
  `division` varchar(45) DEFAULT NULL,
  `location` varchar(45) DEFAULT NULL,
  `sublocation` varchar(45) DEFAULT NULL,
  `createdBy` int(11) DEFAULT NULL,
  `createdOn` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `photo` varchar(200) NOT NULL,
  PRIMARY KEY (`idiprs`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `iprs`
--

/*!40000 ALTER TABLE `iprs` DISABLE KEYS */;
/*!40000 ALTER TABLE `iprs` ENABLE KEYS */;


--
-- Definition of table `quotes`
--

DROP TABLE IF EXISTS `quotes`;
CREATE TABLE `quotes` (
  `idquotes` int(11) NOT NULL AUTO_INCREMENT,
  `quoter` varchar(45) DEFAULT NULL,
  `quoteDescription` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`idquotes`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `quotes`
--

/*!40000 ALTER TABLE `quotes` DISABLE KEYS */;
/*!40000 ALTER TABLE `quotes` ENABLE KEYS */;


--
-- Definition of table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `idroles` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(100) NOT NULL,
  `createdBy` varchar(45) NOT NULL,
  `createdOn` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idroles`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roles`
--

/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
