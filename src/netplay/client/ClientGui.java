package netplay.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import map.Map;
import map.Tile;
import player.Team;
import game.Game;
import graphics.TileButton;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ClientGui extends JFrame {
    private static final long serialVersionUID = -564843573372153957L;
    
    private static final int LEFT_BUTTON = MouseEvent.BUTTON1;
    private static final int START_WIDTH = 200;
    private static final int START_HEIGHT = 120;
    private final int GUI_WIDTH = 650;
    private final int GUI_HEIGHT = 400;
    
    private Client client;
    private Game game;
    private Map map;
    private Team playerTeam;
    
    /* Start screen */
    private JPanel startPanel;
    private JLabel namePrompt;
    private JTextField teamNameField;
    private JButton connect;
    private JScrollPane infoPane;
    
    /* Info table */
    private DefaultTableModel infoModel;
    private JTable infoTable;
    private JLabel waiting;

    /* Map panel */
    private JPanel mainPanel;
    private JPanel mainContainer;
    private JLayeredPane mapPane;
    private JPanel tilePanel;
    private ArrayList<TileButton> tileButtons;
    
    public ClientGui(Client client) {
        this.client = client;
        map = new Map();
        initialize();
    }
    
    public void start() {
        getContentPane().add(startPanel,BorderLayout.CENTER);
        this.setVisible(true);
        this.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2,
                dim.height/2-this.getSize().height/2);
    }
    
    private void initMapPane() {
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
        mainPanel.setBackground(Color.LIGHT_GRAY);

        getContentPane().add(mainPanel, BorderLayout.EAST);
        mainPanel.setVisible(false);
    }
    
    private void initInfoTable() {
        String[] columnNames = { "Unit", "Turn Delay", "Health" };
        infoModel = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = 3626814961431990334L;

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
        getContentPane().add(infoPane, BorderLayout.WEST);
        infoPane.setVisible(false);
    }
    
    protected void initMainGame() {
        startPanel.setVisible(false);
        initInfoTable();
        initMapPane();
        infoPane.setVisible(true);
        mainPanel.setVisible(true);
        this.pack();
    }
    
    private void initStartPanel() {
        startPanel = new JPanel();
        startPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(startPanel);
        startPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        namePrompt = new JLabel("Enter team name:");
        startPanel.add(namePrompt);
        
        teamNameField = new JTextField();
        startPanel.add(teamNameField);
        teamNameField.setColumns(20);
        
        connect = new JButton("Connect");
        connect.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == LEFT_BUTTON) {
                    client.connect();
                    client.sendTeamName(teamNameField.getText());
                    namePrompt.setVisible(false);
                    teamNameField.setVisible(false);
                    connect.setVisible(false);
                    waiting.setVisible(true);
                    System.out.println("Clicked");
                    client.receiveMatch();
                }
            }
        });
        
        waiting = new JLabel("Waiting...");
        startPanel.add(waiting);
        waiting.setVisible(false);
        startPanel.add(connect);
    }
    
    private void initialize() {
        setTitle("Skirmish");
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));
        initStartPanel();
    }
}
