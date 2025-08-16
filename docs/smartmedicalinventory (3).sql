-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Aug 16, 2025 at 10:52 PM
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
  `pwd` varchar(255) NOT NULL,
  `name` varchar(50) NOT NULL,
  `gmail` varchar(100) NOT NULL,
  `contact` varchar(11) NOT NULL,
  `org_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Admin`
--

INSERT INTO `Admin` (`admin_id`, `pwd`, `name`, `gmail`, `contact`, `org_id`) VALUES
(2001, 'pass123', 'Emily Admin', 'emily@lifecare.com', '99988877700', 1001),
(2002, 'pass124', 'David Admin', 'david@wellness.com', '99988877701', 1002),
(2003, 'pass125', 'Sophia Admin', 'sophia@carepoint.com', '99988877702', 1003),
(2004, 'pass126', 'James Admin', 'james@healthylife.com', '99988877703', 1004),
(2005, 'pass127', 'Olivia Admin', 'olivia@mediworld.com', '99988877704', 1005),
(2006, 'ZGVmYXVsdHBhc3N3b3Jk', 'Default Admin', 'defaultadmin@medicalshop.com', '0000000000', 1006),
(2007, 'MTIzNA==', 'jihad', 'jihad@gmail.com', '5131', 1007);

-- --------------------------------------------------------

--
-- Table structure for table `Customer`
--

