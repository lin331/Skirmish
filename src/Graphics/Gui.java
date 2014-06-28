package Graphics;

import Map.Map;
import Map.Position;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;

public class Gui extends JFrame {

    private Map map;
	private JPanel background;
	private JPanel mainPanel;
	
    public Gui() {
        initialize();      
        renderTiles();
    }
	
    public void initialize() {
        map = new Map();
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
        PathFinder pfinder = new PathFinder();
        ImageIcon tileIcon = new ImageIcon("res/tile.png");
        for (int i = 0; i < map.getWidth(); i++) {
            for(int j = 0; j < map.getHeight(); j++) {
                Position p = new Position(i,j);
                TileButton b = new TileButton(p, tileIcon);
                b.addMouseListener(pfinder);
                b.setBorder(null);
                mainPanel.add(b);
            }
        }
		
        validate();
    }
}