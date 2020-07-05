package de.uulm.sp.pvs.util.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
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
}