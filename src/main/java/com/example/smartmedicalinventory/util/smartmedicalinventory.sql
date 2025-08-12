-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Aug 09, 2025 at 09:25 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `smartmedicalinventory`
--

-- --------------------------------------------------------

--
-- Table structure for table `Admin`
--

CREATE TABLE `Admin` (
  `admin_id` int(11) NOT NULL,
  `pwd` varchar(8) NOT NULL,
  `name` varchar(50) NOT NULL,
  `gmail` varchar(100) NOT NULL,
  `contact` varchar(11) NOT NULL,
  `org_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Customer`
--

CREATE TABLE `Customer` (
  `customer_id` bigint(255) NOT NULL,
  `pwd` varchar(8) NOT NULL,
  `name` varchar(50) NOT NULL,
  `gmail` varchar(100) NOT NULL,
  `contact` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Customer_buy_history`
--

CREATE TABLE `Customer_buy_history` (
  `fk_customer_id` bigint(255) NOT NULL,
  `fk_org_id` int(11) NOT NULL,
  `fk_dept_id` int(11) NOT NULL,
  `fk_med_id` int(11) NOT NULL,
  `trans_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `datetime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Department`
--

CREATE TABLE `Department` (
  `dept_id` int(11) NOT NULL,
  `name` int(11) NOT NULL,
  `dept_cost` int(11) NOT NULL,
  `budget` int(11) NOT NULL,
  `fk_org_id` int(11) NOT NULL,
  `fk_admin_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Inventory`
--

CREATE TABLE `Inventory` (
  `fk_org_id` int(11) NOT NULL,
  `fk_dept_id` int(11) NOT NULL,
  `fk_med_id` int(11) NOT NULL,
  `med_type` varchar(50) NOT NULL,
  `quantity` double NOT NULL,
  `location` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Manager`
--

CREATE TABLE `Manager` (
  `manager_id` int(11) NOT NULL,
  `pwd` int(11) NOT NULL,
  `name` int(11) NOT NULL,
  `gmail` int(11) NOT NULL,
  `contact` int(11) NOT NULL,
  `fk_dept_id` int(11) NOT NULL,
  `fk_org_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Medicine`
--

CREATE TABLE `Medicine` (
  `med_id` int(11) NOT NULL,
  `med_name` varchar(50) NOT NULL,
  `med_type` varchar(50) NOT NULL,
  `company` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Organization`
--

CREATE TABLE `Organization` (
  `org_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  `gmail` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Organization_buy_history`
--

CREATE TABLE `Organization_buy_history` (
  `buy_id` int(11) NOT NULL,
  `fk_org_id` int(11) NOT NULL,
  `fk_dept_id` int(11) NOT NULL,
  `fk_med_id` int(11) NOT NULL,
  `quantity` double NOT NULL,
  `buy_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sell_record`
--

CREATE TABLE `sell_record` (
  `fk_org_id` int(11) NOT NULL,
  `fk_dept_id` int(11) NOT NULL,
  `month_1` double NOT NULL,
  `month_2` double NOT NULL,
  `month_3` double NOT NULL,
  `month_4` double NOT NULL,
  `month_5` int(11) NOT NULL,
  `month_6` int(11) NOT NULL,
  `fk_med_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Admin`
--
ALTER TABLE `Admin`
  ADD PRIMARY KEY (`admin_id`),
  ADD KEY `org_id` (`org_id`);

--
-- Indexes for table `Customer`
--
ALTER TABLE `Customer`
  ADD PRIMARY KEY (`customer_id`);

--
-- Indexes for table `Customer_buy_history`
--
ALTER TABLE `Customer_buy_history`
  ADD PRIMARY KEY (`trans_id`),
  ADD KEY `sdfafd` (`fk_org_id`),
  ADD KEY `fhdrty` (`fk_dept_id`),
  ADD KEY `medicineid` (`fk_med_id`),
  ADD KEY `drydf` (`fk_customer_id`);

--
-- Indexes for table `Department`
--
ALTER TABLE `Department`
  ADD PRIMARY KEY (`dept_id`),
  ADD KEY `foriegn` (`fk_org_id`),
  ADD KEY `foriegn2` (`fk_admin_id`);

--
-- Indexes for table `Inventory`
--
ALTER TABLE `Inventory`
  ADD PRIMARY KEY (`fk_org_id`,`fk_dept_id`,`fk_med_id`),
  ADD KEY `department_id` (`fk_dept_id`),
  ADD KEY `medicine_id` (`fk_med_id`);

--
-- Indexes for table `Manager`
--
ALTER TABLE `Manager`
  ADD PRIMARY KEY (`manager_id`),
  ADD KEY `fkorg_id` (`fk_org_id`),
  ADD KEY `fkdept_id` (`fk_dept_id`);

--
-- Indexes for table `Medicine`
--
ALTER TABLE `Medicine`
  ADD PRIMARY KEY (`med_id`);

--
-- Indexes for table `Organization`
--
ALTER TABLE `Organization`
  ADD PRIMARY KEY (`org_id`);

--
-- Indexes for table `Organization_buy_history`
--
ALTER TABLE `Organization_buy_history`
  ADD PRIMARY KEY (`buy_id`),
  ADD KEY `ghfgh` (`fk_org_id`),
  ADD KEY `dsf` (`fk_dept_id`),
  ADD KEY `sdf` (`fk_med_id`);

--
-- Indexes for table `sell_record`
--
ALTER TABLE `sell_record`
  ADD PRIMARY KEY (`fk_org_id`,`fk_dept_id`,`fk_med_id`),
  ADD KEY `vbnvbnv` (`fk_med_id`),
  ADD KEY ` bcvb` (`fk_dept_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Admin`
--
ALTER TABLE `Admin`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Customer`
--
ALTER TABLE `Customer`
  MODIFY `customer_id` bigint(255) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Customer_buy_history`
--
ALTER TABLE `Customer_buy_history`
  MODIFY `trans_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Department`
--
ALTER TABLE `Department`
  MODIFY `dept_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Manager`
--
ALTER TABLE `Manager`
  MODIFY `manager_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Medicine`
--
ALTER TABLE `Medicine`
  MODIFY `med_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Organization`
--
ALTER TABLE `Organization`
  MODIFY `org_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Organization_buy_history`
--
ALTER TABLE `Organization_buy_history`
  MODIFY `buy_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Admin`
--
ALTER TABLE `Admin`
  ADD CONSTRAINT `org_id` FOREIGN KEY (`org_id`) REFERENCES `Organization` (`org_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Customer_buy_history`
--
ALTER TABLE `Customer_buy_history`
  ADD CONSTRAINT `drydf` FOREIGN KEY (`fk_customer_id`) REFERENCES `Customer` (`customer_id`),
  ADD CONSTRAINT `fhdrty` FOREIGN KEY (`fk_dept_id`) REFERENCES `Department` (`dept_id`),
  ADD CONSTRAINT `medicineid` FOREIGN KEY (`fk_med_id`) REFERENCES `Medicine` (`med_id`),
  ADD CONSTRAINT `sdfafd` FOREIGN KEY (`fk_org_id`) REFERENCES `Organization` (`org_id`);

--
-- Constraints for table `Department`
--
ALTER TABLE `Department`
  ADD CONSTRAINT `foriegn` FOREIGN KEY (`fk_org_id`) REFERENCES `Organization` (`org_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `foriegn2` FOREIGN KEY (`fk_admin_id`) REFERENCES `Admin` (`admin_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Inventory`
--
ALTER TABLE `Inventory`
  ADD CONSTRAINT `department_id` FOREIGN KEY (`fk_dept_id`) REFERENCES `Department` (`dept_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `medicine_id` FOREIGN KEY (`fk_med_id`) REFERENCES `Medicine` (`med_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `orgnaziaon_id` FOREIGN KEY (`fk_org_id`) REFERENCES `Organization` (`org_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Manager`
--
ALTER TABLE `Manager`
  ADD CONSTRAINT `fkdept_id` FOREIGN KEY (`fk_dept_id`) REFERENCES `Department` (`dept_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fkorg_id` FOREIGN KEY (`fk_org_id`) REFERENCES `Organization` (`org_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `Organization_buy_history`
--
ALTER TABLE `Organization_buy_history`
  ADD CONSTRAINT `dsf` FOREIGN KEY (`fk_dept_id`) REFERENCES `Department` (`dept_id`),
  ADD CONSTRAINT `ghfgh` FOREIGN KEY (`fk_org_id`) REFERENCES `Organization` (`org_id`),
  ADD CONSTRAINT `sdf` FOREIGN KEY (`fk_med_id`) REFERENCES `Medicine` (`med_id`);

--
-- Constraints for table `sell_record`
--
ALTER TABLE `sell_record`
  ADD CONSTRAINT ` bcvb` FOREIGN KEY (`fk_dept_id`) REFERENCES `Department` (`dept_id`),
  ADD CONSTRAINT `fdhghbj` FOREIGN KEY (`fk_org_id`) REFERENCES `Organization` (`org_id`),
  ADD CONSTRAINT `vbnvbnv` FOREIGN KEY (`fk_med_id`) REFERENCES `Medicine` (`med_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
