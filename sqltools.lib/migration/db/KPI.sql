-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 08, 2017 at 03:41 PM
-- Server version: 5.6.21
-- PHP Version: 5.5.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `fortiss2`
--

-- --------------------------------------------------------

--
-- Table structure for table `KPI`
--

CREATE TABLE IF NOT EXISTS `KPI` (
  `containerID` bigint(20) NOT NULL,
  `kpitype` varchar(80) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `value` double NOT NULL,
  `timestamp` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `KPI`
--

INSERT INTO `KPI` (`containerID`, `kpitype`, `value`, `timestamp`) VALUES
(0, 'Volt_distortion', -79.03753087403477, 1487109600000);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
