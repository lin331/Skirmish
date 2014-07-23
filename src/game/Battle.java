package game;

import static output.Output.Print.*;

import player.Archer;
import player.Unit;
import map.Pathtype;

public class Battle {
    Unit a;
    Unit b;
    int aDmg;
    int bDmg;
    int flanked;
    
    /* Public methods */
    public Battle(Unit a, Unit b, int flanked) {
        printf("log.txt", "Battle engaged: %s vs. %s\n", a, b);
        this.a = a;
        this.b = b;
        this.flanked = flanked;
    }

    /* Check if battle exists */
    public boolean contains(Unit ally, Unit enemy) {
        if (this.a == ally && this.b == enemy) {
            return true;
        }
        else if (this.a == enemy && this.b == ally) {
            return true;
        }
        return false;
    }

    /* Process battle */
    public void doBattle() {
        if (this.flanked == 0 || this.flanked == 3) {
            normal();
        }
        else {
            try {
                flanked();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* Battle for archers */
    public void doBattle(Archer a) {
         int damage = a.getRangedAttack();
         damage = (int) (damage * a.getType().getAttackModifier(b.getType()));
         b.reduceHealth(damage);
         if (b.isDead()) {
             printf("log.txt", "%s killed by %s\n", b, a);
             b.setDead();             
         }
    }

    /* Getters */
    /* Prob not needed.... */
    public Unit getA() {
        return this.a;
    }

    public Unit getB() {
        return this.b;
    }

    /* Overrides */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Battle battle = (Battle) obj;
        if (battle.contains(this.a, this.b)) {
            return true;
        }
        return false;
    }
    
    /* Private methods */
    /* Calculate damage modifiers */
    private void damageMods() {
        // Calculate modified attack damage
        /* @formatter:off */
        this.aDmg = (int) (a.getAttack() * a.getType()
                .getAttackModifier(b.getType()));
        this.bDmg = (int) (b.getAttack() * b.getType()
                .getAttackModifier(a.getType()));
        /* @formatter:on */
    }
    
    /* Method to do battle normally or when both units are flanked */
    private void normal() {
        damageMods();
        if (this.flanked == 3) {
            printf("log.txt", "%s & %s are flanked\n", a, b);
            this.aDmg = (int) (aDmg * 0.5);
            this.bDmg = (int) (bDmg * 0.5);            
        }
        // A is stationary & B is moving: B then A
        if (a.getPathtype() == Pathtype.STATIONARY &&
                b.getPathtype() != Pathtype.STATIONARY) {
            printf("log.txt", "B then A\n");
            if (b.getPathtype() == Pathtype.GOAL ||
                    b.getPathtype() == Pathtype.SAFEGOAL) {
                printf("log.txt", "%s using goal move\n", b);
                // Unit b does nothing
                // Check if b is blocked by a
                if (b.getPathSize() > 1) {
                    
                }
                else {
                    if (b.isBlockedBy(a)) {
                        b.clearPath();
                    }
                }
            }
            else {
                printf("log.txt", "\t%s attacks %s for %d\n", b, a, bDmg);
                a.reduceHealth(bDmg);
                if (a.isDead()) {
                    printf("log.txt","%s killed by %s\n", a, b); 
                    a.setDead();
                    return;
                }
            }
            
            printf("log.txt", "\t%s attacks %s for %d\n", a, b, aDmg);
            b.reduceHealth(aDmg);
            if (b.isDead()) {
                printf("log.txt", "%s killed by %s\n", b, a);
                b.setDead();
            }
        }
        // A is moving & B is stationary: A then B
        // This should not happen
        else if (b.getPathtype() == Pathtype.STATIONARY &&
                a.getPathtype() != Pathtype.STATIONARY) {
            printf("log.txt", "!!!!!you fucked up here!!!!!\n"
                    + "ERROR: A not stationary\n");
            if (a.getPathtype() == Pathtype.GOAL ||
                    a.getPathtype() == Pathtype.SAFEGOAL) {
                // Unit a does nothing
                // Check if a is blocked by b
                if (a.getPathSize() > 1) {
                    
                }
                else {
                    if (a.isBlockedBy(b)) {
                        a.clearPath();
                    }
                }
            }
            else {
                b.reduceHealth(aDmg);
                if (b.isDead()) {
                    b.setDead();
                    return;
                }
            }
            a.reduceHealth(bDmg);
            if (a.isDead()) {
                a.setDead();
            }
        }
        // A and B simultaneously attack
        else {
            printf("log.txt", "A and B\n");
            a.reduceHealth(bDmg);
            b.reduceHealth(aDmg);
            if (a.isDead()) {
                printf("log.txt", "%s killed by %s\n", a, b);
                a.setDead();
            }
            if (b.isDead()) {
                printf("log.txt", "%s killed by %s\n", b, a);
                b.setDead();
            }
        }
    }
    
    /* Method to do battle when there is a flank */
    private void flanked() throws Exception {
        if (this.flanked == 0 || this.flanked == 3) {
            throw new Exception("Not flanked/both flanked");
        }
        damageMods();
        // B attacks first
        if (this.flanked == 1) {
            printf("log.txt", "%s is flanked\n", a);
            this.aDmg = (int) (aDmg * 0.5);
            if (b.getPathtype() == Pathtype.GOAL ||
                    b.getPathtype() == Pathtype.SAFEGOAL) {
                printf("log.txt", "%s goal move\n", b);
                // Unit b does nothing
                // Check if b is blocked by a
                if (b.getPathSize() > 1) {
                    
                }
                else {
                    if (b.isBlockedBy(a)) {
                        b.clearPath();
                    }
                }
            }
            else {
                printf("log.txt", "\t%s attacks %s for %d\n", b, a, bDmg);
                a.reduceHealth(bDmg);
                if (a.isDead()) {
                    printf("log.txt", "%s killed by %s\n", a, b);
                    a.setDead();
                    return;
                }
            }
            printf("log.txt", "\t%s attacks %s for %d\n", a, b, aDmg);
            b.reduceHealth(aDmg);
            if (b.isDead()) {
                printf("log.txt", "%s killed by %s\n", b, a);
                b.setDead();
            }
        }
        // A attacks first
        else {
            printf("log.txt", "%s is flanked\n", b);
            this.bDmg = (int) (bDmg * 0.5);
            if (a.getPathtype() == Pathtype.GOAL ||
                    a.getPathtype() == Pathtype.SAFEGOAL) {
                printf("log.txt", "%s goal move\n", a);
                // Unit a does nothing
                // Check if a is blocked by b
                if (a.getPathSize() > 1) {
                    
                }
                else {
                    if (a.isBlockedBy(b)) {
                        a.clearPath();
                    }
                }
            }
            else {
                b.reduceHealth(aDmg);
                if (b.isDead()) {
                    printf("log.txt", "%s killed by %s\n", b, a);
                    b.setDead();
                    return;
                }
            }
            a.reduceHealth(bDmg);
            if (a.isDead()) {
                printf("log.txt", "%s killed by %s\n", a, b);
                a.setDead();
            }
        }
            
    }
}
