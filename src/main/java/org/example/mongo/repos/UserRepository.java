package org.example.mongo.repos;

import org.example.mongo.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    User findByUsername(final String username);

    User save(User user);

}
