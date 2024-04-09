-- CREATE DATABASE secao04;

-- Nao funciona no PostgreSQL - (Funciona no MySQL)
-- USE secao04;

-- CREATE TABLE tipos_produto(
-- 	codigo SERIAL PRIMARY KEY,
--	descricao VARCHAR(30) NOT NULL
-- );

-- CREATE TABLE produtos(
--	codigo SERIAL PRIMARY KEY, 
--	descricao VARCHAR(30) NOT NULL,
-- 	preco MONEY NOT NULL,  --Tipo Money aceita string ou number
--	codigo_tipo INT REFERENCES tipos_produto(codigo) NOT NULL
-- );

-- INSERT INTO tipos_produto (descricao) VALUES ('Computador');
-- INSERT INTO tipos_produto (descricao) VALUES ('Impressora');

-- INSERT INTO produtos (descricao, preco, codigo_tipo) VALUES ('Desktop', '1200', 1);
-- INSERT INTO produtos (descricao, preco, codigo_tipo) VALUES ('Laptop', '1800', 1);
-- INSERT INTO produtos (descricao, preco, codigo_tipo) VALUES ('Impr. Jato Tinta', '300', 2);
-- INSERT INTO produtos (descricao, preco, codigo_tipo) VALUES ('Impr. Laser', '500', 2);

-- Select
-- SELECT * FROM tipos_produto;

-- SELECT codigo, descricao FROM tipos_produto;

-- SELECT * FROM produtos;

-- SELECT codigo, descricao, codigo_tipo FROM produtos;

-- Erro Select
-- SELECT codigo, descricao, preco, codigo_tipo FROM produtos;

-- Alias 
SELECT p.codigo AS cod, p.descricao AS descr, p.preco AS pre, p.codigo_tipo AS ctp FROM produtos AS p;

