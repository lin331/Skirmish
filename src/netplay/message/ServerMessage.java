package netplay.message;

import java.util.ArrayList;

import player.Unit;

public class ServerMessage extends Message {
    private static final long serialVersionUID = -565625422634518794L;
    
    private int id = -1;
    private String enemyName = null;
    private ArrayList<Unit> units = null;
    
    public ServerMessage(int type, int id, String enemyName, String msg) {
        super(type, msg);
        this.id = id;
        this.enemyName = enemyName;
    }
    
    public ServerMessage(int type, ArrayList<Unit> units) {
        super(type);
        this.units = units;
    }
    
    public int getID() {
        return id;
    }
    
    public String getEnemyName() {
        return enemyName;
    }
    
    public ArrayList<Unit> getUnits() {
        return units;
    }
}
