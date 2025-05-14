-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 12, 2025 at 01:15 PM
-- Server version: 8.3.0
-- PHP Version: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `market`
--

-- --------------------------------------------------------

--
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
CREATE TABLE IF NOT EXISTS `brand` (
  `brand_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`brand_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `brand`
--

INSERT INTO `brand` (`brand_id`, `name`) VALUES
(31, 'Activia'),
(39, 'Agricola'),
(16, 'Almette'),
(5, 'Aqua Carpatica'),
(45, 'Ariel'),
(20, 'Avicola'),
(4, 'Barilla'),
(6, 'Bunica'),
(51, 'Cappy'),
(32, 'Colgate'),
(28, 'Cotnari'),
(8, 'Davidoff'),
(52, 'Delaco'),
(7, 'Deluxe'),
(24, 'Dero'),
(30, 'Deroni'),
(18, 'Din Ogradă'),
(41, 'Dorna'),
(46, 'Elseve'),
(35, 'Ferma Veche'),
(14, 'Fin Carre'),
(42, 'Floriol'),
(48, 'Fuchs'),
(19, 'Generic'),
(10, 'Head & Shoulders'),
(50, 'Heidi'),
(36, 'Hochland'),
(44, 'Jacobs'),
(13, 'Jidvei'),
(38, 'K-Bio'),
(37, 'K-Classic'),
(27, 'Kamis'),
(12, 'Kotanyi'),
(2, 'Lidl'),
(40, 'Mărgăritar'),
(43, 'Milka'),
(26, 'Motto'),
(34, 'Olympus'),
(15, 'Pambac'),
(9, 'Persil'),
(3, 'Pilos'),
(29, 'Poiana'),
(17, 'Proxi'),
(47, 'Pufina'),
(49, 'Recas'),
(22, 'RoStar'),
(53, 'Santorini'),
(25, 'Schauma'),
(21, 'Spornic'),
(23, 'Tchibo'),
(33, 'Ursus'),
(11, 'Zewa'),
(1, 'Zuzu');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`category_id`, `name`) VALUES
(7, 'alimente de bază'),
(8, 'băuturi'),
(10, 'cafea'),
(5, 'carne'),
(13, 'condimente'),
(9, 'gustări'),
(12, 'îngrijire personală'),
(1, 'lactate'),
(4, 'legume și fructe'),
(2, 'ouă'),
(3, 'panificație'),
(6, 'paste făinoase'),
(14, 'pește'),
(11, 'produse de menaj');

-- --------------------------------------------------------

--
-- Table structure for table `magazine`
--

