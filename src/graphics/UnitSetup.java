package graphics;

import map.Tile;
import player.Archer;
import player.Team;
import player.Unit;
import player.UnitType;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;


public class UnitSetup implements MouseListener {
    private Gui gui;

    private boolean listening;
    private boolean finished;
    // Used for menu options
    private boolean choosingUnitType;
    private boolean editing;
    // Used for tile icons
    private boolean undo;
    private boolean mouseExiting;

    private ArrayList<Unit> units;
    private ArrayList<Tile> tiles;
    private ArrayList<TileButton> buttons;
    private int unitNum;

    TileButton selected;

    public UnitSetup(Gui gui) {
        this.gui = gui;
        this.listening = false;
        this.finished = false;
        this.choosingUnitType = false;
        this.mouseExiting = false;
        this.undo = false;
        this.editing = false;
        this.unitNum = 0;
        units = new ArrayList<Unit>();
        tiles = new ArrayList<Tile>();
        buttons = new ArrayList<TileButton>();
    }

    public boolean isListening() {
        return this.listening;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public int getUnitNum() {
        return this.unitNum;
    }

    public void start() {
        this.listening = true;
        this.finished = false;
    }

    public void undo() {
        if (!choosingUnitType) {
            if (unitNum > 0) {
                undo = true;
                listening = true;
                unitNum--;
                Team team = gui.getCurrentTeam();
                Unit u = units.remove(unitNum);
                team.remove(u);
                Tile t = tiles.remove(unitNum);
                t.setUnit(null);
                TileButton b = buttons.remove(unitNum);
                b.setIcon(chooseIcon(b));
                undo = false;
            }
            choosingUnitType = false;
            gui.unitOptions.setVisible(false);
        }
    }

    public void clear() {
        if (listening) {
            if (choosingUnitType) {
                gui.unitOptions.setVisible(false);
                choosingUnitType = false;
            }
            if (editing) {
                gui.editUnit.setVisible(false);
                editing = false;
            }
            for (Tile t : tiles) {
                t.setUnit(null);
            }
            tiles.clear();
            Team t = gui.getCurrentTeam();
            t.getUnits().clear();
            units.clear();
            mouseExiting = true;
            for (TileButton b : buttons) {
                b.setIcon(chooseIcon(b));
            }
            mouseExiting = false;
            buttons.clear();
            unitNum = 0;
        }
    }
    
    public void endTurn() {
        if (!choosingUnitType) {
            unitNum = 0;
            units.clear();
            tiles.clear();
            buttons.clear();
            unitNum = 0;
            listening = false;
            finished = true;
        }
    }

    public void setUnitType(UnitType type) {
        if (listening) {
            Team t = gui.getCurrentTeam();
            unitNum++;
            Unit unit;
            if (type == UnitType.ARCHER) {
                unit = new Archer(t, unitNum, tiles.get(unitNum - 1));
            }
            else {
                unit = new Unit(t, unitNum, type, tiles.get(unitNum - 1));
            }
            units.add(unit);
            t.addUnit(unit);
            tiles.get(unitNum - 1).setUnit(unit);
            TileButton b = buttons.get(unitNum - 1);
            b.setIcon(chooseIcon(b));
            choosingUnitType = false;
        }
    }

    public void chooseUnitType(UnitType type) {
        setUnitType(type);
        gui.unitOptions.setVisible(false);
    }

    public void remove() {
        if (listening && editing) {
            unitNum--;
            Unit u = selected.getTile().getUnit();
            Tile t = u.getTile();
            Team team = gui.getCurrentTeam();
            team.remove(u);
            units.remove(u);
            tiles.remove(t);
            t.setUnit(null);
            selected.setIcon(chooseIcon(selected));
            gui.editUnit.setVisible(false);
            editing = false;
        }
    }

    public void change() {
        if (listening && editing) {
            Unit u = selected.getTile().getUnit();

        }
    }

    /* Overrides */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (listening && !choosingUnitType) {
            if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                TileButton b = (TileButton) e.getSource();
                if (b.getTile().isEmpty()) {
                    buttons.add(b);
                    tiles.add(b.getTile());
                    choosingUnitType = true;
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
                    gui.unitOptions.setLocation(chooseBoxX, chooseBoxY);
                    gui.unitOptions.setVisible(true);
                }
            }
            else if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
                TileButton b = (TileButton) e.getSource();
                if (!b.getTile().isEmpty()) {
                    System.out.println("Editing unit");
                    selected = b;
                    editing = true;
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
                                 - (32 * 4 / 3) + 37;
                    }
                    gui.editUnit.setLocation(chooseBoxX, chooseBoxY);
                    gui.editUnit.setVisible(true);
                }
            }
        }
        else if (listening && choosingUnitType) {
            if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                gui.unitOptions.setVisible(false);
                choosingUnitType = false;
                buttons.remove(buttons.size() - 1);
                tiles.remove(tiles.size() - 1);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        TileButton b = (TileButton) e.getSource();
        b.setIcon(chooseIcon(b));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseExiting = true;
        TileButton b = (TileButton) e.getSource();
        b.setIcon(chooseIcon(b));
        mouseExiting = false;
    }

    /* Private methods */
    private ImageIcon chooseIcon(TileButton b) {
        ImageIcon icon = new ImageIcon("res/tile.png");
        if (undo) {
            icon = new ImageIcon("res/tile.png");
        }
        else if (b.getTile().isEmpty() && editing) {
            icon = new ImageIcon("res/tile.png");            
        }
        else if (b.getTile().isEmpty() && !mouseExiting) {
            icon = new ImageIcon("res/pathTile1.png");
        }
        else if (b.getTile().isEmpty() && mouseExiting) {
            icon = new ImageIcon("res/tile.png");
        }
        else if (!b.getTile().isEmpty()) {
            StringBuilder sb = new StringBuilder("res/passed");
            switch (b.getTile().getUnit().getType()) {
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
                    break;
            }
            if (b.getTile().getUnit().getTeam().getName() == "A") {
                sb.append("1");
            }
            else if (b.getTile().getUnit().getTeam().getName() == "B") {
                sb.append("2");
            }
            sb.append("Tile1.png");
            icon = new ImageIcon(sb.toString());
        }
        else {
            icon = new ImageIcon("res/tile.png");
        }
        return icon;
    }
}
