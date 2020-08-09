package org.example.mongo.repos.impl;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.WriteModel;
import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import org.example.mongo.models.Quotes;
import org.example.mongo.repos.QuotesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.ReturnDocument.AFTER;

public class QuotesRepositoryImpl implements QuotesRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();

    @Autowired
    private MongoClient client;
    private MongoCollection<Quotes> quotesCollection;
    int count = 0;

    @PostConstruct
    void init() {
        quotesCollection = client.getDatabase("test").getCollection("quotes", Quotes.class);
    }

    @Override
    public Quotes save(Quotes quotes) {
        quotes.setId(new ObjectId());
        quotesCollection.insertOne(quotes);
        return quotes;
    }

    @Override
    public List<Quotes> saveAll(List<Quotes> quotes) {
        try (ClientSession clientSession = client.startSession()) {

            return clientSession.withTransaction(() -> {
                quotes.forEach(p -> p.setId(new ObjectId()));
                quotesCollection.insertMany(clientSession, quotes);
                return quotes;
            }, txnOptions);
        }
    }

    @Override
    public List<Quotes> findAll() {
        return quotesCollection.find().into(new ArrayList<>());
    }

    @Override
    public List<Quotes> findAll(List<String> ids) {
        return quotesCollection.find(in("_id", mapToObjectIds(ids))).into(new ArrayList<>());
    }

    @Override
    public Quotes findOne(String id) {
        return quotesCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public long count() {
        return quotesCollection.countDocuments();
    }

    @Override
    public long delete(String id) {
        return quotesCollection.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount();
    }

    @Override
    public long delete(List<String> ids) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> quotesCollection.deleteMany(clientSession, in("_id", mapToObjectIds(ids))).getDeletedCount(),
                    txnOptions);
        }
    }

    @Override
    public long deleteAll() {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> quotesCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(), txnOptions);
        }
    }

    @Override
    public Quotes update(Quotes quotes) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return quotesCollection.findOneAndReplace(eq("_id", quotes.getId()), quotes, options);
    }

    @Override
    public long update(List<Quotes> quotes) {
        List<WriteModel<Quotes>> writes = quotes.stream()
                .map(p -> new ReplaceOneModel<>(eq("_id", p.getId()), p))
                .collect(Collectors.toList());
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> quotesCollection.bulkWrite(clientSession, writes).getModifiedCount(), txnOptions);
        }
    }

    @Override
    public List<Quotes> finAllByType(String type) {
        return quotesCollection.find(eq("type", type)).into(new ArrayList<>());
    }

    @Override
    public List<Quotes> finByType(String type) {
        List<Quotes> quoteList = quotesCollection.find(eq("type", type)).into(new ArrayList<>());
        List<Quotes> newList = new ArrayList<>();
        for (int i = count; i < count + 3 && i < quoteList.size(); i++) {
            newList.add(quoteList.get(i));
        }
        return newList;
    }

    @Override
    public List<Quotes> finAllByAuthor(String author) {
        return quotesCollection.find(eq("author", author)).into(new ArrayList<>());
    }

    @Override
    public List<Quotes> finByAuthor(String author) {
        List<Quotes> quoteList = quotesCollection.find(eq("author", author)).into(new ArrayList<>());
        List<Quotes> newList = new ArrayList<>();
        for (int i = count; i < count + 3 && i < quoteList.size(); i++) {
            newList.add(quoteList.get(i));
        }
        return newList;
    }

    private List<ObjectId> mapToObjectIds(List<String> ids) {
        return ids.stream().map(ObjectId::new).collect(Collectors.toList());
    }
}
