package netplay.message;

import java.util.ArrayList;

import player.Unit;

public class ClientMessage extends Message {
    private static final long serialVersionUID = 263133923225258258L;
    
    private int id = -1;
    private String name = null;
    private ArrayList<Unit> units = null;
    
    public ClientMessage(int type) {
        super(type);
    }
    
    /* Message with team name to server */
    public ClientMessage(int type, String name) {
        super(type);
        this.name = name;
    }
    
    /* Message with unit setup or commands to server */
    public ClientMessage(int type, int id, ArrayList<Unit> units) {
        super(type);
        this.id = id;
        this.units = units;        
    }
    
    public int getID() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public ArrayList<Unit> getUnits() {
        return units;
    }

}
