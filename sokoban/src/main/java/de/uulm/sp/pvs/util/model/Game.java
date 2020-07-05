package de.uulm.sp.pvs.util.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(GameKey.class)
public class Game {
	@Id
	private String level_name;
	private Timestamp date;
	private boolean won;
	@Id
	private int player_id;

	public void setLevelName(final String levelName) {
		this.level_name = levelName;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public boolean getWon() {
		return this.won;
	}

	public void setWon(final boolean won) {
		this.won = won;
	}

	public void setPlayerId(final int playerId) {
		this.player_id = playerId;
	}


	/**
	 * 
	 * @param em
	 * @param levelName
	 * @param won
	 * @param playerId
	 */
	public static void addGame(final EntityManager em, final String levelName, final boolean won, final int playerId) {
		em.getTransaction().begin();
		final Game game = new Game();
		game.setLevelName(levelName);
		game.setWon(won);
		game.setPlayerId(playerId);
		em.persist(game);
		em.getTransaction().commit();
	}

}