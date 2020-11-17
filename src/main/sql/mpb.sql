-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Hôte : db
-- Généré le : mar. 17 nov. 2020 à 15:13
-- Version du serveur :  10.5.7-MariaDB-1:10.5.7+maria~focal
-- Version de PHP : 7.4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `mpb`
--

-- --------------------------------------------------------

--
-- Structure de la table `affiliation`
--

CREATE TABLE `affiliation` (
  `id` int(11) NOT NULL,
  `domaine` varchar(50) NOT NULL,
  `lien` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `beginners`
--

CREATE TABLE `beginners` (
  `id_beginner` int(11) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `beginners`
--

INSERT INTO `beginners` (`id_beginner`, `name`) VALUES
(1, 'sports'),
(2, 'paris-sportifs'),
(3, 'bookmakers');

-- --------------------------------------------------------

--
-- Structure de la table `bookmakers`
--

CREATE TABLE `bookmakers` (
  `id_book` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `type` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `bookmakers`
--

INSERT INTO `bookmakers` (`id_book`, `name`, `type`) VALUES
(1, 'winamax', 'bonus'),
(1, 'winamax', 'parrain'),
(1, 'winamax', 'avis'),
(2, 'betclic', 'bonus'),
(2, 'betclic', 'parrain'),
(2, 'betclic', 'avis'),
(3, 'unibet', 'bonus'),
(3, 'unibet', 'parrain'),
(3, 'unibet', 'avis');

-- --------------------------------------------------------

--
-- Structure de la table `clients`
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
-- Structure de la table `clientSiteParrain`
--

CREATE TABLE `clientSiteParrain` (
  `id_Client` int(11) NOT NULL,
  `id_Site` int(11) NOT NULL,
  `code_P` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `ClientsRoles`
--

CREATE TABLE `ClientsRoles` (
  `id_Client` int(11) NOT NULL,
  `id_Role` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `historique`
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
-- Structure de la table `info_payement`
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
-- Structure de la table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `role` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `site_synthese`
--

CREATE TABLE `site_synthese` (
  `id` int(11) NOT NULL,
  `domaine` varchar(50) NOT NULL,
  `compte` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `affiliation`
--
ALTER TABLE `affiliation`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `beginners`
--
ALTER TABLE `beginners`
  ADD PRIMARY KEY (`id_beginner`);

--
-- Index pour la table `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `clientSiteParrain`
--
ALTER TABLE `clientSiteParrain`
  ADD PRIMARY KEY (`id_Client`),
  ADD KEY `liaison_Ps` (`id_Site`);

--
-- Index pour la table `ClientsRoles`
--
ALTER TABLE `ClientsRoles`
  ADD PRIMARY KEY (`id_Client`),
  ADD KEY `liaison_role` (`id_Role`);

--
-- Index pour la table `historique`
--
ALTER TABLE `historique`
  ADD PRIMARY KEY (`id`,`id_Client`),
  ADD KEY `liaison_utilisateur` (`id_Client`);

--
-- Index pour la table `info_payement`
--
ALTER TABLE `info_payement`
  ADD PRIMARY KEY (`id_Client`);

--
-- Index pour la table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `site_synthese`
--
ALTER TABLE `site_synthese`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `clients`
--
ALTER TABLE `clients`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `historique`
--
ALTER TABLE `historique`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `site_synthese`
--
ALTER TABLE `site_synthese`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
