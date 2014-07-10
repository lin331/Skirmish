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
    
    public void turn1() {
        game.getTurn().add(teams[0].getUnit(1));
        teams[0].getUnit(1).getPath().setType(Pathtype.STANDARD);
        teams[0].getUnit(1).getPath().add(tiles[0][1]);
        teams[0].getUnit(1).getPath().add(tiles[0][2]);
        teams[0].getUnit(1).getPath().add(tiles[0][3]);
        // teams[0].getUnit(1).getPath().add(tiles[0][4]);
        game.getTurn().add(teams[0].getUnit(2));
        teams[0].getUnit(2).getPath().setType(Pathtype.STANDARD);
        teams[0].getUnit(2).getPath().add(tiles[2][1]);
        teams[0].getUnit(2).getPath().add(tiles[2][2]);
        game.getTurn().add(teams[1].getUnit(1));
        teams[1].getUnit(1).getPath().setType(Pathtype.STANDARD);
        teams[1].getUnit(1).getPath().add(tiles[0][7]);
        teams[1].getUnit(1).getPath().add(tiles[0][6]);
        teams[1].getUnit(1).getPath().add(tiles[0][5]);
        teams[1].getUnit(1).getPath().add(tiles[0][4]);
        game.getTurn().add(teams[1].getUnit(2));
        teams[1].getUnit(2).getPath().setType(Pathtype.STANDARD);
        teams[1].getUnit(2).getPath().add(tiles[1][7]);        
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
        while (game.isActive()) {
            if (game.getTurn().isEmpty()) {
                // game.requestTurn();
                System.out.println("Getting turn");
                turn1();
                game.getTurn().setNextTiles();
            }
            game.sortTurn();
            game.processTurn();
            game.viewMap();
            if (game.getTurn().isEmpty()) {
                System.out.println("Game finished");
                game.end();
            }
        }
    }
    
    public void initialize() {
        game.initialize();
        Map map = game.getMap();
        tiles = map.getTiles();
        game.makeTeams();
        teams = game.getTeams();
        add1();
        game.initialize2();
        game.viewMap();
    }
    
    @Test
    public void test() {
        /* Game setup */
        initialize();
        run();


    }
}
