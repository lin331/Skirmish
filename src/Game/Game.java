package Game;

import java.util.Scanner;

import Graphics.Gui;
import Map.Map;
import Player.Team;
import Player.Type;
import Player.Unit;

public class Game {
    private Map map; // Map for game
    private boolean active; // Flag for active
    private Team[] teams; // Array for teams
    private Turn turn; // Used for list of command

    /* Constructor for game */
    Game() {
        initialize();
    }

    /* Initialize game map and create teams */
    private void initialize() {
        map = new Map();
        active = false;
        makeTeams();
        turn = new Turn(map, teams);
    }

    /* Initialize teams */
    private void makeTeams() {
        teams = new Team[2];
        teams[0] = new Team("A");
        teams[1] = new Team("B");
    }

    /* Put units on map tiles */
    void setUnits() {
        map.setUnits(teams);
    }

    /* Sort turn by move priority */
    void sortTurn() {
        turn.sort();
    }

    /* Processes turn */
    void processTurn() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        turn.process();
        map.checkBattle(teams[0]);
    }

    /* Begins the game */
    public void start() {
        active = true;
    }

    /* Ends the game */
    public void end() {
        active = false;
    }

    /* Prints the current game map to screen */
    void viewMap() {
        map.printMap();
    }

    /* Getter methods below */
    public Map getMap() {
        return map;
    }

    public Team[] getTeams() {
        return teams;
    }

    public Turn getTurn() {
        return turn;
    }

    public boolean isActive() {
        return active;
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
            for (int i = 0; i < num; i++) {
                System.out.println("Unit #" + (i + 1) + ":");
                System.out.println("Enter x coordinate: ");
                String string = s.next();
                if (string.equals("end")) {
                    break;
                }
                int x = Integer.parseInt(string);
                System.out.println("Enter y coordinate: ");
                int y = s.nextInt();
                Unit u = new Unit(t, i + 1, Type.DEFAULT, map.getTiles()[y][x]);
                t.addUnit(u);
            }
            System.out.println(t.toString() + " Total Units: " + num);
        }
    }

    /* Prompt for selecting unit */
    private Unit selectUnit(Team team) {
        @SuppressWarnings("resource")
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
    private void requestTurn() {
        for (int i = 0; i < 2; i++) {
            System.out.println(teams[i].toString() + "'s turn:");
            for (int j = 0; j < turn.getMaxCommands(); j++) {
                System.out.println("Command #" + (j + 1) + ": ");
                Unit unit = selectUnit(teams[i]);
                if (unit == null) {
                    break;
                }
                turn.add(unit);
                unit.addPath(map);
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        Gui gui = new Gui(game);
        game.addUnits();
        game.setUnits();
        game.start();
        gui.renderTiles();
        while (game.active) {
            if (game.turn.isEmpty()) {
                game.requestTurn();
            }
            game.processTurn();
            gui.renderTiles();
        }
    }
}
