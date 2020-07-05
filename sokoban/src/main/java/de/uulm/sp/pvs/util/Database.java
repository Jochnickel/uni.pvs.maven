package de.uulm.sp.pvs.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.uulm.sp.pvs.util.model.Game;
import de.uulm.sp.pvs.util.model.Player;

public class Database {
	private static final String PERSISTENCE_UNIT_NAME = "sokoban";
	private final static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	private final static EntityManager em = factory.createEntityManager();

	/**
	 * Test function
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(final String[] args) throws Exception {
		System.out.println("\n>> Showcase Database:");
		Player.createPlayer(em, "name");
		// Player.createPlayer(d.em, "name");
		System.out.println(Player.doesPlayerExist(em, "name"));
		Player.printPlayers(em);

		Game.addGame(em, "levelName", true, Player.getPlayerId(em, "name"));
		// Game.addGame(em, "levelName", true, Player.getPlayerId(em, "name"));
		Game.printGames(em);

		em.close();
	}

	public static boolean doesPlayerExist(String name) {
		return Player.doesPlayerExist(em, name);
	}

	public static int getPlayerId(String name) {
		return Player.getPlayerId(em, name);
	}

	public static void createPlayer(String name) {
		Player.createPlayer(em, name);
	}

	public static void playerWon(int playerId) {
		Player.playerWon(em, playerId);
	}

	public static void playerLost(int playerId) {
		Player.playerLost(em, playerId);
	}

	public static void addGame(String levelName, boolean won, int playerId) {
		Game.addGame(em, levelName, won, playerId);
	}

	public static void printPlayers() {
		Player.printPlayers(em);
	}

	public static void printGames() {
		Game.printGames(em);
	}

}