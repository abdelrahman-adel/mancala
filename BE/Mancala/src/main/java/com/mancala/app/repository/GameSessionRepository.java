package com.mancala.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mancala.app.model.GameSession;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, String> {

	List<GameSession> findByPlayer1OrPlayer2(String player1, String player2);
}
