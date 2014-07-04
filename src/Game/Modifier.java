package Game;

import Player.Type;
import Player.Unit;

public class Modifier {
    double[][] modifiers = {
            { 1, 1, 2, 1 },
            { 1, 1, 1, 2 },
            { 1, 2, 1, 2 },
            { 2, 0.75, 2, 1 }
            };
    
    public Modifier() {
        // IDK
    }
    
    public double lookup(Unit a, Unit b) {
        int attacker;
        int defender;
        Type aType = a.getType();
        Type bType = b.getType();
        switch (aType) {
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
                attacker = 2;
                break;
            default:
                attacker = -1;
                break;
        }
        switch (bType) {
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
                defender = 2;
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
