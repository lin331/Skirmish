package Graphics;

import Map.Path;
import Map.Tile;
import Player.Team;
import Player.Unit;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;

public class PathFinder implements MouseListener {

    private Gui gui;
    
    private boolean listening;
    private boolean drawingPath;

    private ArrayList<Path> paths;
    private ArrayList<Unit> unitsMoved;
    private int pathNum;
    
    public PathFinder(Gui gui) {
        this.gui = gui;
        this.paths = new ArrayList<Path>();
        this.unitsMoved = new ArrayList<Unit>();
        this.pathNum = 0;
        this.listening = false;
        this.drawingPath = false;
    }
    
    public boolean getListening() {
        return this.listening;
    }
    
    public int getPathNum() {
        return this.pathNum;
    }
    
    public void setListening() {
        this.listening = true;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if (listening) {
            TileButton b = (TileButton)e.getSource();
            if (!b.getTile().isEmpty() && !unitsMoved.contains(b.getTile().getUnit()) &&
                b.getTile().getUnit().getTeam().getName() == gui.getCurrentTeam().getName()) {   
                drawingPath = true;
                unitsMoved.add(b.getTile().getUnit());
                paths.add(new Path(b.getTile().getUnit()));
                b.setIcon(chooseIcon(b));
            }
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
		if (listening && drawingPath) {
            pathNum++;
			TileButton b = (TileButton)e.getSource();
			b.getTile().getUnit().setPath(paths.get(pathNum - 1));
			System.out.println(b.getTile().getUnit().getPath());
			drawingPath = false;
            listening = false;
            
            // Turn MAX_COMMANDS
            if (pathNum == 3) {
                pathNum = 0;
                unitsMoved.clear();
                paths.clear();
                gui.renderTiles();
            }
		}
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        if (listening && drawingPath) {     
            if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                TileButton b = (TileButton)e.getSource();
                if (!paths.get(pathNum).isValid(b.getTile())) {
                    //drawingPath = false;
                    return;
                }
                paths.get(pathNum).add(b.getTile());
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
        ImageIcon icon = null;
        if (b.getTile().isEmpty()) {
            switch (pathNum) {
                case 0: icon = new ImageIcon ("res/pathTile1.png");
                        break;
                case 1: icon = new ImageIcon ("res/pathTile2.png");
                        break;
                case 2: icon = new ImageIcon ("res/pathTile3.png");
                        break;
                default: break;
            }
        }
        else {
            if (b.getTile().getUnit().getTeam().getName() == "A") {
                switch (pathNum) {
                    case 0: icon = new ImageIcon ("res/passedUnit1Tile1.png");
                            break;
                    case 1: icon = new ImageIcon ("res/passedUnit1Tile2.png");
                            break;
                    case 2: icon = new ImageIcon ("res/passedUnit1Tile3.png");
                            break;
                    default: break;
                }
            }
            else if (b.getTile().getUnit().getTeam().getName() == "B") {
                switch (pathNum) {
                    case 0: icon = new ImageIcon ("res/passedUnit2Tile1.png");
                            break;
                    case 1: icon = new ImageIcon ("res/passedUnit2Tile2.png");
                            break;
                    case 2: icon = new ImageIcon ("res/passedUnit2Tile3.png");
                            break;
                    default: break;
                }
            }            
        }
        return icon;
    }
}