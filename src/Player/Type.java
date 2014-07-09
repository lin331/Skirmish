package Player;

public enum Type {
    /* @formatter:off */
    DEFAULT  (10, 5, 5),
    FOOTMAN  (90, 20, 5),
    SPEARMAN (70, 20, 5),
    ARCHER   (70, 30, 3),
    CAVALRY  (70, 25, 10);
    /* @formatter:on */
    
    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getMove() {
        return move;
    }
    
    public double getAttackModifier(Type t) {
        double[][] modifiers = {
                { 1, 1, 2, 1 },
                { 1, 1, 1, 2 },
                { 1, 2, 1, 2 },
                { 2, 0.75, 2, 1 }
                };
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
            default:
                defender = -1;
                break;
        }
        if (attacker == -1 || defender == -1) {
            return 1;
        }
        return modifiers[attacker][defender];
    }
    
    private final int health;
    private final int attack;
    private final int move;

    Type(int health, int attack, int move) {
        this.health = health;
        this.attack = attack;
        this.move = move;
    }
}

// SUICIDAL