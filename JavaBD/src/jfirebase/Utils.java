package jfirebase;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class Utils {
	
	static Scanner teclado = new Scanner(System.in);

	public static HttpClient conectar() {
		HttpClient conn = HttpClient.newBuilder().build();
		return conn;
	}

	public static void desconectar() {
		System.out.println("Desconectando...");
	}
	
	public static void listar() {
		HttpClient conn = conectar();

		String link = "https://guniversity-3305a-default-rtdb.firebaseio.com/produtos.json";

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(link))
				.build();

		try{
			HttpResponse<String> response = conn.send(request, HttpResponse.BodyHandlers.ofString());
			if (response.body().equals("null")) {
				System.out.println("Não existem produtos cadastrados.");
			} else {
				JSONObject obj = new JSONObject(response.body());
				System.out.println("Listando produtos...");
				System.out.println("---------------------");
				for(int i = 0; i < obj.length(); i++) {
					JSONObject prod = (JSONObject) obj.get(obj.names().getString(i));
					System.out.println("Id: " + obj.names().getString(i));
					System.out.println("Produto: " + prod.get("nome"));
					System.out.println("Preço: " + prod.get("preco"));
					System.out.println("Estoque: " + prod.get("estoque"));
					System.out.println("--------------------");
				}
			}
		} catch(IOException e) {
			System.out.println("Houve um erro durante a conexão. " + e.getMessage());
		} catch(InterruptedException e) {
			System.out.println("Houve um erro durante a conexão. " + e.getMessage());
		}
	}
	
	public static void inserir() {
		HttpClient conn = conectar();

		String link = "https://guniversity-3305a-default-rtdb.firebaseio.com/produtos.json";

		System.out.println("Informe o nome do produto: ");
		String nome = teclado.nextLine();
		System.out.println("Informe o preço do produto: ");
		float preco = teclado.nextFloat();
		System.out.println("Informe o estoque do produto: ");
		int estoque = teclado.nextInt();

		JSONObject newProduto = new JSONObject();
		newProduto.put("nome", nome);
		newProduto.put("preco", preco);
		newProduto.put("estoque", estoque);

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(link))
				.POST(HttpRequest.BodyPublishers.ofString(newProduto.toString()))
				.header("Content-Type", "application/json")
				.build();

		try{
			HttpResponse<String> response = conn.send(request, HttpResponse.BodyHandlers.ofString());

			JSONObject obj = new JSONObject(response.body());

			if(response.statusCode() == 200){
				System.out.println("O produto " + nome + " foi cadastrado com sucesso.");
			} else {
				System.out.println(obj);
				System.out.println("Status: " + response.statusCode());
			}
		} catch(IOException e) {
			System.out.println("Houve um erro durante a conexão. " + e.getMessage());
		} catch(InterruptedException e) {
			System.out.println("Houve um erro durante a conexão. " + e.getMessage());
		}
	}
	
	public static void atualizar() {
		HttpClient conn = conectar();

		System.out.println("informe o id do produto: ");
		String id = teclado.nextLine();

		System.out.println("Informe o nome do produto: ");
		String nome = teclado.nextLine();
		System.out.println("Informe o preço do produto: ");
		float preco = teclado.nextFloat();
		System.out.println("Informe o estoque do produto: ");
		int estoque = teclado.nextInt();

		String link = "https://guniversity-3305a-default-rtdb.firebaseio.com/produtos/" + id + ".json";

		JSONObject newProduto = new JSONObject();
		newProduto.put("nome", nome);
		newProduto.put("preco", preco);
		newProduto.put("estoque", estoque);

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(link))
				.PUT(HttpRequest.BodyPublishers.ofString(newProduto.toString()))
				.header("Content-Type", "application/json")
				.build();

		try {
			HttpResponse<String> response = conn.send(request, HttpResponse.BodyHandlers.ofString());

			JSONObject obj = new JSONObject(response.body());

			if(response.statusCode() == 200){
				System.out.println("O produto " + nome + " foi atualizado com sucesso.");
				System.out.println(response.body());
			}else if (response.statusCode() == 400) {
				System.out.println("Nao existe produto com o id informado.");
			}else {
				System.out.println(obj);
				System.out.println("Status: " + response.statusCode());
			}
		} catch(IOException e) {
			System.out.println("Houve um erro durante a conexão. " + e.getMessage());
		} catch(InterruptedException e) {
			System.out.println("Houve um erro durante a conexão. " + e.getMessage());
		}
	}
	
	public static void deletar() {
		HttpClient conn = conectar();

		System.out.println("informe o id do produto: ");
		String id = teclado.nextLine();

		String link = "https://guniversity-3305a-default-rtdb.firebaseio.com/produtos/" + id + ".json";

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(link))
				.DELETE()
				.build();

		try {
			HttpResponse<String> response = conn.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) {
				System.out.println("O produto foi deletado com sucesso");
			} else if (response.statusCode() == 404) {
				System.out.println("Nao existe produto com o id informado.");
			} else {
				System.out.println(response.body());
				System.out.println("Status: " + response.statusCode());
			}
		} catch(IOException e) {
			System.out.println("Houve um erro durante a conexão. " + e.getMessage());
		} catch(InterruptedException e) {
			System.out.println("Houve um erro durante a conexão. " + e.getMessage());
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
