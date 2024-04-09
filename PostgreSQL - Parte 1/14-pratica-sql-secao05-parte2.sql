-- SELECT * FROM tipos_produto;

-- SELECT * FROM produtos;
--O alias precisa estar em aspas duplas, sem aspas não aceita espaço e fica sempre minúsculo no resultado
SELECT p.codigo AS "ID", p.descricao AS "DESCRIÇÃO", p.preco ASA "PREÇO", tp.descricao AS Tipo
	FROM produtos AS p, tipos_produto AS tp
    WHERE p.codigo_tipo = tp.codigo;