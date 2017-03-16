-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 09. Mrz 2017 um 12:39
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
-- Tabellenstruktur für Tabelle `DoubleEvents`
--

CREATE TABLE `DoubleEvents` (
  `ContainerID` bigint(20) NOT NULL,
  `value` double NOT NULL,
  `maxAbsError` double NOT NULL,
  `timestamp` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `DoubleEvents`
--
ALTER TABLE `DoubleEvents`
  ADD UNIQUE KEY `uniq` (`ContainerID`,`timestamp`),
  ADD KEY `time` (`timestamp`),
  ADD KEY `devid` (`ContainerID`),
  ADD KEY `timestamp` (`timestamp`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
