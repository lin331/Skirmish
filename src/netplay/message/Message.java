package netplay.message;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = -3000465444051178713L;
    private String msg;
    
    public Message() {
        
    }
    
    public Message(String msg) {
        this.msg = msg;
    }
    
    public String toString() {
        return this.msg;
    }
}