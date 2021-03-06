package game;

import graphics.Gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import player.Archer;
import player.Team;
import player.Unit;
import player.UnitType;
import map.Map;

public class Game {
    private Gui gui;
    private Map map; // Map for game
    private boolean active; // Flag for active
    private Team[] teams; // Array for teams
    private Turn turn; // Used for list of command
    private Combat combat;

    /* Public methods */
    /* Getters */
    public Map getMap() {
        return map;
    }

    public Team[] getTeams() {
        return teams;
    }

    public Turn getTurn() {
        return turn;
    }

    /* Private methods */
    /* Constructor for game */
    private Game() {
        this.gui = null;
        this.map = null;
        this.active = false;
        this.teams = null;
        this.turn = null;
        this.combat = null;
    }

    /* Setters */
    /* Begins the game */
    private void start() {
        this.active = true;
    }

    /* Ends the game */
    private void end() {
        this.active = false;
    }

    /* Getters */
    private boolean isActive() {
        return active;
    }

    /* Check if turn is empty */
    public boolean isTurnEmpty() {
        return this.turn.isEmpty();
    }

    /* Initialize game map and create teams */
    private void initialize() {
        this.gui = null;
        this.map = new Map();
        makeTeams();
    }

    /* Initialize teams */
    private void makeTeams() {
        this.teams = new Team[2];
        this.teams[0] = new Team("A");
        this.teams[1] = new Team("B");
    }

    /* Initialize game mechanics */
    private void initialize2() {
        this.active = false;
        this.turn = new Turn(teams);
        this.combat = new Combat(map, turn, teams);
    }

    /* Put units on map tiles */
    private void setUnits() {
        this.map.setUnits(teams);
    }

    private void setGui(Gui gui) {
        this.gui = gui;
    }

