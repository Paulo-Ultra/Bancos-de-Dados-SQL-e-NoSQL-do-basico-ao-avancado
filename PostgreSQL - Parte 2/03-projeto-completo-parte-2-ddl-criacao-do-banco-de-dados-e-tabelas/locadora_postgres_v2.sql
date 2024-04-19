-- -----------------------------------------------------
-- Table `locadora`.`atores`
-- -----------------------------------------------------
CREATE TABLE atores (
  id SERIAL NOT NULL PRIMARY KEY,
  nome VARCHAR(100) NOT NULL
);


-- -----------------------------------------------------
-- Table `locadora`.`generos`
-- -----------------------------------------------------
CREATE TABLE generos (
  id SERIAL NOT NULL PRIMARY KEY,
  genero VARCHAR(45) NOT NULL
);


-- -----------------------------------------------------
-- Table `locadora`.`clientes`
-- -----------------------------------------------------
CREATE TABLE clientes (
  id SERIAL NOT NULL PRIMARY KEY,
  nome VARCHAR(45) NOT NULL,
  sobrenome VARCHAR(45) NOT NULL,
  telefone VARCHAR(20) NOT NULL,
  endereco VARCHAR(100) NOT NULL
);


-- -----------------------------------------------------
-- Table `locadora`.`filmes`
-- -----------------------------------------------------
CREATE TABLE filmes (
  id SERIAL NOT NULL PRIMARY KEY,
  id_genero INT REFERENCES generos(id) NOT NULL,
  titulo VARCHAR(100) NOT NULL,
  valor DECIMAL(8,2) NOT NULL
);


-- -----------------------------------------------------
-- Table `locadora`.`atores_filme`
-- -----------------------------------------------------
CREATE TABLE atores_filme (
  id SERIAL NOT NULL PRIMARY KEY,
  id_filme INT REFERENCES filmes(id) NOT NULL,
  id_ator INT REFERENCES atores(id) NOT NULL,
  personagem VARCHAR(100) NOT NULL
);


-- -----------------------------------------------------
-- Table `locadora`.`dvds`
-- -----------------------------------------------------
CREATE TABLE dvds (
  id SERIAL NOT NULL PRIMARY KEY,
  id_filme INT REFERENCES filmes(id) NOT NULL,
  quantidade INT NOT NULL
);


-- -----------------------------------------------------
-- Table `locadora`.`emprestimos`
-- -----------------------------------------------------
CREATE TABLE emprestimos (
  id SERIAL NOT NULL PRIMARY KEY,
  data DATE NOT NULL,
  id_cliente INT REFERENCES clientes(id) NOT NULL
);


-- -----------------------------------------------------
-- Table `locadora`.`filmes_emprestimo`
-- -----------------------------------------------------
CREATE TABLE filmes_emprestimo (
  id SERIAL NOT NULL PRIMARY KEY,
  id_dvd INT REFERENCES dvds(id) NOT NULL,
  id_emprestimo INT NOT NULL
);


-- -----------------------------------------------------
-- Table `locadora`.`devolucoes`
-- -----------------------------------------------------
CREATE TABLE devolucoes (
  id SERIAL NOT NULL PRIMARY KEY,
  id_emprestimo INT REFERENCES emprestimos(id) NOT NULL,
  data DATE NOT NULL
);


-- -----------------------------------------------------
-- Table `locadora`.`filmes_devolucao`
-- -----------------------------------------------------
CREATE TABLE filmes_devolucao (
  id SERIAL NOT NULL PRIMARY KEY,
  id_devolucao INT REFERENCES devolucoes(id) NOT NULL,
  id_filme_emprestimo INT REFERENCES filmes_emprestimo(id) NOT NULL
);
