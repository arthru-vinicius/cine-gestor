-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           11.5.2-MariaDB - mariadb.org binary distribution
-- OS do Servidor:               Win64
-- HeidiSQL Versão:              12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Copiando estrutura do banco de dados para cine_gestor
CREATE DATABASE IF NOT EXISTS `cine_gestor` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `cine_gestor`;

-- Copiando estrutura para tabela cine_gestor.assento
CREATE TABLE IF NOT EXISTS `assento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_sala` int(11) NOT NULL,
  `linha` char(1) NOT NULL,
  `coluna` int(11) NOT NULL,
  `ocupado` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `id_sala` (`id_sala`),
  CONSTRAINT `assento_ibfk_1` FOREIGN KEY (`id_sala`) REFERENCES `sala` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Copiando dados para a tabela cine_gestor.assento: ~0 rows (aproximadamente)

-- Copiando estrutura para tabela cine_gestor.assentos_ocupados
CREATE TABLE IF NOT EXISTS `assentos_ocupados` (
  `sessao_id` int(11) NOT NULL,
  `assento` varchar(255) DEFAULT NULL,
  KEY `FKb912pmgg7xbd6kgjm4ijqdwvg` (`sessao_id`),
  CONSTRAINT `FKb912pmgg7xbd6kgjm4ijqdwvg` FOREIGN KEY (`sessao_id`) REFERENCES `sessao` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Copiando dados para a tabela cine_gestor.assentos_ocupados: ~0 rows (aproximadamente)

-- Copiando estrutura para tabela cine_gestor.filme
CREATE TABLE IF NOT EXISTS `filme` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(100) NOT NULL,
  `sinopse` text DEFAULT NULL,
  `duracao` varchar(255) NOT NULL,
  `classificacao_etaria` varchar(255) NOT NULL,
  `imagem_caminho` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Copiando dados para a tabela cine_gestor.filme: ~0 rows (aproximadamente)

-- Copiando estrutura para tabela cine_gestor.ingresso
CREATE TABLE IF NOT EXISTS `ingresso` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_sessao` int(11) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `id_preco` int(11) DEFAULT NULL,
  `tipo` enum('inteira','meia') NOT NULL,
  `metodo_pagamento` enum('pix','cartao_credito','cartao_debito','dinheiro') NOT NULL,
  `assento` varchar(255) NOT NULL,
  `data_venda` datetime NOT NULL,
  `preco_pago` decimal(38,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_sessao` (`id_sessao`),
  KEY `id_usuario` (`id_usuario`),
  KEY `id_preco` (`id_preco`),
  CONSTRAINT `ingresso_ibfk_1` FOREIGN KEY (`id_sessao`) REFERENCES `sessao` (`id`),
  CONSTRAINT `ingresso_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
  CONSTRAINT `ingresso_ibfk_3` FOREIGN KEY (`id_preco`) REFERENCES `precoingresso` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Copiando dados para a tabela cine_gestor.ingresso: ~0 rows (aproximadamente)

-- Copiando estrutura para tabela cine_gestor.precoingresso
CREATE TABLE IF NOT EXISTS `precoingresso` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dia_semana` enum('domingo','segunda','terça','quarta','quinta','sexta','sábado') NOT NULL,
  `preco` decimal(38,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Copiando dados para a tabela cine_gestor.precoingresso: ~0 rows (aproximadamente)

-- Copiando estrutura para tabela cine_gestor.sala
CREATE TABLE IF NOT EXISTS `sala` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numero_sala` varchar(255) NOT NULL,
  `qtd_cadeiras` int(11) NOT NULL,
  `colunas` int(11) NOT NULL,
  `linhas` int(11) NOT NULL,
  `lado_entrada` enum('esquerda','direita') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `numero_sala` (`numero_sala`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Copiando dados para a tabela cine_gestor.sala: ~0 rows (aproximadamente)

-- Copiando estrutura para tabela cine_gestor.sessao
CREATE TABLE IF NOT EXISTS `sessao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_filme` int(11) DEFAULT NULL,
  `id_sala` int(11) DEFAULT NULL,
  `horario` datetime NOT NULL,
  `assentos_ocupados` text DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_filme` (`id_filme`),
  KEY `id_sala` (`id_sala`),
  CONSTRAINT `sessao_ibfk_1` FOREIGN KEY (`id_filme`) REFERENCES `filme` (`id`),
  CONSTRAINT `sessao_ibfk_2` FOREIGN KEY (`id_sala`) REFERENCES `sala` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Copiando dados para a tabela cine_gestor.sessao: ~0 rows (aproximadamente)

-- Copiando estrutura para tabela cine_gestor.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome_completo` varchar(100) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `contato` varchar(15) DEFAULT NULL,
  `login` varchar(50) NOT NULL,
  `senha` varchar(100) NOT NULL,
  `tipo` enum('administrador','funcionario') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cpf` (`cpf`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Copiando dados para a tabela cine_gestor.usuario: ~2 rows (aproximadamente)
INSERT INTO `usuario` (`id`, `nome_completo`, `cpf`, `contato`, `login`, `senha`, `tipo`) VALUES
	(5, 'Desenvolvedor', '16108525088', '81999999999', 'dev', '$2b$12$q2jtmabF9UITuscSgUxr7ujx53DfX7QV5nMFkpwIl/vYzD5yx3Mji', 'administrador'),
	(6, 'Desenvolvedor2', '15961740056', '81999999999', 'user', '$2b$12$q2jtmabF9UITuscSgUxr7ujx53DfX7QV5nMFkpwIl/vYzD5yx3Mji', 'funcionario');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
