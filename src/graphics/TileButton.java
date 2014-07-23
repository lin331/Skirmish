package graphics;

import map.Tile;

import javax.swing.Icon;
import javax.swing.JButton;


@SuppressWarnings("serial")
public class TileButton extends JButton {

    private Tile tile;

    public TileButton(Tile t) {
        super();
        this.tile = t;
    }

    public TileButton(Tile t, Icon icon) {
        super(icon);
        this.tile = t;
    }

    public Tile getTile() {
        return this.tile;
    }
}