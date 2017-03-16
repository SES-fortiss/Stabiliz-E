-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 10. Mrz 2017 um 18:02
-- Server-Version: 5.7.17-0ubuntu0.16.04.1
-- PHP-Version: 7.0.15-0ubuntu0.16.04.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `smg2`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ContainerManager_ContainerEdges`
--

CREATE TABLE `ContainerManager_ContainerEdges` (
  `ContainerEdgeID` int(11) NOT NULL,
  `ContainerIDParent` bigint(10) NOT NULL,
  `ContainerIDChild` bigint(10) NOT NULL,
  `VirtualContainerEdge` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `ContainerManager_ContainerEdges`
--

INSERT INTO `ContainerManager_ContainerEdges` (`ContainerEdgeID`, `ContainerIDParent`, `ContainerIDChild`, `VirtualContainerEdge`) VALUES
(1, 1, 2, 0),
(2, 1, 3, 0),
(3, 1, 4, 0),
(4, 1, 5, 0),
(5, 2, 6, 0),
(6, 2, 7, 0),
(7, 4, 8, 0),
(8, 4, 9, 0),
(9, 8, 10, 0),
(10, 8, 11, 0),
(11, 8, 12, 0),
(12, 8, 13, 0),
(613, 5, 47, 0),
(612, 5, 46, 0),
(611, 5, 45, 0),
(610, 5, 44, 0),
(609, 5, 43, 0),
(608, 5, 42, 0),
(607, 5, 41, 0),
(606, 5, 40, 0),
(605, 6, 39, 0),
(604, 6, 38, 0),
(603, 6, 37, 0),
(602, 6, 36, 0),
(601, 6, 35, 0),
(600, 6, 34, 0),
(599, 6, 33, 0),
(598, 6, 32, 0),
(597, 6, 31, 0),
(596, 12, 30, 0),
(595, 12, 29, 0),
(594, 13, 28, 0),
(593, 11, 27, 0),
(592, 7, 26, 0),
(591, 7, 25, 0),
(590, 7, 24, 0),
(589, 7, 23, 0),
(588, 7, 22, 0),
(586, 7, 20, 0),
(587, 7, 21, 0),
(584, 7, 18, 0),
(585, 7, 19, 0),
(582, 7, 16, 0),
(583, 7, 17, 0),
(580, 7, 14, 0),
(581, 7, 15, 0),
(614, 13, 48, 0),
(615, 13, 49, 0),
(616, 13, 50, 0),
(617, 13, 51, 0),
(618, 8, 52, 0),
(619, 8, 53, 0),
(620, 8, 54, 0),
(621, 8, 55, 0),
(622, 8, 56, 0),
(623, 8, 57, 0),
(624, 8, 58, 0),
(625, 8, 59, 0),
(626, 8, 60, 0),
(627, 8, 61, 0),
(628, 8, 62, 0),
(629, 8, 63, 0),
(630, 8, 64, 0),
(631, 9, 65, 0),
(632, 9, 66, 0),
(633, 9, 67, 0),
(634, 9, 68, 0),
(635, 9, 69, 0),
(636, 9, 70, 0),
(637, 9, 71, 0),
(638, 9, 72, 0),
(639, 9, 73, 0),
(640, 9, 74, 0),
(641, 9, 75, 0),
(642, 9, 76, 0),
(643, 9, 77, 0),
(644, 8, 78, 0),
(645, 8, 79, 0),
(646, 8, 80, 0),
(647, 8, 81, 0),
(648, 8, 82, 0),
(649, 8, 83, 0),
(650, 8, 84, 0),
(651, 8, 85, 0),
(652, 8, 86, 0),
(653, 8, 87, 0),
(654, 8, 88, 0),
(655, 8, 89, 0),
(656, 8, 90, 0),
(657, 9, 91, 0),
(658, 9, 92, 0),
(659, 9, 93, 0),
(660, 9, 94, 0),
(661, 9, 95, 0),
(662, 9, 96, 0),
(663, 9, 97, 0),
(664, 9, 98, 0),
(665, 9, 99, 0),
(666, 9, 100, 0),
(667, 9, 101, 0),
(668, 9, 102, 0),
(669, 9, 103, 0),
(670, 13, 104, 0),
(671, 12, 105, 0),
(672, 12, 106, 0),
(673, 11, 107, 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ContainerManager_Containers`
--

