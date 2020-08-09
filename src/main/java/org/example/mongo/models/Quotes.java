package org.example.mongo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class Quotes {

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String type;
    private String quote;
    private String author;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quotes)) return false;
        Quotes quotes = (Quotes) o;
        return Objects.equals(getId(), quotes.getId()) &&
                Objects.equals(getType(), quotes.getType()) &&
                Objects.equals(getQuote(), quotes.getQuote()) &&
                Objects.equals(getAuthor(), quotes.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getQuote(), getAuthor());
    }
    @Override
    public String toString() {
        return "Quotes{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", quote='" + quote + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}