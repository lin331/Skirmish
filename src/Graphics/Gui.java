package Graphics;

import Game.Game;
import Map.Map;
import Map.Pathtype;
import Map.Position;
import Map.Tile;
import Player.Team;
import Player.Unit;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;

public class Gui extends JFrame {

    private Game game;
    private Map map;
    private Team currentTeam;
    
    private final int GUI_WIDTH = 650;
    private final int GUI_HEIGHT = 400; 
    
    private final int TILE_WIDTH = 32;
    private final int TILE_HEIGHT = 32;
    
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
    private JPanel mainContainer;
    private JLayeredPane mapPane;
    public JPanel pathOptions;
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
        unitDisplay = new ArrayList<JLabel>();
        turnDisplay = new ArrayList<JLabel>();
        healthDisplay = new ArrayList<JLabel>();
        infoPanel.add(unitPanel);
        infoPanel.add(turnPanel);
        infoPanel.add(healthPanel);
        
        JLabel unitHeader = new JLabel("Unit");
        JLabel turnHeader = new JLabel("Turn Delay");
        JLabel healthHeader = new JLabel("Health");
        unitHeader.setVisible(true);
        turnHeader.setVisible(true);
        healthHeader.setVisible(true);
        unitPanel.add(unitHeader);
        turnPanel.add(turnHeader);
        healthPanel.add(healthHeader);
        
        // set up main panel
        mainPanel = new JPanel(new BorderLayout());
        
        mapPane = new JLayeredPane();
        mapPane.setLayout(null);
        mapPane.setPreferredSize(new Dimension(GUI_WIDTH*2/3, GUI_HEIGHT*2/3));

        mainContainer = new JPanel(new GridBagLayout());
        mainContainer.setBounds(0, 0, GUI_WIDTH*2/3, GUI_HEIGHT*2/3);
        mapPane.add(mainContainer, new Integer(-1), 0);
        
        GridLayout grid = new GridLayout(map.getHeight(), map.getWidth());
        tilePanel = new JPanel(grid);

        mainContainer.add(tilePanel, new GridBagConstraints());
        
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
        mainPanel.add(mapPane, BorderLayout.NORTH);
        
        //set up pathtype choice
        pathOptions = new JPanel(new BorderLayout());
        
        pathOptions.setBounds(0, 0, TILE_WIDTH * 3, TILE_HEIGHT * 2);
        mapPane.add(pathOptions, new Integer(1), 0);
        
        JButton safeGoal = new JButton ("Safe Goal Move");
        safeGoal.setPreferredSize(new Dimension(TILE_WIDTH * 3, TILE_HEIGHT * 2 / 3));
        safeGoal.setFont(new Font("Arial", Font.PLAIN, 8));
        safeGoal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pfinder.choosePathtype(Pathtype.SAFEGOAL);
            }
        });
        
        JButton goal = new JButton("Goal Move");
        goal.setPreferredSize(new Dimension(TILE_WIDTH * 3, TILE_HEIGHT * 2 / 3));    
        goal.setFont(new Font("Arial", Font.PLAIN, 8));     
        goal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pfinder.choosePathtype(Pathtype.GOAL);
            }
        });       
        
        JButton standard = new JButton("Standard Move");
        standard.setPreferredSize(new Dimension(TILE_WIDTH * 3, TILE_HEIGHT * 2 / 3));  
        standard.setFont(new Font("Arial", Font.PLAIN, 8));
        standard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pfinder.choosePathtype(Pathtype.STANDARD);
            }
        });   
        
        pathOptions.add(safeGoal, BorderLayout.NORTH);
        pathOptions.add(goal, BorderLayout.CENTER);
        pathOptions.add(standard, BorderLayout.SOUTH);
        pathOptions.setVisible(false);

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
        setSize(GUI_WIDTH, GUI_HEIGHT);
        setVisible(true);	

        revalidate();
    }
    
    public void setInfoPanel() {
        for (Team t : game.getTeams()) {
            for (Unit u : t.getUnits()) {
                JLabel unitLabel = new JLabel(Integer.toString(u.getNum()));
                JLabel turnLabel = new JLabel(Integer.toString(u.getPath().getDelay()));
                JLabel healthLabel = new JLabel(Integer.toString(u.getHealth()));
                unitLabel.setVisible(true);
                turnLabel.setVisible(true);
                healthLabel.setVisible(true);
                unitDisplay.add(unitLabel);
                turnDisplay.add(turnLabel);
                healthDisplay.add(healthLabel);                
                unitPanel.add(unitDisplay.get(unitDisplay.size() - 1));
                turnPanel.add(turnDisplay.get(turnDisplay.size() - 1));
                healthPanel.add(healthDisplay.get(healthDisplay.size() - 1));
            }
        }
        repaint();
    }
	
    public void render() {
        // render info
        ImageIcon unit1 = new ImageIcon("res/unit1.png");
        ImageIcon unit2 = new ImageIcon("res/unit2.png");
        ImageIcon deadUnit = new ImageIcon("res/deadUnit.png");

        for (Team t : game.getTeams()) {
            for (Unit u : t.getUnits()) {
            unitDisplay.get(u.getNum() - 1).setText(Integer.toString(u.getNum()));
            turnDisplay.get(u.getNum() - 1).setText(Integer.toString(u.getPath().getDelay()));
            healthDisplay.get(u.getNum() - 1).setText(Integer.toString(u.getHealth()));
            }
        }
    
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
        while (!pfinder.isFinished()) {
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
    
    public JLayeredPane getMapPane() {
        return this.mapPane;
    }
}