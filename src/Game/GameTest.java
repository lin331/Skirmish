package Game;

import static org.junit.Assert.*;

import org.junit.Test;

import Map.Map;
import Map.Tile;
import Player.Team;
import Player.Unit;

public class GameTest {

    @Test
    public void test() {
        Game game = new Game();
        Map map = game.getMap();
        Team[] teams = game.getTeams();
        teams[0].addUnit(new Unit(teams[0], 1, new Tile(0,0)));
        teams[0].addUnit(new Unit(teams[0], 2, new Tile(0,1)));
        teams[0].addUnit(new Unit(teams[0], 3, new Tile(0,2)));
        teams[1].addUnit(new Unit(teams[1], 1, new Tile(8,0)));
        teams[1].addUnit(new Unit(teams[1], 2, new Tile(8,1)));
        teams[1].addUnit(new Unit(teams[1], 3, new Tile(8,2)));
        map.setUnits(teams);
        game.viewMap();
    }
}
