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
    private ArrayList<TileButton> tileButtons;
    private PathFinder pfinder;
	
    public Gui(Game g) {
        initialize(g);      
    }
	
    private void initialize(Game g) {
        map = g.getMap();
        
        background = new JPanel();
        
        GridLayout grid = new GridLayout(map.getHeight(), map.getWidth());
        mainPanel = new JPanel(grid);
        
        pfinder = new PathFinder();
        tileButtons = new ArrayList<TileButton>();
        for (int i = 0; i < map.getHeight(); i++) {
            for(int j = 0; j < map.getWidth(); j++) {
                Tile t = map.getTiles()[i][j];
                TileButton b = new TileButton(t, new ImageIcon("res/tile.png"));
                b.addMouseListener(pfinder);
                b.setBorder(null);
                mainPanel.add(b);   
                tileButtons.add(b);                
            }
        }        
		
        getContentPane().add(background);		
        background.add(mainPanel);

        //initialize GUI frame
        setTitle("Skirmish");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
        setSize(600,300);
        setVisible(true);	

        revalidate();
    }
	
    public void renderTiles() {
        ImageIcon tileIcon = new ImageIcon("res/tile.png");
        ImageIcon unitIcon = new ImageIcon("res/unitTile.png");
        for (TileButton b : tileButtons) {
            if (b.getTile().isEmpty()) {
                b.setIcon(tileIcon);
            }
            else {
                b.setIcon(unitIcon);
            }
        }
		
        revalidate();
    }
}