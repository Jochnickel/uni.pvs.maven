package de.uulm.sp.pvs.util.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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

	@Override
	public String toString() {
		return String.format("(Game){level_name:\"%s\", date:%s, won:%b, player_id:%d}", level_name, date, won,
				player_id);
	}

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
	public static void addGame(final EntityManager em, final String levelName, final boolean won, final int playerId) throws Exception {
		em.getTransaction().begin();
		final Game game = new Game();
		game.setLevelName(levelName);
		game.setWon(won);
		game.setPlayerId(playerId);
		game.setDate(Timestamp.valueOf(LocalDateTime.now()));
		em.persist(game);
		em.getTransaction().commit();
	}

	public static void printGames(final EntityManager em) {
		em.createQuery("select g from Game g").getResultList().forEach(g -> System.out.println(g));
	}

}