# mongo-db-example
Данный репозиторий содержит примеры операции с MongoDB из Java кода

## Получение соединения с базой данных
```
MongoClient mongoClient = new MongoClient("localhost", 27017);
MongoDatabase test = mongoClient.getDatabase("test");
```

## Операция чтения 
```
FindIterable<Document> inventoryCollections = test.getCollection("inventory").find();
MongoCursor<Document> iterator = inventoryCollections.iterator();
while (iterator.hasNext()) {
   Document doc = iterator.next();
   System.out.println(doc.get("tags"));
}
```

## Операция записи
```
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
```

## Операция обновления
```
Bson filter = eq("_id", new ObjectId(idOfOneEntity));
Bson updateOperation = set("tags", "Java, Python, SQL, NoSQL");
UpdateResult updateResult = inventoryCollection.updateOne(filter, updateOperation);

inventoryCollections = test.getCollection("inventory").find();
iterator = inventoryCollections.iterator();
while (iterator.hasNext()) {
     Document doc = iterator.next();
     System.out.println(doc.get("tags"));
}
```

## Операция удаления
```
filter = eq("_id", new ObjectId(idOfOneEntity));
Document doc = inventoryCollection.findOneAndDelete(filter);
System.out.println(doc.toJson());
```
