-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Temps de generació: 05-03-2020 a les 16:03:22
-- Versió del servidor: 10.1.13-MariaDB
-- Versió de PHP: 7.0.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de dades: `activitat5`
--
CREATE DATABASE IF NOT EXISTS `activitat5` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `activitat5`;

-- --------------------------------------------------------

--
-- Estructura de la taula `moviment`
--

CREATE TABLE `moviment` (
  `partidaId` int(10) NOT NULL,
  `numMoviment` int(255) NOT NULL,
  `taulell` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Bolcant dades de la taula `moviment`
--

INSERT INTO `moviment` (`partidaId`, `numMoviment`, `taulell`) VALUES
(24, 1, 'X X X X  X   X X    X                           O O O O  O O O O'),
(24, 2, 'X X X X  X   X X    X                    O      O   O O  O O O O'),
(24, 3, 'X X X X  X   X X             X           O      O   O O  O O O O'),
(24, 4, 'X X X X  X   X X             X           O O    O     O  O O O O'),
(25, 1, 'X X X X  X   X X    X                           O O O O  O O O O'),
(25, 2, 'X X X X  X   X X    X                      O    O O   O  O O O O');

-- --------------------------------------------------------

--
-- Estructura de la taula `partida`
--

CREATE TABLE `partida` (
  `partidaId` int(10) NOT NULL,
  `data` datetime DEFAULT CURRENT_TIMESTAMP,
  `guanyador` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Bolcant dades de la taula `partida`
--

INSERT INTO `partida` (`partidaId`, `data`, `guanyador`) VALUES
(21, '2020-02-28 19:19:58', ''),
(22, '2020-02-28 19:22:08', ''),
(23, '2020-03-04 20:18:38', ''),
(24, '2020-03-04 20:25:41', ''),
(25, '2020-03-05 15:52:08', '');

--
-- Indexos per taules bolcades
--

--
-- Index de la taula `moviment`
--
ALTER TABLE `moviment`
  ADD PRIMARY KEY (`partidaId`,`numMoviment`);

--
-- Index de la taula `partida`
--
ALTER TABLE `partida`
  ADD PRIMARY KEY (`partidaId`);

--
-- AUTO_INCREMENT per les taules bolcades
--

--
-- AUTO_INCREMENT per la taula `partida`
--
ALTER TABLE `partida`
  MODIFY `partidaId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
--
-- Restriccions per taules bolcades
--

--
-- Restriccions per la taula `moviment`
--
ALTER TABLE `moviment`
  ADD CONSTRAINT `hjghjh` FOREIGN KEY (`partidaId`) REFERENCES `partida` (`partidaId`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