DROP TABLE IF EXISTS `magazine`;
CREATE TABLE IF NOT EXISTS `magazine` (
  `magazine_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  PRIMARY KEY (`magazine_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `magazine`
--

INSERT INTO `magazine` (`magazine_id`, `name`) VALUES
(3, 'Kaufland'),
(1, 'Lidl'),
(2, 'Profi');

-- --------------------------------------------------------

--
-- Table structure for table `price_snapshot`
--

DROP TABLE IF EXISTS `price_snapshot`;
CREATE TABLE IF NOT EXISTS `price_snapshot` (
  `snapshot_id` int NOT NULL AUTO_INCREMENT,
  `magazine_id` int NOT NULL,
  `product_id` varchar(10) NOT NULL,
  `snapshot_date` date NOT NULL,
  `package_qty` decimal(8,2) NOT NULL,
  `unit_id` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `currency` char(3) NOT NULL,
  PRIMARY KEY (`snapshot_id`),
  KEY `fk_snapshot_magazine` (`magazine_id`),
  KEY `fk_snapshot_product` (`product_id`),
  KEY `fk_snapshot_unit` (`unit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=167 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `price_snapshot`
--

INSERT INTO `price_snapshot` (`snapshot_id`, `magazine_id`, `product_id`, `snapshot_date`, `package_qty`, `unit_id`, `price`, `currency`) VALUES
(1, 1, 'P001', '2025-05-01', 1.00, 1, 9.90, 'RON'),
(2, 1, 'P002', '2025-05-01', 0.40, 2, 11.50, 'RON'),
(3, 1, 'P005', '2025-05-01', 10.00, 3, 13.20, 'RON'),
(4, 1, 'P008', '2025-05-01', 0.30, 2, 12.80, 'RON'),
(5, 1, 'P011', '2025-05-01', 500.00, 4, 3.50, 'RON'),
(6, 1, 'P014', '2025-05-01', 250.00, 4, 6.80, 'RON'),
(7, 1, 'P017', '2025-05-01', 1.00, 2, 28.50, 'RON'),
(8, 1, 'P020', '2025-05-01', 500.00, 4, 5.80, 'RON'),
(9, 1, 'P021', '2025-05-01', 1.00, 2, 4.40, 'RON'),
(10, 1, 'P024', '2025-05-01', 2.00, 1, 5.20, 'RON'),
(11, 1, 'P026', '2025-05-01', 1.00, 2, 6.10, 'RON'),
(12, 1, 'P028', '2025-05-01', 1.00, 1, 9.20, 'RON'),
(13, 1, 'P031', '2025-05-01', 0.20, 2, 7.10, 'RON'),
(14, 1, 'P034', '2025-05-01', 0.25, 2, 22.40, 'RON'),
(15, 1, 'P037', '2025-05-01', 2.50, 1, 49.90, 'RON'),
(16, 1, 'P040', '2025-05-01', 400.00, 5, 17.80, 'RON'),
(17, 1, 'P043', '2025-05-01', 10.00, 6, 18.90, 'RON'),
(18, 1, 'P046', '2025-05-01', 50.00, 4, 6.00, 'RON'),
(19, 1, 'P049', '2025-05-01', 0.75, 1, 23.50, 'RON'),
(20, 1, 'P052', '2025-05-01', 100.00, 4, 3.90, 'RON'),
(21, 2, 'P001', '2025-05-01', 1.00, 1, 12.90, 'RON'),
(22, 2, 'P002', '2025-05-01', 0.40, 2, 11.50, 'RON'),
(23, 2, 'P005', '2025-05-01', 10.00, 3, 12.20, 'RON'),
(24, 2, 'P008', '2025-05-01', 0.30, 2, 12.80, 'RON'),
(25, 2, 'P011', '2025-05-01', 500.00, 4, 3.50, 'RON'),
(26, 2, 'P014', '2025-05-01', 250.00, 4, 8.80, 'RON'),
(27, 2, 'P017', '2025-05-01', 1.00, 2, 26.50, 'RON'),
(28, 2, 'P020', '2025-05-01', 500.00, 4, 5.80, 'RON'),
(29, 2, 'P021', '2025-05-01', 1.00, 2, 4.40, 'RON'),
(30, 2, 'P024', '2025-05-01', 2.00, 1, 5.20, 'RON'),
(31, 2, 'P026', '2025-05-01', 1.00, 2, 6.10, 'RON'),
(32, 2, 'P028', '2025-05-01', 1.00, 1, 9.20, 'RON'),
(33, 2, 'P031', '2025-05-01', 0.20, 2, 7.10, 'RON'),
(34, 2, 'P034', '2025-05-01', 0.25, 2, 22.40, 'RON'),
(35, 2, 'P037', '2025-05-01', 2.50, 1, 49.90, 'RON'),
(36, 2, 'P040', '2025-05-01', 400.00, 5, 17.80, 'RON'),
(37, 2, 'P043', '2025-05-01', 10.00, 6, 18.90, 'RON'),
(38, 2, 'P046', '2025-05-01', 50.00, 4, 6.00, 'RON'),
(39, 2, 'P049', '2025-05-01', 0.75, 1, 23.50, 'RON'),
(40, 2, 'P052', '2025-05-01', 100.00, 4, 3.90, 'RON'),
(66, 1, 'P001', '2025-05-08', 1.00, 1, 9.80, 'RON'),
(67, 1, 'P002', '2025-05-08', 0.40, 2, 11.60, 'RON'),
(68, 1, 'P005', '2025-05-08', 10.00, 3, 13.10, 'RON'),
(69, 1, 'P008', '2025-05-08', 0.30, 2, 12.90, 'RON'),
(70, 1, 'P011', '2025-05-08', 500.00, 4, 3.40, 'RON'),
(71, 1, 'P014', '2025-05-08', 250.00, 4, 6.90, 'RON'),
(72, 1, 'P017', '2025-05-08', 1.00, 2, 28.90, 'RON'),
(73, 1, 'P020', '2025-05-08', 500.00, 4, 5.70, 'RON'),
(74, 1, 'P021', '2025-05-08', 1.00, 2, 4.30, 'RON'),
(75, 1, 'P024', '2025-05-08', 2.00, 1, 5.10, 'RON'),
(76, 1, 'P026', '2025-05-08', 1.00, 2, 6.00, 'RON'),
(77, 1, 'P028', '2025-05-08', 1.00, 1, 9.30, 'RON'),
(78, 1, 'P031', '2025-05-08', 0.20, 2, 7.00, 'RON'),
(79, 1, 'P034', '2025-05-08', 0.25, 2, 22.60, 'RON'),
(80, 1, 'P037', '2025-05-08', 2.50, 1, 49.50, 'RON'),
(81, 1, 'P040', '2025-05-08', 400.00, 5, 17.90, 'RON'),
(82, 1, 'P043', '2025-05-08', 10.00, 6, 18.80, 'RON'),
(83, 1, 'P046', '2025-05-08', 50.00, 4, 6.05, 'RON'),
(84, 1, 'P049', '2025-05-08', 0.75, 1, 23.30, 'RON'),
(85, 1, 'P052', '2025-05-08', 100.00, 4, 3.80, 'RON'),
(86, 1, 'P055', '2025-05-08', 1.00, 2, 3.00, 'RON'),
(87, 1, 'P057', '2025-05-08', 1.00, 2, 2.80, 'RON'),
(88, 1, 'P064', '2025-05-08', 1.00, 2, 5.50, 'RON'),
(89, 1, 'P065', '2025-05-08', 0.20, 2, 25.00, 'RON'),
(90, 1, 'P066', '2025-05-08', 0.15, 2, 8.50, 'RON'),
(91, 2, 'P001', '2025-05-08', 1.00, 1, 13.00, 'RON'),
(92, 2, 'P004', '2025-05-08', 0.40, 2, 11.40, 'RON'),
(93, 2, 'P007', '2025-05-08', 10.00, 3, 12.50, 'RON'),
(94, 2, 'P010', '2025-05-08', 0.30, 2, 12.70, 'RON'),
(95, 2, 'P013', '2025-05-08', 500.00, 4, 3.60, 'RON'),
(96, 2, 'P016', '2025-05-08', 250.00, 4, 8.70, 'RON'),
(97, 2, 'P019', '2025-05-08', 1.00, 2, 26.80, 'RON'),
(98, 2, 'P020', '2025-05-08', 500.00, 4, 5.90, 'RON'),
(99, 2, 'P023', '2025-05-08', 1.00, 2, 4.50, 'RON'),
(100, 2, 'P024', '2025-05-08', 2.00, 1, 5.30, 'RON'),
(101, 2, 'P027', '2025-05-08', 1.00, 2, 6.15, 'RON'),
(102, 2, 'P030', '2025-05-08', 1.00, 1, 9.10, 'RON'),
(103, 2, 'P033', '2025-05-08', 0.20, 2, 7.20, 'RON'),
(104, 2, 'P036', '2025-05-08', 0.25, 2, 22.00, 'RON'),
(105, 2, 'P039', '2025-05-08', 2.50, 1, 48.90, 'RON'),
(106, 2, 'P042', '2025-05-08', 400.00, 5, 17.50, 'RON'),
(107, 2, 'P045', '2025-05-08', 10.00, 6, 18.50, 'RON'),
(108, 2, 'P048', '2025-05-08', 50.00, 4, 6.10, 'RON'),
(109, 2, 'P051', '2025-05-08', 0.75, 1, 23.00, 'RON'),
(110, 2, 'P054', '2025-05-08', 100.00, 4, 4.00, 'RON'),
(111, 2, 'P059', '2025-05-08', 0.50, 2, 2.60, 'RON'),
(112, 2, 'P067', '2025-05-08', 1.00, 2, 8.00, 'RON'),
(113, 2, 'P068', '2025-05-08', 0.33, 2, 5.00, 'RON'),
(114, 2, 'P069', '2025-05-08', 100.00, 5, 10.50, 'RON'),
(115, 2, 'P070', '2025-05-08', 0.50, 1, 3.80, 'RON'),
(116, 3, 'P001', '2025-05-01', 1.00, 1, 10.10, 'RON'),
(117, 3, 'P003', '2025-05-01', 0.40, 2, 11.80, 'RON'),
(118, 3, 'P006', '2025-05-01', 10.00, 3, 13.50, 'RON'),
(119, 3, 'P009', '2025-05-01', 0.30, 2, 13.10, 'RON'),
(120, 3, 'P012', '2025-05-01', 500.00, 4, 3.60, 'RON'),
(121, 3, 'P015', '2025-05-01', 250.00, 4, 7.00, 'RON'),
(122, 3, 'P018', '2025-05-01', 1.00, 2, 27.90, 'RON'),
(123, 3, 'P020', '2025-05-01', 500.00, 4, 5.90, 'RON'),
(124, 3, 'P022', '2025-05-01', 1.00, 2, 4.50, 'RON'),
(125, 3, 'P025', '2025-05-01', 2.00, 1, 5.30, 'RON'),
(126, 3, 'P027', '2025-05-01', 1.00, 2, 6.20, 'RON'),
(127, 3, 'P029', '2025-05-01', 1.00, 1, 9.50, 'RON'),
(128, 3, 'P032', '2025-05-01', 0.20, 2, 7.50, 'RON'),
(129, 3, 'P035', '2025-05-01', 0.25, 2, 23.00, 'RON'),
(130, 3, 'P038', '2025-05-01', 2.50, 1, 50.50, 'RON'),
(131, 3, 'P041', '2025-05-01', 400.00, 5, 18.00, 'RON'),
(132, 3, 'P044', '2025-05-01', 10.00, 6, 19.20, 'RON'),
(133, 3, 'P047', '2025-05-01', 50.00, 4, 6.10, 'RON'),
(134, 3, 'P050', '2025-05-01', 0.75, 1, 24.00, 'RON'),
(135, 3, 'P053', '2025-05-01', 100.00, 4, 4.10, 'RON'),
(136, 3, 'P056', '2025-05-01', 1.00, 2, 3.20, 'RON'),
(137, 3, 'P058', '2025-05-01', 1.00, 2, 2.90, 'RON'),
(138, 3, 'P059', '2025-05-01', 0.50, 2, 2.50, 'RON'),
(139, 3, 'P060', '2025-05-01', 1.00, 1, 7.80, 'RON'),
(140, 3, 'P062', '2025-05-01', 0.25, 2, 15.00, 'RON'),
(141, 3, 'P001', '2025-05-08', 1.00, 1, 10.00, 'RON'),
(142, 3, 'P003', '2025-05-08', 0.40, 2, 11.70, 'RON'),
(143, 3, 'P006', '2025-05-08', 10.00, 3, 13.60, 'RON'),
(144, 3, 'P009', '2025-05-08', 0.30, 2, 13.00, 'RON'),
(145, 3, 'P012', '2025-05-08', 500.00, 4, 3.55, 'RON'),
(146, 3, 'P015', '2025-05-08', 250.00, 4, 7.10, 'RON'),
(147, 3, 'P018', '2025-05-08', 1.00, 2, 28.00, 'RON'),
(148, 3, 'P020', '2025-05-08', 500.00, 4, 5.85, 'RON'),
(149, 3, 'P022', '2025-05-08', 1.00, 2, 4.45, 'RON'),
(150, 3, 'P025', '2025-05-08', 2.00, 1, 5.25, 'RON'),
(151, 3, 'P027', '2025-05-08', 1.00, 2, 6.25, 'RON'),
(152, 3, 'P029', '2025-05-08', 1.00, 1, 9.60, 'RON'),
(153, 3, 'P032', '2025-05-08', 0.20, 2, 7.40, 'RON'),
(154, 3, 'P035', '2025-05-08', 0.25, 2, 23.10, 'RON'),
(155, 3, 'P038', '2025-05-08', 2.50, 1, 50.80, 'RON'),
(156, 3, 'P041', '2025-05-08', 400.00, 5, 18.10, 'RON'),
(157, 3, 'P044', '2025-05-08', 10.00, 6, 19.30, 'RON'),
(158, 3, 'P047', '2025-05-08', 50.00, 4, 6.15, 'RON'),
(159, 3, 'P050', '2025-05-08', 0.75, 1, 24.10, 'RON'),
(160, 3, 'P053', '2025-05-08', 100.00, 4, 4.15, 'RON'),
(161, 3, 'P056', '2025-05-08', 1.00, 2, 3.10, 'RON'),
(162, 3, 'P058', '2025-05-08', 1.00, 2, 2.95, 'RON'),
(163, 3, 'P059', '2025-05-08', 0.50, 2, 2.40, 'RON'),
(164, 3, 'P061', '2025-05-08', 1.00, 1, 7.60, 'RON'),
(165, 3, 'P063', '2025-05-08', 0.25, 2, 14.80, 'RON'),
(166, 2, 'P019', '2025-05-01', 1.00, 2, 26.80, 'RON');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `product_id` varchar(10) NOT NULL,
  `product_name` varchar(200) NOT NULL,
  `category_id` int NOT NULL,
  `brand_id` int DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `fk_product_brand` (`brand_id`),
  KEY `fk_product_category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`product_id`, `product_name`, `category_id`, `brand_id`) VALUES
('P001', 'lapte zuzu', 1, 1),
('P002', 'iaurt grecesc', 1, 2),
('P003', 'iaurt grecesc', 1, 34),
('P004', 'iaurt grecesc', 1, 17),
('P005', 'ouă mărimea M', 2, 2),
('P006', 'ouă mărimea M', 2, 35),
('P007', 'ouă mărimea M', 2, 18),
('P008', 'brânză telemea', 1, 3),
('P009', 'brânză telemea', 1, 36),
('P010', 'brânză telemea', 1, 17),
('P011', 'pâine albă', 3, 2),
('P012', 'pâine albă', 3, 37),
('P013', 'pâine albă', 3, 17),
('P014', 'roșii cherry', 4, 2),
('P015', 'roșii cherry', 4, 38),
('P016', 'roșii cherry', 4, 19),
('P017', 'piept pui', 5, 2),
('P018', 'piept pui', 5, 39),
('P019', 'piept pui', 5, 20),
('P020', 'spaghetti nr.5', 6, 4),
('P021', 'zahăr tos', 7, 2),
('P022', 'zahăr tos', 7, 40),
('P023', 'zahăr tos', 7, 17),
('P024', 'apă plată', 8, 5),
('P025', 'apă plată', 8, 41),
('P026', 'banane', 4, 2),
('P027', 'banane', 4, 19),
('P028', 'ulei floarea-soarelui', 7, 6),
('P029', 'ulei floarea-soarelui', 7, 42),
('P030', 'ulei floarea-soarelui', 7, 21),
('P031', 'biscuiți cu unt', 9, 7),
('P032', 'biscuiți cu unt', 9, 43),
('P033', 'biscuiți cu unt', 9, 22),
('P034', 'cafea măcinată', 10, 8),
('P035', 'cafea măcinată', 10, 44),
('P036', 'cafea măcinată', 10, 23),
('P037', 'detergent lichid', 11, 9),
('P038', 'detergent lichid', 11, 45),
('P039', 'detergent lichid', 11, 24),
('P040', 'șampon păr gras', 12, 10),
('P041', 'șampon păr gras', 12, 46),
('P042', 'șampon păr gras', 12, 25),
('P043', 'hârtie igienică 3 straturi', 11, 11),
('P044', 'hârtie igienică 3 straturi', 11, 47),
('P045', 'hârtie igienică 3 straturi', 11, 26),
('P046', 'piper negru măcinat', 13, 12),
('P047', 'piper negru măcinat', 13, 48),
('P048', 'piper negru măcinat', 13, 27),
('P049', 'vin alb demisec', 8, 13),
('P050', 'vin alb demisec', 8, 49),
('P051', 'vin alb demisec', 8, 28),
('P052', 'ciocolată neagră 70%', 9, 14),
('P053', 'ciocolată neagră 70%', 9, 50),
('P054', 'ciocolată neagră 70%', 9, 29),
('P055', 'cartofi albi', 4, 2),
('P056', 'cartofi albi', 4, 19),
('P057', 'ceapă galbenă', 4, 2),
('P058', 'ceapă galbenă', 4, 19),
('P059', 'morcovi', 4, 19),
('P060', 'suc portocale', 8, 51),
('P061', 'suc portocale', 8, 53),
('P062', 'cașcaval', 1, 52),
('P063', 'cașcaval', 1, 37),
('P064', 'mălai extra', 7, 15),
('P065', 'file somon', 14, 2),
('P066', 'cremă de brânză', 1, 16),
('P067', 'orez bob lung', 7, 30),
('P068', 'iaurt de băut', 1, 31),
('P069', 'pastă de dinți', 12, 32),
('P070', 'bere blondă', 8, 33);

-- --------------------------------------------------------

--
-- Table structure for table `promotion`
--

DROP TABLE IF EXISTS `promotion`;
CREATE TABLE IF NOT EXISTS `promotion` (
  `promotion_id` int NOT NULL AUTO_INCREMENT,
  `from_date` date NOT NULL,
  `to_date` date NOT NULL,
  `discount_pct` decimal(5,2) NOT NULL,
  PRIMARY KEY (`promotion_id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `promotion`
--

INSERT INTO `promotion` (`promotion_id`, `from_date`, `to_date`, `discount_pct`) VALUES
(1, '2025-05-01', '2025-05-07', 10.00),
(2, '2025-05-01', '2025-05-07', 15.00),
(3, '2025-05-03', '2025-05-09', 20.00),
(4, '2025-05-04', '2025-05-10', 12.00),
(5, '2025-05-02', '2025-05-08', 5.00),
(6, '2025-05-02', '2025-05-08', 8.00),
(7, '2025-05-06', '2025-05-12', 25.00),
(8, '2025-05-05', '2025-05-11', 15.00),
(9, '2025-05-03', '2025-05-09', 18.00),
(10, '2025-05-01', '2025-05-07', 5.00),
(11, '2025-05-04', '2025-05-10', 7.00),
(12, '2025-05-02', '2025-05-08', 6.00),
(13, '2025-05-05', '2025-05-11', 10.00),
(14, '2025-05-06', '2025-05-12', 12.00),
(15, '2025-05-04', '2025-05-10', 9.00),
(16, '2025-05-03', '2025-05-09', 14.00),
(17, '2025-05-06', '2025-05-12', 6.00),
(18, '2025-05-05', '2025-05-11', 11.00),
(19, '2025-05-06', '2025-05-12', 5.00),
(20, '2025-05-01', '2025-05-07', 8.00),
(21, '2025-05-01', '2025-05-07', 12.00),
(22, '2025-05-02', '2025-05-08', 10.00),
(23, '2025-05-03', '2025-05-09', 5.00),
(24, '2025-05-04', '2025-05-10', 15.00),
(25, '2025-05-01', '2025-05-07', 20.00),
(27, '2025-05-03', '2025-05-09', 10.00),
(28, '2025-05-04', '2025-05-10', 6.00),
(29, '2025-05-01', '2025-05-07', 7.00),
(30, '2025-05-01', '2025-05-07', 10.00),
(31, '2025-05-01', '2025-05-07', 22.00),
(33, '2025-05-02', '2025-05-08', 12.00),
(34, '2025-05-02', '2025-05-08', 7.00),
(36, '2025-05-03', '2025-05-09', 8.00),
(37, '2025-05-03', '2025-05-09', 10.00),
(38, '2025-05-04', '2025-05-10', 10.00),
(40, '2025-05-08', '2025-05-14', 12.00),
(42, '2025-05-08', '2025-05-14', 7.00),
(43, '2025-05-08', '2025-05-14', 5.00),
(44, '2025-05-09', '2025-05-15', 8.00),
(45, '2025-05-09', '2025-05-15', 20.00),
(46, '2025-05-09', '2025-05-15', 12.00),
(47, '2025-05-09', '2025-05-15', 10.00),
(48, '2025-05-10', '2025-05-16', 15.00),
(49, '2025-05-10', '2025-05-16', 18.00),
(51, '2025-05-10', '2025-05-16', 8.00),
(52, '2025-05-08', '2025-05-14', 9.00),
(54, '2025-05-08', '2025-05-14', 6.00),
(56, '2025-05-09', '2025-05-15', 18.00),
(59, '2025-05-10', '2025-05-16', 12.00),
(63, '2025-05-08', '2025-05-14', 10.00),
(65, '2025-05-09', '2025-05-15', 15.00),
(67, '2025-05-09', '2025-05-15', 5.00),
(68, '2025-05-10', '2025-05-16', 7.00),
(71, '2025-05-10', '2025-05-16', 6.00),
(74, '2025-05-08', '2025-05-14', 6.00),
(81, '2025-05-10', '2025-05-16', 6.00),
(83, '2025-05-08', '2025-05-14', 11.00),
(84, '2025-05-09', '2025-05-15', 7.00),
(86, '2025-05-08', '2025-05-14', 8.00),
(87, '2025-05-09', '2025-05-15', 12.00),
(88, '2025-05-10', '2025-05-16', 11.00),
(89, '2025-05-08', '2025-05-14', 13.00),
(91, '2025-05-10', '2025-05-16', 5.00);

-- --------------------------------------------------------

--
-- Table structure for table `promotion_apply`
--

DROP TABLE IF EXISTS `promotion_apply`;
CREATE TABLE IF NOT EXISTS `promotion_apply` (
  `apply_id` int NOT NULL AUTO_INCREMENT,
  `promotion_id` int NOT NULL,
  `price_snapshot_id` int NOT NULL,
  PRIMARY KEY (`apply_id`),
  KEY `fk_apply_promotion` (`promotion_id`),
  KEY `fk_apply_snapshot` (`price_snapshot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `promotion_apply`
--

INSERT INTO `promotion_apply` (`apply_id`, `promotion_id`, `price_snapshot_id`) VALUES
(1, 1, 1),
(2, 2, 4),
(3, 3, 8),
(4, 4, 14),
(5, 5, 5),
(6, 6, 6),
(7, 7, 15),
(8, 8, 16),
(9, 1, 19),
(10, 9, 20),
(11, 10, 10),
(12, 11, 3),
(13, 12, 7),
(14, 13, 12),
(15, 14, 18),
(16, 15, 13),
(17, 16, 17),
(18, 17, 11),
(19, 18, 2),
(20, 19, 6),
(21, 20, 21),
(22, 23, 30),
(51, 33, 122),
(52, 9, 123),
(53, 38, 129),
(54, 31, 130),
(55, 33, 131),
(56, 36, 134),
(57, 24, 135),
(58, 10, 136),
(59, 34, 139),
(62, 40, 66),
(64, 44, 72),
(65, 48, 79),
(66, 42, 77),
(67, 45, 80),
(68, 49, 81),
(70, 46, 85),
(72, 43, 88),
(73, 47, 89),
(74, 51, 90),
(75, 52, 92),
(76, 63, 93),
(77, 65, 98),
(78, 68, 100),
(79, 56, 105),
(80, 59, 107),
(81, 63, 109),
(82, 67, 111),
(83, 51, 112),
(84, 63, 113),
(85, 65, 114),
(88, 52, 141),
(89, 83, 143),
(90, 84, 145),
(91, 86, 152),
(92, 88, 154),
(93, 89, 157),
(94, 47, 158),
(95, 91, 162),
(96, 52, 164);

-- --------------------------------------------------------

--
-- Table structure for table `unit`
--

DROP TABLE IF EXISTS `unit`;
CREATE TABLE IF NOT EXISTS `unit` (
  `unit_id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(10) NOT NULL,
  `label` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`unit_id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `unit`
--

INSERT INTO `unit` (`unit_id`, `code`, `label`) VALUES
(1, 'l', 'litru'),
(2, 'kg', 'kilogram'),
(3, 'buc', 'bucată'),
(4, 'g', 'gram'),
(5, 'ml', 'mililitru'),
(6, 'role', 'role');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `price_snapshot`
--
ALTER TABLE `price_snapshot`
  ADD CONSTRAINT `fk_snapshot_magazine` FOREIGN KEY (`magazine_id`) REFERENCES `magazine` (`magazine_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_snapshot_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_snapshot_unit` FOREIGN KEY (`unit_id`) REFERENCES `unit` (`unit_id`) ON DELETE RESTRICT ON UPDATE CASCADE;

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `fk_product_brand` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`brand_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_product_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE RESTRICT ON UPDATE CASCADE;

--
-- Constraints for table `promotion_apply`
--
ALTER TABLE `promotion_apply`
  ADD CONSTRAINT `fk_apply_promotion` FOREIGN KEY (`promotion_id`) REFERENCES `promotion` (`promotion_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_apply_snapshot` FOREIGN KEY (`price_snapshot_id`) REFERENCES `price_snapshot` (`snapshot_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
