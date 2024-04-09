-- INSERTS

-- Tipos Produto
INSERT INTO tipos_produto (descricao) VALUES ('Computadores');
INSERT INTO tipos_produto (descricao) VALUES ('Impressoras');
INSERT INTO tipos_produto (descricao) VALUES ('Diversos');

-- Produtos
INSERT INTO produtos (descricao, preco, id_tipo_produto) VALUES ('Notebook DELL 1544', 2345.67, 1);
INSERT INTO produtos (descricao, preco, id_tipo_produto) VALUES ('Impr. Jato de Tinta', 456.00, 2);
INSERT INTO produtos (descricao, preco, id_tipo_produto) VALUES ('Mouse sem fio', 45, 3);

-- Pacientes
INSERT INTO pacientes (nome, endereco, bairro, cidade, estado, cep, data_nascimento) 
	VALUES ('Angelina Jolie', 'Rua da paz, 44', 'Nova Lima', 'Santos', 'SP', '121213213', '1978-04-24');

-- Selects
SELECT * FROM pacientes;