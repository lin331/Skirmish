package player;

public enum UnitType {
    /* @formatter:off */
    FOOTMAN   (90, 20, 5),
    SPEARMAN  (70, 20, 5),
    ARCHER    (70, 30, 3),
    CAVALRY   (70, 25, 10),
    BARBARIAN (50, 30, 6),
    DEFAULT   (20, 5, 5);
    /* @formatter:on */

    private final int health;
    private final int attack;
    private final int move;

    private UnitType(int health, int attack, int move) {
        this.health = health;
        this.attack = attack;
        this.move = move;
    }

    /* Getters */
    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getMove() {
        return move;
    }

    /* Returns damage modifier based on unit type */
    public double getAttackModifier(UnitType t) {
        /* @formatter:off */
        double[][] modifiers = {
                { 1, 1, 2, 1, 1 },
                { 1, 1, 1, 2, 1.5 },
                { 1, 2, 1, 2, 1 },
                { 2, 0.6, 2, 1, 1 },
                { 1, 0.5, 1, 1, 1 }};
        /* @formatter:on */
        int attacker;
        int defender;
        switch (this) {
            case FOOTMAN:
                attacker = 0;
                break;
            case SPEARMAN:
                attacker = 1;
                break;
            case ARCHER:
                attacker = 2;
                break;
            case CAVALRY:
                attacker = 3;
                break;
            case BARBARIAN:
                attacker = 4;
                break;
            default:
                attacker = -1;
                break;
        }
        switch (t) {
            case FOOTMAN:
                defender = 0;
                break;
            case SPEARMAN:
                defender = 1;
                break;
            case ARCHER:
                defender = 2;
                break;
            case CAVALRY:
                defender = 3;
                break;
            case BARBARIAN:
                defender = 4;
                break;
            default:
                defender = -1;
                break;
        }
        if (attacker == -1 || defender == -1) {
            return 1;
        }
        return modifiers[attacker][defender];
    }
}