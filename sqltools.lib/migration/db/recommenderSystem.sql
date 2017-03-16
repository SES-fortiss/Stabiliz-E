-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 03, 2016 at 04:26 PM
-- Server version: 10.1.9-MariaDB
-- PHP Version: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `fortiss2`
--

-- --------------------------------------------------------

--
-- Table structure for table `recommender_blinds`
--

CREATE TABLE `recommender_blinds` (
  `status` int(11) NOT NULL,
  `rangeId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `recommender_blinds`
--

INSERT INTO `recommender_blinds` (`status`, `rangeId`) VALUES
(0, 1),
(25, 2),
(50, 3),
(75, 4),
(100, 5);

-- --------------------------------------------------------

--
-- Table structure for table `recommender_brightness_inside`
--

CREATE TABLE `recommender_brightness_inside` (
  `minVal` bigint(20) NOT NULL,
  `maxVal` bigint(20) NOT NULL,
  `rangeId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `recommender_brightness_inside`
--

INSERT INTO `recommender_brightness_inside` (`minVal`, `maxVal`, `rangeId`) VALUES
(0, 20, 1),
(20, 50, 2),
(50, 100, 3),
(100, 150, 4),
(150, 250, 5),
(250, 500, 6),
(500, 750, 7),
(750, 1000, 8),
(1000, 1500, 9),
(1500, 2000, 10),
(2000, 5000, 11),
(5000, 100000, 12);

-- --------------------------------------------------------

--
-- Table structure for table `recommender_brightness_outside`
--

CREATE TABLE `recommender_brightness_outside` (
  `minVal` bigint(20) NOT NULL,
  `maxVal` bigint(20) NOT NULL,
  `rangeId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `recommender_brightness_outside`
--

INSERT INTO `recommender_brightness_outside` (`minVal`, `maxVal`, `rangeId`) VALUES
(100000, 1000000, 1),
(1000, 10000, 2),
(100, 1000, 3),
(10, 100, 4),
(1, 10, 5),
(0, 1, 6);

-- --------------------------------------------------------

--
-- Table structure for table `recommender_devices`
--

CREATE TABLE `recommender_devices` (
  `deviceType` varchar(80) COLLATE utf8_unicode_ci NOT NULL,
  `wrapperid` varchar(80) COLLATE utf8_unicode_ci NOT NULL,
  `devid` varchar(80) COLLATE utf8_unicode_ci NOT NULL,
  `containerid` int(11) NOT NULL,
  `transactionid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `recommender_devices`
--

INSERT INTO `recommender_devices` (`deviceType`, `wrapperid`, `devid`, `containerid`, `transactionid`) VALUES
('occupancy', 'enoceanUSB.wrapper', '0', 0, 1),
('window', 'enoceanUSB.wrapper', '0', 0, 2),
('light', 'enoceanUSB.wrapper', '0', 0, 3),
('powerPlug', 'enoceanUSB.wrapper', '0', 0, 4),
('insideTemp', 'enoceanUSB.wrapper', '0', 0, 5),
('insideBrightness', 'enoceanUSB.wrapper', '0', 0, 7),
('outsideBrightness', 'enoceanUSB.wrapper', '0', 0, 8),
('blinds', 'enoceanUSB.wrapper', '0', 0, 9),
('heater', 'enoceanUSB.wrapper', '0', 0, 10),
('cheeler', 'enoceanUSB.wrapper', '0', 0, 11),
('time', 'enoceanUSB.wrapper', '0', 0, 13),
('weather', 'enoceanUSB.wrapper', '0', 0, 14),
('room', 'enoceanUSB.wrapper', '0', 0, 15),
('triggeredDev', '', '', 0, 12),
('occupancy', 'enoceanUSB.wrapper', '0003380A-153', 10, 1),
('insideBrightness', 'enoceanUSB.wrapper', '0003380A-5', 10, 7),
('insideBrightness', 'enoceanUSB.wrapper', '0003091A-5', 11, 7),
('occupancy', 'enoceanUSB.wrapper', '0003091A-153', 11, 1),
('outsideBrightness', 'enoceanUSB.wrapper', '000321E6-5', 0, 8),
('insideTemp', 'enoceanUSB.wrapper', '010066F4', 10, 5),
('insideTemp', 'enoceanUSB.wrapper', '01008831', 11, 5),
('insideTemp', 'enoceanUSB.wrapper', '010088C8', 11, 5),
('insideTemp', 'enoceanUSB.wrapper', '0100E48', 11, 5),
('window', 'enoceanUSB.wrapper', '100627F-151', 11, 2),
('window', 'enoceanUSB.wrapper', '1007D14-151', 11, 2),
('light', 'enoceanUSB.wrapper', '001E4E19-5', 10, 3),
('light', 'enoceanUSB.wrapper', '001E4DE8-5', 11, 3),
('light', 'enoceanUSB.wrapper', '001E657C-5', 10, 3),
('light', 'enoceanUSB.wrapper', '001E64E6-5', 11, 3),
('blinds', 'enoceanUSB.wrapper', '001E4D8A-136 ', 11, 9),
('blinds', 'enoceanUSB.wrapper', '001E6596-136', 11, 9),
('blinds', 'enoceanUSB.wrapper', '001E4DD0-136', 10, 9),
('blinds', 'enoceanUSB.wrapper', '001E656C-136', 10, 9),
('powerPlug', 'hexabus', 'fd89:c9eb:5608:48a1:50:c4ff:fe04:83eb', 11, 4),
('powerPlug', 'hexabus', 'fd89:c9eb:5608:48a1:50:c4ff:fe04:8404', 13, 4),
('powerPlug', 'hexabus', 'fd89:c9eb:5608:48a1:50:c4ff:fe04:82bb', 12, 4),
('powerPlug', 'hexabus', 'fd89:c9eb:5608:48a1:50:c4ff:fe04:843d', 12, 4),
('outsideTemp', 'enoceanUSB.wrapper', '0003B078-13', 0, 6),
('outsideTemp', 'enoceanUSB.wrapper', '0', 0, 6),
('window', 'enoceanUSB.wrapper', '1006934-151', 10, 2),
('window', 'enoceanUSB.wrapper', '1003F84-151', 10, 2);

-- --------------------------------------------------------

--
-- Table structure for table `recommender_preferences`
--

CREATE TABLE `recommender_preferences` (
  `roomId` int(255) NOT NULL,
  `item` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `valueRangeId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `recommender_rule`
--

CREATE TABLE `recommender_rule` (
  `ruleId` int(11) NOT NULL,
  `ruleCondition` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `ruleConsequence` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `status` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `recommender_temprature`
--

CREATE TABLE `recommender_temprature` (
  `minVal` double NOT NULL,
  `maxVal` double NOT NULL,
  `rangeId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `recommender_temprature`
--

INSERT INTO `recommender_temprature` (`minVal`, `maxVal`, `rangeId`) VALUES
(-30, -15, 1),
(-15, -10, 2),
(-10, -5, 3),
(-5, 0, 4),
(0, 5, 5),
(5, 10, 6),
(10, 15, 7),
(15, 18, 8),
(18, 20, 9),
(20, 25, 10),
(25, 30, 11),
(30, 35, 12),
(35, 40, 13),
(40, 45, 14);

-- --------------------------------------------------------

--
-- Table structure for table `recommender_time`
--

CREATE TABLE `recommender_time` (
  `minVal` time NOT NULL,
  `maxVal` time NOT NULL,
  `rangeId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `recommender_time`
--

INSERT INTO `recommender_time` (`minVal`, `maxVal`, `rangeId`) VALUES
('00:00:00', '07:00:00', 1),
('00:00:00', '07:00:00', 2),
('07:00:00', '07:30:00', 3),
('07:30:00', '08:00:00', 4),
('08:00:00', '08:30:00', 5),
('08:30:00', '09:00:00', 6),
('09:00:00', '09:30:00', 7),
('09:30:00', '10:00:00', 8),
('10:00:00', '10:30:00', 9),
('10:30:00', '11:00:00', 10),
('11:00:00', '11:30:00', 11),
('11:30:00', '12:00:00', 12),
('12:00:00', '12:30:00', 13),
('12:30:00', '13:00:00', 14),
('13:00:00', '13:30:00', 15),
('13:30:00', '14:00:00', 16),
('14:00:00', '14:30:00', 17),
('14:30:00', '15:00:00', 18),
('15:00:00', '15:30:00', 19),
('15:30:00', '16:00:00', 20),
('16:00:00', '16:30:00', 21),
('16:30:00', '17:00:00', 22),
('17:00:00', '17:30:00', 23),
('17:30:00', '18:00:00', 24),
('18:00:00', '18:30:00', 25),
('18:30:00', '19:00:00', 26),
('19:00:00', '19:30:00', 27),
('19:30:00', '20:00:00', 28),
('20:00:00', '20:30:00', 29),
('20:30:00', '21:00:00', 30),
('21:00:00', '23:59:59', 31);

-- --------------------------------------------------------

--
-- Table structure for table `recommender_weather`
--

CREATE TABLE `recommender_weather` (
  `weatherType` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `rangeId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `recommender_weather`
--

INSERT INTO `recommender_weather` (`weatherType`, `rangeId`) VALUES
('sunny', 1),
('partlyCloudy', 2),
('cloudy', 3),
('windy', 4),
('rainy', 5),
('snowy', 6);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `recommender_blinds`
--
ALTER TABLE `recommender_blinds`
  ADD PRIMARY KEY (`rangeId`);

--
-- Indexes for table `recommender_brightness_inside`
--
ALTER TABLE `recommender_brightness_inside`
  ADD PRIMARY KEY (`rangeId`);

--
-- Indexes for table `recommender_brightness_outside`
--
ALTER TABLE `recommender_brightness_outside`
  ADD PRIMARY KEY (`rangeId`);

--
-- Indexes for table `recommender_preferences`
--
ALTER TABLE `recommender_preferences`
  ADD PRIMARY KEY (`roomId`,`item`);

--
-- Indexes for table `recommender_rule`
--
ALTER TABLE `recommender_rule`
  ADD PRIMARY KEY (`ruleId`);

--
-- Indexes for table `recommender_temprature`
--
ALTER TABLE `recommender_temprature`
  ADD PRIMARY KEY (`rangeId`);

--
-- Indexes for table `recommender_time`
--
ALTER TABLE `recommender_time`
  ADD PRIMARY KEY (`rangeId`);

--
-- Indexes for table `recommender_weather`
--
ALTER TABLE `recommender_weather`
  ADD PRIMARY KEY (`rangeId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `recommender_blinds`
--
ALTER TABLE `recommender_blinds`
  MODIFY `rangeId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `recommender_brightness_inside`
--
ALTER TABLE `recommender_brightness_inside`
  MODIFY `rangeId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `recommender_brightness_outside`
--
ALTER TABLE `recommender_brightness_outside`
  MODIFY `rangeId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `recommender_rule`
--
ALTER TABLE `recommender_rule`
  MODIFY `ruleId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `recommender_time`
--
ALTER TABLE `recommender_time`
  MODIFY `rangeId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;
--
-- AUTO_INCREMENT for table `recommender_weather`
--
ALTER TABLE `recommender_weather`
  MODIFY `rangeId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
