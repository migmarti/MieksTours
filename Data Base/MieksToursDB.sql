-- phpMyAdmin SQL Dump
-- version 4.0.10.18
-- https://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: Dec 03, 2017 at 04:59 PM
-- Server version: 5.6.36-cll-lve
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `MieksToursDB`
--

-- --------------------------------------------------------

--
-- Table structure for table `Interests`
--

CREATE TABLE IF NOT EXISTS `Interests` (
  `InterestId` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  PRIMARY KEY (`InterestId`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=29 ;

--
-- Dumping data for table `Interests`
--

INSERT INTO `Interests` (`InterestId`, `Name`) VALUES
(1, 'Sports'),
(2, 'Movies'),
(9, 'Technology'),
(5, 'Reading'),
(24, 'Education'),
(11, 'Gaming'),
(12, 'Crafts'),
(13, 'Exotic food'),
(14, 'Animals'),
(15, 'Shopping'),
(16, 'Dating'),
(17, 'Relaxing'),
(18, 'Cooking'),
(19, 'Drinking'),
(20, 'Party'),
(21, 'Business'),
(22, 'Beach'),
(23, 'Art'),
(25, 'Education'),
(26, 'Adventuring'),
(27, 'Music');

-- --------------------------------------------------------

--
-- Table structure for table `Requests`
--

CREATE TABLE IF NOT EXISTS `Requests` (
  `RequestId` int(11) NOT NULL AUTO_INCREMENT,
  `HostId` varchar(10) NOT NULL,
  `TravelerId` varchar(10) NOT NULL,
  `StartDate` date NOT NULL,
  `EndDate` date NOT NULL,
  `Comment` varchar(255) NOT NULL,
  `StatusId` int(11) NOT NULL,
  PRIMARY KEY (`RequestId`),
  KEY `Request_Host` (`HostId`),
  KEY `Request_Traveler` (`TravelerId`),
  KEY `Requests_Status` (`StatusId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `Status`
--

CREATE TABLE IF NOT EXISTS `Status` (
  `StatusId` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  PRIMARY KEY (`StatusId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `UserInterests`
--

CREATE TABLE IF NOT EXISTS `UserInterests` (
  `UserInterestId` int(11) NOT NULL AUTO_INCREMENT,
  `UserId` varchar(10) NOT NULL,
  `InterestId` int(11) NOT NULL,
  PRIMARY KEY (`UserInterestId`),
  KEY `UserInterests_Interests` (`InterestId`),
  KEY `UserInterests_Users` (`UserId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

CREATE TABLE IF NOT EXISTS `Users` (
  `UserId` varchar(11) NOT NULL DEFAULT '',
  `FirstName` varchar(50) DEFAULT NULL,
  `LastName` varchar(50) DEFAULT NULL,
  `DoB` date DEFAULT NULL,
  `Location` varchar(200) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `email` varchar(25) DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `HostingStatus` tinyint(1) DEFAULT NULL,
  `Latitud` float NOT NULL,
  `Longitud` float NOT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `Rate` float DEFAULT NULL,
  PRIMARY KEY (`UserId`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Users`
--

INSERT INTO `Users` (`UserId`, `FirstName`, `LastName`, `DoB`, `Location`, `password`, `email`, `Description`, `HostingStatus`, `Latitud`, `Longitud`, `startDate`, `endDate`, `Rate`) VALUES
('roao3b8hwl', 'First Name', 'Last Name', '2017-11-02', 'Baja California', 'password', 'user2@email.com', 'Myself', NULL, 0, 0, '0000-00-00', '0000-00-00', NULL),
('t463n9732l', 'Tio Fidel', 'castro', '1977-05-05', 'Tijuana', 'qwer1234', 'fidel.castro@cetys.edu.mx', 'Soy hermano de Mike', NULL, 0, 0, '0000-00-00', '0000-00-00', NULL),
('h4xsoreecu', 'Rodo', 'Rodo', '2020-12-20', 'RodoLand', 'rodo', 'rodo@rodo.rodo', 'Rodo', 0, 0, 0, '2017-04-11', '2017-05-12', NULL),
('da9pxforlv', 'Sam', 'Magdaleno', '1987-12-18', 'Centro de EnseÃ±anza TÃ©cnica y Superior Universidad Campus Tijuana', 'password1', 'miekdev@email.com', 'Sam es good boi', NULL, 0, 0, '0000-00-00', '0000-00-00', NULL),
('5zm3dkd0gi', 'Pancho', 'Gutierrez', '2017-11-25', 'Tijuas', 'password', 'user@email.com', 'Desc', NULL, 0, 0, '0000-00-00', '0000-00-00', NULL),
('sas8du9asda', NULL, NULL, NULL, NULL, 'ilikeburritos', 'samboys', NULL, NULL, 0, 0, '0000-00-00', '0000-00-00', NULL),
('fkkep1dl41', 'TEST', 'TEST', '2017-12-06', 'South Atlantic Ocean', '1234567', 'ASD@TEST.COM', 'I LIKE TURTLES', NULL, 0, 0, '0000-00-00', '0000-00-00', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `userstemp`
--

CREATE TABLE IF NOT EXISTS `userstemp` (
  `password` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userstemp`
--

INSERT INTO `userstemp` (`password`, `email`) VALUES
('meme', 'pollo'),
('poaadsdsadsadsadsadsaadsllo', 'caca@gmai.com'),
('roodpass', 'rodo'),
('pass', 'adnajd');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
