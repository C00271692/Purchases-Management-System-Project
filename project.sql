-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 29, 2023 at 02:26 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `project`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customerID` int(11) NOT NULL,
  `name` varchar(99) NOT NULL,
  `surname` varchar(99) NOT NULL,
  `address` varchar(99) NOT NULL,
  `email` varchar(99) NOT NULL,
  `phone` varchar(99) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customerID`, `name`, `surname`, `address`, `email`, `phone`) VALUES
(1, 'Joe', 'Doe', 'Carlow', 'Joedoe@email.com', '123456'),
(5, 'Mark', 'Zuckeberg', 'LA', 'mark@zuckerberg.com', '12135665'),
(6, 'Mary', 'Johnson', '45 lower', 'mary@johnson', '123456'),
(7, 'Larry', 'Dwyer', 'Upper 45', 'Larry@Dwyer', '658752'),
(9, 'Kacper', 'Krakowiak', '123 Upper Street', 'kacper@email.com', '123465');

-- --------------------------------------------------------

--
-- Table structure for table `invoice`
--

CREATE TABLE `invoice` (
  `invoiceID` int(11) NOT NULL,
  `customerID` int(11) NOT NULL,
  `productID` int(11) NOT NULL,
  `name` varchar(99) DEFAULT NULL COMMENT 'inherited from customer',
  `surname` varchar(99) DEFAULT NULL COMMENT 'inherited from customer',
  `address` varchar(99) DEFAULT NULL COMMENT 'inherited from customer',
  `email` varchar(99) DEFAULT NULL,
  `pname` varchar(99) DEFAULT NULL COMMENT 'inherited from product',
  `price` varchar(99) DEFAULT NULL COMMENT 'inherited from product',
  `totalPrice` int(11) NOT NULL,
  `quantity` varchar(99) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `invoice`
--

INSERT INTO `invoice` (`invoiceID`, `customerID`, `productID`, `name`, `surname`, `address`, `email`, `pname`, `price`, `totalPrice`, `quantity`) VALUES
(1, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, 90, '3'),
(2, 7, 1, NULL, NULL, NULL, NULL, NULL, NULL, 60, '2'),
(3, 1, 1, 'Joe', 'Doe', 'Carlow', 'Joedoe@email.com', 'oral b toothbrush', '30.0', 90, '3'),
(4, 7, 5, 'Larry', 'Dwyer', 'Upper 45', 'Larry@Dwyer', 'tea pot', '12.0', 36, '3'),
(5, 5, 5, 'Mark', 'Zuckeberg', 'LA', 'mark@zuckerberg.com', 'tea pot', '12.0', 36, '3'),
(6, 1, 1, 'Joe', 'Doe', 'Carlow', 'Joedoe@email.com', 'oral b toothbrush', '30.0', 30, '1'),
(7, 1, 1, 'Joe', 'Doe', 'Carlow', 'Joedoe@email.com', 'oral b toothbrush', '30.0', 30, '1'),
(8, 9, 5, 'Kacper', 'Krakowiak', '123 Upper Street', 'kacper@email.com', 'tea pot', '12.0', 24, '2'),
(9, 1, 1, 'Joe', 'Doe', 'Carlow', 'Joedoe@email.com', 'oral b toothbrush', '30.0', 90, '3');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `productID` int(11) NOT NULL,
  `pname` varchar(99) NOT NULL,
  `price` varchar(99) NOT NULL,
  `amountInStock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`productID`, `pname`, `price`, `amountInStock`) VALUES
(1, 'oral b toothbrush', '30', 250),
(2, 'lighters', '0.30', 900),
(5, 'tea pot', '12', 125);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customerID`);

--
-- Indexes for table `invoice`
--
ALTER TABLE `invoice`
  ADD PRIMARY KEY (`invoiceID`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`productID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `customerID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `invoice`
--
ALTER TABLE `invoice`
  MODIFY `invoiceID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `productID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
