package Graphics;

import Map.Position;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TileButton extends JButton {

    private Position pos;
    
    public TileButton(Position p) {
        super();
        this.pos = p;
    }
    
    public TileButton(Position p, Icon icon) {
        super(icon);
        this.pos = p;
    }
    
    public Position getPos() {
        return this.pos;
    }
}