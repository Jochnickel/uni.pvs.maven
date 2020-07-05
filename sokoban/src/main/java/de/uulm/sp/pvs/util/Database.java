package de.uulm.sp.pvs.util;

import java.util.ArrayList;
import java.util.List;

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
		List<Player> asd = new ArrayList<Player>();
		List<?> fkg = asd;
		System.out.println(asd);
		// List<Player> ok = fkg;


		fkg.add(null);
		// ok.add(null);



		System.out.println("\n>> Showcase Database:");
		final Database d = new Database();
		Player.createPlayer(d.em, "name");
		System.out.println(Player.doesPlayerExist(d.em, "name"));
	}

}