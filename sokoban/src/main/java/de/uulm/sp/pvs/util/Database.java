package de.uulm.sp.pvs.util;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.uulm.sp.pvs.util.model.Player;

class Database {
	private static final String PERSISTENCE_UNIT_NAME = "sokoban";
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	private static EntityManager em = factory.createEntityManager();


	/**
	 * Test function
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(">> Showcase Database:");
		System.out.println("Creating Player");
		Database d = new Database();
		// d.createPlayer("Hanss");
		Player p = new Player();
		System.out.println(p.getPlayerId());
	}


	/**
	 * 
	 * @param name
	 * @return
	 */
	public boolean doesPlayerExist(String name){
		List<Player> playerList = em.createQuery("select * from players").getResultList();
		for (Player player : playerList) {
			if (Objects.equals(player.getPlayerName(), name)){
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
	public int getPlayerId(String name){
		List<Player> playerList = em.createQuery("select * from players").getResultList();
		for (Player player : playerList) {
			if (Objects.equals(player.getPlayerName(), name)){
				return player.getPlayerId();
			}
		}
		throw new IllegalArgumentException();
	}


	public void createPlayer(String name){
		em.getTransaction().begin();
		Player player = new Player();

	}


	public void playerWon(int playerId){

	}


	public void playerLost(int playerId){

	}


    public void addGame(String levelName, boolean won, int playerId){

	}
	

	
}