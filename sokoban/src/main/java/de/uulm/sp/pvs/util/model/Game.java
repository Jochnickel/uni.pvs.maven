package de.uulm.sp.pvs.util.model;

import java.sql.Timestamp;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Game {
	@Id
	private String level_name;
	private Timestamp date;
	private boolean won;
	@Id
	private int player_id;

	

}