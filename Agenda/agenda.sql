create database dbagenda;
use dbagenda;
CREATE TABLE contatos (
  `idcon` int primary key AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `fone` varchar(15) NOT NULL,
  `email` varchar(50) 
);
show tables;
describe contatos;