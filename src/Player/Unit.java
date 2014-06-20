package Player;
/**
 * 
 * @author Wells
 *
 * Generic Unit class
 */
public class Unit {
	protected Type type; // Unit type
	protected int health; // Unit's health stat
	protected int attack; // Unit's attack stat
	protected int coord[][]; // Unit's location
	protected boolean dead; // Dead?
	
	/**
	 * Default constructor for units
	 * 
	 * @param health Unit's health stat
	 * @param attack Unit's attack stat
	 * @param coord  Unit's location on map
	 */
	public Unit(int health, int attack, int coord[][]) {
		type = Type.DEFAULT;
		this.health = health;
		this.attack = attack;
		this.coord = coord;
		dead = false;
	}
	
	/**
	 * Constructor for specified unit type
	 * 
	 * @param type   Unit type
	 * @param health Unit's health stat
	 * @param attack Unit's attack stat
	 * @param coord  Unit's location on map
	 */
	public Unit(Type type, int health, int attack, int coord[][]) {
		this.type = type;
		this.health = health;
		this.attack = attack;
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
}