CREATE TABLE `ContainerManager_Containers` (
  `ContainerID` bigint(10) NOT NULL,
  `WrapperID` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `DevID` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `ContainerHRName` varchar(255) CHARACTER SET latin1 NOT NULL,
  `ContainerType` varchar(50) CHARACTER SET latin1 NOT NULL,
  `ContainerFunction` varchar(50) CHARACTER SET latin1 NOT NULL,
  `VirtualContainer` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `ContainerManager_Containers`
--

INSERT INTO `ContainerManager_Containers` (`ContainerID`, `WrapperID`, `DevID`, `ContainerHRName`, `ContainerType`, `ContainerFunction`, `VirtualContainer`) VALUES
(1, '', '', 'fortiss', 'BUILDING', 'ROOT', 0),
(2, '', '', 'Floor -1', 'FLOOR', 'UTILITY', 0),
(3, '', '', 'Floor 0', 'FLOOR', 'OFFICE', 0),
(4, '', '', 'Floor 2', 'FLOOR', 'OFFICE', 0),
(5, '', '', 'Roof', 'FLOOR', 'UTILITY', 0),
(6, '', '', 'Utilityroom', 'ROOM', 'UTILITY', 0),
(7, '', '', 'Serverroom', 'ROOM', 'UTILITY', 0),
(8, '', '', 'Eastwing', 'WING', 'OFFICE', 0),
(9, '', '', 'Westwing', 'WING', 'OFFICE', 0),
(10, '', '', 'Room 224', 'ROOM', 'CONFERENCE', 0),
(11, '', '', 'Room 225', 'ROOM', 'OFFICE', 0),
(12, '', '', 'Eastkitchen', 'ROOM', 'KITCHEN', 0),
(13, '', '', 'Room 206', 'ROOM', 'OFFICE', 0),
(14, 'siemens211.wrapper', 'C1_MMXU1.A.phsA.cVal.cVal.mag', 'Amperemeter Phase [A]', 'DEVICE', 'NONE', 0),
(15, 'siemens211.wrapper', 'C1_MMXU1.A.phsB.cVal.cVal.mag', 'Amperemeter Phase [A]', 'DEVICE', 'NONE', 0),
(16, 'siemens211.wrapper', 'C1_MMXU1.A.phsC.cVal.cVal.mag', 'Amperemeter Phase [A]', 'DEVICE', 'NONE', 0),
(17, 'siemens211.wrapper', 'C1_MMXU1.Hz.mag.mag', 'Frequency [Hz]', 'DEVICE', 'NONE', 0),
(18, 'siemens211.wrapper', 'C1_MMXU1.PhV.phsA.cVal.cVal.mag', 'Voltmeter Phase [V]', 'DEVICE', 'NONE', 0),
(19, 'siemens211.wrapper', 'C1_MMXU1.PhV.phsB.cVal.cVal.mag', 'Voltmeter Phase [V]', 'DEVICE', 'NONE', 0),
(20, 'siemens211.wrapper', 'C1_MMXU1.PhV.phsC.cVal.cVal.mag', 'Voltmeter Phase [V]', 'DEVICE', 'NONE', 0),
(21, 'siemens211.wrapper', 'C1_MMXU1.VA.phsA.cVal.cVal.mag', 'VoltAmpere [VA]', 'DEVICE', 'NONE', 0),
(22, 'siemens211.wrapper', 'C1_MMXU1.VA.phsB.cVal.cVal.mag', 'VoltAmpere [VA]', 'DEVICE', 'NONE', 0),
(23, 'siemens211.wrapper', 'C1_MMXU1.VA.phsC.cVal.cVal.mag', 'VoltAmpere [VA]', 'DEVICE', 'NONE', 0),
(24, 'siemens211.wrapper', 'C1_MMXU1.W.phsA.cVal.cVal.mag', 'Powermeter Phase [W]', 'DEVICE', 'NONE', 0),
(25, 'siemens211.wrapper', 'C1_MMXU1.W.phsB.cVal.cVal.mag', 'Powermeter Phase [W]', 'DEVICE', 'NONE', 0),
(26, 'siemens211.wrapper', 'C1_MMXU1.W.phsC.cVal.cVal.mag', 'Powermeter Phase [W]', 'DEVICE', 'NONE', 0),
(27, 'hexabus.wrapper', 'fd89:c9eb:5608:48a1:50:c4ff:fe04:83eb.2', '225_Monitor HexabusPlug+ power meter', 'DEVICE', 'NONE', 1),
(28, 'hexabus.wrapper', 'fd89:c9eb:5608:48a1:50:c4ff:fe04:8404.2', '206_Laptop HexabusPlug+ power meter', 'DEVICE', 'NONE', 1),
(29, 'hexabus.wrapper', 'fd89:c9eb:5608:48a1:50:c4ff:fe04:82bb.2', 'Kitchen_East_Microwave HexabusPlug+ power meter', 'DEVICE', 'NONE', 1),
(30, 'hexabus.wrapper', 'fd89:c9eb:5608:48a1:50:c4ff:fe04:843d.2', 'Kitchen_Fridg HexabusPlug+ power meter', 'DEVICE', 'NONE', 1),
(31, 'sonnenbattery.wrapper', 'consumption', 'House comsumption', 'DEVICE', 'NONE', 0),
(32, 'sonnenbattery.wrapper', 'frequency', 'AC frequency', 'DEVICE', 'NONE', 0),
(33, 'sonnenbattery.wrapper', 'grid_feed', 'grid feed', 'DEVICE', 'NONE', 0),
(34, 'sonnenbattery.wrapper', 'power_inverter', 'Power at inverter', 'DEVICE', 'NONE', 0),
(35, 'sonnenbattery.wrapper', 'pv_production', 'PV Production', 'DEVICE', 'NONE', 0),
(36, 'sonnenbattery.wrapper', 'battery_percentage', 'Battery [%]', 'DEVICE', 'NONE', 0),
(37, 'sonnenbattery.wrapper', 'user_state', 'User state of charge', 'DEVICE', 'NONE', 0),
(38, 'sonnenbattery.wrapper', 'ac_frequency', 'AC voltage', 'DEVICE', 'NONE', 0),
(39, 'sonnenbattery.wrapper', 'battery_voltage', 'Battery [V]', 'DEVICE', 'NONE', 0),
(40, 'solarlog.wrapper', 'solar_generator_watt', 'Solar Generator [W]', 'DEVICE', 'NONE', 0),
(41, 'solarlog.wrapper', 'solar_generator_temperature', 'Temperature (solarlog.wrapper.solar_generator_temperature)', 'DEVICE', 'NONE', 0),
(42, 'solarlog.wrapper', 'solar_generator_voltage', 'Solar Generator udc1 [V]', 'DEVICE', 'NONE', 0),
(43, 'solarlog.wrapper', 'solar_generator_savings', 'Solar Generator Savings [KG]', 'DEVICE', 'NONE', 0),
(44, 'solarlog.wrapper', 'solar_feed-in_watt', 'Solar Feed-in [W]', 'DEVICE', 'NONE', 0),
(45, 'solarlog.wrapper', 'solar_feed-in_temperature', 'Solar Feed-in [C]', 'DEVICE', 'NONE', 0),
(46, 'solarlog.wrapper', 'solar_feed-in_sum', 'Solar Day Sum [WH]', 'DEVICE', 'NONE', 0),
(47, 'solarlog.wrapper', 'solar_inventor_status', 'Solar Inventor Status', 'DEVICE', 'NONE', 0),
(48, 'labcon.wrapper', 'coffeemaker_plug', 'CoffeeMaker PowerPlug', 'DEVICE', 'NONE', 0),
(49, 'labcon.wrapper', 'coffeemaker_powermeter', 'CoffeeMaker Consumption [W]', 'DEVICE', 'NONE', 0),
(50, 'labcon.wrapper', 'multisensor_temperature', 'Multisensor [C]', 'DEVICE', 'NONE', 0),
(51, 'labcon.wrapper', 'multisensor_brightness', 'Multisensor [Lux]', 'DEVICE', 'NONE', 0),
(52, 'siemens212.wrapper', 'C1_MMXU1.A.phsA.cVal.cVal.mag', 'Amperemeter Phase [A]', 'DEVICE', 'NONE', 0),
(53, 'siemens212.wrapper', 'C1_MMXU1.A.phsB.cVal.cVal.mag', 'Amperemeter Phase [A]', 'DEVICE', 'NONE', 0),
(54, 'siemens212.wrapper', 'C1_MMXU1.A.phsC.cVal.cVal.mag', 'Amperemeter Phase [A]', 'DEVICE', 'NONE', 0),
(55, 'siemens212.wrapper', 'C1_MMXU1.Hz.mag.mag', 'Frequency [Hz]', 'DEVICE', 'NONE', 0),
(56, 'siemens212.wrapper', 'C1_MMXU1.PhV.phsA.cVal.cVal.mag', 'Voltmeter Phase [V]', 'DEVICE', 'NONE', 0),
(57, 'siemens212.wrapper', 'C1_MMXU1.PhV.phsB.cVal.cVal.mag', 'Voltmeter Phase [V]', 'DEVICE', 'NONE', 0),
(58, 'siemens212.wrapper', 'C1_MMXU1.PhV.phsC.cVal.cVal.mag', 'Voltmeter Phase [V]', 'DEVICE', 'NONE', 0),
(59, 'siemens212.wrapper', 'C1_MMXU1.VA.phsA.cVal.cVal.mag', 'VoltAmpere [VA]', 'DEVICE', 'NONE', 0),
(60, 'siemens212.wrapper', 'C1_MMXU1.VA.phsB.cVal.cVal.mag', 'VoltAmpere [VA]', 'DEVICE', 'NONE', 0),
(61, 'siemens212.wrapper', 'C1_MMXU1.VA.phsC.cVal.cVal.mag', 'VoltAmpere [VA]', 'DEVICE', 'NONE', 0),
(62, 'siemens212.wrapper', 'C1_MMXU1.W.phsA.cVal.cVal.mag', 'Powermeter Phase [W]', 'DEVICE', 'NONE', 0),
(63, 'siemens212.wrapper', 'C1_MMXU1.W.phsB.cVal.cVal.mag', 'Powermeter Phase [W]', 'DEVICE', 'NONE', 0),
(64, 'siemens212.wrapper', 'C1_MMXU1.W.phsC.cVal.cVal.mag', 'Powermeter Phase [W]', 'DEVICE', 'NONE', 0),
(65, 'siemens212.wrapper', 'C2_MMXU1.A.phsA.cVal.cVal.mag', 'Amperemeter Phase [A]', 'DEVICE', 'NONE', 0),
(66, 'siemens212.wrapper', 'C2_MMXU1.A.phsB.cVal.cVal.mag', 'Amperemeter Phase [A]', 'DEVICE', 'NONE', 0),
(67, 'siemens212.wrapper', 'C2_MMXU1.A.phsC.cVal.cVal.mag', 'Amperemeter Phase [A]', 'DEVICE', 'NONE', 0),
(68, 'siemens212.wrapper', 'C2_MMXU1.Hz.mag.mag', 'Frequency [Hz]', 'DEVICE', 'NONE', 0),
(69, 'siemens212.wrapper', 'C2_MMXU1.PhV.phsA.cVal.cVal.mag', 'Voltmeter Phase [V]', 'DEVICE', 'NONE', 0),
(70, 'siemens212.wrapper', 'C2_MMXU1.PhV.phsB.cVal.cVal.mag', 'Voltmeter Phase [V]', 'DEVICE', 'NONE', 0),
(71, 'siemens212.wrapper', 'C2_MMXU1.PhV.phsC.cVal.cVal.mag', 'Voltmeter Phase [V]', 'DEVICE', 'NONE', 0),
(72, 'siemens212.wrapper', 'C2_MMXU1.VA.phsA.cVal.cVal.mag', 'VoltAmpere [VA]', 'DEVICE', 'NONE', 0),
(73, 'siemens212.wrapper', 'C2_MMXU1.VA.phsB.cVal.cVal.mag', 'VoltAmpere [VA]', 'DEVICE', 'NONE', 0),
(74, 'siemens212.wrapper', 'C2_MMXU1.VA.phsC.cVal.cVal.mag', 'VoltAmpere [VA]', 'DEVICE', 'NONE', 0),
(75, 'siemens212.wrapper', 'C2_MMXU1.W.phsA.cVal.cVal.mag', 'Powermeter Phase [W]', 'DEVICE', 'NONE', 0),
(76, 'siemens212.wrapper', 'C2_MMXU1.W.phsB.cVal.cVal.mag', 'Powermeter Phase [W]', 'DEVICE', 'NONE', 0),
(77, 'siemens212.wrapper', 'C2_MMXU1.W.phsC.cVal.cVal.mag', 'Powermeter Phase [W]', 'DEVICE', 'NONE', 0),
(78, 'siemens212.wrapper', 'C3_MMXU1.A.phsA.cVal.cVal.mag', 'Amperemeter Phase [A]', 'DEVICE', 'NONE', 0),
(79, 'siemens212.wrapper', 'C3_MMXU1.A.phsB.cVal.cVal.mag', 'Amperemeter Phase [A]', 'DEVICE', 'NONE', 0),
(80, 'siemens212.wrapper', 'C3_MMXU1.A.phsC.cVal.cVal.mag', 'Amperemeter Phase [A]', 'DEVICE', 'NONE', 0),
(81, 'siemens212.wrapper', 'C3_MMXU1.Hz.mag.mag', 'Frequency [Hz]', 'DEVICE', 'NONE', 0),
(82, 'siemens212.wrapper', 'C3_MMXU1.PhV.phsA.cVal.cVal.mag', 'Voltmeter Phase [V]', 'DEVICE', 'NONE', 0),
(83, 'siemens212.wrapper', 'C3_MMXU1.PhV.phsB.cVal.cVal.mag', 'Voltmeter Phase [V]', 'DEVICE', 'NONE', 0),
(84, 'siemens212.wrapper', 'C3_MMXU1.PhV.phsC.cVal.cVal.mag', 'Voltmeter Phase [V]', 'DEVICE', 'NONE', 0),
(85, 'siemens212.wrapper', 'C3_MMXU1.VA.phsA.cVal.cVal.mag', 'VoltAmpere [VA]', 'DEVICE', 'NONE', 0),
(86, 'siemens212.wrapper', 'C3_MMXU1.VA.phsB.cVal.cVal.mag', 'VoltAmpere [VA]', 'DEVICE', 'NONE', 0),
(87, 'siemens212.wrapper', 'C3_MMXU1.VA.phsC.cVal.cVal.mag', 'VoltAmpere [VA]', 'DEVICE', 'NONE', 0),
(88, 'siemens212.wrapper', 'C3_MMXU1.W.phsA.cVal.cVal.mag', 'Powermeter Phase [W]', 'DEVICE', 'NONE', 0),
(89, 'siemens212.wrapper', 'C3_MMXU1.W.phsB.cVal.cVal.mag', 'Powermeter Phase [W]', 'DEVICE', 'NONE', 0),
(90, 'siemens212.wrapper', 'C3_MMXU1.W.phsC.cVal.cVal.mag', 'Powermeter Phase [W]', 'DEVICE', 'NONE', 0),
(91, 'siemens212.wrapper', 'C4_MMXU1.A.phsA.cVal.cVal.mag', 'Amperemeter Phase [A]', 'DEVICE', 'NONE', 0),
(92, 'siemens212.wrapper', 'C4_MMXU1.A.phsB.cVal.cVal.mag', 'Amperemeter Phase [A]', 'DEVICE', 'NONE', 0),
(93, 'siemens212.wrapper', 'C4_MMXU1.A.phsC.cVal.cVal.mag', 'Amperemeter Phase [A]', 'DEVICE', 'NONE', 0),
(94, 'siemens212.wrapper', 'C4_MMXU1.Hz.mag.mag', 'Frequency [Hz]', 'DEVICE', 'NONE', 0),
(95, 'siemens212.wrapper', 'C4_MMXU1.PhV.phsA.cVal.cVal.mag', 'Voltmeter Phase [V]', 'DEVICE', 'NONE', 0),
(96, 'siemens212.wrapper', 'C4_MMXU1.PhV.phsB.cVal.cVal.mag', 'Voltmeter Phase [V]', 'DEVICE', 'NONE', 0),
(97, 'siemens212.wrapper', 'C4_MMXU1.PhV.phsC.cVal.cVal.mag', 'Voltmeter Phase [V]', 'DEVICE', 'NONE', 0),
(98, 'siemens212.wrapper', 'C4_MMXU1.VA.phsA.cVal.cVal.mag', 'VoltAmpere [VA]', 'DEVICE', 'NONE', 0),
(99, 'siemens212.wrapper', 'C4_MMXU1.VA.phsB.cVal.cVal.mag', 'VoltAmpere [VA]', 'DEVICE', 'NONE', 0),
(100, 'siemens212.wrapper', 'C4_MMXU1.VA.phsC.cVal.cVal.mag', 'VoltAmpere [VA]', 'DEVICE', 'NONE', 0),
(101, 'siemens212.wrapper', 'C4_MMXU1.W.phsA.cVal.cVal.mag', 'Powermeter Phase [W]', 'DEVICE', 'NONE', 0),
(102, 'siemens212.wrapper', 'C4_MMXU1.W.phsB.cVal.cVal.mag', 'Powermeter Phase [W]', 'DEVICE', 'NONE', 0),
(103, 'siemens212.wrapper', 'C4_MMXU1.W.phsC.cVal.cVal.mag', 'Powermeter Phase [W]', 'DEVICE', 'NONE', 0),
(104, 'hexabus.wrapper', 'fd89:c9eb:5608:48a1:50:c4ff:fe04:8404.2#Powerplug', '206_Laptop HexabusPlug+ power meter Powerplug', 'DEVICE', 'NONE', 0),
(105, 'hexabus.wrapper', 'fd89:c9eb:5608:48a1:50:c4ff:fe04:843d.2#Powerplug', 'Kitchen_Fridg HexabusPlug+ power meter Powerplug', 'DEVICE', 'NONE', 0),
(106, 'hexabus.wrapper', 'fd89:c9eb:5608:48a1:50:c4ff:fe04:82bb.2#Powerplug', 'Kitchen_East_Microwave HexabusPlug+ power meter Powerplug', 'DEVICE', 'NONE', 0),
(107, 'hexabus.wrapper', 'fd89:c9eb:5608:48a1:50:c4ff:fe04:83eb.2#Powerplug', '225_Monitor HexabusPlug+ power meter Powerplug', 'DEVICE', 'NONE', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ContainerManager_Devices`
--

CREATE TABLE `ContainerManager_Devices` (
  `DeviceCode` int(11) NOT NULL,
  `DeviceType` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `SMGDeviceType` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `AllowedUserProfile` int(11) NOT NULL DEFAULT '0',
  `MinUpdateRate` double NOT NULL,
  `MaxUpdateRate` double NOT NULL,
  `AcceptsCommands` int(11) NOT NULL DEFAULT '0',
  `HasValue` int(11) NOT NULL DEFAULT '0',
  `RangeMin` double NOT NULL,
  `RangeMax` double NOT NULL,
  `RangeStep` double NOT NULL,
  `CommandMinRange` double NOT NULL DEFAULT '-1',
  `CommandMaxRange` double NOT NULL DEFAULT '-1',
  `CommandRangeStep` double NOT NULL DEFAULT '-1',
  `CommandRangeStepType` varchar(256) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'NONE',
  `HumanReadableName` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `Description` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `ContainerManager_Devices`
--

INSERT INTO `ContainerManager_Devices` (`DeviceCode`, `DeviceType`, `SMGDeviceType`, `AllowedUserProfile`, `MinUpdateRate`, `MaxUpdateRate`, `AcceptsCommands`, `HasValue`, `RangeMin`, `RangeMax`, `RangeStep`, `CommandMinRange`, `CommandMaxRange`, `CommandRangeStep`, `CommandRangeStepType`, `HumanReadableName`, `Description`) VALUES
(1, 'ACCELEROMETER', 'Accelerometer', 0, 1000, 3600000, 0, 0, 0, 50, 0.1, -1, -1, -1, 'NONE', '3-Axis Accelerometer', 'Measures the acceleration force in m/s^2 that is applied to a device on all three physical axes (x, y, and z), including the force of gravity.'),
(13, 'AMBIENT_TEMPERATURE', 'Temperature', 1, 1000, 3600000, 0, 0, -50, 100, 0.1, -1, -1, -1, 'NONE', 'Thermometer', 'Ambient temperature sensor in Celsius'),
(15, 'GAME_ROTATION_VECTOR', 'GameRotationVector', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', 'Uncalibrated rotation sensor', 'Identical to TYPE_ROTATION_VECTOR except that it doesn\'t use the geomagnetic field. Therefore the Y axis doesn\'t point north, but instead to some other reference, that reference is allowed to drift by the same order of magnitude as the gyroscope drift around the Z axis.'),
(20, 'GEOMAGNETIC_ROTATION_VECTOR', 'GeomagneticRotationVector', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', 'Geo-magnetic rotation sensor', 'Similar to TYPE_ROTATION_VECTOR, but using a magnetometer instead of using a gyroscope. This sensor uses lower power than the other rotation vectors, because it doesn\'t use the gyroscope. However, it is more noisy and will work best outdoors.'),
(9, 'GRAVITY', 'Gravity', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', 'Gravity sensor', 'Gravity sensor'),
(10, 'LINEAR_LINEAR_ACCELERATION', 'Acceleration', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(2, 'MAGNETIC_FIELD', 'MagneticField', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(14, 'MAGNETIC_FIELD_UNCALIBRATED', 'MagneticFieldUncalibrated', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(6, 'PRESSURE', 'Pressure', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(8, 'PROXIMITY', 'Proximity', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(12, 'RELATIVE_HUMIDITY', 'Humidity', 1, 1000, 3600000, 0, 0, 0, 100, 1, -1, -1, -1, 'NONE', '', ''),
(11, 'ROTATION_VECTOR', 'RotationVector', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(17, 'SIGNIFICANT_MOTION', 'SignificantMotion', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(19, 'STEP_COUNTER', 'StepCounter', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(18, 'STEP_DETECTOR', 'StepDetector', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(5, 'LIGHT', 'Brightness', 1, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'LINEAR', '', ''),
(101, '', 'ConsumptionPowermeter', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(102, '', 'ConsumptionVoltmeter', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(103, '', 'ConsumptionAmperemeter', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(104, '', 'ConsumptionPowermeterAggregated', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(111, '', 'ProductionPowermeter', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(112, '', 'ProductionVoltmeter', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(113, '', 'ProductionAmperemeter', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(114, '', 'ProductionPowermeterAggregated', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(121, '', 'FeedPowerMeter', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(122, '', 'FeedVoltmeter', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(123, '', 'FeedAmperemeter', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(124, '', 'FeedPowerMeterAggregated', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(132, '', 'Heating', 0, 1000, 3600000, 1, 0, 0, 100, 1, 0, 100, 1, 'LINEAR', '', ''),
(135, '', 'Cooling', 0, 1000, 3600000, 1, 0, 0, 100, 1, 0, 100, 1, 'LINEAR', '', ''),
(136, '', 'Blinds', 1, 1000, 3600000, 1, 0, 0, 100, 1, 0, 100, 1, 'LINEAR', '', ''),
(137, '', 'Balance', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(138, '', 'Powerplug', 0, 1000, 3600000, 1, 0, 0, 1, 1, 0, 1, 1, 'LINEAR', '', ''),
(151, '', 'Window', 1, 1000, 3600000, 0, 0, 0, 1, 1, -1, -1, -1, 'NONE', '', ''),
(152, '', 'Door', 1, 1000, 3600000, 0, 0, 0, 1, 1, -1, -1, -1, 'NONE', '', ''),
(153, '', 'Occupancy', 0, 1000, 3600000, 0, 0, 0, 1, 1, -1, -1, -1, 'NONE', '', ''),
(139, '', 'Noise', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(140, '', 'Battery', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(141, '', 'Calculator', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(142, '', 'Frequency', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(143, '', 'LightSimple', 0, -1, -1, 1, 1, 0, 1, 1, 0, 1, 1, 'LINEAR', '', ''),
(144, '', 'LightDimmable', 0, -1, -1, 1, 1, 1, 100, 1, 0, 100, 1, 'LINEAR', '', ''),
(145, '', 'Switch', 0, -1, -1, 1, 1, 0, 1, 1, 0, 1, 1, 'LINEAR', 'Switch configurable [Default: 0/1 off/on]', 'Switch Device to describe any switchable Device (e.g. Aircondition Auto,Cool,Fan)');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `ContainerManager_ContainerEdges`
--
ALTER TABLE `ContainerManager_ContainerEdges`
  ADD PRIMARY KEY (`ContainerEdgeID`),
  ADD KEY `ContainerIDParent` (`ContainerIDParent`,`ContainerIDChild`);

--
-- Indizes für die Tabelle `ContainerManager_Containers`
--
ALTER TABLE `ContainerManager_Containers`
  ADD PRIMARY KEY (`ContainerID`),
  ADD UNIQUE KEY `ContainerID` (`ContainerID`),
  ADD KEY `ContainerHRName` (`ContainerHRName`),
  ADD KEY `ContainerHRName_2` (`ContainerHRName`),
  ADD KEY `ContainerID_2` (`ContainerID`),
  ADD KEY `ContainerType` (`ContainerType`),
  ADD KEY `ContainerFunction` (`ContainerFunction`);

--
-- Indizes für die Tabelle `ContainerManager_Devices`
--
ALTER TABLE `ContainerManager_Devices`
  ADD PRIMARY KEY (`DeviceCode`),
  ADD UNIQUE KEY `DeviceID` (`DeviceCode`),
  ADD KEY `DeviceType` (`DeviceType`),
  ADD KEY `SMGDeviceType` (`SMGDeviceType`),
  ADD KEY `AllowedUserProfile` (`AllowedUserProfile`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `ContainerManager_ContainerEdges`
--
ALTER TABLE `ContainerManager_ContainerEdges`
  MODIFY `ContainerEdgeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=674;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
