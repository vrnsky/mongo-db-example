package ru.skillfactory;


import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class App {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase test = mongoClient.getDatabase("test");

        //Читаем данные
        FindIterable<Document> inventoryCollections = test.getCollection("inventory").find();
        MongoCursor<Document> iterator = inventoryCollections.iterator();
        while (iterator.hasNext()) {
            Document doc = iterator.next();
            System.out.println(doc.get("tags"));
        }

        //Записываем данные
        MongoCollection<Document> inventoryCollection = test.getCollection("inventory");
        Document document = new Document();
        document.put("name", "Egor");
        document.put("company", "SkillFactory");
        document.put("tags", "Java, Python, SQL");
        inventoryCollection.insertOne(document);

        iterator = inventoryCollections.iterator();
        String idOfOneEntity = null;
        while (iterator.hasNext()) {
            Document doc = iterator.next();
            System.out.println(doc.get("tags"));
            idOfOneEntity = doc.get("_id").toString();
        }

        //Обновляем данные
        Bson filter = eq("_id", new ObjectId(idOfOneEntity));
        Bson updateOperation = set("tags", "Java, Python, SQL, NoSQL");
        UpdateResult updateResult = inventoryCollection.updateOne(filter, updateOperation);

        inventoryCollections = test.getCollection("inventory").find();
        iterator = inventoryCollections.iterator();
        while (iterator.hasNext()) {
            Document doc = iterator.next();
            System.out.println(doc.get("tags"));
        }

        //Удаляем данные
        filter = eq("_id", new ObjectId(idOfOneEntity));
        Document doc = inventoryCollection.findOneAndDelete(filter);
        System.out.println(doc.toJson());
    }
}
