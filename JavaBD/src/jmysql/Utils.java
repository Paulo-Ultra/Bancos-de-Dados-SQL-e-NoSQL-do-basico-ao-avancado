package jmysql;

import java.sql.*;
import java.util.Scanner;

public class Utils {
	
	static Scanner teclado = new Scanner(System.in);

	public static Connection conectar() {
		String CLASSE_DRIVER = "com.mysql.cj.jdbc.Driver";
		String USUARIO = "paulo";
		String SENHA = "udemy";
		String URL_SERVIDOR = "jdbc:mysql://localhost:3305/jmysql?useSSL=false";

		try {
			Class.forName(CLASSE_DRIVER);
			return DriverManager.getConnection(URL_SERVIDOR, USUARIO, SENHA);
		} catch (SQLException | ClassNotFoundException e) {
			if(e instanceof ClassNotFoundException){
				System.out.println("Verifique o driver de conexão");
			}
            e.getMessage();
			throw new RuntimeException(e);
        }
    }

	public static void desconectar(Connection conn) {
		if(conn != null) {
			try{
				conn.close();
			} catch (SQLException e) {
				System.out.println("Não foi possível fechar a conexão");
                throw new RuntimeException(e);
            }
        }
	}
	
	public static void listar() {
		String BUSCAR_TODOS = """
				SELECT * FROM PRODUTOS
				""";

		try{
			Connection conn = conectar();
			PreparedStatement produtos = conn.prepareStatement(BUSCAR_TODOS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet res = produtos.executeQuery();

			res.last();
			int qtd = res.getRow();
			res.beforeFirst();

			if(qtd > 0){
				System.out.println("Listando produtos...");
				System.out.println("---------------------------");

				while (res.next()){
					System.out.println("ID: " + res.getInt(1));
					System.out.println("Produto: " + res.getString(2));
					System.out.println("Preço: " + res.getFloat(3));
					System.out.println("Estoque: " + res.getInt(4));
					System.out.println("-------------------------------");
				}
			} else {
				System.out.println("Não existem produtos cadastrados");
			}

			produtos.close();
			desconectar(conn);
		} catch (Exception e) {
            e.printStackTrace();
			e.getMessage();
			System.err.println("Erro buscando produtos");
			System.exit(-42);
        }
    }
	
	public static void inserir() {
		System.out.println("Informe o nome do produto");
		String nome = teclado.nextLine();

		System.out.println("Informe o preço do produto");
		float preco = teclado.nextFloat();

		System.out.println("Informe a quantidade em estoque");
		int estoque = teclado.nextInt();

		String INSERIR = """
				INSERT INTO PRODUTOS (nome, preco, estoque) 
				VALUES (?, ?, ?)				
				""";

		try{
			Connection conn = conectar();
			PreparedStatement salvar = conn.prepareStatement(INSERIR);
			salvar.setString(1, nome);
			salvar.setFloat(2, preco);
			salvar.setInt(3, estoque);

			salvar.executeUpdate();
			salvar.close();

			desconectar(conn);
			System.out.println("O produto " + nome + " foi inserido com sucesso");
		} catch (Exception e){
			e.printStackTrace();
			e.getMessage();
			System.err.println("Erro salvando produto");
			System.exit(-42);
		}
	}
	
	public static void atualizar() {
		System.out.println("Informe o código do produto: ");
		int id = Integer.parseInt(teclado.nextLine());

		String BUSCAR_POR_ID = """
				SELECT * FROM PRODUTOS
				WHERE ID = ?
				""";

		String ATUALIZAR = """
						UPDATE PRODUTOS SET
						NOME=?,
						PRECO=?,
						ESTOQUE=?
						WHERE ID=?
						""";

		try{
			Connection conn = conectar();
			PreparedStatement produto = conn.prepareStatement(BUSCAR_POR_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			produto.setInt(1, id);
			ResultSet res = produto.executeQuery();

			res.last();
			int qtd = res.getRow();
			res.beforeFirst();
			if(qtd > 0 ){
				System.out.println("Informe o nome do produto");
				String nome = teclado.nextLine();

				System.out.println("Informe o preço do produto");
				float preco = teclado.nextFloat();

				System.out.println("Informe a quantidade em estoque");
				int estoque = teclado.nextInt();

				PreparedStatement update = conn.prepareStatement(ATUALIZAR, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

				update.setString(1, nome);
				update.setFloat(2, preco);
				update.setInt(3, estoque);
				update.setInt(4, id);

				update.executeUpdate();
				update.close();
				desconectar(conn);
				System.out.println("O produto " + nome + " foi atualizado com sucesso");
			} else {
				System.out.println("Não existe produto com o id informado.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
			System.err.println("Erro ao atualizar produto");
			System.exit(-42);
        }

    }
	
	public static void deletar() {
		String DELETAR = """
				DELETE FROM PRODUTOS
				WHERE ID=?
				""";

		String BUSCAR_POR_ID = """
				SELECT * FROM PRODUTOS
				WHERE ID = ?
				""";

		System.out.println("Informe o código do produto: ");
		int id = Integer.parseInt(teclado.nextLine());

		try{
			Connection conn = conectar();
			PreparedStatement produto = conn.prepareStatement(BUSCAR_POR_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			produto.setInt(1, id);
			ResultSet res = produto.executeQuery();

			res.last();
			int qtd = res.getRow();
			res.beforeFirst();
			if(qtd > 0) {
				PreparedStatement del = conn.prepareStatement(DELETAR);
				del.setInt(1, id);
				del.executeUpdate();
				del.close();
				desconectar(conn);
				System.out.println("O produto foi deletado com sucesso.");
			} else {
				System.out.println("Não existe o produto com id informado.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			System.err.println("Erro ao deletar produto");
			System.exit(-42);
			//Esse exit a saída sempre é 0, se for diferente de zero é erro.
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
