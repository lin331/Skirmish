package Graphics;

import Game.Game;
import Map.Map;
import Map.Position;
import Map.Tile;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;

public class Gui extends JFrame {

    private Map map;
	private JPanel background;
	private JPanel mainPanel;
	
    public Gui(Game g) {
        initialize(g);      
        renderTiles();
    }
	
    private void initialize(Game g) {
        map = g.getMap();
        background = new JPanel();

        GridLayout grid = new GridLayout(map.getHeight(), map.getWidth());
        mainPanel = new JPanel(grid);
		
        setTitle("Skirmish");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	

        getContentPane().add(background);		
        background.add(mainPanel);

        setSize(600,300);
        setVisible(true);		
    }
	
    public void renderTiles() {
        mainPanel.removeAll();
        PathFinder pfinder = new PathFinder();
        ImageIcon tileIcon = new ImageIcon("res/tile.png");
        ImageIcon unitIcon = new ImageIcon("res/unitTile.png");
        for (int i = 0; i < map.getHeight(); i++) {
            for(int j = 0; j < map.getWidth(); j++) {
                TileButton b;
                Tile t = map.getTiles()[i][j];
                if (t.isEmpty()) {
                    b = new TileButton(t, tileIcon);
                }
                else {
                    b = new TileButton(t, unitIcon);
                }
                b.addMouseListener(pfinder);
                b.setBorder(null);
                mainPanel.add(b);
            }
        }
		
        revalidate();
    }
}