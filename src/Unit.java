
public class Unit {
	protected Type type;
	protected int health;
	protected int attack;
	protected boolean dead;
	
	public Unit(int health, int attack) {
		type = Type.DEFAULT;
		this.health = health;
		this.attack = attack;
		dead = false;
	}
	
	public Unit(Type type, int health, int attack) {
		this.type = type;
		this.health = health;
		this.attack = attack;
		dead = false;
	}
	
	public void reduceHealth(int damage) {
		health -= damage;
		checkDead();
	}
	
	public void checkDead() {
		if (health < 1) {
			dead = true;
		}
	}
}
