package graphics;

import static output.Output.Print.*;
import player.Unit;
import map.Path;
import map.Pathtype;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class PathFinder implements MouseListener {

    private Gui gui;

    private boolean listening;
    private boolean drawingPath;
    private boolean finished;
    private boolean choosingPathtype;
    private boolean settingDelay;

    private ArrayList<Path> paths;
    private ArrayList<Unit> unitsMoved;
    private ArrayList<TileButton> lastPath;
    private int pathNum;

    private TileButton selected;

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
        this.settingDelay = false;
        this.selected = null;
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

    public void setPathtype(Pathtype type) {
        paths.get(paths.size() - 1).setType(type);
    }

    public void start() {
        this.listening = true;
        this.finished = false;
    }

    public void endTurn() {
        if (!choosingPathtype) {
            pathNum = 0;
            unitsMoved.clear();
            paths.clear();
            lastPath.clear();
            gui.render();
            listening = false;
            finished = true;
        }
    }

    public void undo() {
        if (!choosingPathtype) {
            if (pathNum > 0) {
                listening = true;
                pathNum--;
                unitsMoved.remove(unitsMoved.size() - 1);
                paths.remove(paths.size() - 1);

                for (TileButton b : lastPath) {
                    b.setIcon(chooseIcon(b));
                }
            }
            choosingPathtype = false;
            gui.pathOptions.setVisible(false);
        }
    }

    public void choosePathtype(Pathtype type) {
        setPathtype(type);
        choosingPathtype = false;
        gui.pathOptions.setVisible(false);
        printf("log.txt", "Chose %s move\n", paths.get(paths.size() - 1)
                .getType());
    }

    public void setDelay(String d) {
        if (settingDelay) {
            int delay = 0;
            try {
                delay = Integer.parseInt(d);
            } catch (NumberFormatException e) {
                delay = 0;
            }
            selected.getTile().getUnit().setDelay(delay);
            gui.delayOption.setVisible(false);
            selected = null;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (listening && !choosingPathtype) {
            if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                TileButton b = (TileButton) e.getSource();
                if (!b.getTile().isEmpty()
                        && !unitsMoved.contains(b.getTile().getUnit())
                        && b.getTile().getUnit().getTeam().getName() == gui
                                .getCurrentTeam().getName()) {
                    lastPath.clear();
                    drawingPath = true;
                    unitsMoved.add(b.getTile().getUnit());
                    paths.add(b.getTile().getUnit().getPath());
                    lastPath.add(b);
                    b.setIcon(chooseIcon(b));
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (listening && drawingPath) {
            if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                pathNum++;
                TileButton b = (TileButton) e.getSource();
                b.getTile().getUnit().setPath(paths.get(pathNum - 1));
                printf("log.txt", "%s\n", b.getTile().getUnit().getPath());
                drawingPath = false;
                choosingPathtype = true;
    
                // tile height/width = 32
                // horizontal padding = 73
                // vertical padding = 37
                int chooseBoxX = 0;
                int chooseBoxY = 0;
                if (b.getTile().getX() < 6) {
                    chooseBoxX = (b.getTile().getX() * 32) + 32 + 73;
                }
                else {
                    chooseBoxX = (b.getTile().getX() * 32) - 32 * 3 + 73;
                }
                if (b.getTile().getY() < 3) {
                    chooseBoxY = (b.getTile().getY() * 32) + 32 + 37;
                }
                else {
                    chooseBoxY = (b.getTile().getY() * 32) 
                             - (32 * 10 / 3) + 37;
                }
                gui.pathOptions.setLocation(chooseBoxX, chooseBoxY);
                gui.pathOptions.setVisible(true);
    
                gui.revalidate();
    
                // Turn MAX_COMMANDS
                if (pathNum == 3) {
                    listening = false;
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (listening && drawingPath) {
            if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                TileButton b = (TileButton) e.getSource();
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
        if (listening && !drawingPath && !choosingPathtype) {
            if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
                System.out.println("Right click");
                settingDelay = true;
                selected = (TileButton) e.getSource();
                if (!selected.getTile().getUnit().isPathEmpty()) {
                    int x = (selected.getTile().getX() * 32) + 32 + 73;
                    int y = (selected.getTile().getY() * 32) + 32 + 37;
                    gui.rightClickOptions.setLocation(x,y);
                    gui.rightClickOptions.setVisible(true);
                }
            }
        }
    }

    private ImageIcon chooseIcon(TileButton b) {
        ImageIcon icon = null;
        if (drawingPath) {
            if (b.getTile().isEmpty()) {
                switch (pathNum) {
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
                switch (pathNum) {
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
}
