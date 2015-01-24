package netplay.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import map.Map;
import player.Team;
import game.Game;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClientGui extends JFrame {
    private static final long serialVersionUID = -564843573372153957L;
    
    private static final int LEFT_BUTTON = MouseEvent.BUTTON1;
    private static final int START_WIDTH = 200;
    private static final int START_HEIGHT = 120;
    private Client client;
    private Game game;
    private Map map;
    private Team playerTeam;
    
    /* Start screen */
    private JPanel startPanel;
    private JLabel namePrompt;
    private JTextField teamNameField;
    private JButton connect;
    
    public ClientGui(Client client) {
        this.client = client;
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
        
    }
    
    private void initInfoTable() {
        
    }
    
    private void initMainGame() {
        initInfoTable();
        initMapPane();
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
                    System.out.println("Clicked");
                }
            }
        });
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
