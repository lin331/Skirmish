package Graphics;

import Map.Path;
import Map.Tile;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;

public class PathFinder implements MouseListener {

    private Path path;
    private boolean listening;
    private boolean started;
    
    public PathFinder() {
        this.path = null;
        this.listening = true;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if (listening) {
            TileButton b = (TileButton)e.getSource();
            if (!b.getTile().isEmpty()) {   
                started = true;
                path = new Path(b.getTile().getUnit());
                ImageIcon unitIcon = new ImageIcon("res/activeUnitTile.png");
                b.setIcon(unitIcon);
            }
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if (listening && started) {
            listening = false;
        }
        System.out.println(path);
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        if (listening && started) {
            if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                TileButton b = (TileButton)e.getSource();
                path.add(b.getTile());
                
                ImageIcon unitIcon = new ImageIcon("res/passedUnitTile.png");
                ImageIcon icon = new ImageIcon("res/pathTile.png");
                if (b.getTile().isEmpty()) {
                    b.setIcon(icon);        
                }
                else {
                    b.setIcon(unitIcon);
                }
            }
        }
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        if (listening && started) {
            if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                TileButton b = (TileButton)e.getSource();
                
                ImageIcon activeUnitIcon = new ImageIcon("res/activeUnitTile.png");
                ImageIcon passedUnitIcon = new ImageIcon("res/passedUnitTile.png");
                ImageIcon icon = new ImageIcon("res/pathTile.png");
                if (b.getTile().isEmpty()) {
                    b.setIcon(icon);        
                }
                else {
                    b.setIcon(passedUnitIcon);
                }
            }  
        }        
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    
    }
}