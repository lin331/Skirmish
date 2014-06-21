package Player;

/**
 * 
 * @author Wells
 *
 * Generic Unit class
 */
public class Unit {
	protected Team team; // Unit's team
	protected int num; // Unit's number
	protected Type type; // Unit type
	protected int health; // Unit's health stat
	protected int attack; // Unit's attack stat
	protected int move; // How far unit can move
	protected Position pos; // Unit's location
	protected boolean dead; // Dead?
	
	/**
	 * Default constructor for units
	 * 
	 * @param health Unit's health stat
	 * @param attack Unit's attack stat
	 * @param pos  Unit's location on map
	 */
	public Unit(Team team, int health, int attack, Position pos) {
		this.team = team;
		num = team.getNumUnits();
		team.add();
		type = Type.DEFAULT;
		this.health = health;
		this.attack = attack;
		this.pos = pos;
		dead = false;
	}
	
	/**
	 * Constructor for specified unit type
	 * 
	 * @param type   Unit type
	 * @param health Unit's health stat
	 * @param attack Unit's attack stat
	 * @param pos  Unit's location on map
	 */
	public Unit(Team team, Type type, int health, int attack, Position pos) {
		this.team = team;
		num = team.getNumUnits();
		team.add();
		this.type = type;
		this.health = health;
		this.attack = attack;
		this.pos = pos;
		dead = false;
	}
	
	/**
	 * Method to set health when unit is attacked
	 * 
	 * @param damage Health to be reduced by
	 */
	public void reduceHealth(int damage) {
		health -= damage;
		checkDead();
	}
	
	/**
	 * Method to check if unit is dead
	 * Sets dead variable to true if unit is dead
	 */
	public void checkDead() {
		if (health < 1) {
			dead = true;
		}
	}
	
	public Team getTeam() {
		return team;
	}
	
	public int getNum() {
		return num;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getAttack() {
		return attack;
	}
	
	public int getMove() {
		return move;
	}
	
	public Position getPos() {
		return pos;
	}
}
