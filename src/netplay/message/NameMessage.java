package netplay.message;

public class NameMessage extends Message {
    private static final long serialVersionUID = -1654750546190249919L;
    private String name;
    
    public NameMessage(String name) {
        this.name = name;
    }
    
    public String toString() {
        return this.name;
    }

}
