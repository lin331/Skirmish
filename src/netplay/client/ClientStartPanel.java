package netplay.client;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class ClientStartPanel extends JPanel {

    private static final long serialVersionUID = -4961616243309608780L;

    private static final int LEFT_BUTTON = MouseEvent.BUTTON1;
    private static final int CHAR_LIMIT = 16;
    Client client;
    JLabel namePrompt;
    JTextField teamNameField;
    JButton connect;
    JLabel waiting;
    
    public ClientStartPanel(Client client) {
        this.client = client;
        initialize();
    }
    
    private void initialize() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        namePrompt = new JLabel("Enter team name:");
        namePrompt.setVisible(true);
        this.add(namePrompt);
        
        teamNameField = new JTextField();
        teamNameField.setColumns(20);
        teamNameField.setVisible(true);
        teamNameField.setDocument(new JTextFieldLimit(CHAR_LIMIT));
        this.add(teamNameField);

        waiting = new JLabel("Waiting...");
        this.add(waiting);
        waiting.setVisible(false);
        
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
                    //repaint();
                    client.receiveMatch();
                }
            }
        });
        connect.setVisible(true);
        this.add(connect);
    }
    
    private class JTextFieldLimit extends PlainDocument {
        private static final long serialVersionUID = 1555818031675627560L;
        private int limit;
        
        public JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }
        
        @Override
        public void insertString(int offs, String str, AttributeSet a) 
                throws BadLocationException {                
           if (getLength() + str.length() > limit) {
               
              str = str.substring(0, limit - getLength());
           }
           super.insertString(offs, str, a);
        }   
    }
}
