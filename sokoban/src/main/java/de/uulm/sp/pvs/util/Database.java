package de.uulm.sp.pvs.util;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.uulm.sp.pvs.util.model.Player;

class Database {
	private static final String PERSISTENCE_UNIT_NAME = "sokoban";
	private final static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	private final static EntityManager em = factory.createEntityManager();

	/**
	 * Test function
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		System.out.println(">> Showcase Database:");
		final Database d = new Database();
		System.out.println(d.doesPlayerExist("name"));
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public boolean doesPlayerExist(final String name) {
		final List<Player> playerList = em.createQuery("select * from players").getResultList();
		for (final Player player : playerList) {
			if (Objects.equals(player.getPlayerName(), name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param name as String
	 * @return playerID as int
	 * @throws IllegalArgumentException
	 */
	public int getPlayerId(final String name) {
		final List<Player> playerList = em.createQuery("select * from players").getResultList();
		for (final Player player : playerList) {
			if (Objects.equals(player.getPlayerName(), name)) {
				return player.getPlayerId();
			}
		}
		throw new IllegalArgumentException();
	}

	/**
	 * 
	 * @param name
	 */
	public void createPlayer(final String name) {
		em.getTransaction().begin();
		final Player player = new Player();
		player.setName(name);
		em.persist(player);
		em.getTransaction().commit();
	}

	/**
	 * @param playerId 
	 */
	public void playerWon(final int playerId) {
		em.getTransaction().begin();
		final List<Player> allPlayers = em.createQuery("select * from players").getResultList();
		for (final Player player : allPlayers) {
			if(playerId==player.getPlayerId()){
				player.incrementWon();
				em.persist(player);
				break;
			}
		}
		em.getTransaction().commit();
	}

	public void playerLost(final int playerId) {

	}

	public void addGame(final String levelName, final boolean won, final int playerId) {

	}

}