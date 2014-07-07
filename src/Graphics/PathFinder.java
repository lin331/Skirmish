package Graphics;

import Map.Path;
import Map.Tile;
import Player.Unit;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;

public class PathFinder implements MouseListener {

    private ArrayList<Path> paths;
    private ArrayList<Unit> unitsMoved;
    private int pathNum;
    private boolean listening;
    private boolean drawingPath;
    
    public PathFinder() {
        this.paths = new ArrayList<Path>();
        this.unitsMoved = new ArrayList<Unit>();
        this.pathNum = 0;
        this.listening = true;
        this.drawingPath = false;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if (listening) {
            TileButton b = (TileButton)e.getSource();
            if (!b.getTile().isEmpty() && !unitsMoved.contains(b.getTile().getUnit())) {   
                drawingPath = true;
                pathNum++;
                unitsMoved.add(b.getTile().getUnit());
                paths.add(new Path(b.getTile().getUnit()));
                b.setIcon(chooseIcon(b));
            }
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
		if (listening && drawingPath) {
			TileButton b = (TileButton)e.getSource();
			b.getTile().getUnit().setPath(paths.get(pathNum - 1));
			System.out.println(b.getTile().getUnit().getPath());
			drawingPath = false;
            
            // Turn MAX_COMMANDS
            if (pathNum == 3) {
                listening = false;
            }
		}
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        if (listening && drawingPath) {     
            if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                TileButton b = (TileButton)e.getSource();
                if (!paths.get(pathNum - 1).isValid(b.getTile())) {
                    //drawingPath = false;
                    return;
                }
                paths.get(pathNum - 1).add(b.getTile());
                b.setIcon(chooseIcon(b));
            }
        }
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
      
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    
    }
    
    public ImageIcon chooseIcon(TileButton b) {
        ImageIcon icon;
        if (b.getTile().isEmpty()) {
            switch (pathNum) {
                case 1: icon = new ImageIcon ("res/pathTile1.png");
                        break;
                case 2: icon = new ImageIcon ("res/pathTile2.png");
                        break;
                case 3: icon = new ImageIcon ("res/pathTile3.png");
                        break;
                default: icon = null;
                         break;
            }
        }
        else {
            switch (pathNum) {
                case 1: icon = new ImageIcon ("res/passedUnitTile1.png");
                        break;
                case 2: icon = new ImageIcon ("res/passedUnitTile2.png");
                        break;
                case 3: icon = new ImageIcon ("res/passedUnitTile3.png");
                        break;
                default: icon = null;
                        break;
            }
        }
        return icon;
    }
}