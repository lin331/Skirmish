package netplay.message;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = -3000465444051178713L;
    
    public static final int CLIENT_NAME = 0;
    public static final int CLIENT_UNITS = 1;
    public static final int CLIENT_COMMAND = 2;
    public static final int SERVER_NAME = 3;
    public static final int SERVER_UNITS = 4;
    public static final int SERVER_COMMAND = 5;
    public static final int GAME_OVER = 6;
    
    private int type;
    private String msg;
    
    public Message() {
        this.type = -1;
        this.msg = null;
    }
    public Message(int type) {
        this.type = type;
        this.msg = "";
    }
    
    public Message(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }
    
    public int getType() {
        return type;
    }
    
    public String getMsg() {
        return msg;
    }
    
    
}