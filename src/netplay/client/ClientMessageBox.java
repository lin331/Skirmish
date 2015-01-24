package netplay.client;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ClientMessageBox extends JTextArea {
    private static final long serialVersionUID = 296644658408508210L;
    
    private static final int ROW_LIMIT = 10;
    private static final int COL_DEFAULT = 32;
    
    public ClientMessageBox() {
        super("This is the message box", ROW_LIMIT, COL_DEFAULT);
        initialize();
        setLineWrap(true);
        setWrapStyleWord(true);
    }
    
    private void initialize() {
        this.setEditable(false);
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
        this.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLineCount();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLineCount();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLineCount();
            }

            private void updateLineCount() {
                int lineCount = ClientMessageBox.this.getLineCount();
                if (lineCount <= ROW_LIMIT) {
                    ClientMessageBox.this.setRows(lineCount);
                }
            }
        });
    }
}
