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

    private Game game;

    private Map map;
	private JPanel background;
    
    private JPanel infoPanel;
    private JLabel units;
    private JLabel turn;
    private JLabel health;
    
	private JPanel mainPanel;
    private JPanel tilePanel;
    private ArrayList<TileButton> tileButtons;
    private PathFinder pfinder;
    private JLabel miscLabel;
	
    public Gui(Game g) {
        this.game = g;
        initialize();      
    }
	
    private void initialize() {
        map = game.getMap();
        
        background = new JPanel();
        
        infoPanel = new JPanel();
        units = new JLabel("Units");
        turn = new JLabel("Turn");
        health = new JLabel("Health");
        infoPanel.add(units);
        infoPanel.add(turn);
        infoPanel.add(health);
        
        mainPanel = new JPanel(new BorderLayout());

        GridLayout grid = new GridLayout(map.getHeight(), map.getWidth());
        tilePanel = new JPanel(grid);
        
        pfinder = new PathFinder();
        tileButtons = new ArrayList<TileButton>();
        for (int i = 0; i < map.getHeight(); i++) {
            for(int j = 0; j < map.getWidth(); j++) {
                Tile t = map.getTiles()[i][j];
                TileButton b = new TileButton(t, new ImageIcon("res/tile.png"));
                b.addMouseListener(pfinder);
                b.setBorder(null);
                tilePanel.add(b);   
                tileButtons.add(b);                
            }
        }
        mainPanel.add(tilePanel, BorderLayout.NORTH);

        miscLabel = new JLabel("Miscellaneous Label");
        mainPanel.add(miscLabel, BorderLayout.SOUTH);
		
        getContentPane().add(background);	
        background.add(infoPanel);
        background.add(mainPanel);


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