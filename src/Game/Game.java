package Game;

import java.util.Scanner;

import Graphics.Gui;
import Map.Map;
import Map.Position;
import Player.Team;
import Player.Unit;

public class Game {
    private final int MAX_COMMANDS = 3;
    private Map map; // Map for game
    private boolean active; // Flag for active
    private Team[] teams; // Array for teams
    private TurnList turn; // Used for list of command

    /** Constructor for game */
    private Game() {
        initialize();
    }

    /** Initialize game map and create teams */
    private void initialize() {
        map = new Map();
        active = false;
        ;
        makeTeams();
        turn = new TurnList();
    }

    /** Initialize teams */
    private void makeTeams() {
        teams = new Team[2];
        teams[0] = new Team("Team A");
        teams[1] = new Team("Team B");
    }

    /** Add units to team */
    private void addUnits() {
        for (Team t : teams) {
            System.out.println(t.toString() + ":");
            t.addUnits();
        }
    }

    /** Put units on map tiles */
    private void setUnits() {
        map.setUnits(teams);
    }
    
    /** Get the game map */
    public Map getMap() {
        return this.map;
    }

    /** Prompt for selecting unit */
    private Unit selectUnit(Team team) {
        Scanner s = new Scanner(System.in);
        System.out.println("Select unit: 1-" + team.getNumUnits());
        String string = s.next();
        if (string.equals("end")) {
            return null;
        }
        int u = Integer.parseInt(string);
        while (u < 1 || u > team.getNumUnits()) {
            System.out.println("Invalid unit");
            System.out.println("Select unit: 1-" + team.getNumUnits());
            string = s.next();
            if (string.equals("end")) {
                return null;
            }
            u = Integer.parseInt(string);
        }
        Unit unit = team.getUnit(u);
        return unit;
    }

    /** Takes move commands and processes them */
    private void getTurn() {
        Scanner s = new Scanner(System.in);
        for (int i = 0; i < 2; i++) {
            System.out.println(teams[i].toString() + "'s turn:");
            for (int j = 0; j < MAX_COMMANDS; j++) {
                System.out.println("Command #" + (j + 1) + ": ");
                Unit unit = selectUnit(teams[i]);
                if (unit == null) {
                    break;
                }
                turn.add(unit);
                unit.addPath(map);
            }
        }
        // s.close();
    }

    /** Processes turn */
    private void processTurn() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!turn.isEmpty()) {
            turn.process();
        }
    }

    /** Prints the current game map to screen */
    private void viewMap() {
        map.printMap();
    }

    /** Begins the game */
    public void gameStart() {
        active = true;
    }

    public static void main(String[] args) {
        Game game = new Game();
        Gui gui = new Gui(game);
        game.viewMap();
        game.addUnits();
        game.setUnits();
        gui.renderTiles();
        game.viewMap();
        game.getTurn();
        game.turn.print();
        game.processTurn();
        gui.renderTiles();
        /*
        while(game.active) { 
            game.getTurn(); 
            game.processTurn();
            game.viewMap(); 
        }
         */
    }
}
