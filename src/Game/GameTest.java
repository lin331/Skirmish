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
        teams[0].addUnit(new Unit(teams[0], 1, new Tile(0, 0)));
        teams[0].addUnit(new Unit(teams[0], 2, new Tile(0, 2)));
        teams[0].addUnit(new Unit(teams[0], 3, new Tile(0, 4)));
        teams[1].addUnit(new Unit(teams[1], 1, new Tile(8, 0)));
        teams[1].addUnit(new Unit(teams[1], 2, new Tile(8, 2)));
        teams[1].addUnit(new Unit(teams[1], 3, new Tile(8, 5)));
        game.setUnits();
        game.viewMap();

        game.getTurn().add(teams[0].getUnit(1));
        teams[0].getUnit(1).getPath().add(new Tile(1, 0));
        teams[0].getUnit(1).getPath().add(new Tile(2, 0));
        teams[0].getUnit(1).getPath().add(new Tile(3, 0));
        game.getTurn().add(teams[0].getUnit(2));
        teams[0].getUnit(2).getPath().add(new Tile(1, 2));
        teams[0].getUnit(2).getPath().add(new Tile(2, 2));
        game.getTurn().add(teams[1].getUnit(1));
        teams[1].getUnit(1).getPath().add(new Tile(7, 0));
        teams[1].getUnit(1).getPath().add(new Tile(6, 0));
        game.getTurn().add(teams[1].getUnit(2));
        teams[1].getUnit(2).getPath().add(new Tile(7, 2));

        game.start();
        while (game.isActive()) {
            game.processTurn();
            game.viewMap();
            if (game.getTurn().isEmpty()) {
                game.end();
            }
        }

        assertTrue(teams[0].getUnit(1).getTile().equals(new Tile(3, 0)));
        assertTrue(teams[0].getUnit(2).getTile().equals(new Tile(2, 2)));
        assertTrue(teams[1].getUnit(1).getTile().equals(new Tile(6, 0)));
        assertTrue(teams[1].getUnit(2).getTile().equals(new Tile(7, 2)));
    }
}
