package com.mancala.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mancala.app.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

	List<User> findByUsername(String username);

}
