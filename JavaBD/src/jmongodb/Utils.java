package jmongodb;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONObject;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class Utils {
	
	static Scanner teclado = new Scanner(System.in);

	public static MongoCollection<Document> conectar() {
		try {
			MongoClient conn = MongoClients.create(
					MongoClientSettings.builder()
							.applyToClusterSettings(builder ->
									builder.hosts(List.of(new ServerAddress("localhost", 27017))))
											.build());
			MongoDatabase database = conn.getDatabase("jmongo");

            return database.getCollection("produtos");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void desconectar(MongoCursor<Document> cursor) {
		cursor.close();
	}
	
	public static void listar() {
		MongoCollection<Document> collection = conectar();

		if(collection.countDocuments() > 0){
			MongoCursor<Document> cursor = collection.find().iterator();
			try{
				System.out.println("Listando produtos...");
				System.out.println("--------------------");
				while (cursor.hasNext()) {
					String json = cursor.next().toJson();

					JSONObject obj = new JSONObject(json);
					JSONObject id = obj.getJSONObject("_id");

					System.out.println("ID: " + id.get("$oid"));
					System.out.println("Produto: " + obj.get("nome"));
					System.out.println("Preço: " + obj.get("preco"));
					System.out.println("Estoque: " + obj.get("estoque"));
					System.out.println("------------------------");
				}
			} catch (Exception e){
				e.printStackTrace();
			}
			desconectar(cursor);
		} else {
			System.out.println("Não existem documentos cadastrados.");
		}
	}
	
	public static void inserir() {
		MongoCollection<Document> collection = conectar();
		System.out.println("Informe o nome do produto: ");
		String nome = teclado.nextLine();
		System.out.println("Informe o preço do produto: ");
		float preco = teclado.nextFloat();
		System.out.println("Informe o estoque do produto: ");
		int estoque = teclado.nextInt();

		JSONObject nproduto = new JSONObject();
		nproduto.put("nome", nome);
		nproduto.put("preco", preco);
		nproduto.put("estoque", estoque);

		if(collection != null) {
			collection.insertOne(Document.parse(nproduto.toString()));
			System.out.println("O produto " + nome + " foi inserido com sucesso!");
		} else {
			System.out.println("Erro ao inserir produto!");
		}

	}
	
	public static void atualizar() {
		MongoCollection<Document> collection = conectar();
		System.out.println("Informe o id do produto: ");
		String id = teclado.nextLine();

		System.out.println("Informe o nome do produto: ");
		String nome = teclado.nextLine();

		System.out.println("Informe o preço do produto: ");
		float preco = teclado.nextFloat();

		System.out.println("Informe o estoque do produto: ");
		int estoque = teclado.nextInt();

		Bson query = combine(set("nome", nome), set("preco", preco), set("estoque", estoque));

		try {
			UpdateResult res = collection.updateOne(new Document("_id", new ObjectId(id)), query);

			if (res.getModifiedCount() == 1) {
				System.out.println("O produto " + nome + " foi atualizado com sucesso.");
			} else {
				System.out.println("O produto não pode ser atualizado.");
			}
		} catch (IllegalArgumentException e) {
			e.getMessage();
			System.out.println("Erro ao atualizar produto!");
		}
	}
	
	public static void deletar() {
		MongoCollection<Document> collection = conectar();
		System.out.println("Informe o id do produto: ");
		String id = teclado.nextLine();

		DeleteResult res = collection.deleteOne(new Document("_id", new ObjectId(id)));

		if(res.getDeletedCount() == 1) {
			System.out.println("O produto foi excluído com sucesso.");
		} else {
			System.out.println("Não foi possível excluir o produto.");
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
