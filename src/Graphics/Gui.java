package Graphics;

import Game.Game;
import Map.Map;
import Map.Pathtype;
import Map.Position;
import Map.Tile;
import Player.Team;
import Player.Unit;
import Player.UnitType;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

import java.util.ArrayList;

public class Gui extends JFrame {

    private Game game;
    private Map map;
    private Team currentTeam;

    private final int GUI_WIDTH = 650;
    private final int GUI_HEIGHT = 400;

    private final int TILE_WIDTH = 32;
    private final int TILE_HEIGHT = 32;

    // left side of gui
    private JScrollPane infoPane;
    private JTable infoTable;
    private DefaultTableModel infoModel;

    // right side of gui
    private JPanel mainPanel;
    private JPanel mainContainer;
    private JLayeredPane mapPane;
    public JPanel pathOptions;
    public JPanel unitOptions;
    private JPanel tilePanel;
    private ArrayList<TileButton> tileButtons;
    private PathFinder pfinder;
    private UnitSetup uSetup;
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

        // set up info panel
        String[] columnNames = { "Unit", "Turn Delay", "Health" };
        infoModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            public Class getColumnClass(int column) {
                if (column == 0)
                    return Icon.class;
                else
                    return Object.class;
            }
        };
        infoTable = new JTable(infoModel);
        infoTable.setRowHeight(30);
        infoTable.setPreferredSize(new Dimension(GUI_WIDTH / 3, GUI_HEIGHT));
        infoTable.setPreferredScrollableViewportSize(infoTable
                .getPreferredSize());

        infoPane = new JScrollPane(infoTable);
        infoPane.setBackground(Color.LIGHT_GRAY);
        infoPane.getViewport().setBackground(Color.LIGHT_GRAY);
        infoTable.setBackground(Color.LIGHT_GRAY);
        infoTable.getTableHeader().setBackground(Color.LIGHT_GRAY);

        infoPane.setVerticalScrollBarPolicy(JScrollPane.
                VERTICAL_SCROLLBAR_NEVER);
        infoPane.setHorizontalScrollBarPolicy(JScrollPane.
                HORIZONTAL_SCROLLBAR_NEVER);

        // set up main panel
        mainPanel = new JPanel(new BorderLayout());

        mapPane = new JLayeredPane();
        mapPane.setLayout(null);
        mapPane.setPreferredSize(new Dimension(GUI_WIDTH * 2 / 3,
                GUI_HEIGHT * 2 / 3));

        mainContainer = new JPanel(new GridBagLayout());
        mainContainer.setBounds(0, 0, GUI_WIDTH * 2 / 3, GUI_HEIGHT * 2 / 3);
        mapPane.add(mainContainer, new Integer(-1), 0);
        mainContainer.setBackground(Color.YELLOW);
        mainContainer.setOpaque(true);

        GridLayout grid = new GridLayout(map.getHeight(), map.getWidth());
        tilePanel = new JPanel(grid);

        mainContainer.add(tilePanel, new GridBagConstraints());

        // set up tile grid
        pfinder = new PathFinder(this);
        uSetup = new UnitSetup(this);
        tileButtons = new ArrayList<TileButton>();
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                Tile t = map.getTiles()[i][j];
                TileButton b = new TileButton(t, new ImageIcon("res/tile.png"));
                b.addMouseListener(pfinder);
                b.addMouseListener(uSetup);
                b.setBorder(null);
                tilePanel.add(b);
                tileButtons.add(b);
            }
        }
        mainPanel.add(mapPane, BorderLayout.NORTH);
        
        // setup unit type choices
        unitOptions = new JPanel(new BorderLayout());
        unitOptions.setBounds(0, 0, TILE_WIDTH * 3, TILE_HEIGHT * 2);
        mapPane.add(unitOptions, 1, 0);
        unitOptions.setVisible(false);
        
        // def = default
        JButton def = new JButton("Default");
        def.setPreferredSize(new Dimension(TILE_WIDTH * 3,
                TILE_HEIGHT * 2 / 3));
        def.setFont(new Font("Arial", Font.PLAIN, 8));
        def.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uSetup.chooseUnitType(UnitType.DEFAULT);
            }
        });
        
        JButton footman = new JButton("Footman");
        footman.setPreferredSize(new Dimension(TILE_WIDTH * 3,
                TILE_HEIGHT * 2 / 3));
        footman.setFont(new Font("Arial", Font.PLAIN, 8));
        footman.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uSetup.chooseUnitType(UnitType.FOOTMAN);
            }
        });

        JButton spearman = new JButton("Spearman");
        spearman .setPreferredSize(new Dimension(TILE_WIDTH * 3,
                TILE_HEIGHT * 2 / 3));
        spearman .setFont(new Font("Arial", Font.PLAIN, 8));
        spearman .addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uSetup.chooseUnitType(UnitType.SPEARMAN);
            }
        });

        JButton cavalry = new JButton("Standard Move");
        cavalry.setPreferredSize(new Dimension(TILE_WIDTH * 3,
                TILE_HEIGHT * 2 / 3));
        cavalry.setFont(new Font("Arial", Font.PLAIN, 8));
        cavalry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uSetup.chooseUnitType(UnitType.CAVALRY);
            }
        });
        
        JButton archer = new JButton("Standard Move");
        archer.setPreferredSize(new Dimension(TILE_WIDTH * 3,
                TILE_HEIGHT * 2 / 3));
        archer.setFont(new Font("Arial", Font.PLAIN, 8));
        archer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uSetup.chooseUnitType(UnitType.ARCHER);
            }
        });
        
        // set up path type choices
        pathOptions = new JPanel(new BorderLayout());
        pathOptions.setBounds(0, 0, TILE_WIDTH * 3, TILE_HEIGHT * 2);
        mapPane.add(pathOptions, new Integer(1), 0);

        JButton safeGoal = new JButton("Safe Goal Move");
        safeGoal.setPreferredSize(new Dimension(TILE_WIDTH * 3,
                TILE_HEIGHT * 2 / 3));
        safeGoal.setFont(new Font("Arial", Font.PLAIN, 8));
        safeGoal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pfinder.choosePathtype(Pathtype.SAFEGOAL);
            }
        });

        JButton goal = new JButton("Goal Move");
        goal.setPreferredSize(new Dimension(TILE_WIDTH * 3,
                TILE_HEIGHT * 2 / 3));
        goal.setFont(new Font("Arial", Font.PLAIN, 8));
        goal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pfinder.choosePathtype(Pathtype.GOAL);
            }
        });

        JButton standard = new JButton("Standard Move");
        standard.setPreferredSize(new Dimension(TILE_WIDTH * 3,
                TILE_HEIGHT * 2 / 3));
        standard.setFont(new Font("Arial", Font.PLAIN, 8));
        standard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pfinder.choosePathtype(Pathtype.STANDARD);
            }
        });
        
        pathOptions.add(standard, BorderLayout.NORTH);
        pathOptions.add(safeGoal, BorderLayout.CENTER);
        pathOptions.add(goal, BorderLayout.SOUTH);
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
        pathButtons.setBackground(Color.LIGHT_GRAY);
        pathButtons.add(undo);
        pathButtons.add(endTurn);
        commandPanel.add(commandLabel);
        commandPanel.add(pathButtons);
        commandPanel.setBackground(Color.LIGHT_GRAY);
        commandPanel.setOpaque(true);
        mainPanel.add(commandPanel, BorderLayout.SOUTH);
        mainPanel.setBackground(Color.LIGHT_GRAY);

        // set up the frame
        add(infoPane, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        setTitle("Skirmish");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(GUI_WIDTH, GUI_HEIGHT);
        setVisible(true);

        revalidate();
    }

    public void setInfoPanel() {
        for (int i = 0; i < 2; i++) {
            Team t = game.getTeams()[i];
            for (Unit u : t.getUnits()) {
                ImageIcon unitIcon;
                if (i == 0) {
                    unitIcon = new ImageIcon("res/unit1.png");
                }
                else {
                    unitIcon = new ImageIcon("res/unit2.png");
                }

                Object[] unitRow = new Object[3];
                unitRow[0] = unitIcon;
                unitRow[1] = Integer.toString(u.getPath().getDelay());
                unitRow[2] = Integer.toString(u.getHealth());
                infoModel.addRow(unitRow);
            }
        }
        repaint();
    }

    public void render() {
        // render info
        int teamASize = game.getTeams()[0].getUnits().size();
        for (int i = 0; i < 2; i++) {
            Team t = game.getTeams()[i];
            int index = 0;
            if (i == 1) {
                index = teamASize;
            }
            for (int j = 0; j < t.getUnits().size(); j++) {
                if (t.getUnits().get(j).isDead()) {
                    infoModel.setValueAt(new ImageIcon("res/deadUnit.png"),
                            index + j, 0);
                }
                infoModel.setValueAt(t.getUnits().get(j).getPath().getDelay(),
                        index + j, 1);
                infoModel.setValueAt(t.getUnits().get(j).getHealth(),
                        index + j, 2);
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
                if (b.getTile().getUnit().getTeam().getName() == 
                        game.getTeams()[0].getName())
                {
                    b.setIcon(unit1Tile);
                }
                else if (b.getTile().getUnit().getTeam().getName() ==
                        game.getTeams()[1].getName())
                {
                    b.setIcon(unit2Tile);
                }
            }
        }

        revalidate();
    }

    public void requestPath() {
        pfinder.start();
        while (!pfinder.isFinished()) {
            int commandsLeft = game.getTurn().getMaxCommands()
                    - pfinder.getPathNum();
            commandLabel.setText(currentTeam + "'s Turn: " + commandsLeft
                    + " commands remaining");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
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