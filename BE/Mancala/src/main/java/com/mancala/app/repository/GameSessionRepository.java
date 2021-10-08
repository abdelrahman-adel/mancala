package com.mancala.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mancala.app.model.GameSession;

@Repository
public interface GameSessionRepository extends MongoRepository<GameSession, String> {

	List<GameSession> findByPlayer1OrPlayer2(String player1, String player2);
}
