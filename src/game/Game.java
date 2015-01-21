package game;

import static output.Output.Print.*;

import graphics.Gui;
import player.Team;
import player.Unit;
import map.Map;

import java.util.ArrayList;
import java.util.Date;

public class Game {
    private Gui gui;
    private Map map; // Map for game
    private boolean active; // Flag for active
    private Team[] teams; // Array for teams
    private Turn turn; // Used for list of command
    private Combat combat;

    /*
     *  Public methods 
    */
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

    /*
     * Private methods
     */
    private Game() {
        this.gui = null;
        this.map = null;
        this.active = false;
        this.teams = null;
        this.turn = null;
        this.combat = null;
    }

    /* Begins the game */
    private void start() {
        this.active = true;
        gui.render();
        gui.setupCommandInput();
    }

    /* Ends the game */
    private void end() {
        this.active = false;
    }

    /* Check if game is active */
    private boolean isActive() {
        return active;
    }

    /* Check if turn is empty */
    public boolean isTurnEmpty() {
        return this.turn.isFinished();
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

    /* Needed for gui apparently... */
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
            printf("log.txt", "Turn cycle: %d\n", i);
            turn.process();
            map.printMap();
            gui.render();
            combat.checkBattle();
            i++;
        } while (!turn.isFinished());
        combat.clearBattles();
        turn.checkDelay();
        turn.clearArchers();
        printStats();
    }

    /* Check if one team lost */
    private boolean isOver() {
        boolean b1 = !teams[0].hasUnits();
        boolean b2 = !teams[1].hasUnits();
        if (b1 && b2) {
            printf("log.txt", "Tie game\n");
            return true;
        }
        else if (b1) {
            printf("log.txt", "%s has lost\n", teams[0]);
            return true;
        }
        else if (b2) {
            printf("log.txt", "%s has lost\n", teams[1]);
            return true;
        }
        return false;
    }

    /* Prints units' stats to console */
    private void printStats() {
        printf("log.txt", "Unit stats:\n");
        for (Team t : teams) {
            ArrayList<Unit> units = t.getUnits();
            for (Unit u : units) {
                u.printStats();
            }
        }
    }

    /* Add units to team */
    private void addUnits() {
        gui.setupUnitSetup();
        for (int i = 0; i < 2; i++) {
            gui.setCurrentTeam(teams[i]);
            printf("log.txt", "%s's turn to add units\n", teams[i]);
            gui.addUnits();
            printf("log.txt", "%s has %d units\n",
                teams[i], teams[i].getUnits().size());
        }
        gui.removeUnitSetup();
    }
    
    /* Takes move commands and processes them */
    private void requestTurn() {
        for (int i = 0; i < 2; i++) {
            gui.setCurrentTeam(teams[i]);
            printf("log.txt", "%s's turn: \n", teams[i]);
            gui.requestPath();
        }
    }

    /*
     * Testing-purposes
     */
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
        printf("log.txt", "\n\n" + new Date().toString() + "\n");
        Game game = new Game();
        printf("log.txt", "Game started\n");
        game.initialize();
        Gui gui = new Gui(game);
        game.setGui(gui);
        game.addUnits();
        game.setUnits();
        game.initialize2();
        gui.setInfoPanel();
        game.start();
        while (game.isActive()) {
            if (game.turn.isFinished()) {
                game.requestTurn();
            }
            game.processTurn();
            gui.render();
            if (game.isOver()) {
                game.end();
                gui.dispose();
                game.reset();
                printf("log.txt", "\n\n" + new Date().toString() + "\n");
            }
        }
    }
}
