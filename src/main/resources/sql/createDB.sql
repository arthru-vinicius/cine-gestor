-- Copiando estrutura do banco de dados para cinemadb
CREATE DATABASE IF NOT EXISTS `cinemadb` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci */;
USE `cinemadb`;

-- Tabela de Usuário
CREATE TABLE IF NOT EXISTS `usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nome_completo` varchar(100) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `contato` varchar(15) DEFAULT NULL,
  `login` varchar(50) NOT NULL,
  `senha` varchar(50) NOT NULL,
  `tipo` enum('ADMINISTRADOR','FUNCIONARIO') NOT NULL,
  `status` enum('ATIVO','EXCLUIDO') DEFAULT 'ATIVO',
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `cpf` (`cpf`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Tabela de Filme
CREATE TABLE IF NOT EXISTS `filme` (
  `id_filme` int(11) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(100) NOT NULL,
  `sinopse` text DEFAULT NULL,
  `duracao` time NOT NULL,
  `classificacao_etaria` varchar(10) NOT NULL,
  `imagem` varchar(255) DEFAULT NULL,
  `status` enum('ATIVO','EXCLUIDO') DEFAULT 'ATIVO',
  PRIMARY KEY (`id_filme`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Tabela de Sala
CREATE TABLE IF NOT EXISTS `sala` (
  `id_sala` int(11) NOT NULL AUTO_INCREMENT,
  `numero_sala` varchar(10) NOT NULL,
  `qtd_cadeiras` int(11) NOT NULL,
  `colunas` int(11) NOT NULL,
  `linhas` int(11) NOT NULL,
  `lado_entrada` enum('ESQUERDA','DIREITA') NOT NULL,
  `status` enum('ATIVO','EXCLUIDO') DEFAULT 'ATIVO',
  PRIMARY KEY (`id_sala`),
  UNIQUE KEY `numero_sala` (`numero_sala`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Tabela de Sessão
CREATE TABLE IF NOT EXISTS `sessao` (
  `id_sessao` int(11) NOT NULL AUTO_INCREMENT,
  `id_filme` int(11) NOT NULL,
  `id_sala` int(11) NOT NULL,
  `horario` datetime NOT NULL,
  `status` enum('ATIVO','EXCLUIDO') DEFAULT 'ATIVO',
  PRIMARY KEY (`id_sessao`),
  KEY `id_filme` (`id_filme`),
  KEY `id_sala` (`id_sala`),
  CONSTRAINT `sessao_ibfk_1` FOREIGN KEY (`id_filme`) REFERENCES `filme` (`id_filme`),
  CONSTRAINT `sessao_ibfk_2` FOREIGN KEY (`id_sala`) REFERENCES `sala` (`id_sala`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Tabela de Preços de Ingressos
CREATE TABLE IF NOT EXISTS `preco_ingresso` (
  `id_preco` int(11) NOT NULL AUTO_INCREMENT,
  `dia_semana` enum('DOMINGO','SEGUNDA','TERÇA','QUARTA','QUINTA','SEXTA','SÁBADO') NOT NULL,
  `preco` decimal(10,2) NOT NULL,
  `status` enum('ATIVO','EXCLUIDO') DEFAULT 'ATIVO',
  PRIMARY KEY (`id_preco`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Tabela de Ingressos
CREATE TABLE IF NOT EXISTS `ingresso` (
  `id_ingresso` int(11) NOT NULL AUTO_INCREMENT,
  `id_sessao` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_preco` int(11) NOT NULL,
  `data_venda` datetime NOT NULL,
  `preco_pago` decimal(10,2) NOT NULL,
  `tipo` enum('INTEIRA','MEIA') NOT NULL DEFAULT 'INTEIRA',
  `status_ingresso` enum('TEMPORARIO','CONFIRMADO') DEFAULT 'TEMPORARIO',
  `status` enum('ATIVO','EXCLUIDO') DEFAULT 'ATIVO',
  PRIMARY KEY (`id_ingresso`),
  KEY `id_sessao` (`id_sessao`),
  KEY `id_usuario` (`id_usuario`),
  KEY `id_preco` (`id_preco`),
  CONSTRAINT `ingresso_ibfk_1` FOREIGN KEY (`id_sessao`) REFERENCES `sessao` (`id_sessao`),
  CONSTRAINT `ingresso_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `ingresso_ibfk_3` FOREIGN KEY (`id_preco`) REFERENCES `preco_ingresso` (`id_preco`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Tabela de Assento
CREATE TABLE IF NOT EXISTS `assentos` (
  `id_assento` int(11) NOT NULL AUTO_INCREMENT,
  `id_sessao` int(11) NOT NULL,
  `id_ingresso` int(11) DEFAULT NULL,
  `identificador` varchar(10) NOT NULL,
  `status_assento` enum('DISPONIVEL','OCUPADO') DEFAULT 'DISPONIVEL',
  `status` enum('ATIVO','EXCLUIDO') DEFAULT 'ATIVO',
  PRIMARY KEY (`id_assento`),
  KEY `fk_sessao` (`id_sessao`),
  KEY `fk_ingresso` (`id_ingresso`),
  CONSTRAINT `fk_ingresso` FOREIGN KEY (`id_ingresso`) REFERENCES `ingresso` (`id_ingresso`),
  CONSTRAINT `fk_sessao` FOREIGN KEY (`id_sessao`) REFERENCES `sessao` (`id_sessao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Tabela de Controle de Caixa
CREATE TABLE IF NOT EXISTS `controle_caixa` (
  `id_transacao` int(11) NOT NULL AUTO_INCREMENT,
  `tipo_transacao` enum('ENTRADA','SAIDA') NOT NULL,
  `valor` decimal(10,2) NOT NULL,
  `data_transacao` datetime NOT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `status` enum('ATIVO','EXCLUIDO') DEFAULT 'ATIVO',
  PRIMARY KEY (`id_transacao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;