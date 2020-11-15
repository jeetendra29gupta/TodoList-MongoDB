package org.jeetu.work.toto.service;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.jeetu.work.toto.model.Todo;
import org.springframework.stereotype.Service;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

@Service
public class TodoService {
	private static String dbName = "todoList";
	private static String collectionName = "todo";

	public static MongoCollection<Document> getDBManager() {
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase mydatabase = mongoClient.getDatabase(dbName);
		MongoCollection<Document> dbCollection = mydatabase.getCollection(collectionName);
		return dbCollection;
	}

	public void dropCollection() {
		MongoCollection<Document> dbCollection = getDBManager();
		dbCollection.drop();
	}

	public void saveTodo(Todo todo) {
		MongoCollection<Document> dbCollection = getDBManager();
		Document document = new Document("task", todo.getTask());
		document.append("completed", todo.isCompleted());
		document.append("added", todo.getAdded());
		document.append("finished", todo.getFinished());
		dbCollection.insertOne(document);
	}

	public List<Todo> getAllTodo() {
		MongoCollection<Document> dbCollection = getDBManager();
		FindIterable<Document> records = dbCollection.find();
		MongoCursor<Document> iterator = records.iterator();

		List<Todo> todolist = new ArrayList<Todo>();
		try {
			while (iterator.hasNext()) {
				Document document = iterator.next();
				Todo todo = new Todo(document.get("_id").toString(), document.getString("task"),
						document.getBoolean("completed"), document.getDate("added"), document.getDate("finished"));
				todolist.add(todo);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			iterator.close();
		}

		return todolist;
	}

	public void deleteTodo(String id) {
		MongoCollection<Document> dbCollection = getDBManager();
		Document document = dbCollection.find(eq("_id", new ObjectId(id))).first();
		if (document != null) {
			dbCollection.deleteOne(new Document("_id", new ObjectId(id)));
		}
	}

	public void doneTodo(String id) {
		MongoCollection<Document> dbCollection = getDBManager();
		Document document = dbCollection.find(eq("_id", new ObjectId(id))).first();
		if (document != null) {
			dbCollection.updateOne(eq("_id", new ObjectId(id)), new Document("$set", new Document("completed", true)));
			dbCollection.updateOne(eq("_id", new ObjectId(id)),
					new Document("$set", new Document("finished", new Date())));
			dbCollection.updateOne(eq("_id", new ObjectId(id)), new Document("$set", new Document("added", null)));
		}

	}

	public void updateTodo(String id) {
		MongoCollection<Document> dbCollection = getDBManager();
		Document document = dbCollection.find(eq("_id", new ObjectId(id))).first();
		if (document != null) {
			dbCollection.updateOne(eq("_id", new ObjectId(id)), new Document("$set", new Document("completed", false)));
			dbCollection.updateOne(eq("_id", new ObjectId(id)), new Document("$set", new Document("finished", null)));
			dbCollection.updateOne(eq("_id", new ObjectId(id)),
					new Document("$set", new Document("added", new Date())));
		}
	}

}
