package jredis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Utils {
	
	static Scanner teclado = new Scanner(System.in);

	public static Jedis conectar() {
		Jedis conn = new Jedis("http://localhost:6379");
		return conn;
	}

	public static void desconectar(Jedis conn) {
		conn.disconnect();
	}
	
	public static void listar() {
		Jedis conn = conectar();

		try {
			Set<String> res = conn.keys("produtos:*");

			if(res.size() > 0){
				System.out.println("Listando produtos....");
				System.out.println("----------------------");
				for (String chave : res) {
					Map<String, String> produto = conn.hgetAll(chave);
					System.out.println("Id: " + chave);
					System.out.println("Produto: " + produto.get("nome"));
					System.out.println("Preço: " + produto.get("preco"));
					System.out.println("Estoque: " + produto.get("estoque"));
					System.out.println("----------------------");
				}
			} else {
				System.out.println("Não existem produtos cadastrados.");
			}
		} catch (JedisConnectionException e) {
			e.printStackTrace();
			System.out.println("Verifique se o servidor Redis está ativo. " + e.getMessage());
		}
		desconectar(conn);
	}
	
	public static void inserir() {
		Jedis conn = conectar();

		System.out.println("Informe o nome do produto: ");
		String nome = getTeclado();
		System.out.println("Informe o preço do produto: ");
		float preco = teclado.nextFloat();
		System.out.println("Informe o estoque do produto: ");
		int estoque = teclado.nextInt();

		Map<String, String> valor = new HashMap<>();
		valor.put("nome", nome);
		valor.put("preco", String.valueOf(preco));
		valor.put("estoque", String.valueOf(estoque));

		String chave = "produtos:" + Utils.geraId();

		try {
			String res = conn.hmset(chave, valor);
			if(res != null) {
				System.out.println("O produto " + nome + " foi inserido com sucesso.");
			} else {
				System.out.println("Não foi possível inserir o produto.");
			}
		} catch (JedisConnectionException e) {
			System.out.println("Verifique se o Redis está ativo. " + e.getMessage());
		}
		desconectar(conn);
	}

	public static void atualizar() {
		Jedis conn = conectar();

		System.out.println("Informe a chave do produto: ");
		String chave = getTeclado();
		boolean keyExists = conn.exists(chave);

		System.out.println("Informe o nome do produto: ");
		String nome = getTeclado();
		System.out.println("Informe o preço do produto: ");
		float preco = teclado.nextFloat();
		System.out.println("Informe o estoque do produto: ");
		int estoque = teclado.nextInt();

		Map<String, String> valor = new HashMap<>();
		valor.put("nome", nome);
		valor.put("preco", String.valueOf(preco));
		valor.put("estoque", String.valueOf(estoque));

		try {
			if(keyExists) {
				String res = conn.hmset(chave, valor);
				if (res != null) {
					System.out.println("O produto " + nome + " foi atualizado com sucesso.");
				} else {
					System.out.println("Não foi possível atualizar o produto.");
				}
				System.out.println("Não existe produto com o chave informada");
			}
			if(!keyExists) {
				System.out.println("Não existe produto com o chave informada");
			}
		} catch (JedisConnectionException e) {
			System.out.println("Verifique se o Redis está ativo. " + e.getMessage());
		}
		desconectar(conn);
	}

	public static void deletar() {
		Jedis conn = conectar();
		System.out.println("Informe a chave do produto: ");
		String chave = getTeclado();

		try {
			Long ret = conn.del(chave);

			if(ret != null && ret > 0) {
				System.out.println("O produto foi deletado com sucesso.");
			} else{
				System.out.println("Não existe produto com o chave informada");
			}
		} catch(JedisConnectionException e) {
			System.out.println("Verifique se o Redis está ativo: " + e.getMessage());
		}
		desconectar(conn);
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

	private static String geraId() {
		Jedis conn = conectar();
		String chave = conn.get("chave");

		if(chave != null) {
			chave = String.valueOf(conn.incr("chave"));
			desconectar(conn);
			return chave;
		} else {
			conn.set("chave", "1");
			desconectar(conn);
			return "1";
		}
	}

	private static String getTeclado() {
		String chave = teclado.nextLine();
		return chave;
	}
}
