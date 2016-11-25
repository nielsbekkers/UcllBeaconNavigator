-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Gegenereerd op: 25 nov 2016 om 09:31
-- Serverversie: 10.1.16-MariaDB
-- PHP-versie: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ucllbeacons`
--
CREATE DATABASE IF NOT EXISTS `ucllbeacons` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `ucllbeacons`;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `beacons`
--

CREATE TABLE `beacons` (
  `major` varchar(20) NOT NULL,
  `minor` varchar(20) NOT NULL,
  `UUID` varchar(50) NOT NULL,
  `name` text NOT NULL,
  `locationTitle` text NOT NULL,
  `locationDescription` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `beacons`
--

INSERT INTO `beacons` (`major`, `minor`, `UUID`, `name`, `locationTitle`, `locationDescription`) VALUES
('12345', '', '', '', '', ''),
('1234555555', '000', '', '', '', ''),
('12345587564', '', '', '', '', ''),
('1234567890', '0001', 'B9407F30-F5F8-466E-AFF9-25556B57FE6D', 'BlueberryPi', 'location', 'description');

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `beacons`
--
ALTER TABLE `beacons`
  ADD PRIMARY KEY (`major`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