CREATE TABLE `Customer` (
  `customer_id` bigint(255) NOT NULL,
  `pwd` varchar(255) NOT NULL,
  `name` varchar(50) NOT NULL,
  `gmail` varchar(100) NOT NULL,
  `contact` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Customer`
--

INSERT INTO `Customer` (`customer_id`, `pwd`, `name`, `gmail`, `contact`) VALUES
(5001, 'cpass111', 'Chris Evans', 'chris@email.com', '91234567800'),
(5002, 'cpass112', 'Emma Watson', 'emma@email.com', '91234567801'),
(5003, 'cpass113', 'Daniel Craig', 'daniel@email.com', '91234567802'),
(5004, 'cpass114', 'Scarlett J', 'scarlett@email.com', '91234567803'),
(5005, 'cpass115', 'Tom Hardy', 'tom@email.com', '91234567804'),
(5006, 'a2tvd3NhcjI1MzUw', 'kawser', 'mkshuvo@gmail.com', '01984144898'),
(5007, 'c2h1dm8=', 'shuvo', 'shuvo', '54654'),
(5008, 'MTIzNA==', 'kawser', 'kawser', '3541351'),
(5009, 'cGFzc3dvcmQxMjM=', 'Sample Customer', 'sample@example.com', '1234567890'),
(5010, 'cGFzc3dvcmQxMjM=', 'Sample Customer', 'sample@example.com', '1234567890');

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

--
-- Dumping data for table `Customer_buy_history`
--

INSERT INTO `Customer_buy_history` (`fk_customer_id`, `fk_org_id`, `fk_dept_id`, `fk_med_id`, `trans_id`, `quantity`, `datetime`) VALUES
(5001, 1001, 3001, 6, 9001, 2, '2025-03-01 10:00:00'),
(5002, 1002, 3002, 7, 9002, 1, '2025-03-02 11:30:00'),
(5003, 1003, 3003, 8, 9003, 3, '2025-03-03 12:45:00'),
(5004, 1004, 3004, 9, 9004, 5, '2025-03-04 14:00:00'),
(5005, 1005, 3005, 10, 9005, 1, '2025-03-05 15:15:00'),
(5007, 1006, 3006, 6, 9007, 1, '2025-08-17 01:40:39'),
(5007, 1006, 3006, 7, 9008, 1, '2025-08-17 01:40:43'),
(5007, 1006, 3006, 8, 9009, 1, '2025-08-17 01:40:46'),
(5007, 1006, 3006, 6, 9010, 1, '2025-08-17 01:41:00'),
(5007, 1006, 3006, 7, 9011, 1, '2025-08-17 01:43:38'),
(5007, 1006, 3006, 7, 9012, 1, '2025-08-17 02:01:58'),
(5007, 1006, 3006, 6, 9013, 1, '2025-08-17 02:08:54'),
(5007, 1006, 3006, 6, 9014, 1, '2025-08-17 02:08:55'),
(5008, 1006, 3006, 6, 9015, 1, '2025-08-17 02:10:41'),
(5008, 1006, 3006, 6, 9016, 1, '2025-08-17 02:10:42'),
(5008, 1006, 3006, 7, 9017, 1, '2025-08-17 02:10:43');

-- --------------------------------------------------------

--
-- Table structure for table `Department`
--

CREATE TABLE `Department` (
  `dept_id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `dept_cost` int(11) NOT NULL,
  `budget` int(11) NOT NULL,
  `fk_org_id` int(11) NOT NULL,
  `fk_admin_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Department`
--

INSERT INTO `Department` (`dept_id`, `name`, `dept_cost`, `budget`, `fk_org_id`, `fk_admin_id`) VALUES
(3001, 'Cardiology', 40000, 90000, 1001, 2001),
(3002, 'Neurology', 35000, 80000, 1002, 2002),
(3003, 'Orthopedics', 45000, 95000, 1003, 2003),
(3004, 'Radiology', 30000, 70000, 1004, 2004),
(3005, 'Dermatology', 20000, 50000, 1005, 2005),
(3006, 'Default Department', 0, 10000, 1006, 2006),
(3007, 'Default Department', 0, 100000, 1007, 2007);

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

--
-- Dumping data for table `Inventory`
--

INSERT INTO `Inventory` (`fk_org_id`, `fk_dept_id`, `fk_med_id`, `med_type`, `quantity`, `location`) VALUES
(1001, 3001, 6, 'Diabetes', 250, 'Shelf F1'),
(1002, 3002, 7, 'Antibiotic', 180, 'Shelf G2'),
(1003, 3003, 8, 'Gastric', 300, 'Shelf H3'),
(1004, 3004, 9, 'Allergy', 220, 'Shelf I4'),
(1005, 3005, 10, 'Injection', 100, 'Shelf J5');

-- --------------------------------------------------------

--
-- Table structure for table `Manager`
--

CREATE TABLE `Manager` (
  `manager_id` int(11) NOT NULL,
  `pwd` varchar(255) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `gmail` varchar(255) NOT NULL,
  `contact` varchar(20) NOT NULL,
  `fk_dept_id` int(11) NOT NULL,
  `fk_org_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Manager`
--

INSERT INTO `Manager` (`manager_id`, `pwd`, `name`, `gmail`, `contact`, `fk_dept_id`, `fk_org_id`) VALUES
(4001, 'm123', 'Michael Manager', 'michael@lifecare.com', '9001112233', 3001, 1001),
(4002, 'm124', 'Rachel Manager', 'rachel@wellness.com', '9001112234', 3002, 1002),
(4003, 'm125', 'Steve Manager', 'steve@carepoint.com', '9001112235', 3003, 1003),
(4004, 'm126', 'Clara Manager', 'clara@healthylife.com', '9001112236', 3004, 1004),
(4005, 'm127', 'Bruce Manager', 'bruce@mediworld.com', '9001112237', 3005, 1005),
(4006, 'MTIzNA==', 'manger1', 'manager1@gmail.com', '019851', 3007, 1007);

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

--
-- Dumping data for table `Medicine`
--

INSERT INTO `Medicine` (`med_id`, `med_name`, `med_type`, `company`) VALUES
(6, 'Metformin', 'Diabetes', 'PharmaLife'),
(7, 'Azithromycin', 'Antibiotic', 'MediCare'),
(8, 'Omeprazole', 'Gastric', 'HealthFirst'),
(9, 'Cetirizine', 'Allergy', 'BioPharma'),
(10, 'Insulin', 'Injection', 'WellnessLab');

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

--
-- Dumping data for table `Organization`
--

INSERT INTO `Organization` (`org_id`, `name`, `address`, `gmail`) VALUES
(1001, 'LifeCare Hospital', '789 Cure Road, MediTown', 'contact@lifecare.com'),
(1002, 'Wellness Hospital', '12 Healing Street, HealthCity', 'info@wellness.com'),
(1003, 'CarePoint Clinic', '98 Relief Lane, MedVille', 'care@carepoint.com'),
(1004, 'HealthyLife Ltd', '55 Fitness Blvd, Vital City', 'hello@healthylife.com'),
(1005, 'MediWorld', '222 Global Ave, Health Town', 'world@mediworld.com'),
(1006, 'Default Medical Shop', 'Default Address', 'default@medicalshop.com'),
(1007, 'jihad\'s pharma', 'mohammadpur', 'jihad@gmail.com');

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

--
-- Dumping data for table `Organization_buy_history`
--

INSERT INTO `Organization_buy_history` (`buy_id`, `fk_org_id`, `fk_dept_id`, `fk_med_id`, `quantity`, `buy_date`) VALUES
(8001, 1001, 3001, 6, 80, '2025-03-10 09:00:00'),
(8002, 1002, 3002, 7, 120, '2025-03-11 10:30:00'),
(8003, 1003, 3003, 8, 200, '2025-03-12 12:15:00'),
(8004, 1004, 3004, 9, 150, '2025-03-13 14:00:00'),
(8005, 1005, 3005, 10, 60, '2025-03-14 16:30:00');

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
-- Dumping data for table `sell_record`
--

INSERT INTO `sell_record` (`fk_org_id`, `fk_dept_id`, `month_1`, `month_2`, `month_3`, `month_4`, `month_5`, `month_6`, `fk_med_id`) VALUES
(1001, 3001, 1300, 1400.25, 1350.5, 1450.75, 160, 170, 6),
(1002, 3002, 1100.5, 1250.75, 1200, 1300.25, 140, 150, 7),
(1003, 3003, 1500.25, 1600.75, 1700, 1800.5, 200, 210, 8),
(1004, 3004, 900.75, 1000, 950.5, 1050.25, 120, 130, 9),
(1005, 3005, 800.25, 850.75, 900.5, 950, 100, 110, 10);

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
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2008;

--
-- AUTO_INCREMENT for table `Customer`
--
ALTER TABLE `Customer`
  MODIFY `customer_id` bigint(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5011;

--
-- AUTO_INCREMENT for table `Customer_buy_history`
--
ALTER TABLE `Customer_buy_history`
  MODIFY `trans_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9018;

--
-- AUTO_INCREMENT for table `Department`
--
ALTER TABLE `Department`
  MODIFY `dept_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3008;

--
-- AUTO_INCREMENT for table `Manager`
--
ALTER TABLE `Manager`
  MODIFY `manager_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4007;

--
-- AUTO_INCREMENT for table `Medicine`
--
ALTER TABLE `Medicine`
  MODIFY `med_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `Organization`
--
ALTER TABLE `Organization`
  MODIFY `org_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1008;

--
-- AUTO_INCREMENT for table `Organization_buy_history`
--
ALTER TABLE `Organization_buy_history`
  MODIFY `buy_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8006;

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
