-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-05-2025 a las 12:47:32
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `parkingdpo`
--
CREATE DATABASE IF NOT EXISTS parkingdpo;
USE parkingdpo;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cancelled_reservations`
--

CREATE TABLE `cancelled_reservations` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `slot_id` int(11) DEFAULT NULL,
  `vehicle_plate` varchar(15) CHARACTER SET utf8 COLLATE utf8_spanish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `entry_leave_logs`
--

CREATE TABLE `entry_leave_logs` (
  `id` int(11) NOT NULL,
  `slot_id` int(11) NOT NULL,
  `vehicle_plate` varchar(15) NOT NULL,
  `action` enum('entry','leave') NOT NULL,
  `timestamp` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `entry_leave_logs`
--

INSERT INTO `entry_leave_logs` (`id`, `slot_id`, `vehicle_plate`, `action`, `timestamp`) VALUES
(1, 7, 'YTL013', 'entry', '2025-05-21 12:48:52'),
(2, 8, 'WJV537', 'entry', '2025-05-21 12:48:56'),
(3, 9, 'ZJO888', 'entry', '2025-05-21 12:51:50'),
(4, 10, 'KMN762', 'entry', '2025-05-21 12:51:54'),
(5, 11, 'QOZ571', 'entry', '2025-05-21 12:51:57'),
(6, 9, 'ZJO888', 'leave', '2025-05-21 12:52:00'),
(7, 9, 'MFH420', 'entry', '2025-05-21 12:52:15'),
(8, 5, 'KMW633', 'leave', '2025-05-21 12:52:18'),
(9, 5, 'WUH751', 'entry', '2025-05-21 12:52:21'),
(10, 5, 'WUH751', 'leave', '2025-05-21 12:52:24'),
(11, 5, 'LTF362', 'entry', '2025-05-21 12:52:27'),
(12, 10, 'KMN762', 'leave', '2025-05-21 12:52:30'),
(13, 10, 'KAN584', 'entry', '2025-05-21 12:52:39'),
(14, 10, 'KAN584', 'leave', '2025-05-21 12:52:42'),
(15, 10, 'XER000', 'entry', '2025-05-21 12:52:51'),
(16, 5, 'LTF362', 'leave', '2025-05-21 12:52:54'),
(17, 5, 'PJA743', 'entry', '2025-05-21 12:52:57'),
(18, 4, 'BLR606', 'leave', '2025-05-21 12:53:00'),
(19, 4, 'IKR231', 'entry', '2025-05-21 12:53:15'),
(20, 6, 'IKW113', 'leave', '2025-05-21 12:53:18'),
(21, 6, 'EXK334', 'entry', '2025-05-21 12:53:27'),
(22, 11, 'QOZ571', 'leave', '2025-05-21 12:53:30'),
(23, 11, 'XPL856', 'entry', '2025-05-21 12:53:51'),
(24, 5, 'PJA743', 'leave', '2025-05-21 12:53:54'),
(25, 5, 'RZB050', 'entry', '2025-05-21 12:53:57'),
(26, 4, 'IKR231', 'leave', '2025-05-21 12:54:00'),
(27, 4, 'FKJ776', 'entry', '2025-05-21 12:54:06'),
(28, 9, 'MFH420', 'leave', '2025-05-21 12:54:09'),
(29, 9, 'MZH804', 'entry', '2025-05-21 12:54:15'),
(30, 8, 'WJV537', 'leave', '2025-05-21 12:54:18'),
(31, 8, 'KPP123', 'entry', '2025-05-21 12:54:30'),
(32, 11, 'XPL856', 'leave', '2025-05-21 12:54:33'),
(33, 11, 'DRI458', 'entry', '2025-05-21 12:54:36'),
(34, 6, 'EXK334', 'leave', '2025-05-22 11:01:25'),
(35, 6, 'TOO511', 'entry', '2025-05-22 11:01:32'),
(36, 7, 'YTL013', 'leave', '2025-05-24 19:26:19'),
(37, 7, 'WZD206', 'entry', '2025-05-24 19:26:39'),
(38, 4, 'FKJ776', 'leave', '2025-05-24 19:33:31'),
(39, 4, 'GEQ235', 'entry', '2025-05-24 19:34:04'),
(40, 9, 'MZH804', 'leave', '2025-05-24 19:34:41'),
(41, 9, 'ZUH507', 'entry', '2025-05-24 19:34:48'),
(42, 7, 'WZD206', 'leave', '2025-05-24 19:34:55'),
(43, 7, 'CIR121', 'entry', '2025-05-24 19:35:09'),
(44, 7, 'CIR121', 'leave', '2025-05-24 19:35:16'),
(45, 7, 'CXQ852', 'entry', '2025-05-24 19:35:45'),
(46, 8, 'KPP123', 'leave', '2025-05-24 19:36:34'),
(47, 6, 'UOU741', 'entry', '2025-05-24 19:43:45'),
(48, 12, 'TRC256', 'entry', '2025-05-24 19:47:05'),
(49, 18, 'JUV335', 'entry', '2025-05-24 19:47:23'),
(50, 10, 'ODU637', 'entry', '2025-05-24 19:47:41'),
(51, 25, 'YFS366', 'entry', '2025-05-24 19:47:59'),
(52, 16, 'RRK363', 'entry', '2025-05-24 19:48:17'),
(53, 13, 'LMN234', 'leave', '2025-05-24 19:48:35'),
(54, 31, 'WLA861', 'entry', '2025-05-24 19:48:53');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `parkings`
--

CREATE TABLE `parkings` (
  `plant` int(11) NOT NULL,
  `size` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `parkings`
--

INSERT INTO `parkings` (`plant`, `size`) VALUES
(1, 60),
(2, 60),
(3, 60);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `slots`
--

CREATE TABLE `slots` (
  `id` int(11) NOT NULL,
  `plant` int(11) NOT NULL,
  `slot_number` int(11) NOT NULL,
  `is_occupied` tinyint(1) DEFAULT 0,
  `vehicle_plate` varchar(15) DEFAULT NULL,
  `booked` tinyint(1) DEFAULT 0,
  `vehicle_type` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `slots`
--

INSERT INTO `slots` (`id`, `plant`, `slot_number`, `is_occupied`, `vehicle_plate`, `booked`, `vehicle_type`) VALUES
(1, 1, 4, 0, 'QRS123', 1, 'Car'),
(2, 1, 4, 1, 'BCD678', 0, 'Car'),
(3, 1, 4, 0, 'VWX012', 1, 'Car'),
(4, 1, 4, 1, 'XYZ678', 0, 'Car'),
(5, 1, 4, 1, 'RST012', 0, 'Car'),
(6, 1, 2, 1, 'UOU741', 0, 'Motorbike'),
(7, 1, 2, 0, 'OPQ567', 1, 'Motorbike'),
(8, 1, 6, 0, NULL, 0, 'Truck'),
(9, 1, 4, 1, 'NOP890', 0, 'Car'),
(10, 1, 2, 1, 'ODU637', 0, 'Motorbike'),
(11, 1, 6, 0, 'CDE345', 1, 'Truck'),
(12, 1, 4, 1, 'TRC256', 0, 'Car'),
(13, 1, 2, 0, NULL, 0, 'Motorbike'),
(14, 1, 6, 0, NULL, 0, 'Truck'),
(15, 1, 4, 0, 'MNO345', 1, 'Car'),
(16, 1, 2, 1, 'RRK363', 0, 'Motorbike'),
(17, 1, 6, 1, 'PQR678', 0, 'Truck'),
(18, 1, 4, 1, 'JUV335', 0, 'Car'),
(19, 1, 2, 1, 'STU901', 0, 'Motorbike'),
(20, 1, 6, 0, NULL, 0, 'Truck'),
(21, 2, 6, 0, NULL, 0, 'Truck'),
(22, 2, 4, 1, 'EFG123', 0, 'Car'),
(23, 2, 2, 0, NULL, 0, 'Motorbike'),
(24, 2, 6, 0, 'HIJ456', 1, 'Truck'),
(25, 2, 4, 1, 'YFS366', 0, 'Car'),
(26, 2, 2, 1, 'KLM789', 0, 'Motorbike'),
(27, 2, 6, 0, NULL, 0, 'Truck'),
(28, 2, 4, 1, 'NOP012', 0, 'Car'),
(29, 2, 2, 0, NULL, 0, 'Motorbike'),
(30, 2, 6, 0, 'QRS345', 1, 'Truck'),
(31, 2, 4, 1, 'WLA861', 0, 'Car'),
(32, 2, 2, 1, 'TUV678', 0, 'Motorbike'),
(33, 2, 6, 0, NULL, 0, 'Truck'),
(34, 2, 4, 0, 'WXY901', 1, 'Car'),
(35, 2, 2, 0, NULL, 0, 'Motorbike'),
(36, 2, 6, 0, 'ZAB234', 1, 'Truck'),
(37, 2, 4, 0, NULL, 0, 'Car'),
(38, 2, 2, 1, 'CDE567', 0, 'Motorbike'),
(39, 2, 6, 0, NULL, 0, 'Truck'),
(40, 2, 4, 0, 'FGH890', 1, 'Car'),
(41, 3, 2, 0, NULL, 0, 'Motorbike'),
(42, 3, 6, 1, 'IJK123', 0, 'Truck'),
(43, 3, 4, 0, NULL, 0, 'Car'),
(44, 3, 2, 0, 'LMN456', 1, 'Motorbike'),
(45, 3, 6, 0, NULL, 0, 'Truck'),
(46, 3, 4, 1, 'OPQ789', 0, 'Car'),
(47, 3, 2, 0, NULL, 0, 'Motorbike'),
(48, 3, 6, 1, 'RST012', 0, 'Truck'),
(49, 3, 4, 0, NULL, 0, 'Car'),
(50, 3, 2, 0, 'UVW345', 1, 'Motorbike'),
(51, 3, 6, 0, NULL, 0, 'Truck'),
(52, 3, 4, 1, 'XYZ678', 0, 'Car'),
(53, 3, 2, 0, NULL, 0, 'Motorbike'),
(54, 3, 6, 0, 'ABC901', 1, 'Truck'),
(55, 3, 4, 0, NULL, 0, 'Car'),
(56, 3, 2, 1, 'DEF234', 0, 'Motorbike'),
(57, 3, 6, 0, NULL, 0, 'Truck'),
(58, 3, 4, 0, 'GHI567', 1, 'Car'),
(59, 3, 2, 0, NULL, 0, 'Motorbike'),
(60, 3, 6, 1, 'JKL890', 0, 'Truck');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `email`) VALUES
(1, 'gere', 'Gere1234', 'gere@gmail.com'),
(2, 'user2', 'User1234', 'user2@gmail.com'),
(3, 'user3', 'User1234', 'user3@gmail.com'),
(4, 'user4', 'User1234', 'user4@gmail.com'),
(5, 'sergi', 'sergi', 'sergi@gmail.com'),
(6, 'user6', 'User1234', 'user6@gmail.com'),
(7, 'user7', 'User1234', 'user7@gmail.com'),
(8, 'user8', 'User1234', 'user8@gmail.com'),
(9, 'user9', 'User1234', 'user9@gmail.com'),
(10, 'user10', 'User1234', 'user10@gmail.com'),
(11, 'user11', 'User1234', 'user11@gmail.com'),
(12, 'user12', 'User1234', 'user12@gmail.com'),
(13, 'user13', 'User1234', 'user13@gmail.com'),
(14, 'user14', 'User1234', 'user14@gmail.com'),
(15, 'user15', 'User1234', 'user15@gmail.com'),
(16, 'user16', 'User1234', 'user16@gmail.com'),
(17, 'user17', 'User1234', 'user17@gmail.com'),
(18, 'user18', 'User1234', 'user18@gmail.com'),
(19, 'user19', 'User1234', 'user19@gmail.com'),
(20, 'user20', 'User1234', 'user20@gmail.com'),
(21, 'user21', 'User1234', 'user21@gmail.com'),
(22, 'user22', 'User1234', 'user22@gmail.com'),
(23, 'user23', 'User1234', 'user23@gmail.com'),
(24, 'user24', 'User1234', 'user24@gmail.com'),
(25, 'user25', 'User1234', 'user25@gmail.com'),
(26, 'user26', 'User1234', 'user26@gmail.com'),
(27, 'user27', 'User1234', 'user27@gmail.com'),
(28, 'user28', 'User1234', 'user28@gmail.com'),
(29, 'user29', 'User1234', 'user29@gmail.com'),
(30, 'user30', 'User1234', 'user30@gmail.com'),
(31, 'user31', 'User1234', 'user31@gmail.com'),
(32, 'user32', 'User1234', 'user32@gmail.com'),
(33, 'user33', 'User1234', 'user33@gmail.com'),
(34, 'user34', 'User1234', 'user34@gmail.com'),
(35, 'user35', 'User1234', 'user35@gmail.com'),
(36, 'user36', 'User1234', 'user36@gmail.com'),
(37, 'user37', 'User1234', 'user37@gmail.com'),
(38, 'user38', 'User1234', 'user38@gmail.com'),
(39, 'user39', 'User1234', 'user39@gmail.com'),
(40, 'user40', 'User1234', 'user40@gmail.com'),
(41, 'user41', 'User1234', 'user41@gmail.com'),
(42, 'user42', 'User1234', 'user42@gmail.com'),
(43, 'user43', 'User1234', 'user43@gmail.com'),
(44, 'user44', 'User1234', 'user44@gmail.com'),
(45, 'user45', 'User1234', 'user45@gmail.com'),
(46, 'user46', 'User1234', 'user46@gmail.com'),
(47, 'user47', 'User1234', 'user47@gmail.com'),
(48, 'user48', 'User1234', 'user48@gmail.com'),
(49, 'user49', 'User1234', 'user49@gmail.com'),
(50, 'user50', 'User1234', 'user50@gmail.com'),
(51, 'user51', 'User1234', 'user51@gmail.com'),
(52, 'user52', 'User1234', 'user52@gmail.com'),
(53, 'user53', 'User1234', 'user53@gmail.com'),
(54, 'user54', 'User1234', 'user54@gmail.com'),
(55, 'user55', 'User1234', 'user55@gmail.com'),
(56, 'user56', 'User1234', 'user56@gmail.com'),
(57, 'user57', 'User1234', 'user57@gmail.com'),
(58, 'user58', 'User1234', 'user58@gmail.com'),
(59, 'user59', 'User1234', 'user59@gmail.com'),
(60, 'user60', 'User1234', 'user60@gmail.com'),
(61, 'user61', 'User1234', 'user61@gmail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehicles`
--

