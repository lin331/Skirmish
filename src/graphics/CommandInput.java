package graphics;

import static output.Output.Print.*;
import player.Unit;
import player.Archer;
import player.UnitType;
import map.Path;
import map.Pathtype;
import map.Tile;

import java.awt.Component;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class CommandInput implements MouseListener {

    private static final int LEFT_CLICK = MouseEvent.BUTTON1_MASK;
    private static final int RIGHT_CLICK = MouseEvent.BUTTON3_MASK;

    private Gui gui;

    private boolean listening;
    private boolean finished;
    private boolean drawingPath;
    private boolean choosingPathtype;
    private boolean editing;
    private boolean settingDelay;
    private boolean selectArcherAttack;

    private ArrayList<Unit> units;
    private ArrayList<Unit> tempUnits;
    private ArrayList<Path> paths;
    private ArrayList<TileButton> lastPath;
    private int num;
    private int commands;

    private TileButton selected;
    private TileButton lastButton;
    
    public CommandInput(Gui gui) {
        this.gui = gui;

        this.units = new ArrayList<Unit>();
        this.tempUnits = new ArrayList<Unit>();
        this.paths = new ArrayList<Path>();
        this.lastPath = new ArrayList<TileButton>();
        
        this.listening = false;
        this.finished = false;
        this.drawingPath = false;
        this.choosingPathtype = false;
        this.settingDelay = false;
        this.editing = false;
        this.selectArcherAttack = false;

        this.num = 0;
        this.commands = 0;
        
        this.selected = null;
        this.lastButton = null;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public boolean isListening() {
        return this.listening;
    }
    
    public boolean isBusy() {
        return drawingPath || choosingPathtype || settingDelay;
    }

    public int getNum() {
        return this.num;
    }

    public void activate(boolean flag) {
        this.listening = flag;
    }
    
    public void start() {
        this.finished = false;
    }

    public void endTurn() {
        if (listening && !choosingPathtype) {
            units.clear();
            tempUnits.clear();
            paths.clear();
            lastPath.clear();
            selected = null;
            lastButton = null;
            num = 0;
            commands = 0;
            gui.render();
            finished = true;
        }
    }

    public void setPathtype(Pathtype type) {
        // set tempPath type
        paths.get(num).setType(type);
        // add unit to units move list
        units.add(selected.getTile().getUnit());
        // set temp variables to null
        lastPath.clear();
        if (selected.getTile().getUnit().getType() == UnitType.ARCHER) {
            selectArcherAttack = true;
            printf("Select archer attack tile\n");
            return;
        }
        selected = null;
        lastButton = null;
        // increment counts
        num++;
        commands++;
    }

    public void choosePathtype(Pathtype type) {
        if (listening) {
            setPathtype(type);
            choosingPathtype = false;
            gui.pathOptions.setVisible(false);
            /*printf("log.txt", "Chose %s move\n", paths.get(paths.size() - 1)
                    .getType());*/
        }
    }

    public void changePath() {
        if (listening) {
            Unit u = selected.getTile().getUnit();
            if (u.hasPathDelay()) {
                
            }
        }
    }

    public void cancelPath() throws Exception {
        if (listening) {
            Unit u = selected.getTile().getUnit();
            if (selected.getTile().getUnit().isPathEmpty()) {
                throw new Exception("Path empty");
            }
            else {
                u.getPath().clear();
                selected = null;
            }
        }
    }

    public void setDelay(String d) throws Exception {
        if (listening && settingDelay) {
            if (selected.getTile().getUnit().isPathEmpty()) {
                throw new Exception("Path empty");
            }
            int delay = 0;
            try {
                delay = Integer.parseInt(d);
            } catch (NumberFormatException e) {
                delay = 0;
            }
            selected.getTile().getUnit().setDelay(delay);
            settingDelay = false;
            gui.delayOption.setVisible(false);
            gui.updateTable();
            selected = null;
        }
    }

    public void undoChanges() {
        if (listening) {
            // TODO: GET THIS WORKING
            // Update tiles i think
            /*Unit u = selected.getTile().getUnit();
            Unit temp = findUnit(u);
            units.remove(temp);
            u.setDelay(temp.getDelay());
            u.getPath().clear();*/
        }
    }

    public void getDelay() throws Exception {
        if (listening) {
            if (selected.getTile().getUnit().isPathEmpty()) {
                throw new Exception("Path empty");
            }
            settingDelay = true;
            int x = (selected.getTile().getX() * 32) + 32 + 73;
            int y = (selected.getTile().getY() * 32) + 32 + 37;
            gui.delayOption.setLocation(x,y);
            gui.delayOption.setVisible(true);
            gui.editCommand.setVisible(false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (listening && selectArcherAttack) {
            // Left-click
            if (e.getModifiers() == LEFT_CLICK) {
                /* Selects archer attack tile */
                Archer a = (Archer) selected.getTile().getUnit();
                Tile archerAttackTile = ((TileButton) e.getSource()).getTile();
                a.setAttackTile(archerAttackTile);
                if (archerAttackTile != null) {
                    printf("Archer's attack tile: %s\n", a.getTile());
                    selected = null;
                    lastButton = null;
                    num++;
                    commands++;
                    selectArcherAttack = false;
                }
                else {
                    printf("Archer attack tile invalid\nSelect again\n");
                }
                return;
            }
        }
        /*if (listening && !drawingPath && !choosingPathtype) {
            // Left-click
            if (e.getModifiers() == LEFT_CLICK) {
                if (editing) {
                    // TODO: GET THIS WORKING
                    editing = false;
                    for (Component c : gui.editCommand.getComponents()) {
                        c.setVisible(false);
                    }
                    gui.editCommand.setVisible(false);
                    selected = null;
                }
            }
            // Right-click
            if (e.getModifiers() == RIGHT_CLICK) {
                if (editing) {
                    editing = false;
                    for (Component c : gui.editCommand.getComponents()) {
                        c.setVisible(false);
                    }
                    gui.editCommand.setVisible(false);
                    selected = null;
                    return;
                }
                TileButton b = (TileButton) e.getSource();
                if (!b.getTile().isEmpty()
                    && b.getTile().getUnit().getTeam()
                            == gui.getCurrentTeam()) {
                    Unit u = b.getTile().getUnit();
                    if (!u.isPathEmpty()) {
                        editing = true;
                        selected = b;
                        Component[] c = gui.editCommand.getComponents();
                        // 0 = change path
                        // 1 = cancel path
                        // 2 = set delay
                        // 3 = change delay
                        // 4 = undo changes
                        if (u.getPath().isEmpty()) {
                            c[0].setVisible(false);
                            c[1].setVisible(false);
                        }
                        else {
                            c[0].setVisible(false);
                            c[1].setVisible(false);
                        }
                        if (!u.hasPathDelay()) {
                            c[2].setVisible(true);
                            c[3].setVisible(false);
                        }
                        else {
                            c[2].setVisible(false);
                            c[3].setVisible(true);
                        }
                        if (units.contains(u)) {
                            c[4].setVisible(true);
                        }
                        else {
                            c[4].setVisible(false);
                        }
                        int x = (selected.getTile().getX() * 32) + 32 + 73;
                        int y = (selected.getTile().getY() * 32) + 32 + 37;
                        gui.editCommand.setLocation(x,y);
                        gui.editCommand.setVisible(true);
                    }
                }
            }
        }
        else if (listening && !drawingPath && (choosingPathtype || editing)) {
            // Left-click
            if (e.getModifiers() == LEFT_CLICK) {
                if (choosingPathtype) {
                    choosingPathtype = false;
                    gui.pathOptions.setVisible(false);
                    for (TileButton b : lastPath) {
                        b.setIcon(chooseIcon(b));
                    }
                    lastPath.clear();
                    Unit u = selected.getTile().getUnit();
                    u.getPath().clear();
                    Path p = tempUnits.remove(num).getPath();
                    for (Tile t : p.getTiles()) {
                        u.getPath().add(t);
                    }
                    paths.remove(num);
                    selected = null;
                    lastButton = null;
                }
            }
        }*/
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (listening && !choosingPathtype) {
            if (selectArcherAttack) {
                return;
            }
            if (e.getModifiers() == LEFT_CLICK) {
                if (commands == 3) {
                    return;
                }
                TileButton b = (TileButton) e.getSource();
                if (!b.getTile().isEmpty()
                        && !units.contains(b.getTile().getUnit())
                        && b.getTile().getUnit().getTeam() 
                                == gui.getCurrentTeam()) {
                    selected = b;
                    lastButton = b;
                    Unit u = selected.getTile().getUnit();
                    tempUnits.add(new Unit(u));
                    paths.add(u.getPath());
                    drawingPath = true;
                    // units.add(b.getTile().getUnit());
                    // paths.add(u.getPath());
                    lastPath.add(b);
                    b.setIcon(chooseIcon(b));
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (listening && drawingPath) {
            if (e.getModifiers() == LEFT_CLICK) {
                if (!lastPath.isEmpty()) {
                    // pathNum++;
                    /*units.get(units.size() - 1)
                            .setPath(paths.get(pathNum - 1));*/
                    /*TileButton b = (TileButton) e.getSource();
                    b.getTile().getUnit().setPath(paths.get(pathNum - 1));*/
                    /*printf("log.txt", "%s\n", units.get(units.size() - 1)
                            .getPath());*/
                    drawingPath = false;
                    choosingPathtype = true;
        
                    // tile height/width = 32
                    // horizontal padding = 73
                    // vertical padding = 37
                    int x = (lastButton.getTile().getX() * 32) + 32 + 73;
                    int y = (lastButton.getTile().getY() * 32) + 32 + 37;
                    gui.pathOptions.setLocation(x, y);
                    gui.pathOptions.setVisible(true);
        
                    gui.revalidate();
        
                    // Turn MAX_COMMANDS
                    /*if (pathNum == 3) {
                        listening = false;
                    }*/
                }
                else {
                    drawingPath = false;
                    lastButton.setIcon(chooseIcon(lastButton));
                    lastButton = null;
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (listening && drawingPath) {
            if (e.getModifiers() == LEFT_CLICK) {
                TileButton b = (TileButton) e.getSource();
                if (!paths.get(num).isValid(b.getTile())) {
                    return;
                }
                if (paths.get(num).add(b.getTile()) != null) {
                    lastPath.add(b);
                    lastButton = b;
                    b.setIcon(chooseIcon(b));
                }
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private ImageIcon chooseIcon(TileButton b) {
        if (listening) {
            ImageIcon icon = null;
            if (drawingPath) {
                if (b.getTile().isEmpty()) {
                    switch (num) {
                        case 0:
                            icon = new ImageIcon("res/pathTile1.png");
                            break;
                        case 1:
                            icon = new ImageIcon("res/pathTile2.png");
                            break;
                        case 2:
                            icon = new ImageIcon("res/pathTile3.png");
                            break;
                        default:
                            break;
                    }
                }
                else {
                    Unit u = b.getTile().getUnit();
                    StringBuilder sb = new StringBuilder("res/passed");
                    switch (u.getType()) {
                        case FOOTMAN:
                            sb.append("Footman");
                            break;
                        case SPEARMAN:
                            sb.append("Spearman");
                            break;
                        case ARCHER:
                            sb.append("Archer");
                            break;
                        case CAVALRY:
                            sb.append("Cavalry");
                            break;
                        case BARBARIAN:
                            sb.append("Barbarian");
                            break;
                        default:
                            sb.append("Unit");
                            break;
                    }
                    if (b.getTile().getUnit().getTeam().getName() == "A") {
                        sb.append("1");
                    }
                    else if (b.getTile().getUnit().getTeam().getName() == "B") {
                        sb.append("2");
                    }
                    switch (num) {
                        case 0:
                            sb.append("Tile1");
                            break;
                        case 1:
                            sb.append("Tile2");
                            break;
                        case 2:
                            sb.append("Tile3");
                            break;
                        default:
                            break;
                    }
                    sb.append(".png");
                    icon = new ImageIcon(sb.toString());
                }
            } // if drawingPath
            else {
                if (b.getTile().isEmpty()) {
                    icon = new ImageIcon("res/tile.png");
                }
                else {
                    Unit u = b.getTile().getUnit();
                    StringBuilder sb = new StringBuilder("res/");
                    switch (u.getType()) {
                        case FOOTMAN:
                            sb.append("Footman");
                            break;
                        case SPEARMAN:
                            sb.append("Spearman");
                            break;
                        case ARCHER:
                            sb.append("Archer");
                            break;
                        case CAVALRY:
                            sb.append("Cavalry");
                            break;
                        case BARBARIAN:
                            sb.append("Barbarian");
                            break;
                        default:
                            sb.append("Unit");
                            break;
                    }
                    if (b.getTile().getUnit().getTeam().getName() == "A") {
                        sb.append("1");
                    }
                    else if (b.getTile().getUnit().getTeam().getName() == "B") {
                        sb.append("2");
                    }
                    sb.append("Tile.png");
                    icon = new ImageIcon(sb.toString());
                }
            }
            return icon;
        }
        return null;
    }

    private Unit findUnit(Unit unit) {
        for (Unit u : tempUnits) {
            if (unit.equals(u)) {
                return u;
            }
        }
        return null;
    }
}
