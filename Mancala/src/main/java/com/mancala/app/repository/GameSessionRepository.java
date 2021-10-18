package com.mancala.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.mancala.app.model.GameSession;
import com.mancala.app.model.GameStatus;

@Repository
public interface GameSessionRepository extends MongoRepository<GameSession, String> {

	@Query("{ '$or' : [{ 'player1' : ?0}, { 'player2' : ?1}], 'status' : { '$in' : ?2} }")
	GameSession findGameByStatusAndPlayer1OrPlayer2(String player1, String player2, List<GameStatus> statuses);

	List<GameSession> findByStatus(GameStatus status);

	List<GameSession> findByStatusIn(List<GameStatus> statuses);
}
