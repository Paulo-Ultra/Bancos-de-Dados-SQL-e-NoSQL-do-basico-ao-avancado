-- Excluindo dados

SELECT * FROM produtos_compra;

SELECT * FROM receitas_medica;

DELETE FROM receitas_medica WHERE id_produto_compra = 3;

-- Se tentasse deletar direto não funcionaria por conta das constraints da receitas_medica (FK)
DELETE FROM produtos_compra WHERE id = 3;