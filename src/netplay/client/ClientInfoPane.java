package netplay.client;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ClientInfoPane extends JScrollPane {
    private static final long serialVersionUID = -7534142528951501407L;

    private final int GUI_WIDTH = 650;
    private final int GUI_HEIGHT = 400;
    
    DefaultTableModel infoModel;
    JTable infoTable;
    
    public ClientInfoPane() {
        super();
        initialize();
    }
    
    private void initialize() {
        String[] columnNames = { "Unit", "Turn Delay", "Health" };
        infoModel = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = 3626814961431990334L;

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

        //infoPane = new JScrollPane(infoTable);
        this.setViewportView(infoTable);
        this.setBackground(Color.LIGHT_GRAY);
        this.getViewport().setBackground(Color.LIGHT_GRAY);
        infoTable.setBackground(Color.LIGHT_GRAY);
        infoTable.getTableHeader().setBackground(Color.LIGHT_GRAY);

        this.setVerticalScrollBarPolicy(JScrollPane.
                VERTICAL_SCROLLBAR_NEVER);
        this.setHorizontalScrollBarPolicy(JScrollPane.
                HORIZONTAL_SCROLLBAR_NEVER);
        this.setVisible(false);
    }
}
