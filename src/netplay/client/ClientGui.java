package netplay.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import map.Map;
import player.Team;
import game.Game;

public class ClientGui extends JFrame {
    private static final long serialVersionUID = -564843573372153957L;
    
    private Client client;
    private Game game;
    private Map map;
    private Team playerTeam;
    
    /* Start screen */
    private ClientStartPanel startPanel;
    private JScrollPane infoPane;
    private ClientGamePanel gamePanel;
    
    protected ClientGui(Client client) {
        super();
        this.client = client;
        map = new Map();
        initialize();
    }
    
    protected ClientMessageBox getMsgBox() {
        return gamePanel.getMsgBox();
    }
    
    protected void start() {
        getContentPane().add(startPanel,BorderLayout.CENTER);
        startPanel.setVisible(true);
        this.setVisible(true);
        this.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2,
                dim.height/2-this.getSize().height/2);
    }

    protected void unitSetup() {
        
    }
    
    protected void initMainGame() {
        startPanel.setVisible(false);
        //getContentPane().remove(startPanel);
        infoPane.setVisible(true);
        gamePanel.setVisible(true);
        getContentPane().add(infoPane, BorderLayout.WEST);
        getContentPane().add(gamePanel, BorderLayout.EAST);
        revalidate();
        this.pack();
    }
    
    private void initGamePanel() {
        gamePanel = new ClientGamePanel(client);
        gamePanel.setVisible(false);
    }

    private void initInfoPane() {
        infoPane = new ClientInfoPane();
        infoPane.setVisible(false);
    }
    
    private void initStartPanel() {
        startPanel = new ClientStartPanel(client);
        startPanel.setVisible(false);
    }
    
    private void initialize() {
        setTitle("Skirmish");
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));
        initStartPanel();
        initInfoPane();
        initGamePanel();
    }
}
