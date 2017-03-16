-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 09. Mrz 2017 um 12:37
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
  `ContainerIDParent` bigint(20) NOT NULL,
  `ContainerIDChild` bigint(20) NOT NULL,
  `VirtualContainerEdge` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ContainerManager_Containers`
--

CREATE TABLE `ContainerManager_Containers` (
  `ContainerID` bigint(20) NOT NULL,
  `WrapperID` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `DevID` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `ContainerHRName` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `ContainerType` varchar(50) CHARACTER SET latin1 NOT NULL,
  `ContainerFunction` varchar(50) CHARACTER SET latin1 NOT NULL,
  `VirtualContainer` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ContainerManager_Devices`
--

CREATE TABLE `ContainerManager_Devices` (
  `DeviceCode` int(11) NOT NULL,
  `DeviceType` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `SMGDeviceType` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
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
  `CommandRangeStepType` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT 'NONE',
  `HumanReadableName` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Description` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

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
(12, 'RELATIVE_HUMIDITY', 'Humidity', 1, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(11, 'ROTATION_VECTOR', 'Rotationvector', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
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
(151, '', 'Window', 1, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(152, '', 'Door', 1, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
(153, '', 'Occupancy', 0, 1000, 3600000, 0, 0, 0, 0, 0, -1, -1, -1, 'NONE', '', ''),
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
  MODIFY `ContainerEdgeID` int(11) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
