CREATE DATABASE IF NOT EXISTS `pmbdbtest`;
USE `pmbdbtest`;


DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE `utilisateur` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `mot_de_passe` varchar(255) NOT NULL,
  `nom_utilisateur` varchar(45) NOT NULL,
  `solde` decimal(10,2) DEFAULT NULL,
  `compte_bancaire` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



DROP TABLE IF EXISTS `connexion`;
CREATE TABLE `connexion` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `utilisateur_id` int UNSIGNED NOT NULL,
  `ami_id` int UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_utilisateur_id` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`id`),
  CONSTRAINT `fk_ami_id` FOREIGN KEY (`ami_id`) REFERENCES `utilisateur` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `versement`;
CREATE TABLE `versement` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `horodatage` timestamp NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `montant` decimal(10,2) NOT NULL,
  `utilisateur_source` int UNSIGNED NOT NULL,
  `mouvement` varchar(6) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_utilisateur_source_vers` FOREIGN KEY (`utilisateur_source`) REFERENCES `utilisateur` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `virement`;
CREATE TABLE `virement` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `horodatage` timestamp NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `montant` decimal(10,2) NOT NULL,
  `utilisateur_source` int UNSIGNED NOT NULL,
  `utilisateur_destinataire` int UNSIGNED NOT NULL,
  `frais` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_utilisateur_source_vir` FOREIGN KEY (`utilisateur_source`) REFERENCES `utilisateur` (`id`),
  CONSTRAINT `fk_utilisateur_destinataire` FOREIGN KEY (`utilisateur_destinataire`) REFERENCES `utilisateur` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



LOCK TABLES `utilisateur` WRITE;
INSERT INTO utilisateur (email, mot_de_passe, nom_utilisateur, solde, compte_bancaire)
VALUES ('jaboyd0@email.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'JohnBoyd', 974.51, '8418746512'),
        ('drk@email.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'JacobBoyd', 74.51, '8418746513'),
        ('tenz@email.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'TenleyBoyd', 94.51, '8418746512'),
        ('jaboyd1@email.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'RogerBoyd', 97.50, '8418746512'),
        ('jaboyd2@email.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'FeliciaBoyd', 974.51, '8418746544'),
        ('drk1@email.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'JonanathanMarrack', 47.51, '8418746513'),
        ('tenz1@email.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'TessaCarman', 94.50, '8418746512'),
        ('jaboyd3@email.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'PeterDuncan', 9.51, '8418746512'),
        ('jaboyd4@email.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'FosterShepard', 49.51, '8418746544'),
        ('tcoop@ymail.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'TonyCooper', 4.51, '8418746874'),
        ('lily@email.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'LilyCooper', 4.51, '8418749845'),
        ('soph@email.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'SophiaZemicks', 0.51, '8418747878'),
        ('ward@email.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'WarrenZemicks', 4.50, '8418747512'),
        ('zarc@email.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'ZachZemicks', 974.51, '8418747512'),
        ('reg@email.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'ReginoldWalker', 974.51, '8418748547'),
        ('jpeter@email.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'JamiePeters', 9745.1, '8418747462'),
        ('jpeter2@email.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'RonPeters', 974.51, '8418748888'),
        ('aly@imail.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'AllisonBoyd', 51.97, '8418749888'),
        ('bstel@email.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'BrianStelzer', 55.74, '8418747784'),
        ('ssanw@email.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'ShawnaStelzer', 974.94, '8418747784'),
        ('bstel2@email.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'KendrikStelzer', 74.47, '8418747784'),
        ('clivfd@ymail.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'CliveFerguson', 974.51, '8418746741'),
        ('gramps@email.com', '$2a$10$W.4hO4Qx9yJ3z3CofvLqsuk3vEDkVxeAF6xYgPyE5CKJLWqk2vjsS', 'EricCadigan', 974.51, '8418747458');
UNLOCK TABLES;

LOCK TABLES `connexion` WRITE;
INSERT INTO connexion (utilisateur_id, ami_id)
VALUES (1, 2),
	(3, 4),
	(5, 6),
	(5, 7),
	(8, 9),
	(10, 9),
	(11, 12),
	(13, 14),
	(15, 16),
	(16, 17),
	(18, 17),
	(20, 19),
	(21, 22),
	(21, 6),
	(6, 23),
	(23, 2);
UNLOCK TABLES;

LOCK TABLES `versement` WRITE;
INSERT INTO versement (horodatage, description, montant, utilisateur_source, mouvement)
VALUES ('2022-03-07 09:56:45', 'test', 20.01, 2, 'DEBIT'),
	('2022-03-07 09:56:45', 'test', 20.01, 4, 'CREDIT'),
	('2022-03-07 09:56:45', 'test', 20.01, 5, 'CREDIT'),
	('2022-03-07 09:56:45', 'test', 20.01, 6, 'DEBIT'),
	('2022-03-07 09:56:45', 'test', 20.01, 7, 'DEBIT'),
	('2022-03-07 09:56:45', 'test', 20.01, 9, 'DEBIT'),
	('2022-03-07 09:56:45', 'test', 20.01, 11, 'CREDIT'),
	('2022-03-07 09:56:45', 'test', 20.01, 12, 'CREDIT'),
	('2022-03-07 09:56:45', 'test', 20.01, 13, 'DEBIT'),
	('2022-03-07 09:56:45', 'test', 20.01, 15, 'DEBIT'),
	('2022-03-07 09:56:45', 'test', 20.01, 16, 'DEBIT'),
	('2022-03-07 09:56:45', 'test', 20.01, 18, 'CREDIT'),
	('2022-03-07 09:56:45', 'test', 20.01, 19, 'DEBIT'),
	('2022-03-07 09:56:45', 'test', 20.01, 20, 'DEBIT'),
	('2022-03-07 09:56:45', 'test', 20.01, 21, 'CREDIT'),
	('2022-03-07 09:56:45', 'test', 20.01, 22, 'DEBIT');
UNLOCK TABLES;

LOCK TABLES `virement` WRITE;
INSERT INTO virement (horodatage, description, montant, utilisateur_source, utilisateur_destinataire, frais)
VALUES ('2022-03-07 09:56:45', 'test', 33.30, 1, 2, 3.50),
	('2022-03-07 09:56:45', 'test', 33.30, 2, 3, 3.50),
	('2022-03-07 09:56:45', 'test', 33.30, 4, 5, 3.50),
	('2022-03-07 09:56:45', 'test', 33.30, 4, 6, 3.50),
	('2022-03-07 09:56:45', 'test', 33.30, 7, 8, 3.50),
	('2022-03-07 09:56:45', 'test', 33.30, 9, 8, 3.50),
	('2022-03-07 09:56:45', 'test', 33.30, 11, 12, 3.50),
	('2022-03-07 09:56:45', 'test', 33.30, 12, 13, 3.50),
	('2022-03-07 09:56:45', 'test', 33.30, 14, 15, 3.50),
	('2022-03-07 09:56:45', 'test', 33.30, 14, 16, 3.50),
	('2022-03-07 09:56:45', 'test', 33.30, 17, 18, 3.50),
	('2022-03-07 09:56:45', 'test', 33.30, 19, 18, 3.50),
	('2022-03-07 09:56:45', 'test', 33.30, 20, 21, 3.50),
	('2022-03-07 09:56:45', 'test', 33.30, 22, 23, 3.50),
	('2022-03-07 09:56:45', 'test', 33.30, 20, 21, 3.50);
UNLOCK TABLES;

