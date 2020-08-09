package org.example.mongo.repos;

import org.example.mongo.models.Quotes;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuotesRepository {
    Quotes save(Quotes quotes);

    List<Quotes> saveAll(List<Quotes> quotes);

    List<Quotes> findAll();

    List<Quotes> findAll(List<String> ids);

    Quotes findOne(String id);

    long count();

    long delete(String id);

    long delete(List<String> ids);

    long deleteAll();

    Quotes update(Quotes quotes);

    long update(List<Quotes> quotess);

    List<Quotes> finAllByType(String type);

    /*added to find by type **/
    List<Quotes> finByType(String type);

    public List<Quotes> finAllByAuthor(String author);

    /*added to find by type **/
    List<Quotes> finByAuthor(String author);


}