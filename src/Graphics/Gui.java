package Graphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;

import Map.Map;

public class Gui extends JFrame {

    private Map map;
	private JPanel background;
	private JPanel mainPanel;
	private ArrayList<JButton> tileButtons;
	
    public Gui() {
        initialize();
	
        ImageIcon tileIcon = new ImageIcon("res/tile.png");
        for (int i = 0; i < map.getWidth()*map.getHeight(); i++) {
            JButton b = new JButton(tileIcon);
            //b.setBorder(null);
            mainPanel.add(b);
            tileButtons.add(b);
        }
		
        validate();
    }
	
    public void initialize() {
        map = new Map();
        background = new JPanel();
        tileButtons = new ArrayList<JButton>();

        GridLayout grid = new GridLayout(map.getHeight(), map.getWidth());
        mainPanel = new JPanel(grid);
		
        setTitle("Skirmish");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	

        getContentPane().add(background);		
        background.add(mainPanel);

        setSize(600,300);
        setVisible(true);		
    }
	
}