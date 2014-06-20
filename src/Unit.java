/**
 * 
 * @author Wells
 *
 * Generic Unit class
 */
public class Unit {
	protected Type type;
	protected int health;
	protected int attack;
	protected boolean dead;
	
	/**
	 * Default constructor for units
	 * 
	 * @param health Unit's health stat
	 * @param attack Unit's attack stat
	 */
	public Unit(int health, int attack) {
		type = Type.DEFAULT;
		this.health = health;
		this.attack = attack;
		dead = false;
	}
	
	/**
	 * Constructor for specified unit type
	 * 
	 * @param type   Unit type
	 * @param health Unit's health stat
	 * @param attack Unit's attack stat
	 */
	public Unit(Type type, int health, int attack) {
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
