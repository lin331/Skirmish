package netplay.client;

import graphics.TileButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import map.Map;
import map.Tile;

public class ClientMapPane extends JLayeredPane {
    private static final long serialVersionUID = 6359024098473362799L;
    private final int GUI_WIDTH = 650;
    private final int GUI_HEIGHT = 400;
    
    private JPanel mainContainer;
    private JPanel tilePanel;
    private Map map;
    private ArrayList<TileButton> tileButtons;
    
    public ClientMapPane(Client client) {
        super();
        this.map = new Map();
        initialize();
    }
    
    public void initialize() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(GUI_WIDTH * 2 / 3,
                GUI_HEIGHT * 2 / 3));

        mainContainer = new JPanel(new GridBagLayout());
        mainContainer.setBounds(0, 0, GUI_WIDTH * 2 / 3, GUI_HEIGHT * 2 / 3);
        this.add(mainContainer, new Integer(-1), 0);
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
    }
}