    /* Processes turn */
    private void processTurn() {
        turn.setup();
        int i = 1;
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            turn.process();
            map.printMap();
            gui.render();
            combat.checkBattle();
            combat.checkArchers();
            System.out.println("Turn cycle: " + i);
            i++;
        } while (!turn.isEmpty());
        combat.clearBattles();
        turn.checkDelay();
        printStats();
    }

    /* Check if one team lost */
    private boolean isOver() {
        boolean b1 = !teams[0].hasUnits();
        boolean b2 = !teams[1].hasUnits();
        if (b1 && b2) {
            System.out.println("Tie game");
            return true;
        }
        else if (b1) {
            System.out.println(teams[0] + " has lost");
            return true;
        }
        else if (b2) {
            System.out.println(teams[1] + " has lost");
            return true;
        }
        return false;
    }

    /* Prints units' stats to console */
    private void printStats() {
        System.out.println("Unit stats:");
        for (Team t : teams) {
            ArrayList<Unit> units = t.getUnits();
            for (Unit u : units) {
                u.printStats();
            }
        }
    }

    /* Takes move commands and processes them */
    private void requestTurn() {
        for (int i = 0; i < 2; i++) {
            gui.setCurrentTeam(teams[i]);
            System.out.println(teams[i].toString() + "'s turn:");
            gui.requestPath();
        }
    }

    /* For console input: */
    /* Add units to team */
    private void addUnits() {
        @SuppressWarnings("resource")
        Scanner s = new Scanner(System.in);
        System.out.println("Enter # of units per team: ");
        int num = Integer.parseInt(s.next());
        for (Team t : teams) {
            System.out.println(t.toString() + ":");
            units:
            for (int i = 0; i < num; i++) {
                System.out.println("Unit #" + (i + 1) + ":");
                boolean valid = false;
                int x = -1;
                int y = -1;
                do {
                    System.out.println("Enter x coordinate: ");
                    String string = s.next();
                    if (string.equals("end")) {
                        break units;
                    }
                    x = Integer.parseInt(string);
                    System.out.println("Enter y coordinate: ");
                    y = s.nextInt();
                    valid = map.getTiles()[y][x].getUnit() == null;
                    if (!valid) {
                        System.out.println("Unit already on tile.\n"
                                + "Re-enter coordinates.");
                    }
                } while (!valid);
                Unit u = new Unit(t, i + 1, UnitType.DEFAULT,
                        map.getTiles()[y][x]);
                t.addUnit(u);
            }
            System.out.println(t.toString() + " Total Units: " + num);
        }
    }

    private void addUnits2() {
        @SuppressWarnings("resource")
        Scanner s = new Scanner(System.in);
        System.out.println("Enter # of units per team: ");
        int num = Integer.parseInt(s.next());
        for (Team t : teams) {
            System.out.println(t.toString() + ":");
            for (int i = 0; i < num; i++) {
                System.out.println("Unit #" + (i + 1) + ":");
                System.out.println("Pick unit type:"
                        + "(1 - Footman, 2 - Spearman, "
                        + "3 - Archer, 4 - Calvary)");
                int ut = s.nextInt();
                UnitType type;
                switch (ut) {
                    case 1:
                        type = UnitType.FOOTMAN;
                        break;
                    case 2:
                        type = UnitType.SPEARMAN;
                        break;
                    case 3:
                        type = UnitType.ARCHER;
                        break;
                    case 4:
                        type = UnitType.CAVALRY;
                        break;
                    default:
                        type = UnitType.DEFAULT;
                }
                boolean valid = false;
                int x = -1;
                int y = -1;
                do {
                    System.out.println("Enter x coordinate: ");
                    String string = s.next();
                    if (string.equals("end")) {
                        break;
                    }
                    x = Integer.parseInt(string);
                    System.out.println("Enter y coordinate: ");
                    y = s.nextInt();
                    valid = map.getTiles()[y][x].getUnit() != null;
                    if (!valid) {
                        System.out.println("Unit already on tile\n"
                                + "Re-enter coordinates");
                    }
                } while (!valid);
                if (type != UnitType.ARCHER){ 
                    Unit u = new Unit(t, i + 1, type, map.getTiles()[y][x]);
                    t.addUnit(u);
                }
                else {
                    Archer u = new Archer(t, i + 1, map.getTiles()[y][x]);
                    t.addUnit(u);
                }
            }
            System.out.println(t.toString() + " Total Units: " + num);
        }
    }

    /* Prompt for selecting unit */
    private Unit selectUnit(Team team) {
        Scanner s = new Scanner(System.in);
        System.out.println("Select unit: 1-" + team.getUnits().size());
        String string = s.next();
        if (string.equals("end")) {
            return null;
        }
        int u = Integer.parseInt(string);
        while (u < 1 || u > team.getUnits().size()) {
            System.out.println("Invalid unit");
            System.out.println("Select unit: 1-" + team.getUnits().size());
            string = s.next();
            if (string.equals("end")) {
                return null;
            }
            u = Integer.parseInt(string);
        }
        Unit unit = team.getUnit(u);
        return unit;
    }

    /* Takes move commands and processes them */
    /* May be broken */
    private void requestTurnConsole() {
        for (int i = 0; i < 2; i++) {
            System.out.println(teams[i].toString() + "'s turn:");
            for (int j = 0; j < turn.getMaxCommands(); j++) {
                System.out.println("Command #" + (j + 1) + ": ");
                Unit unit = selectUnit(teams[i]);
                if (unit == null) {
                    break;
                }
                // turn.add(unit);
                unit.addPath(map);
            }
        }
    }

    /* Testing-purposes */
    /* Reset game */
    private void reset() {
        initialize();
        Gui gui = new Gui(this);
        setGui(gui);
        addUnits();
        setUnits();
        initialize2();
        gui.setInfoPanel();
        start();
        gui.render();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        Gui gui = new Gui(game);
        game.setGui(gui);
        game.addUnits();
        game.setUnits();
        game.initialize2();
        gui.setInfoPanel();
        game.start();
        gui.render();
        while (game.isActive()) {
            if (game.turn.isEmpty()) {
                game.requestTurn();
            }
            game.processTurn();
            gui.render();
            if (game.isOver()) {
                game.end();
                game.reset();
            }
        }
    }
}
