-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: db
-- Generation Time: Nov 15, 2020 at 01:53 PM
-- Server version: 10.5.7-MariaDB-1:10.5.7+maria~focal
-- PHP Version: 7.4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mpb_client`
--

-- --------------------------------------------------------

--
-- Table structure for table `affiliation`
--

CREATE TABLE `affiliation` (
  `id` int(11) NOT NULL,
  `domaine` varchar(50) NOT NULL,
  `lien` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `clients`
--

CREATE TABLE `clients` (
  `id` int(11) NOT NULL,
  `nom` varchar(20) NOT NULL,
  `prenom` varchar(20) NOT NULL,
  `courriel` varchar(20) NOT NULL,
  `pseudo` varchar(20) NOT NULL,
  `mdp` varchar(20) NOT NULL,
  `solde` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `clientSiteParrain`
--

CREATE TABLE `clientSiteParrain` (
  `id_Client` int(11) NOT NULL,
  `id_Site` int(11) NOT NULL,
  `code_P` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `ClientsRoles`
--

CREATE TABLE `ClientsRoles` (
  `id_Client` int(11) NOT NULL,
  `id_Role` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `historique`
--

CREATE TABLE `historique` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `type` varchar(20) NOT NULL,
  `valeur` double NOT NULL,
  `id_Client` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `info_payement`
--

CREATE TABLE `info_payement` (
  `id_Client` int(11) NOT NULL,
  `IBAN` varchar(50) NOT NULL,
  `BIC` varchar(50) NOT NULL,
  `compte` double NOT NULL,
  `transaction_en_cours` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `role` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `site_synthese`
--

CREATE TABLE `site_synthese` (
  `id` int(11) NOT NULL,
  `domaine` varchar(50) NOT NULL,
  `compte` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `affiliation`
--
ALTER TABLE `affiliation`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `clientSiteParrain`
--
ALTER TABLE `clientSiteParrain`
  ADD PRIMARY KEY (`id_Client`),
  ADD KEY `liaison_Ps` (`id_Site`);

--
-- Indexes for table `ClientsRoles`
--
ALTER TABLE `ClientsRoles`
  ADD PRIMARY KEY (`id_Client`),
  ADD KEY `liaison_role` (`id_Role`);

--
-- Indexes for table `historique`
--
ALTER TABLE `historique`
  ADD PRIMARY KEY (`id`,`id_Client`),
  ADD KEY `liaison_utilisateur` (`id_Client`);

--
-- Indexes for table `info_payement`
--
ALTER TABLE `info_payement`
  ADD PRIMARY KEY (`id_Client`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `site_synthese`
--
ALTER TABLE `site_synthese`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `clients`
--
ALTER TABLE `clients`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `historique`
--
ALTER TABLE `historique`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `site_synthese`
--
ALTER TABLE `site_synthese`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
