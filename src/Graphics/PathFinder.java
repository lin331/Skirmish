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
    private boolean drawingPath;
    
    public PathFinder() {
        this.path = null;
        this.listening = true;
        this.drawingPath = false;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if (listening) {
            TileButton b = (TileButton)e.getSource();
            if (!b.getTile().isEmpty()) {   
                drawingPath = true;
                path = new Path(b.getTile().getUnit());
                ImageIcon unitIcon = new ImageIcon("res/activeUnitTile.png");
                b.setIcon(unitIcon);
            }
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
		if (listening) {
			TileButton b = (TileButton)e.getSource();
			if (path != null) {
				b.getTile().getUnit().setPath(path);
				System.out.println(b.getTile().getUnit().getPath());
				listening = false;
			}
		}
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        if (listening && drawingPath) {
            if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                TileButton b = (TileButton)e.getSource();
                if (!path.isValid(b.getTile())) {
                    drawingPath = false;
                    return;
                }
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
        if (listening && drawingPath) {
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