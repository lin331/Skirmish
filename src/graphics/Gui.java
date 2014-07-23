package graphics;

import game.Game;
import player.Team;
import player.Unit;
import player.UnitType;
import map.Map;
import map.Pathtype;
import map.Tile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
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

    /* Setters */
    public void setCurrentTeam(Team t) {
        this.currentTeam = t;
    }
    
    /* Getters */
    public Team getCurrentTeam() {
        return currentTeam;
    }

    public JLayeredPane getMapPane() {
        return this.mapPane;
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
            for (int j = 0; j < t.getUnits().size() - 1; j++) {
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

        for (TileButton b : tileButtons) {
            ImageIcon icon = null;
            if (b.getTile().isEmpty()) {
                icon = new ImageIcon("res/tile.png");
            }
            else {
                Unit u = b.getTile().getUnit();
                StringBuilder sb = new StringBuilder("res/");
                switch (u.getType()) {
                    case FOOTMAN:
                        sb.append("Footman");
                        break;
                    case SPEARMAN:
                        sb.append("Spearman");
                        break;
                    case ARCHER:
                        sb.append("Archer");
                        break;
                    case CAVALRY:
                        sb.append("Cavalry");
                        break;
                    case BARBARIAN:
                        sb.append("Barbarian");
                        break;
                    default:
                        sb.append("Unit");
                        break;
                }
                if (b.getTile().getUnit().getTeam().getName() == "A") {
                    sb.append("1");
                }
                else if (b.getTile().getUnit().getTeam().getName() == "B") {
                    sb.append("2");
                }
                sb.append("Tile.png");
                icon = new ImageIcon(sb.toString());
            }
            b.setIcon(icon);
        }

        revalidate();
    }

    public void addUnits() {
        setupUnitChoices();
        uSetup.start();
        while (!uSetup.isFinished()) {
            int unitNum = uSetup.getUnitNum() + 1;
            commandLabel.setText("Team " + currentTeam + ": Unit " + unitNum
                    + "  Select tile");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        removeUnitChoices();
        commandLabel.setText("");
    }
    
    public void requestPath() {
        setupPathChoices();
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

    /* Private methods */
    private void initialize() {
        // set up info panel
        String[] columnNames = { "Unit", "Turn Delay", "Health" };
        infoModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @SuppressWarnings({ "unchecked", "rawtypes" })
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
                b.setBorder(null);
                tilePanel.add(b);
                tileButtons.add(b);
            }
        }
        mainPanel.add(mapPane, BorderLayout.NORTH);
        
        // set up turn panel under tiles
        commandPanel = new JPanel();
        commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.Y_AXIS));

        commandLabel = new JLabel();
        undo = new JButton("Undo");
        
        endTurn = new JButton("End Turn");
        
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

    private void setupUnitChoices() {
        for (TileButton b : tileButtons) {
            b.addMouseListener(uSetup);
        }
        // setup unit type choices
        unitOptions = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        unitOptions.setBounds(0, 0, TILE_WIDTH * 3, TILE_HEIGHT * 10 / 3);
        mapPane.add(unitOptions, 1, 0);
        unitOptions.setVisible(false);
        
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

        JButton cavalry = new JButton("Cavalry");
        cavalry.setPreferredSize(new Dimension(TILE_WIDTH * 3,
                TILE_HEIGHT * 2 / 3));
        cavalry.setFont(new Font("Arial", Font.PLAIN, 8));
        cavalry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uSetup.chooseUnitType(UnitType.CAVALRY);
            }
        });
        
        JButton archer = new JButton("Archer");
        archer.setPreferredSize(new Dimension(TILE_WIDTH * 3,
                TILE_HEIGHT * 2 / 3));
        archer.setFont(new Font("Arial", Font.PLAIN, 8));
        archer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uSetup.chooseUnitType(UnitType.ARCHER);
            }
        });
        
        JButton barbarian = new JButton("Barbarian");
        barbarian.setPreferredSize(new Dimension(TILE_WIDTH * 3,
                TILE_HEIGHT * 2 / 3));
        barbarian.setFont(new Font("Arial", Font.PLAIN, 8));
        barbarian.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uSetup.chooseUnitType(UnitType.BARBARIAN);
            }
        });
        
        unitOptions.add(footman);
        unitOptions.add(spearman);
        unitOptions.add(cavalry);
        unitOptions.add(archer);   
        unitOptions.add(barbarian);
        
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uSetup.undo();
            }
        });
        endTurn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uSetup.endTurn();
            }
        });
    }
    
    private void removeUnitChoices() {
        for (TileButton b : tileButtons) {
            b.removeMouseListener(uSetup);
        }
        undo.removeActionListener(undo.getActionListeners()[0]);
        endTurn.removeActionListener(endTurn.getActionListeners()[0]);
    }
    
    private void setupPathChoices() {
        // set up path type choices
        for (TileButton b : tileButtons) {
            b.addMouseListener(pfinder);
        }
        
        pathOptions = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
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
        
        pathOptions.add(standard);
        pathOptions.add(safeGoal);
        pathOptions.add(goal);
        pathOptions.setVisible(false);    
        
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pfinder.undo();
            }
        }); 
        endTurn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pfinder.endTurn();
            }
        });  
    }
}