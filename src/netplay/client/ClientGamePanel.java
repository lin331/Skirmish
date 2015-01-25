package netplay.client;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

public class ClientGamePanel extends JPanel {
    ClientMapPane mapPane;
    ClientMessageBox msgBox;
    
    protected ClientGamePanel(Client client) {
        super();
        initialize(client);
    }
    
    protected ClientMessageBox getMsgBox() {
        return msgBox;
    }
    
    private void initialize(Client client) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.LIGHT_GRAY);
        
        mapPane = new ClientMapPane(client);
        mapPane.setVisible(true);
        this.add(mapPane, BorderLayout.NORTH);
        
        msgBox = new ClientMessageBox();
        msgBox.setVisible(true);
        this.add(msgBox, BorderLayout.SOUTH);
        
        this.setVisible(false);
    }
}
