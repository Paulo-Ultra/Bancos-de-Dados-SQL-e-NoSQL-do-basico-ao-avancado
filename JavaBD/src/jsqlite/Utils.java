package jsqlite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Scanner;

public class Utils {

	static Logger logger = LoggerFactory.getLogger("Utils");
	static Scanner teclado = new Scanner(System.in);

	public static Connection conectar() {
		String URL_SERVIDOR = "jdbc:sqlite:src/jsqlite/jsqlite3.udemy.db";

		try{
			Connection conn = DriverManager.getConnection(URL_SERVIDOR);
			String TABLE = """
					CREATE TABLE IF NOT EXISTS PRODUTOS(
						ID INTEGER PRIMARY KEY AUTOINCREMENT,
						NOME TEXT NOT NULL,
						PRECO REAL NOT NULL,
						ESTOQUE INTEGER NOT NULL
					);
					""";
			Statement stmt = conn.createStatement();
			stmt.execute(TABLE);

			return conn;

		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Não foi possível conectar ao SQLite: " + e.getMessage());
			logger.error("Erro");
			return null;
		}
	}

	public static void desconectar(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
			e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
	
	public static void listar() {
		String BUSCAR_TODOOS = """
				SELECT * FROM PRODUTOS
				""";

		try {
			Connection conn = conectar();
			PreparedStatement produtos = conn.prepareStatement(BUSCAR_TODOOS);

			ResultSet res = produtos.executeQuery();

			while (res.next()) {
				System.out.println("--------Produto-----------");
				System.out.println("Id: " + res.getInt(1));
				System.out.println("Produto: " + res.getString(2));
				System.out.println("Preço: " + res.getFloat(3));
				System.out.println("Estoque: " + res.getInt(4));
				System.out.println("--------------------------");
			}
			produtos.close();
			desconectar(conn);

		} catch (Exception e){
			e.printStackTrace();
			System.err.println("Erro ao buscar todos os produtos.");
			System.exit(-42);
		}
	}
	
	public static void inserir() {
		System.out.println("Informe o nome do produto: ");
		String nome = teclado.nextLine();
		System.out.println("Informe o preço do produto: ");
		float preco = teclado.nextFloat();
		System.out.println("Informe a quantidade em estoque: ");
		int estoque = teclado.nextInt();

		String INSERIR = """
				INSERT INTO PRODUTOS (NOME, PRECO, ESTOQUE)
				VALUES (?, ?, ?)
				""";

		try {
			Connection conn = conectar();
			PreparedStatement salvar = conn.prepareStatement(INSERIR);
			salvar.setString(1, nome);
			salvar.setFloat(2, preco);
			salvar.setInt(3, estoque);

			int res = salvar.executeUpdate();

			if(res > 0) {
				System.out.println("O produto " + nome + " foi inserido com sucesso!");
			} else {
				System.out.println("Não foi possível inserir o produto.");
			}
			salvar.close();
			desconectar(conn);
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("Erro salvando produto");
			System.exit(-42);
		}
	}
	
	public static void atualizar() {
		System.out.println("Informe o código do produto: ");
		int id = Integer.parseInt(teclado.nextLine());

		String BUSCAR_POR_ID = "SELECT * FROM PRODUTOS WHERE ID = ?";

		try{
			Connection conn = conectar();
			PreparedStatement produto = conn.prepareStatement(
					BUSCAR_POR_ID);
			produto.setInt(1, id);
			ResultSet res = produto.executeQuery();
			if(res.next()) {
			System.out.println("Informe o nome do produto: ");
			String nome = teclado.nextLine();
			System.out.println("Informe o preço do produto: ");
			float preco = teclado.nextFloat();
			System.out.println("Informe a quantidade em estoque: ");
			int estoque = teclado.nextInt();


			String ATUALIZAR = """
					UPDATE PRODUTOS SET NOME = ?, 
					PRECO = ?, ESTOQUE = ?
					WHERE ID = ?
					""";

			PreparedStatement upd = conn.prepareStatement(ATUALIZAR);
			upd.setString(1, nome);
			upd.setFloat(2, preco);
			upd.setInt(3, estoque);
			upd.setInt(4, id);

			int result = upd.executeUpdate();
				if(result > 0) {
					System.out.println("O produto " + nome + " atualizado com sucesso!");
					upd.close();
					desconectar(conn);
				} else {
					System.out.println("Não foi possível atualizar o produto com este id: " + id);
				}
			} else {
				System.out.println("Não existe produto com este id: " + id);
			}

		} catch (Exception e){
			e.printStackTrace();
			System.err.println("Não foi possivel atualizar o  produto com id " + id);
		}
	}
	
	public static void deletar() {
		String DELETAR = "DELETE FROM PRODUTOS WHERE ID = ?";
		String BUSCAR_POR_ID = "SELECT * FROM PRODUTOS WHERE ID = ?";

		System.out.println("Informe o código do produto: ");
		int id = Integer.parseInt(teclado.nextLine());

		try{
			Connection conn = conectar();
			PreparedStatement produto = conn.prepareStatement(
					BUSCAR_POR_ID);
			produto.setInt(1, id);
			ResultSet res = produto.executeQuery();

			if(res.next()) {
				PreparedStatement deletar = conn.prepareStatement(DELETAR);
				deletar.setInt(1, id);
				int result = deletar.executeUpdate();
				deletar.close();
				desconectar(conn);
				if (result > 0) {
					System.out.println("O produto foi deletado com sucesso!");
				} else {
					System.out.println("Não foi possível deletar o produto com id: " + id);
				}
			} else {
				System.out.println("Não existe produto com o id informado.");
			}

		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("Erro ao deletar produto");
			System.exit(-42);
		}
	}
	
	public static void menu() {
		System.out.println("==================Gerenciamento de Produtos===============");
		System.out.println("Selecione uma opção: ");
		System.out.println("1 - Listar produtos.");
		System.out.println("2 - Inserir produtos.");
		System.out.println("3 - Atualizar produtos.");
		System.out.println("4 - Deletar produtos.");
		
		int opcao = Integer.parseInt(teclado.nextLine());
		if(opcao == 1) {
			listar();
		}else if(opcao == 2) {
			inserir();
		}else if(opcao == 3) {
			atualizar();
		}else if(opcao == 4) {
			deletar();
		}else {
			System.out.println("Opção inválida.");
		}
	}
}
