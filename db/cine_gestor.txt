-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS cine_gestor;
USE cine_gestor;

-- Tabela de Usuários
CREATE TABLE Usuario (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome_completo VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    contato VARCHAR(15),
    login VARCHAR(50) UNIQUE NOT NULL,
    senha VARCHAR(50) NOT NULL,
    tipo ENUM('administrador', 'funcionario') NOT NULL
);

-- Tabela de Filmes
CREATE TABLE Filme (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    sinopse TEXT,
    duracao TIME NOT NULL,
    classificacao_etaria VARCHAR(10) NOT NULL
);

-- Tabela de Salas
CREATE TABLE Sala (
    id INT PRIMARY KEY AUTO_INCREMENT,
    numero_sala VARCHAR(10) UNIQUE NOT NULL,
    qtd_cadeiras INT NOT NULL,
    colunas INT NOT NULL,
    linhas INT NOT NULL,
    lado_entrada ENUM('esquerda', 'direita') NOT NULL
);

-- Tabela de Sessões
CREATE TABLE Sessao (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_filme INT,
    id_sala INT,
    horario DATETIME NOT NULL,
    FOREIGN KEY (id_filme) REFERENCES Filme(id),
    FOREIGN KEY (id_sala) REFERENCES Sala(id)
);

-- Tabela de Preços de Ingressos
CREATE TABLE PrecoIngresso (
    id INT PRIMARY KEY AUTO_INCREMENT,
    dia_semana ENUM('domingo', 'segunda', 'terça', 'quarta', 'quinta', 'sexta', 'sábado') NOT NULL,
    preco DECIMAL(10, 2) NOT NULL
);

-- Tabela de Ingressos
CREATE TABLE Ingresso (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_sessao INT,
    id_usuario INT,
    id_preco INT,
    data_venda DATETIME NOT NULL,
    FOREIGN KEY (id_sessao) REFERENCES Sessao(id),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id),
    FOREIGN KEY (id_preco) REFERENCES PrecoIngresso(id)
);
