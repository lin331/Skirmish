package Game;

import static org.junit.Assert.*;

import org.junit.Test;

import Graphics.Gui;
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
        teams[0].getUnit(1).getPath().setType(Pathtype.GOAL);
        teams[0].getUnit(1).getPath().add(tiles[0][1]);
        teams[0].getUnit(1).getPath().add(tiles[0][2]);
        teams[0].getUnit(1).getPath().add(tiles[0][3]);
        teams[0].getUnit(1).getPath().add(tiles[0][4]);
        game.getTurn().add(teams[0].getUnit(2));
        teams[0].getUnit(2).getPath().setType(Pathtype.STANDARD);
        teams[0].getUnit(2).getPath().add(tiles[2][1]);
        teams[0].getUnit(2).getPath().add(tiles[2][2]);
        game.getTurn().add(teams[1].getUnit(1));
        teams[1].getUnit(1).getPath().setType(Pathtype.GOAL);
        teams[1].getUnit(1).getPath().add(tiles[0][7]);
        teams[1].getUnit(1).getPath().add(tiles[0][6]);
        teams[1].getUnit(1).getPath().add(tiles[0][5]);
        teams[1].getUnit(1).getPath().add(tiles[0][4]);
        game.getTurn().add(teams[1].getUnit(2));
        teams[1].getUnit(2).getPath().setType(Pathtype.STANDARD);
        teams[1].getUnit(2).getPath().add(tiles[1][7]);        
    }
    
    @Test
    public void test() {
        flag = false;
        if (flag) {
            gui = new Gui(game);
        }
        teams = game.getTeams();
        tiles = game.getMap().getTiles();
        add1();
        game.setUnits();
        game.viewMap();
        if (flag) {
            gui.renderTiles();
        }

        game.start();
        while (game.isActive()) {
            if (game.getTurn().isEmpty()) {
                // game.requestTurn();
                System.out.println("Getting turn");
                turn1();
            }
            game.sortTurn();
            game.processTurn();
            game.viewMap();
            if (flag) {
                gui.renderTiles();
            }
            if (game.getTurn().isEmpty()) {
                System.out.println("Game finished");
                game.end();
            }
        }
        assertTrue(teams[0].getUnit(1).getNext() == null);
        assertTrue(teams[0].getUnit(2).getNext() == null);
        assertTrue(teams[0].getUnit(3).getNext() == null);
        assertTrue(teams[1].getUnit(1).getNext() == null);
        assertTrue(teams[1].getUnit(2).getNext() == null);
        assertTrue(teams[1].getUnit(3).getNext() == null);
        assertTrue(teams[0].getUnit(1).getPathtype() == Pathtype.STATIONARY);
        assertTrue(teams[0].getUnit(2).getPathtype() == Pathtype.STATIONARY);
        assertTrue(teams[0].getUnit(3).getPathtype() == Pathtype.STATIONARY);
        assertTrue(teams[1].getUnit(1).getPathtype() == Pathtype.STATIONARY);
        assertTrue(teams[1].getUnit(2).getPathtype() == Pathtype.STATIONARY);
        assertTrue(teams[1].getUnit(3).getPathtype() == Pathtype.STATIONARY);

    }
}
