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
    private boolean mouseExiting;
    private boolean choosingUnitType;
    private boolean undo;
    private ArrayList<Unit> units;
    private ArrayList<Tile> tiles;
    private ArrayList<TileButton> buttons;
    private int unitNum;

    public UnitSetup(Gui gui) {
        this.gui = gui;
        this.listening = false;
        this.finished = false;
        this.choosingUnitType = false;
        this.mouseExiting = false;
        this.undo = false;
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

    public void setUnitType(UnitType type) {
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

    public void chooseUnitType(UnitType type) {
        setUnitType(type);
        gui.unitOptions.setVisible(false);
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
                    if (b.getTile().getY() < 3) {
                        chooseBoxY = (b.getTile().getY() * 32);
                    }
                    else {
                        chooseBoxY = (b.getTile().getY() * 32);
                    }
                    if (b.getTile().getX() < 6) {
                        chooseBoxX = (b.getTile().getX() * 32) + 32 * 3 + 7;
                    }
                    else {
                        chooseBoxX = (b.getTile().getX() * 32) - 25;
                    }
                    gui.unitOptions.setBounds(chooseBoxX, chooseBoxY, 32 * 3,
                            32 * 10 / 3);
                    gui.unitOptions.setVisible(true);
                }
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

    private ImageIcon chooseIcon(TileButton b) {
        ImageIcon icon = new ImageIcon("res/tile.png");
        if (undo) {
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
