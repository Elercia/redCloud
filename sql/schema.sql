-- --------------------------------------------------------
-- Hôte :                        127.0.0.1
-- Version du serveur:           10.3.9-MariaDB - mariadb.org binary distribution
-- SE du serveur:                Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Export de données de la table redcloud.directory : 1 rows
/*!40000 ALTER TABLE `directory` DISABLE KEYS */;
INSERT INTO `directory` (`id`, `creationDate`, `name`, `resourceId`, `parentDirectory_id`, `user_id`) VALUES
	(1, '2019-01-14 23:10:47', 'root', 'db037ae5-9643-49d0-9601-1ec903209c4b', NULL, 1);
/*!40000 ALTER TABLE `directory` ENABLE KEYS */;

-- Export de données de la table redcloud.file : 2 rows
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
INSERT INTO `file` (`id`, `creationDate`, `fileName`, `resourceId`, `size`, `directory_id`) VALUES
	(2, '2019-01-14 22:20:30', 'file', '4371b70b-38e5-4e49-bf24-3548d91cf7b9', 1722, 1),
	(3, '2019-01-14 22:22:59', 'vincent-rabier.pub', 'a6f86886-4d91-4867-ae95-de384b9746c7', 1722, 1);
/*!40000 ALTER TABLE `file` ENABLE KEYS */;

-- Export de données de la table redcloud.hibernate_sequence : 4 rows
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` (`next_val`) VALUES
	(4),
	(4),
	(4),
	(4);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;

-- Export de données de la table redcloud.token : 1 rows
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
INSERT INTO `token` (`id`, `accessToken`, `creationDate`, `expiringDate`, `refreshToken`, `storedUser_id`) VALUES
	(1, 'e328a051450f414ca059db0a7b6fdde4', '2019-01-14 22:50:38', '2019-01-14 23:33:37', 'c2816735e56c4ef9ac11b42869b63346', 1);
/*!40000 ALTER TABLE `token` ENABLE KEYS */;

-- Export de données de la table redcloud.user : 1 rows
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `creationDate`, `hashedPassword`, `name`, `resourceId`, `userType`) VALUES
	(1, '2019-01-14 22:51:22', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'admin', '6ce1dddb-b9e3-47de-84fb-764f6eb79b16', 'ADMIN');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