CREATE TABLE `vehicles` (
  `plate` varchar(15) NOT NULL,
  `brand` varchar(50) NOT NULL,
  `model` varchar(50) NOT NULL,
  `color` varchar(30) NOT NULL,
  `owner_id` int(11) NOT NULL,
  `type_vehicle` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `vehicles`
--

INSERT INTO `vehicles` (`plate`, `brand`, `model`, `color`, `owner_id`, `type_vehicle`) VALUES
('ABC123', 'Toyota', 'Corolla', 'Red', 1, 'Car'),
('ABC789', 'Ford', 'Explorer', 'Black', 53, 'SUV'),
('ABC901', 'Kia', 'Rio', 'Silver', 27, 'Compact'),
('ABN423', 'SimBrand', 'SimModel', 'Gray', 38, 'Motorbike'),
('AKD057', 'SimBrand', 'SimModel', 'Gray', 38, 'Motorbike'),
('AKI306', 'SimBrand', 'SimModel', 'Gray', 27, 'Motorbike'),
('AMH215', 'SimBrand', 'SimModel', 'Gray', 29, 'Car'),
('ANA028', 'SimBrand', 'SimModel', 'Gray', 31, 'Motorbike'),
('AOX078', 'SimBrand', 'SimModel', 'Gray', 17, 'Motorbike'),
('APW364', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('AQW048', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('ATB715', 'SimBrand', 'SimModel', 'Gray', 57, 'Motorbike'),
('ATM587', 'SimBrand', 'SimModel', 'Gray', 24, 'Car'),
('ATS021', 'SimBrand', 'SimModel', 'Gray', 7, 'Car'),
('AUM564', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('AWO885', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('AXR705', 'SimBrand', 'SimModel', 'Gray', 34, 'Car'),
('AYH053', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('AZD414', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('AZK637', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('BAI717', 'SimBrand', 'SimModel', 'Gray', 4, 'Car'),
('BAT426', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('BAY230', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('BCD678', 'Hyundai', 'Genesis', 'Black', 36, 'Sport'),
('BCD890', 'Subaru', 'Impreza', 'Green', 10, 'Sedan'),
('BCM186', 'SimBrand', 'SimModel', 'Gray', 4, 'Motorbike'),
('BDJ654', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('BDT542', 'SimBrand', 'SimModel', 'Gray', 10, 'Car'),
('BEH324', 'SimBrand', 'SimModel', 'Gray', 23, 'Motorbike'),
('BEP786', 'SimBrand', 'SimModel', 'Gray', 40, 'Motorbike'),
('BGQ206', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('BIY773', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('BJD376', 'SimBrand', 'SimModel', 'Gray', 41, 'Car'),
('BKZ734', 'SimBrand', 'SimModel', 'Gray', 58, 'Motorbike'),
('BLR606', 'SimBrand', 'SimModel', 'Gray', 37, 'Car'),
('BQC613', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('BRA645', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('BRL511', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('BVV718', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('BWN104', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('BXO821', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('BYF408', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('CCP652', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('CCT200', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('CDE345', 'Nissan', 'Rogue', 'Blue', 45, 'SUV'),
('CDE567', 'Volkswagen', 'Jetta', 'White', 19, 'Sedan'),
('CFJ347', 'SimBrand', 'SimModel', 'Gray', 59, 'Motorbike'),
('CGU760', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('CID416', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('CIR121', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('CMV057', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('CPN735', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('CQG287', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('CRR703', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('CSE062', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('CSQ221', 'SimBrand', 'SimModel', 'Gray', 36, 'Car'),
('CSS041', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('CTO753', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('CUP306', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('CUX037', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('CXQ852', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('CXT886', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('CYY741', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('CZB223', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('CZG847', 'SimBrand', 'SimModel', 'Gray', 34, 'Car'),
('CZJ821', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('DBW615', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('DDB501', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('DDZ105', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('DEA875', 'SimBrand', 'SimModel', 'Gray', 54, 'Motorbike'),
('DEF012', 'Chevrolet', 'Traverse', 'Gray', 54, 'SUV'),
('DEF234', 'Mazda', '2', 'Red', 28, 'Compact'),
('DEF456', 'Ford', 'Transit', 'White', 1, 'Truck'),
('DEF9012', 'Honda', 'Civic', 'Negro', 2, 'Sedan'),
('DFW533', 'SimBrand', 'SimModel', 'Gray', 48, 'Motorbike'),
('DHH637', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('DIZ762', 'SimBrand', 'SimModel', 'Gray', 8, 'Motorbike'),
('DJZ036', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('DKZ850', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('DLM613', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('DMY082', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('DNC510', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('DQF152', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('DQH482', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('DQO726', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('DQW834', 'SimBrand', 'SimModel', 'Gray', 7, 'Car'),
('DRG443', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('DRI458', 'SimBrand', 'SimModel', 'Gray', 49, 'Car'),
('DRZ455', 'SimBrand', 'SimModel', 'Gray', 11, 'Motorbike'),
('DSL021', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('DSQ877', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('DUM484', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('DXL668', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('DXM143', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('DZZ027', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('EAB361', 'SimBrand', 'SimModel', 'Gray', 15, 'Motorbike'),
('EAI578', 'SimBrand', 'SimModel', 'Gray', 44, 'Motorbike'),
('EBZ833', 'SimBrand', 'SimModel', 'Gray', 47, 'Motorbike'),
('ECU243', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('EDH674', 'SimBrand', 'SimModel', 'Gray', 15, 'Motorbike'),
('EFA354', 'SimBrand', 'SimModel', 'Gray', 39, 'Car'),
('EFG123', 'Toyota', 'Camry', 'Black', 11, 'Sedan'),
('EFG901', 'Kia', 'Stinger', 'White', 37, 'Sport'),
('EFQ624', 'SimBrand', 'SimModel', 'Gray', 14, 'Car'),
('EHD846', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('EHX473', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('ETZ321', 'SimBrand', 'SimModel', 'Gray', 58, 'Car'),
('EUJ312', 'SimBrand', 'SimModel', 'Gray', 33, 'Car'),
('EUL503', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('EUN147', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('EXH726', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('EXK334', 'SimBrand', 'SimModel', 'Gray', 57, 'Car'),
('EYN442', 'SimBrand', 'SimModel', 'Gray', 9, 'Motorbike'),
('EYS452', 'SimBrand', 'SimModel', 'Gray', 34, 'Motorbike'),
('EZS172', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('FAG844', 'SimBrand', 'SimModel', 'Gray', 41, 'Car'),
('FAQ074', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('FGH678', 'Hyundai', 'Tucson', 'Red', 46, 'SUV'),
('FGH890', 'Subaru', 'Legacy', 'Blue', 20, 'Sedan'),
('FGU472', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('FIM107', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('FIQ537', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('FJD657', 'SimBrand', 'SimModel', 'Gray', 15, 'Motorbike'),
('FJZ114', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('FKE153', 'SimBrand', 'SimModel', 'Gray', 38, 'Motorbike'),
('FKJ776', 'SimBrand', 'SimModel', 'Gray', 38, 'Car'),
('FKV620', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('FME202', 'SimBrand', 'SimModel', 'Gray', 59, 'Car'),
('FMR017', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('FMR176', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('FON383', 'SimBrand', 'SimModel', 'Gray', 47, 'Car'),
('FPT471', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('FRF630', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('FRZ110', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('FUS768', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('FWM286', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('FWM324', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('FXL340', 'SimBrand', 'SimModel', 'Gray', 4, 'Motorbike'),
('FXP346', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('FXY334', 'SimBrand', 'SimModel', 'Gray', 56, 'Car'),
('FZG168', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('FZL614', 'SimBrand', 'SimModel', 'Gray', 45, 'Car'),
('GDQ807', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('GEH457', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('GEQ235', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('GEX525', 'SimBrand', 'SimModel', 'Gray', 11, 'Car'),
('GGR518', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('GHI345', 'Nissan', 'Murano', 'Red', 55, 'SUV'),
('GHI3456', 'Nissan', 'Altima', 'Blanco', 2, 'Sedan'),
('GHI567', 'Volkswagen', 'Polo', 'Gray', 29, 'Compact'),
('GHL543', 'SimBrand', 'SimModel', 'Gray', 55, 'Motorbike'),
('GMW883', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('GPF581', 'SimBrand', 'SimModel', 'Gray', 44, 'Car'),
('GQP401', 'SimBrand', 'SimModel', 'Gray', 56, 'Motorbike'),
('GTY277', 'SimBrand', 'SimModel', 'Gray', 25, 'Motorbike'),
('HAI515', 'SimBrand', 'SimModel', 'Gray', 16, 'Car'),
('HGD333', 'SimBrand', 'SimModel', 'Gray', 15, 'Motorbike'),
('HIC308', 'SimBrand', 'SimModel', 'Gray', 7, 'Motorbike'),
('HIJ234', 'Mazda', 'MX-5', 'Blue', 38, 'Sport'),
('HIJ456', 'Honda', 'Accord', 'White', 12, 'Sedan'),
('HQO325', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('HRY007', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('HRZ766', 'SimBrand', 'SimModel', 'Gray', 35, 'Car'),
('HTI110', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('HUN043', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('HUO275', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('HXL834', 'SimBrand', 'SimModel', 'Gray', 23, 'Car'),
('HZJ672', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('IDE701', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('IDR202', 'SimBrand', 'SimModel', 'Gray', 45, 'Motorbike'),
('IDV687', 'SimBrand', 'SimModel', 'Gray', 56, 'Motorbike'),
('IFA431', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('IGD548', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('IGY638', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('IGZ315', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('IHR368', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('IJK123', 'Toyota', 'Yaris', 'Gray', 21, 'Compact'),
('IJK901', 'Kia', 'Sportage', 'Gray', 47, 'SUV'),
('IKR231', 'SimBrand', 'SimModel', 'Gray', 52, 'Car'),
('IKW113', 'SimBrand', 'SimModel', 'Gray', 44, 'Car'),
('IMO645', 'SimBrand', 'SimModel', 'Gray', 35, 'Motorbike'),
('INB363', 'SimBrand', 'SimModel', 'Gray', 26, 'Motorbike'),
('INV657', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('IPL601', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('ITO041', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('ITV183', 'SimBrand', 'SimModel', 'Gray', 38, 'Motorbike'),
('IUP817', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('IUY150', 'SimBrand', 'SimModel', 'Gray', 17, 'Motorbike'),
('IVL607', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('IWM741', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('JAH062', 'SimBrand', 'SimModel', 'Gray', 27, 'Motorbike'),
('JAN111', 'NA', 'NA', 'NA', 1, 'Car'),
('JAN112', 'NA', 'NA', 'NA', 1, 'Car'),
('JAN113', 'NA', 'NA', 'NA', 1, 'Car'),
('JAN114', 'NA', 'NA', 'NA', 1, 'Truck'),
('JAN115', 'NA', 'NA', 'NA', 1, 'Truck'),
('JAN123', 'NA', 'NA', 'NA', 1, 'Car'),
('JAN211', 'NA', 'NA', 'NA', 1, 'Car'),
('JAN212', 'NA', 'NA', 'NA', 1, 'Car'),
('JCX251', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('JFH214', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('JGN860', 'SimBrand', 'SimModel', 'Gray', 23, 'Car'),
('JHI563', 'SimBrand', 'SimModel', 'Gray', 47, 'Car'),
('JHK242', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('JHO354', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('JHS482', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('JII566', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('JJT842', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('JKJ181', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('JKL678', 'Hyundai', 'Santa Fe', 'Green', 56, 'SUV'),
('JKL7890', 'Chevy', 'Cruze', 'Gris', 3, 'Sedan'),
('JKL890', 'Subaru', 'WRX', 'Black', 30, 'Sport'),
('JLU453', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('JOJ855', 'SimBrand', 'SimModel', 'Gray', 36, 'Motorbike'),
('JOZ710', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('JRV657', 'SimBrand', 'SimModel', 'Gray', 6, 'Motorbike'),
('JTB075', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('JTG405', 'SimBrand', 'SimModel', 'Gray', 36, 'Car'),
('JUG500', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('JUV335', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('JWR473', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('JWW161', 'SimBrand', 'SimModel', 'Gray', 37, 'Car'),
('JXU514', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('JYW148', 'SimBrand', 'SimModel', 'Gray', 23, 'Motorbike'),
('KAN584', 'SimBrand', 'SimModel', 'Gray', 50, 'Car'),
('KCX342', 'SimBrand', 'SimModel', 'Gray', 9, 'Motorbike'),
('KFF173', 'SimBrand', 'SimModel', 'Gray', 21, 'Car'),
('KFV826', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('KGA868', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('KHA116', 'SimBrand', 'SimModel', 'Gray', 16, 'Motorbike'),
('KHT708', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('KLM567', 'Volkswagen', 'Scirocco', 'Gray', 39, 'Sport'),
('KLM789', 'Ford', 'Fusion', 'Gray', 13, 'Sedan'),
('KMF481', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('KMN762', 'SimBrand', 'SimModel', 'Gray', 59, 'Car'),
('KMW633', 'SimBrand', 'SimModel', 'Gray', 23, 'Car'),
('KNG245', 'SimBrand', 'SimModel', 'Gray', 29, 'Motorbike'),
('KPP123', 'SimBrand', 'SimModel', 'Gray', 14, 'Car'),
('KQZ705', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('KTH541', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('KUE235', 'SimBrand', 'SimModel', 'Gray', 54, 'Motorbike'),
('KVJ433', 'SimBrand', 'SimModel', 'Gray', 5, 'Motorbike'),
('KYE808', 'SimBrand', 'SimModel', 'Gray', 15, 'Motorbike'),
('KYX030', 'SimBrand', 'SimModel', 'Gray', 30, 'Car'),
('LBJ723', 'SimBrand', 'SimModel', 'Gray', 15, 'Motorbike'),
('LEI537', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('LIQ316', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('LKA282', 'SimBrand', 'SimModel', 'Gray', 18, 'Motorbike'),
('LKZ477', 'SimBrand', 'SimModel', 'Gray', 12, 'Motorbike'),
('LMN234', 'Mazda', 'CX-5', 'Black', 48, 'SUV'),
('LMN456', 'Honda', 'Fit', 'Red', 22, 'Compact'),
('LMN654', 'Honda', 'Civic', 'Black', 1, 'Car'),
('LOB376', 'SimBrand', 'SimModel', 'Gray', 51, 'Car'),
('LOO531', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('LPU625', 'SimBrand', 'SimModel', 'Gray', 49, 'Motorbike'),
('LPY367', 'SimBrand', 'SimModel', 'Gray', 43, 'Motorbike'),
('LRZ075', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('LSQ456', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('LTF362', 'SimBrand', 'SimModel', 'Gray', 45, 'Car'),
('LTJ648', 'SimBrand', 'SimModel', 'Gray', 53, 'Motorbike'),
('LZX540', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('MBL184', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('MBQ073', 'SimBrand', 'SimModel', 'Gray', 34, 'Motorbike'),
('MBY753', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('MCC775', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('MFH420', 'SimBrand', 'SimModel', 'Gray', 57, 'Car'),
('MFY082', 'SimBrand', 'SimModel', 'Gray', 17, 'Motorbike'),
('MGY810', 'SimBrand', 'SimModel', 'Gray', 15, 'Motorbike'),
('MIL626', 'SimBrand', 'SimModel', 'Gray', 56, 'Motorbike'),
('MIQ361', 'SimBrand', 'SimModel', 'Gray', 4, 'Motorbike'),
('MJD580', 'SimBrand', 'SimModel', 'Gray', 50, 'Motorbike'),
('MJL308', 'SimBrand', 'SimModel', 'Gray', 4, 'Car'),
('MKQ821', 'SimBrand', 'SimModel', 'Gray', 35, 'Car'),
('MMI276', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('MNO123', 'Toyota', 'Supra', 'White', 31, 'Sport'),
('MNO1234', 'Hyundai', 'Elantra', 'Verde', 3, 'Sedan'),
('MNO345', 'Nissan', 'Altima', 'White', 5, 'Sedan'),
('MNO901', 'Kia', 'Sorento', 'Silver', 57, 'SUV'),
('MOM203', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('MOR243', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('MOTO99', 'Kawasaki', 'Ninja', 'Green', 1, 'Motorbike'),
('MPK140', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('MPW040', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('MPZ785', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('MQE218', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('MQW444', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('MTX838', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('MTZ520', 'SimBrand', 'SimModel', 'Gray', 54, 'Motorbike'),
('MVH176', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('MVP418', 'SimBrand', 'SimModel', 'Gray', 4, 'Motorbike'),
('MWR320', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('MXK773', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('MXY868', 'SimBrand', 'SimModel', 'Gray', 7, 'Car'),
('MYF402', 'SimBrand', 'SimModel', 'Gray', 39, 'Car'),
('MZH804', 'SimBrand', 'SimModel', 'Gray', 17, 'Car'),
('NCN131', 'SimBrand', 'SimModel', 'Gray', 5, 'Car'),
('NET707', 'SimBrand', 'SimModel', 'Gray', 17, 'Car'),
('NFV005', 'SimBrand', 'SimModel', 'Gray', 44, 'Motorbike'),
('NFX865', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('NGO400', 'SimBrand', 'SimModel', 'Gray', 56, 'Motorbike'),
('NID153', 'SimBrand', 'SimModel', 'Gray', 20, 'Motorbike'),
('NJT818', 'SimBrand', 'SimModel', 'Gray', 38, 'Motorbike'),
('NKL365', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('NLG703', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('NNZ514', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('NOP012', 'Chevrolet', 'Cruze', 'Blue', 14, 'Sedan'),
('NOP670', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('NOP890', 'Subaru', 'BRZ', 'Red', 40, 'Sport'),
('NPW652', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('NQE076', 'SimBrand', 'SimModel', 'Gray', 17, 'Car'),
('NRG331', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('NRJ520', 'SimBrand', 'SimModel', 'Gray', 40, 'Motorbike'),
('NRV637', 'SimBrand', 'SimModel', 'Gray', 2, 'Motorbike'),
('NRY473', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('NUH225', 'SimBrand', 'SimModel', 'Gray', 36, 'Motorbike'),
('NWV480', 'SimBrand', 'SimModel', 'Gray', 38, 'Motorbike'),
('NWV623', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('NYA242', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('NYJ226', 'SimBrand', 'SimModel', 'Gray', 57, 'Motorbike'),
('NYO647', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('NZG620', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('NZO201', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('OAD810', 'SimBrand', 'SimModel', 'Gray', 49, 'Motorbike'),
('OBH840', 'SimBrand', 'SimModel', 'Gray', 48, 'Motorbike'),
('OBI614', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('OBV540', 'SimBrand', 'SimModel', 'Gray', 21, 'Car'),
('OBV713', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('OCB223', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('ODU637', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('OEI562', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('OFE571', 'SimBrand', 'SimModel', 'Gray', 7, 'Motorbike'),
('OJJ852', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('OJZ551', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('OMQ276', 'SimBrand', 'SimModel', 'Gray', 4, 'Car'),
('ONS617', 'SimBrand', 'SimModel', 'Gray', 54, 'Car'),
('OOH655', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('OPJ063', 'SimBrand', 'SimModel', 'Gray', 25, 'Motorbike'),
('OPQ567', 'Volkswagen', 'Tiguan', 'Green', 49, 'SUV'),
('OPQ789', 'Ford', 'Fiesta', 'Black', 23, 'Compact'),
('ORN842', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('OST231', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('OTX531', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('OUM027', 'SimBrand', 'SimModel', 'Gray', 19, 'Car'),
('OXR337', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('PAP084', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('PEI584', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('PEP343', 'NA', 'NA', 'NA', 1, 'Car'),
('PGP501', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('PJA743', 'SimBrand', 'SimModel', 'Gray', 9, 'Car'),
('PMD682', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('PMU548', 'SimBrand', 'SimModel', 'Gray', 48, 'Motorbike'),
('POE320', 'SimBrand', 'SimModel', 'Gray', 31, 'Car'),
('PPO222', 'NA', 'NA', 'NA', 1, 'Truck'),
('PPP111', 'NA', 'NA', 'NA', 1, 'Car'),
('PQG132', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('PQL184', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('PQR234', 'Mazda', 'CX-9', 'Blue', 58, 'SUV'),
('PQR456', 'Honda', 'S2000', 'Blue', 32, 'Sport'),
('PQR5678', 'Kia', 'Rio', 'Azul', 1, 'Compacto'),
('PQR678', 'Hyundai', 'Elantra', 'Silver', 6, 'Sedan'),
('PRL226', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('PRZ843', 'SimBrand', 'SimModel', 'Gray', 10, 'Motorbike'),
('PSH467', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('PTP180', 'SimBrand', 'SimModel', 'Gray', 21, 'Motorbike'),
('PTQ878', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('PUX388', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('PWL150', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('PWM651', 'SimBrand', 'SimModel', 'Gray', 12, 'Car'),
('PXB283', 'SimBrand', 'SimModel', 'Gray', 44, 'Motorbike'),
('PXG078', 'SimBrand', 'SimModel', 'Gray', 20, 'Car'),
('PXX252', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('PYK661', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('PYL531', 'SimBrand', 'SimModel', 'Gray', 13, 'Car'),
('PZJ768', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('QAP207', 'SimBrand', 'SimModel', 'Gray', 47, 'Car'),
('QAP831', 'SimBrand', 'SimModel', 'Gray', 38, 'Motorbike'),
('QCQ047', 'SimBrand', 'SimModel', 'Gray', 21, 'Car'),
('QEA885', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('QEW826', 'SimBrand', 'SimModel', 'Gray', 7, 'Car'),
('QFU634', 'SimBrand', 'SimModel', 'Gray', 40, 'Motorbike'),
('QKD847', 'SimBrand', 'SimModel', 'Gray', 20, 'Motorbike'),
('QMZ115', 'SimBrand', 'SimModel', 'Gray', 60, 'Motorbike'),
('QNQ512', 'SimBrand', 'SimModel', 'Gray', 60, 'Motorbike'),
('QOF683', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('QOZ571', 'SimBrand', 'SimModel', 'Gray', 22, 'Car'),
('QPG024', 'SimBrand', 'SimModel', 'Gray', 39, 'Car'),
('QPR546', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('QQD457', 'SimBrand', 'SimModel', 'Gray', 19, 'Motorbike'),
('QQP106', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('QRS123', 'Toyota', 'GT86', 'Black', 41, 'Sport'),
('QRS345', 'Nissan', 'Sentra', 'Silver', 15, 'Sedan'),
('QRW380', 'SimBrand', 'SimModel', 'Gray', 21, 'Car'),
('QTN164', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('QWK782', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('QWZ206', 'SimBrand', 'SimModel', 'Gray', 53, 'Car'),
('QYU244', 'SimBrand', 'SimModel', 'Gray', 38, 'Motorbike'),
('RBB552', 'SimBrand', 'SimModel', 'Gray', 60, 'Motorbike'),
('RID362', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('RIO726', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('RQZ178', 'SimBrand', 'SimModel', 'Gray', 37, 'Motorbike'),
('RRK363', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('RRL212', 'SimBrand', 'SimModel', 'Gray', 37, 'Motorbike'),
('RST012', 'Chevrolet', 'Spark', 'Green', 24, 'Compact'),
('RST890', 'Subaru', 'Forester', 'Silver', 50, 'SUV'),
('RTL544', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('RTS436', 'SimBrand', 'SimModel', 'Gray', 55, 'Car'),
('RVL847', 'SimBrand', 'SimModel', 'Gray', 19, 'Car'),
('RWG858', 'SimBrand', 'SimModel', 'Gray', 23, 'Motorbike'),
('RYL138', 'SimBrand', 'SimModel', 'Gray', 42, 'Motorbike'),
('RYL620', 'SimBrand', 'SimModel', 'Gray', 17, 'Car'),
('RZB050', 'SimBrand', 'SimModel', 'Gray', 8, 'Car'),
('SAO614', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('SCF744', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('SDA757', 'SimBrand', 'SimModel', 'Gray', 38, 'Motorbike'),
('SDJ404', 'SimBrand', 'SimModel', 'Gray', 8, 'Motorbike'),
('SEU506', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('SGL287', 'SimBrand', 'SimModel', 'Gray', 12, 'Car'),
('SIF121', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('SIJ674', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('SPK751', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('SQH462', 'SimBrand', 'SimModel', 'Gray', 56, 'Motorbike'),
('SSV702', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('STB123', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('STU567', 'Volkswagen', 'Atlas', 'White', 59, 'SUV'),
('STU789', 'Ford', 'Mustang', 'Red', 33, 'Sport'),
('STU901', 'Kia', 'Forte', 'Gray', 7, 'Compact'),
('STU9012', 'Mazda', '3', 'Rojo', 2, 'Sedan'),
('SUL770', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('SVK807', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('SWS274', 'SimBrand', 'SimModel', 'Gray', 9, 'Car'),
('SXY682', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('SYU706', 'SimBrand', 'SimModel', 'Gray', 12, 'Motorbike'),
('SZD036', 'SimBrand', 'SimModel', 'Gray', 46, 'Car'),
('SZO448', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('TBP630', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('TDE833', 'SimBrand', 'SimModel', 'Gray', 8, 'Car'),
('TEI488', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('TEL426', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('TEV860', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('TFF028', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('TFP542', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('THF535', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('THU687', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('TKR888', 'NA', 'NA', 'NA', 1, 'Truck'),
('TMF688', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('TMY040', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('TNF330', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('TNW040', 'SimBrand', 'SimModel', 'Gray', 49, 'Car'),
('TOO511', 'SimBrand', 'SimModel', 'Gray', 22, 'Car'),
('TRC256', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('TRK888', 'Volvo', 'FH', 'Grey', 1, 'Truck'),
('TSY557', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('TTU125', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('TUC301', 'SimBrand', 'SimModel', 'Gray', 48, 'Car'),
('TUV456', 'Honda', 'CR-V', 'Green', 42, 'SUV'),
('TUV678', 'Hyundai', 'Sonata', 'Red', 16, 'Sedan'),
('TVZ386', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('TWI423', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('TWM031', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('TXI767', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('TXS007', 'SimBrand', 'SimModel', 'Gray', 43, 'Motorbike'),
('TZK422', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('UAR377', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('UCE030', 'SimBrand', 'SimModel', 'Gray', 32, 'Car'),
('UED440', 'SimBrand', 'SimModel', 'Gray', 41, 'Car'),
('UEU852', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('UFZ675', 'SimBrand', 'SimModel', 'Gray', 35, 'Car'),
('UGC454', 'SimBrand', 'SimModel', 'Gray', 12, 'Motorbike'),
('UHU781', 'SimBrand', 'SimModel', 'Gray', 51, 'Car'),
('ULP886', 'SimBrand', 'SimModel', 'Gray', 44, 'Motorbike'),
('UMY071', 'SimBrand', 'SimModel', 'Gray', 22, 'Motorbike'),
('UNM281', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('UOA603', 'SimBrand', 'SimModel', 'Gray', 18, 'Car'),
('UOG434', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('UOU364', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('UOU741', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('USP410', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('UTH657', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('UUK813', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('UUN103', 'SimBrand', 'SimModel', 'Gray', 39, 'Motorbike'),
('UUQ250', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('UVW123', 'Toyota', 'RAV4', 'Blue', 51, 'SUV'),
('UVW345', 'Nissan', 'Versa', 'White', 25, 'Compact'),
('UZE534', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('VCH260', 'SimBrand', 'SimModel', 'Gray', 54, 'Car'),
('VEJ353', 'SimBrand', 'SimModel', 'Gray', 12, 'Car'),
('VGJ358', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('VHP804', 'SimBrand', 'SimModel', 'Gray', 22, 'Car'),
('VJB342', 'SimBrand', 'SimModel', 'Gray', 8, 'Motorbike'),
('VKJ267', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('VNG116', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('VQG364', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('VRU110', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('VUA353', 'SimBrand', 'SimModel', 'Gray', 17, 'Motorbike'),
('VUH175', 'SimBrand', 'SimModel', 'Gray', 48, 'Car'),
('VWX012', 'Chevrolet', 'Camaro', 'Green', 34, 'Sport'),
('VWX234', 'Mazda', '3', 'Blue', 8, 'Hatchback'),
('VWX3456', 'VW', 'Jetta', 'Negro', 1, 'Sedan'),
('VWX890', 'Subaru', 'Ascent', 'Black', 60, 'SUV'),
('VZG078', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('VZO473', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('VZW374', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('WDM878', 'SimBrand', 'SimModel', 'Gray', 10, 'Motorbike'),
('WFI732', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('WGK464', 'SimBrand', 'SimModel', 'Gray', 22, 'Motorbike'),
('WIB641', 'SimBrand', 'SimModel', 'Gray', 19, 'Motorbike'),
('WJV537', 'SimBrand', 'SimModel', 'Gray', 58, 'Car'),
('WJX355', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('WLA861', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('WMV186', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('WPG107', 'SimBrand', 'SimModel', 'Gray', 13, 'Car'),
('WPM803', 'SimBrand', 'SimModel', 'Gray', 55, 'Car'),
('WPT753', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('WRG138', 'SimBrand', 'SimModel', 'Gray', 39, 'Motorbike'),
('WSG462', 'SimBrand', 'SimModel', 'Gray', 4, 'Motorbike'),
('WTT115', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('WTZ552', 'SimBrand', 'SimModel', 'Gray', 23, 'Car'),
('WUH751', 'SimBrand', 'SimModel', 'Gray', 27, 'Car'),
('WWA752', 'SimBrand', 'SimModel', 'Gray', 25, 'Motorbike'),
('WXY789', 'Ford', 'Escape', 'Silver', 43, 'SUV'),
('WXY901', 'Kia', 'Optima', 'Green', 17, 'Sedan'),
('WZD206', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('XCA185', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('XCU071', 'SimBrand', 'SimModel', 'Gray', 25, 'Car'),
('XDY615', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('XEF835', 'SimBrand', 'SimModel', 'Gray', 41, 'Motorbike'),
('XER000', 'SimBrand', 'SimModel', 'Gray', 7, 'Car'),
('XFZ181', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('XGP317', 'SimBrand', 'SimModel', 'Gray', 59, 'Motorbike'),
('XGR640', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('XGX766', 'SimBrand', 'SimModel', 'Gray', 45, 'Motorbike'),
('XIS614', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('XJP428', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('XJT418', 'SimBrand', 'SimModel', 'Gray', 27, 'Motorbike'),
('XJV801', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('XJY738', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('XNW556', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('XOW843', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('XPE538', 'SimBrand', 'SimModel', 'Gray', 38, 'Car'),
('XPL856', 'SimBrand', 'SimModel', 'Gray', 25, 'Car'),
('XQY346', 'SimBrand', 'SimModel', 'Gray', 57, 'Motorbike'),
('XTW870', 'SimBrand', 'SimModel', 'Gray', 25, 'Car'),
('XVA538', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('XVG803', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('XYZ456', 'Honda', 'Pilot', 'White', 52, 'SUV'),
('XYZ5678', 'Ford', 'Focus', 'Azul', 1, 'Hatchback'),
('XYZ678', 'Hyundai', 'Accent', 'Blue', 26, 'Compact'),
('XYZ987', 'Yamaha', 'YZF-R3', 'Blue', 1, 'Motorbike'),
('XZD261', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('XZJ055', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('YAX512', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('YBM377', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('YDQ565', 'SimBrand', 'SimModel', 'Gray', 57, 'Car'),
('YFE838', 'SimBrand', 'SimModel', 'Gray', 44, 'Motorbike'),
('YFS366', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('YIQ135', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('YIR517', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('YKH017', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('YLK264', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('YMI100', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('YNO087', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('YQB106', 'SimBrand', 'SimModel', 'Gray', 17, 'Motorbike'),
('YSH173', 'SimBrand', 'SimModel', 'Gray', 50, 'Motorbike'),
('YTL013', 'SimBrand', 'SimModel', 'Gray', 59, 'Car'),
('YUB743', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('YUW462', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('YWG720', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('YWW152', 'SimBrand', 'SimModel', 'Gray', 48, 'Car'),
('YZA345', 'Nissan', '370Z', 'Silver', 35, 'Sport'),
('YZA567', 'Volkswagen', 'Golf', 'Red', 9, 'Hatchback'),
('YZA7890', 'Peugeot', '208', 'Blanco', 2, 'Hatchback'),
('ZAB012', 'Chevrolet', 'Equinox', 'White', 44, 'SUV'),
('ZAB234', 'Mazda', '6', 'Black', 18, 'Sedan'),
('ZAN162', 'SimBrand', 'SimModel', 'Gray', 23, 'Car'),
('ZDB145', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('ZDE152', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('ZDS114', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('ZDT204', 'SimBrand', 'SimModel', 'Gray', 4, 'Motorbike'),
('ZEJ488', 'SimBrand', 'SimModel', 'Gray', 38, 'Motorbike'),
('ZFB758', 'SimBrand', 'SimModel', 'Gray', 23, 'Car'),
('ZIS443', 'SimBrand', 'SimModel', 'Gray', 11, 'Motorbike'),
('ZIU051', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('ZJO888', 'SimBrand', 'SimModel', 'Gray', 34, 'Car'),
('ZLT876', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('ZMJ251', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('ZMU545', 'SimBrand', 'SimModel', 'Gray', 22, 'Motorbike'),
('ZMV181', 'SimBrand', 'SimModel', 'Gray', 18, 'Car'),
('ZNH773', 'SimBrand', 'SimModel', 'Gray', 43, 'Motorbike'),
('ZON010', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('ZOP525', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('ZSB720', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('ZSZ343', 'SimBrand', 'SimModel', 'Gray', 7, 'Car'),
('ZTP451', 'SimBrand', 'SimModel', 'Gray', 53, 'Motorbike'),
('ZTX130', 'SimBrand', 'SimModel', 'Gray', 39, 'Motorbike'),
('ZUH507', 'SimBrand', 'SimModel', 'Gray', 1, 'Car'),
('ZYB416', 'SimBrand', 'SimModel', 'Gray', 1, 'Motorbike'),
('ZYQ444', 'SimBrand', 'SimModel', 'Gray', 17, 'Car'),
('ZYQ850', 'SimBrand', 'SimModel', 'Gray', 44, 'Motorbike'),
('ZZT620', 'SimBrand', 'SimModel', 'Gray', 45, 'Motorbike');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cancelled_reservations`
--
ALTER TABLE `cancelled_reservations`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_cancelled_user` (`user_id`),
  ADD KEY `fk_cancelled_slot` (`slot_id`),
  ADD KEY `fk_vehicle` (`vehicle_plate`);

--
-- Indices de la tabla `entry_leave_logs`
--
ALTER TABLE `entry_leave_logs`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `parkings`
--
ALTER TABLE `parkings`
  ADD PRIMARY KEY (`plant`);

--
-- Indices de la tabla `slots`
--
ALTER TABLE `slots`
  ADD PRIMARY KEY (`id`),
  ADD KEY `parking_id` (`plant`),
  ADD KEY `vehicle_plate` (`vehicle_plate`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indices de la tabla `vehicles`
--
ALTER TABLE `vehicles`
  ADD PRIMARY KEY (`plate`),
  ADD UNIQUE KEY `plate` (`plate`),
  ADD UNIQUE KEY `plate_2` (`plate`),
  ADD KEY `owner_id` (`owner_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cancelled_reservations`
--
ALTER TABLE `cancelled_reservations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT de la tabla `entry_leave_logs`
--
ALTER TABLE `entry_leave_logs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT de la tabla `parkings`
--
ALTER TABLE `parkings`
  MODIFY `plant` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `slots`
--
ALTER TABLE `slots`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=112;

--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=62;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cancelled_reservations`
--
ALTER TABLE `cancelled_reservations`
  ADD CONSTRAINT `fk_cancelled_slot` FOREIGN KEY (`slot_id`) REFERENCES `slots` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_cancelled_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_vehicle` FOREIGN KEY (`vehicle_plate`) REFERENCES `vehicles` (`plate`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `slots`
--
ALTER TABLE `slots`
  ADD CONSTRAINT `slots_ibfk_1` FOREIGN KEY (`plant`) REFERENCES `parkings` (`plant`),
  ADD CONSTRAINT `slots_ibfk_2` FOREIGN KEY (`vehicle_plate`) REFERENCES `vehicles` (`plate`) ON DELETE SET NULL;

--
-- Filtros para la tabla `vehicles`
--
ALTER TABLE `vehicles`
  ADD CONSTRAINT `vehicles_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
