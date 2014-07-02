package Game;

import static org.junit.Assert.*;

import org.junit.Test;

import Map.Tile;
import Player.Team;
import Player.Unit;

public class GameTest {

    @Test
    public void test() {
        Game game = new Game();
        Team[] teams = game.getTeams();
        Tile[][] tiles = game.getMap().getTiles();
        teams[0].addUnit(new Unit(teams[0], 1, tiles[0][0]));
        teams[0].addUnit(new Unit(teams[0], 2, tiles[2][0]));
        teams[0].addUnit(new Unit(teams[0], 3, tiles[4][0]));
        teams[1].addUnit(new Unit(teams[1], 1, tiles[0][8]));
        teams[1].addUnit(new Unit(teams[1], 2, tiles[2][8]));
        teams[1].addUnit(new Unit(teams[1], 3, tiles[5][8]));
        game.setUnits();
        game.viewMap();

        game.getTurn().add(teams[0].getUnit(1));
        teams[0].getUnit(1).getPath().add(tiles[0][1]);
        teams[0].getUnit(1).getPath().add(tiles[0][2]);
        teams[0].getUnit(1).getPath().add(tiles[0][3]);
        game.getTurn().add(teams[0].getUnit(2));
        teams[0].getUnit(2).getPath().add(tiles[2][1]);
        teams[0].getUnit(2).getPath().add(tiles[2][2]);
        game.getTurn().add(teams[1].getUnit(1));
        teams[1].getUnit(1).getPath().add(tiles[0][7]);
        teams[1].getUnit(1).getPath().add(tiles[0][6]);
        teams[1].getUnit(1).getPath().add(tiles[0][5]);
        teams[1].getUnit(1).getPath().add(tiles[0][4]);
        teams[1].getUnit(1).getPath().add(tiles[0][3]);
        game.getTurn().add(teams[1].getUnit(2));
        teams[1].getUnit(2).getPath().add(tiles[2][7]);

        game.start();
        while (game.isActive()) {
            game.processTurn();
            game.viewMap();
            if (game.getTurn().isEmpty()) {
                game.end();
            }
        }

    }
}
