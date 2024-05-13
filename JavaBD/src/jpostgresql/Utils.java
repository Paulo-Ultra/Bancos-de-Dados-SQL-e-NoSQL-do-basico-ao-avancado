package jpostgresql;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Utils {
	
	static Scanner teclado = new Scanner(System.in);

	public static Connection conectar() {
		Properties props = new Properties();
		props.setProperty("user", "paulo");
		props.setProperty("password", "udemy");
		props.setProperty("ssl", "false");
		String URL_SERVIDOR = "jdbc:postgresql://localhost:5432/jpostgresql";

		try{
			return DriverManager.getConnection(URL_SERVIDOR, props);
		} catch (Exception e) {
            e.printStackTrace();
			if(e instanceof ClassNotFoundException){
				System.err.println("Verifique o driver de conexão");
			} else {
				System.err.println("Verifique se o servidor está ativo");
			}
			System.exit(-42);
			return null;
        }
	}

	public static void desconectar(Connection conn) {
		if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}
	
	public static void listar() {
		String BUSCAR_TODOS = """
				SELECT * FROM PRODUTOS
				""";

		try{
			Connection conn = conectar();
			PreparedStatement produtos = conn.prepareStatement(
					BUSCAR_TODOS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY
			);
			ResultSet res = produtos.executeQuery();
			while(res.next()) {
				System.out.println("Listando produtos...");
				System.out.println("--------------------");
				System.out.println("ID " + res.getInt(1));
				System.out.println("Produto " + res.getString(2));
				System.out.println("Preço " + res.getFloat(3));
				System.out.println("Estoque " + res.getInt(4));
				System.out.println("-------------------------");

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro buscando todos os produtos");
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

			salvar.executeUpdate();
			salvar.close();
			desconectar(conn);
			System.out.println("O produto " + nome + " foi inserido com sucesso!");
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
					BUSCAR_POR_ID, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			produto.setInt(1, id);
			ResultSet res = produto.executeQuery();
			if(res.next()) {
				System.out.println("Informe o nome do produto: ");
				String nome = teclado.nextLine();
				System.out.println("Informe o preço: ");
				float preco = teclado.nextFloat();
				System.out.println("Informe a quantidade em estoque: ");
				int estoque = teclado.nextInt();

				String ATUALIZAR = """
						UPDATE PRODUTOS SET NOME = ?,
							PRECO = ?,
							ESTOQUE = ?
							WHERE ID = ?
						""";
				PreparedStatement update = conn.prepareStatement(ATUALIZAR);
				update.setString(1, nome);
				update.setFloat(2, preco);
				update.setInt(3, estoque);
				update.setInt(4, id);

				update.executeUpdate();
				update.close();
				desconectar(conn);
				System.out.println("O produto " + nome + " foi atualizado com sucesso!");
			} else {
				System.out.println("Não existe produto com id informado.");
			}

		} catch (Exception e){
			e.printStackTrace();
			System.err.println("Erro buscando produto");
			System.exit(-42);
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
					BUSCAR_POR_ID, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			produto.setInt(1, id);
			ResultSet res = produto.executeQuery();

			if(res.next()) {
				PreparedStatement delete = conn.prepareStatement(DELETAR);
				delete.setInt(1, id);
				delete.executeUpdate();
				delete.close();
				desconectar(conn);
				System.out.println("O produto foi deletado com sucesso!");
			} else {
				System.out.println("Não existe produto com o id informado.");
			}
		} catch (Exception e) {
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
