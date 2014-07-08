package Graphics;

import Game.Game;
import Map.Map;
import Map.Position;
import Map.Tile;
import Player.Team;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;

public class Gui extends JFrame {

    private Game game;
    private Map map;
    private Team currentTeam;
    
	private JPanel background;
    
    // left side of gui
    private JPanel infoPanel;
    private JPanel unitPanel;
    private JPanel turnPanel;
    private JPanel healthPanel;
    private JLabel units;
    private JLabel turn;
    private JLabel health;
    private ArrayList<JLabel> unitDisplay;
    private ArrayList<JLabel> turnDisplay;
    private ArrayList<JLabel> healthDisplay;
    
    // right side of gui
	private JPanel mainPanel;
    private JPanel tilePanel;
    private ArrayList<TileButton> tileButtons;
    private PathFinder pfinder;
    private JPanel commandPanel;
    private JLabel commandLabel;
    private JPanel pathButtons;
    private JButton undo;
    private JButton endTurn;
	
    public Gui(Game g) {
        this.game = g;
        this.map = game.getMap();
        this.currentTeam = null; 
        initialize();
    }
	
    public void initialize() {
        
        background = new JPanel();
        
        // set up info panel
        infoPanel = new JPanel();
        unitPanel = new JPanel();
        unitPanel.setLayout(new BoxLayout(unitPanel, BoxLayout.Y_AXIS));
        turnPanel = new JPanel();
        turnPanel.setLayout(new BoxLayout(turnPanel, BoxLayout.Y_AXIS));
        healthPanel = new JPanel();
        healthPanel.setLayout(new BoxLayout(healthPanel, BoxLayout.Y_AXIS));
        units = new JLabel("Units");
        turn = new JLabel("Turn");
        health = new JLabel("Health");
        unitDisplay = new ArrayList<JLabel>();
        turnDisplay = new ArrayList<JLabel>();
        healthDisplay = new ArrayList<JLabel>();
        infoPanel.add(units);
        infoPanel.add(turn);
        infoPanel.add(health);
        
        mainPanel = new JPanel(new BorderLayout());

        GridLayout grid = new GridLayout(map.getHeight(), map.getWidth());
        tilePanel = new JPanel(grid);
        
        // set up tile grid
        pfinder = new PathFinder(this);
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

        // set up turn panel under tiles
        commandPanel = new JPanel();
        commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.Y_AXIS));
        commandLabel = new JLabel();
        undo = new JButton("Undo");
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pfinder.undo();
            }
        });
        endTurn = new JButton("End Turn");    
        endTurn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pfinder.endTurn();
            }
        });
        pathButtons = new JPanel();
        pathButtons.add(undo);
        pathButtons.add(endTurn);
        commandPanel.add(commandLabel);
        commandPanel.add(pathButtons);
        mainPanel.add(commandPanel, BorderLayout.SOUTH);
		
        // set up the frame and background panel
        getContentPane().add(background);	
        background.add(infoPanel);
        background.add(mainPanel);

        setTitle("Skirmish");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
        setSize(650,400);
        setVisible(true);	

        revalidate();
    }
	
    public void render() {
        // render info
        ImageIcon unit1 = new ImageIcon("res/unit1.png");
        ImageIcon unit2 = new ImageIcon("res/unit2.png");
        ImageIcon deadUnit = new ImageIcon("res/deadUnit.png");
    
        // render tiles
        ImageIcon tileIcon = new ImageIcon("res/tile.png");
        ImageIcon unit1Tile = new ImageIcon("res/unit1Tile.png");
        ImageIcon unit2Tile = new ImageIcon("res/unit2Tile.png");
        
        for (TileButton b : tileButtons) {
            if (b.getTile().isEmpty()) {
                b.setIcon(tileIcon);
            }
            else {
                if (b.getTile().getUnit().getTeam().getName() == game.getTeams()[0].getName()) {
                    b.setIcon(unit1Tile);
                }
                else if (b.getTile().getUnit().getTeam().getName() == 
                         game.getTeams()[1].getName()) {
                    b.setIcon(unit2Tile);
                }
            }
        }
		
        revalidate();
    }
    
    public void requestPath() {
        pfinder.start();
        while (!pfinder.getFinished()) {
            int commandsLeft = game.getTurn().getMaxCommands() - pfinder.getPathNum();
            commandLabel.setText(currentTeam + "'s Turn: " + commandsLeft + 
                                 " commands remaining");
            try {
                Thread.sleep(500);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        commandLabel.setText("");
    }
    
    public Team getCurrentTeam() {
        return currentTeam;
    }
    
    public void setCurrentTeam(Team t) {
        this.currentTeam = t;
    }
}