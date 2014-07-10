package Game;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Graphics.Gui;
import Map.Map;
import Map.Pathtype;
import Map.Tile;
import Player.Team;
import Player.Unit;

@SuppressWarnings("unused")
public class GameTest {
    Game game = new Game();
    Team[] teams;
    Tile[][] tiles;
    private boolean flag;
    Gui gui = null;
    
    public void add1() {
        teams[0].addUnit(new Unit(teams[0], 1, tiles[0][0]));
        teams[0].addUnit(new Unit(teams[0], 2, tiles[2][0]));
        teams[0].addUnit(new Unit(teams[0], 3, tiles[4][0]));
        teams[1].addUnit(new Unit(teams[1], 1, tiles[0][8]));
        teams[1].addUnit(new Unit(teams[1], 2, tiles[1][8]));
        teams[1].addUnit(new Unit(teams[1], 3, tiles[5][8]));        
    }
    
    public void turn(int i) {
        switch (i) {
            case 1:
                teams[0].getUnit(1).getPath().setType(Pathtype.STANDARD);
                teams[0].getUnit(1).getPath().add(tiles[0][1]);
                teams[0].getUnit(1).getPath().add(tiles[0][2]);
                teams[0].getUnit(1).getPath().add(tiles[0][3]);
                // teams[0].getUnit(1).getPath().add(tiles[0][4]);
                teams[0].getUnit(2).getPath().setType(Pathtype.STANDARD);
                teams[0].getUnit(2).getPath().add(tiles[2][1]);
                teams[0].getUnit(2).getPath().add(tiles[2][2]);
                teams[0].getUnit(2).getPath().setDelay(2);
                teams[1].getUnit(1).getPath().setType(Pathtype.STANDARD);
                teams[1].getUnit(1).getPath().add(tiles[0][7]);
                teams[1].getUnit(1).getPath().add(tiles[0][6]);
                teams[1].getUnit(1).getPath().add(tiles[0][5]);
                teams[1].getUnit(1).getPath().add(tiles[0][4]);
                teams[1].getUnit(2).getPath().setType(Pathtype.STANDARD);
                teams[1].getUnit(2).getPath().add(tiles[1][7]);
                teams[1].getUnit(2).getPath().setDelay(1);
                break;
            case 2:
                teams[1].getUnit(3).getPath().add(tiles[5][7]);
                break;
            default:
                break;
        }     
    }
    
    public void add2() {
        teams[0].addUnit(new Unit(teams[0], 1, tiles[1][2]));
        teams[0].getUnit(1).setAttack(10);
        teams[1].addUnit(new Unit(teams[1], 1, tiles[1][4]));
    }
    
    public void turn2() {
        game.getTurn().add(teams[1].getUnit(1));
        teams[1].getUnit(1).getPath().setType(Pathtype.STANDARD);
        teams[1].getUnit(1).getPath().add(tiles[1][3]);           
    }
    
    public void run() {
        /* Game activity */
        game.start();
        int i = 1;
        while (game.isActive()) {
            System.out.println("Turn: " + i);
            if (game.getTurn().isEmpty()) {
                // game.requestTurn();
                System.out.println("Getting turn");
                turn(i);
            }
            game.processTurn();
            // game.viewMap();
            if (i == 4) {
                System.out.println("Game finished");
                game.viewMap();
                game.end();
            }
            i++;
        }
    }
    
    public void initialize() {
        game.initialize();
        teams = game.getTeams();
        tiles = game.getMap().getTiles();
        add1();
        game.setUnits();
        game.initialize2();
    }
    
    @Test
    public void test() {
        /* Game setup */
        initialize();
        run();


    }
}
