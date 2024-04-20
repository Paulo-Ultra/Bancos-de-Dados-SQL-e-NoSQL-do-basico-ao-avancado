-- CREATE DATABASE secao04;

CREATE TABLE tipos_produtos(
	codigo INTEGER PRIMARY KEY AUTOINCREMENT,
	descricao TEXT NOT NULL
);

CREATE TABLE produtos(
	codigo INTEGER PRIMARY KEY AUTOINCREMENT, 
	descricao TEXT NOT NULL,
	preco REAL NOT NULL,
	codigo_tipo INTEGER NOT NULL,
	FOREIGN KEY (codigo_tipo) REFERENCES tipos_produtos(codigo)
);

INSERT INTO tipos_produtos (descricao) VALUES ('Computador');
INSERT INTO tipos_produtos (descricao) VALUES ('Impressora');

INSERT INTO produtos (descricao, preco, codigo_tipo) VALUES ('Desktop', '1200', 1);
INSERT INTO produtos (descricao, preco, codigo_tipo) VALUES ('Laptop', '1800', 1);
INSERT INTO produtos (descricao, preco, codigo_tipo) VALUES ('Impr. Jato Tinta', '300', 2);
INSERT INTO produtos (descricao, preco, codigo_tipo) VALUES ('Impr. Laser', '500', 2);


c) Vamos consultar os dados da tabela tipos_produto;
SELECT * FROM tipos_produtos;

d) Já temos a tabela tipos_produtos, então podemos iniciar a transação;

-- Begin Transaction 
BEGIN TRANSACTION;

	INSERT INTO tipos_produtos (descricao) VALUES ('Notebook');
	INSERT INTO tipos_produtos (descricao) VALUES ('Nobreak');

-- Commit
COMMIT;

Vamos checar se os dados foram realmente salvos:
SELECT * FROM tipos_produto;

-- Roll Back

a) Vamos iniciar uma nova transação
BEGIN TRANSACTION;

b) Inserir uns dados (poderia ser insert, update, delete, etc)
INSERT INTO tipos_produtos (descricao) VALUES ('Tablet');
INSERT INTO tipos_produtos (descricao) VALUES ('Baterias');

Vamos checar se os dados foram salvos mesmo sem o commit:
SELECT * FROM tipos_produtos;

Veja que aparentemente tudo está funcionando normalmente. Mas se tivéssemos efetuado o commit não teríamos
como fazer o rollback;

c) Efetuando o roll back
ROLLBACK;

Vamos checar se os dados foram desfeitos pelo rollback:
SELECT * FROM tipos_produto;

Sim!

OBS: Como o campo id da tabela é auto incremento, os dois inserts que fizemos descartaram os valores dos campos removidos.
Isso significa que os ids utilizamos não serão reutilizados.

Só para efeito de comparação, vamos iniciar uma nova transação, inserir dados e commitar e então tentar o rollback

a) Vamos iniciar uma nova transação
BEGIN TRANSACTION;

b) Inserir uns dados (poderia ser insert, update, delete, etc)
INSERT INTO tipos_produto (descricao) VALUES ('Tablet');
INSERT INTO tipos_produto (descricao) VALUES ('Baterias');

c) Commitar
COMMIT;

d) Consultar os dados
SELECT * FROM tipos_produto;

e) Tentar o rollback (Não é possível efetuar o rollback após commitar)
ROLLBACK;

f) Consultar os dados
SELECT * FROM tipos_produto;

Note que a transação não foi desfeita.
