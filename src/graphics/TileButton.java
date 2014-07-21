package graphics;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import map.Position;
import map.Tile;

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