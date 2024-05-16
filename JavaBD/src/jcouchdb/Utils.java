package jcouchdb;

import org.json.JSONArray;
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
		String link = "http://localhost:5984/jcouch/_all_docs?include_docs=true";
		String username = "admin";
		String password = "admin";
		String basicAuth = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(link))
				.header("Authorization", basicAuth)
				.build();

		try {
			HttpResponse<String> response = conn.send(request, HttpResponse.BodyHandlers.ofString());

			JSONObject obj = new JSONObject(response.body());

			if((int)obj.get("total_rows") > 0) {
				JSONArray produtos = (JSONArray) obj.get("rows");

				System.out.println("Listando produtos....");
				System.out.println("----------------------");
				for(Object produto : produtos) {
					JSONObject doc = (JSONObject) produto;
					JSONObject prod = (JSONObject) doc.get("doc");

					System.out.println("Id: " + prod.get("_id"));
					System.out.println("Rev: " + prod.get("_rev"));
					System.out.println("Produto: " + prod.get("nome"));
					System.out.println("Preço: " + prod.get("preco"));
					System.out.println("Estoque: " + prod.get("estoque"));
					System.out.println("----------------------");
				}
			} else {
				System.out.println("Não existem produtos cadastrados.");
			}

		} catch(IOException e) {
			System.out.println("Houve um erro durante a conexão. " + e.getMessage());
		} catch(InterruptedException e) {
			System.out.println("Houve um erro durante a conexão. " + e.getMessage());
		}
	}
	
	public static void inserir() {
		HttpClient conn = conectar();

		String link = "http://localhost:5984/jcouch";
		String username = "admin";
		String password = "admin";
		String basicAuth = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));

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
				.header("Authorization", basicAuth)
				.header("Content-Type", "application/json")
				.build();

		try{
			HttpResponse<String> response = conn.send(request, HttpResponse.BodyHandlers.ofString());

			JSONObject obj = new JSONObject(response.body());

			if(response.statusCode() == 201){
				System.out.println("O produto foi cadastrado com sucesso.");
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
		System.out.println("informe o _rev do produto: ");
		String rev = teclado.nextLine();

		System.out.println("Informe o nome do produto: ");
		String nome = teclado.nextLine();
		System.out.println("Informe o preço do produto: ");
		float preco = teclado.nextFloat();
		System.out.println("Informe o estoque do produto: ");
		int estoque = teclado.nextInt();

		String link = "http://localhost:5984/jcouch/" + id + "/" + "?rev=" + rev;
		String username = "admin";
		String password = "admin";
		String basicAuth = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));

		JSONObject newProduto = new JSONObject();
		newProduto.put("nome", nome);
		newProduto.put("preco", preco);
		newProduto.put("estoque", estoque);

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(link))
				.PUT(HttpRequest.BodyPublishers.ofString(newProduto.toString()))
				.header("Authorization", basicAuth)
				.header("Content-Type", "application/json")
				.build();

		try {
			HttpResponse<String> response = conn.send(request, HttpResponse.BodyHandlers.ofString());

			JSONObject obj = new JSONObject(response.body());

			if(response.statusCode() == 201){
				System.out.println("O produto " + nome + "foi atualizado com sucesso.");
			}else if (response.statusCode() == 400) {
				System.out.println("Nao existe produto com o id e rev informados.");
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
		System.out.println("informe o _rev do produto: ");
		String rev = teclado.nextLine();

		String link = "http://localhost:5984/jcouch/" + id + "/" + "?rev=" + rev;
		String username = "admin";
		String password = "admin";
		String basicAuth = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(link))
				.DELETE()
				.header("Authorization", basicAuth)
				.build();

		try {
			HttpResponse<String> response = conn.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) {
				System.out.println("O produto foi deletado com sucesso");
			} else if (response.statusCode() == 404) {
				System.out.println("Nao existe produto com o id e rev informados.");
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
