-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 09. Mrz 2017 um 12:38
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
-- Tabellenstruktur für Tabelle `UserManager_Contacts`
--

CREATE TABLE `UserManager_Contacts` (
  `UserID` int(255) NOT NULL,
  `EMail` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Phone` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PrimaryContactInfo` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `UserManager_Contacts`
--

INSERT INTO `UserManager_Contacts` (`UserID`, `EMail`, `Phone`, `PrimaryContactInfo`) VALUES
(1, 'example@example.com', '001234567890', '');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `UserManager_Devices`
--

CREATE TABLE `UserManager_Devices` (
  `DeviceID` int(255) NOT NULL,
  `UserID` int(255) NOT NULL,
  `DeviceName` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `OS` varchar(80) COLLATE utf8_unicode_ci NOT NULL,
  `MACAddress` varchar(80) COLLATE utf8_unicode_ci NOT NULL,
  `PrimaryDevice` int(11) NOT NULL DEFAULT '0',
  `publicKey` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `privateKey` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `LastSeen` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `UserManager_Devices`
--

INSERT INTO `UserManager_Devices` (`DeviceID`, `UserID`, `DeviceName`, `OS`, `MACAddress`, `PrimaryDevice`, `publicKey`, `privateKey`, `LastSeen`) VALUES
(123456789, 2, 'Unit-Testing', '', '000111222', 0, '68a45057-5734-4dad-9f86-ab9e32c4506e', 'jg9e65dui5272c45uds3qrf3b8gc71crjq4raq43', 1488891806648);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `UserManager_Profiles`
--

CREATE TABLE `UserManager_Profiles` (
  `ProfileName` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `UserID` int(255) NOT NULL,
  `ProfileItem` int(11) NOT NULL,
  `ProfileValue` double NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `UserManager_Profiles`
--

INSERT INTO `UserManager_Profiles` (`ProfileName`, `UserID`, `ProfileItem`, `ProfileValue`) VALUES
('profile1', 1, 143, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `UserManager_Role`
--

CREATE TABLE `UserManager_Role` (
  `RoleID` int(255) NOT NULL,
  `RoleName` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Permission` varchar(512) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `UserManager_Role`
--

INSERT INTO `UserManager_Role` (`RoleID`, `RoleName`, `Permission`) VALUES
(1, 'God', ''),
(2, 'SystemAdministrator', ''),
(3, 'RoleAdministrator', ''),
(4, 'RuleAdministrator', ''),
(5, 'FacilityManager', ''),
(6, 'Employee', ''),
(7, 'Guest', '');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `UserManager_Rooms`
--

CREATE TABLE `UserManager_Rooms` (
  `ContainerID` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `UserID` int(255) NOT NULL,
  `RoleID` int(255) NOT NULL DEFAULT '7',
  `CurrentLocation` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `UserManager_Rooms`
--

INSERT INTO `UserManager_Rooms` (`ContainerID`, `UserID`, `RoleID`, `CurrentLocation`) VALUES
('1', 1, 7, 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `UserManager_Users`
--

CREATE TABLE `UserManager_Users` (
  `UserID` int(255) NOT NULL,
  `Username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Password` varchar(512) COLLATE utf8_unicode_ci NOT NULL,
  `Nickname` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `RoleID` int(255) NOT NULL,
  `LoggedIn` tinyint(4) NOT NULL,
  `ActiveProfile` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LastSeen` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `UserManager_Users`
--

INSERT INTO `UserManager_Users` (`UserID`, `Username`, `Password`, `Nickname`, `RoleID`, `LoggedIn`, `ActiveProfile`, `LastSeen`) VALUES
(1, 'user', 'ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff', 'Testuser', 6, 1, 'spring', 20170309122824);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `UserManager_Contacts`
--
ALTER TABLE `UserManager_Contacts`
  ADD PRIMARY KEY (`UserID`),
  ADD KEY `UserID` (`UserID`);

--
-- Indizes für die Tabelle `UserManager_Devices`
--
ALTER TABLE `UserManager_Devices`
  ADD PRIMARY KEY (`DeviceID`),
  ADD UNIQUE KEY `publicKey` (`publicKey`),
  ADD KEY `publicKey_2` (`publicKey`),
  ADD KEY `UserID` (`UserID`);

--
-- Indizes für die Tabelle `UserManager_Profiles`
--
ALTER TABLE `UserManager_Profiles`
  ADD PRIMARY KEY (`ProfileName`,`UserID`,`ProfileItem`),
  ADD KEY `profileIndex` (`ProfileName`,`UserID`);

--
-- Indizes für die Tabelle `UserManager_Role`
--
ALTER TABLE `UserManager_Role`
  ADD PRIMARY KEY (`RoleID`);

--
-- Indizes für die Tabelle `UserManager_Rooms`
--
ALTER TABLE `UserManager_Rooms`
  ADD KEY `UserID` (`UserID`);

--
-- Indizes für die Tabelle `UserManager_Users`
--
ALTER TABLE `UserManager_Users`
  ADD PRIMARY KEY (`UserID`),
  ADD UNIQUE KEY `UserID` (`UserID`),
  ADD UNIQUE KEY `Username` (`Username`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `UserManager_Devices`
--
ALTER TABLE `UserManager_Devices`
  MODIFY `DeviceID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=123456790;
--
-- AUTO_INCREMENT für Tabelle `UserManager_Role`
--
ALTER TABLE `UserManager_Role`
  MODIFY `RoleID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT für Tabelle `UserManager_Users`
--
ALTER TABLE `UserManager_Users`
  MODIFY `UserID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
