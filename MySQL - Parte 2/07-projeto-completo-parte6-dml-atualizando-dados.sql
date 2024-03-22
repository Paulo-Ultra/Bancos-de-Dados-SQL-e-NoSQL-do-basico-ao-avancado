-- Atualizando Dados
UPDATE tipos_produtos SET tipo = 'Bijuterias' WHERE id = 4;

UPDATE produtos SET preco_venda = '4.16', id_tipo_produto = 1, id_fabricante = 1 WHERE id = 2;

UPDATE produtos_compra SET id_compra = 2, id_produto = 1, quantidade = 4 WHERE id = 1;