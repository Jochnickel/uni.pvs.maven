package de.uulm.sp.pvs.util.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int player_id;
	private String player_name;
	private int games_played = 0;
	private int games_won = 0;

	@Override
	public String toString() {
		return String.format("(Player){player_id : %d, player_name : \"%s\", games_played : %d, games_won: %d}",
				player_id, player_name, games_played, games_won);
	}

	public String getPlayerName() {
		return player_name;
	}

	public int getPlayerId() {
		return player_id;
	}

	public int getGamesPlayed() {
		return games_played;
	}

	public int getGamesWon() {
		return games_won;
	}

	public void setName(final String name) {
		this.player_name = name;
	}

	public void incrementWon() {
		++this.games_won;
		++this.games_played;
	}

	public void incrementLost() {
		++this.games_played;
	}

	private static List<Player> selectAllPlayers(final EntityManager em) {
		return em.createQuery("select p from Player p").getResultList();
	}

	/**
	 * 
	 * @param em   EntityManager
	 * @param name any name to be searched
	 * @return if the name was found
	 */
	public static boolean doesPlayerExist(final EntityManager em, final String name) {
		for (final Player player : selectAllPlayers(em)) {
			if (Objects.equals(player.getPlayerName(), name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param em   EntityManager
	 * @param name the players name to get the id from
	 * @return playerID as int
	 * @throws IllegalArgumentException
	 */
	public static int getPlayerId(final EntityManager em, final String name) {
		for (final Player player : selectAllPlayers(em)) {
			if (Objects.equals(player.getPlayerName(), name)) {
				return player.getPlayerId();
			}
		}
		throw new IllegalArgumentException();
	}

	/**
	 * 
	 * @param em   EntityManager
	 * @param name the name of the new player
	 */
	public static void createPlayer(final EntityManager em, final String name) {
		em.getTransaction().begin();
		final Player player = new Player();
		player.setName(name);
		em.persist(player);
		em.getTransaction().commit();
	}

	/**
	 * @param em       EntityManager
	 * @param playerId the player to add the won game to
	 */
	public static void playerWon(final EntityManager em, final int playerId) {
		em.getTransaction().begin();
		for (final Player player : selectAllPlayers(em)) {
			if (playerId == player.getPlayerId()) {
				player.incrementWon();
				em.persist(player);
				break;
			}
		}
		em.getTransaction().commit();
	}

	/**
	 * 
	 * @param em       EntityManager
	 * @param playerId the player to add the lost game to
	 */
	public static void playerLost(final EntityManager em, final int playerId) {
		em.getTransaction().begin();
		for (final Player player : selectAllPlayers(em)) {
			if (playerId == player.getPlayerId()) {
				player.incrementLost();
				em.persist(player);
				break;
			}
		}
		em.getTransaction().commit();

	}

	/**
	 * prints out the List of players
	 * 
	 * @param em EntityManager
	 */
	public static void printPlayers(EntityManager em) {
		selectAllPlayers(em).forEach(p -> System.out.println(p));
	}

}