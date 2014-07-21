package Graphics;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Player.Unit;
import Player.UnitType;

public class UnitSetup implements MouseListener {
    private Gui gui;
    
    private boolean listening;
    private boolean finished;
    private boolean choosingUnitType;
    private ArrayList<Unit> units;
    private int unitNum;
    
    public UnitSetup(Gui gui) {
        this.gui = gui;
        this.listening = false;
        this.finished = false;
        this.choosingUnitType = false;
        this.unitNum = 0;
        units = new ArrayList<Unit>();
    }
    
    public boolean isListening() {
        return this.listening;
    }
    
    public boolean isFinished() {
        return this.finished;
    }

    public void start() {
        this.listening = true;
        this.finished = false;
    }
    
    public void endTurn() {
        if (!choosingUnitType) {
            unitNum = 0;
            units.clear();
            unitNum = 0;
            listening = false;
            finished = true;
            gui.render();
        }
        /*if (!choosingPathtype) {
            pathNum = 0;
            unitsMoved.clear();
            paths.clear();
            lastPath.clear();
            gui.render();
            listening = false;
            finished = true;
        }*/
    }

    public void undo() {
        if (unitNum > 0) {
            listening = true;
            unitNum--;
            units.remove(units.size() - 1);
        }
        choosingUnitType = false;
        gui.unitOptions.setVisible(false);


        /*if (pathNum > 0) {
            listening = true;
            pathNum--;
            unitsMoved.remove(unitsMoved.size() - 1);
            paths.remove(paths.size() - 1);

            for (TileButton b : lastPath) {
                b.setIcon(chooseIcon(b));
            }
        }
        choosingPathtype = false;
        gui.pathOptions.setVisible(false);*/
    }
    
    public void chooseUnitType(UnitType type) {
        
    }
    
    public ImageIcon chooseIcon(TileButton b) {
        ImageIcon icon = null;
        return icon;
    }
    
    /* Overrides */
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Select tile and highlight it and give unit options
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Highlight tile
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Unhighlight tile
        
    }
}
