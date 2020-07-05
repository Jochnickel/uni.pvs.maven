package de.uulm.sp.pvs.util.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

public class GameKey implements Serializable {
    private String level_name;
    private int player_id;
}