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

    @Test
    public void test() {
        Game game = new Game();
        Gui gui = new Gui(game);
        Team[] teams = game.getTeams();
        Tile[][] tiles = game.getMap().getTiles();
        teams[0].addUnit(new Unit(teams[0], 1, tiles[0][0]));
        teams[0].addUnit(new Unit(teams[0], 2, tiles[2][0]));
        teams[0].addUnit(new Unit(teams[0], 3, tiles[4][0]));
        teams[1].addUnit(new Unit(teams[1], 1, tiles[0][8]));
        teams[1].addUnit(new Unit(teams[1], 2, tiles[1][8]));
        teams[1].addUnit(new Unit(teams[1], 3, tiles[5][8]));
        game.setUnits();
        game.viewMap();
        gui.renderTiles();

        game.start();
        while (game.isActive()) {
            if (game.getTurn().isEmpty()) {
                // game.requestTurn();
                System.out.println("Getting turn");
                game.getTurn().add(teams[0].getUnit(1));
                teams[0].getUnit(1).getPath().setType(Pathtype.GOAL);
                teams[0].getUnit(1).getPath().add(tiles[0][1]);
                teams[0].getUnit(1).getPath().add(tiles[0][2]);
                teams[0].getUnit(1).getPath().add(tiles[0][3]);
                teams[0].getUnit(1).getPath().add(tiles[0][4]);
                teams[0].getUnit(1).getPath().add(tiles[0][5]);
                game.getTurn().add(teams[0].getUnit(2));
                teams[0].getUnit(2).getPath().setType(Pathtype.STANDARD);
                teams[0].getUnit(2).getPath().add(tiles[2][1]);
                teams[0].getUnit(2).getPath().add(tiles[2][2]);
                game.getTurn().add(teams[1].getUnit(1));
                teams[1].getUnit(1).getPath().setType(Pathtype.STANDARD);
                teams[1].getUnit(1).getPath().add(tiles[0][7]);
                teams[1].getUnit(1).getPath().add(tiles[0][6]);
                teams[1].getUnit(1).getPath().add(tiles[0][5]);
                // teams[1].getUnit(1).getPath().add(tiles[0][4]);
                game.getTurn().add(teams[1].getUnit(2));
                teams[1].getUnit(2).getPath().setType(Pathtype.STANDARD);
                teams[1].getUnit(2).getPath().add(tiles[1][7]);
            }
            game.processTurn();
            game.viewMap();
            gui.renderTiles();
            if (game.getTurn().isEmpty()) {
                System.out.println("Game finished");
                game.end();
            }
        }

    }
}
