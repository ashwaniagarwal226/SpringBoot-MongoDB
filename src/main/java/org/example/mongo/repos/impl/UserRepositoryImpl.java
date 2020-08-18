package org.example.mongo.repos.impl;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.example.mongo.models.User;
import org.example.mongo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();

    @Autowired
    private MongoClient client;
    private MongoCollection<User> userCollection;
    int count = 0;

    @PostConstruct
    void init() {
        userCollection = client.getDatabase("test").getCollection("user", User.class);
    }

    @Override
    public User findByUsername(String username) {
        return userCollection.find(eq("username", username)).first();
    }

    @Override
    public User save(User user) {
        userCollection.insertOne(user);
        return user;
    }
}
