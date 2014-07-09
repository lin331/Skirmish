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
    private boolean finished;
    private boolean choosingPathtype;

    private ArrayList<Path> paths;
    private ArrayList<Unit> unitsMoved;
    private ArrayList<TileButton> lastPath;
    private int pathNum;
    
    public PathFinder(Gui gui) {
        this.gui = gui;
        this.paths = new ArrayList<Path>();
        this.unitsMoved = new ArrayList<Unit>();
        this.lastPath = new ArrayList<TileButton>();
        this.pathNum = 0;
        this.listening = false;
        this.drawingPath = false;
        this.finished = false;
        this.choosingPathtype = false;
    }
    
    public boolean isFinished() {
        return this.finished;
    }
    
    public boolean isListening() {
        return this.listening;
    }
    
    public int getPathNum() {
        return this.pathNum;
    }
    
    public void start() {
        this.listening = true;
        this.finished = false;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if (listening && !choosingPathtype) {
            TileButton b = (TileButton)e.getSource();
            if (!b.getTile().isEmpty() && !unitsMoved.contains(b.getTile().getUnit()) &&
                b.getTile().getUnit().getTeam().getName() == gui.getCurrentTeam().getName()) {
                lastPath.clear();
                drawingPath = true;
                unitsMoved.add(b.getTile().getUnit());
                paths.add(new Path(b.getTile().getUnit()));
                lastPath.add(b);
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
            
            choosingPathtype = true;
            
            // Tile height/width = 32
            // Horizontal container padding = 73p
            // Vertical container padding = 37p            
            int chooseBoxX = (b.getTile().getX() * 32) + 73 + e.getX();
            int chooseBoxY = (b.getTile().getY() * 32) + 37 + e.getY();
            choosePathtype(chooseBoxX, chooseBoxY);
            
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
                if (!paths.get(pathNum).isValid(b.getTile())) {
                    return;
                }
                paths.get(pathNum).add(b.getTile());
                lastPath.add(b);
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
    
    public void endTurn() {
        pathNum = 0;
        unitsMoved.clear();
        paths.clear();
        lastPath.clear();
        gui.render();
        listening = false;
        finished = true;
    }
    
    public void undo() {
        if (pathNum > 0) {
            listening = true;
            pathNum--;
            unitsMoved.remove(unitsMoved.size() - 1);
            paths.remove(paths.size() - 1);
            
            for (TileButton b : lastPath) {
                b.setIcon(chooseIcon(b));
            }
        }
    }
    
    public void choosePathtype(int x, int y) {
        JPanel pathOptions = new JPanel(new BorderLayout());
        //pathOptions.setLayout(new BoxLayout(pathOptions, BoxLayout.Y_AXIS));
        
        // Tile height/width = 32
        pathOptions.setBounds(x, y, 32 * 3, 32 * 2);
        gui.getMapPane().add(pathOptions, new Integer(1), 0);
        
        JButton safeGoal = new JButton ("Safe Goal Move");
        safeGoal.setPreferredSize(new Dimension(32 * 3, 32 * 2 / 3));
        safeGoal.setFont(new Font("Arial", Font.PLAIN, 8));
        JButton goal = new JButton("Goal Move");
        goal.setPreferredSize(new Dimension(32 * 3, 32 * 2 / 3));    
        goal.setFont(new Font("Arial", Font.PLAIN, 8));        
        JButton standard = new JButton("Standard Move");
        standard.setPreferredSize(new Dimension(32 * 3, 32 * 2 / 3));  
        standard.setFont(new Font("Arial", Font.PLAIN, 8));
        
        pathOptions.add(safeGoal, BorderLayout.NORTH);
        pathOptions.add(goal, BorderLayout.CENTER);
        pathOptions.add(standard, BorderLayout.SOUTH);
        pathOptions.setVisible(true);
        
        gui.revalidate();
        choosingPathtype = false;
    }
    
    public ImageIcon chooseIcon(TileButton b) {
        ImageIcon icon = null;
        if (drawingPath) {
            if (b.getTile().isEmpty()) {
                switch (pathNum) {
                    case 0: icon = new ImageIcon("res/pathTile1.png");
                            break;
                    case 1: icon = new ImageIcon("res/pathTile2.png");
                            break;
                    case 2: icon = new ImageIcon("res/pathTile3.png");
                            break;
                    default: break;
                }
            }
            else {
                if (b.getTile().getUnit().getTeam().getName() == "A") {
                    switch (pathNum) {
                        case 0: icon = new ImageIcon("res/passedUnit1Tile1.png");
                                break;
                        case 1: icon = new ImageIcon("res/passedUnit1Tile2.png");
                                break;
                        case 2: icon = new ImageIcon("res/passedUnit1Tile3.png");
                                break;
                        default: break;
                    }
                }
                else if (b.getTile().getUnit().getTeam().getName() == "B") {
                    switch (pathNum) {
                        case 0: icon = new ImageIcon("res/passedUnit2Tile1.png");
                                break;
                        case 1: icon = new ImageIcon("res/passedUnit2Tile2.png");
                                break;
                        case 2: icon = new ImageIcon("res/passedUnit2Tile3.png");
                                break;
                        default: break;
                    }
                }            
            }
        } // if drawingPath
        else {
            if (b.getTile().isEmpty()) {
                icon = new ImageIcon("res/tile.png");
            }
            else {
                if (b.getTile().getUnit().getTeam().getName() == "A") {
                    icon = new ImageIcon("res/unit1Tile.png");
                }
                else if (b.getTile().getUnit().getTeam().getName() == "B") {
                    icon = new ImageIcon("res/unit2Tile.png");
                }
            }
        }
        
        return icon;
    }
}