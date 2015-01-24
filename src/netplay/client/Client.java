package netplay.client;

import game.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;







import map.Map;
import netplay.message.Message;
//import netplay.message.Message;
import netplay.message.NameMessage;

public class Client {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    private ClientGui gui;
    private Game game;
    private boolean isConnected;
    private boolean isFinished;
    
    public Map getMap() {
        return game.getMap();
    }
    
    private Client() {
        socket = null;
        in = null;
        out = null;
        
        gui = null;
        game = null;
        
        isConnected = false;
        isFinished = false;
    }

    private void unitSetup() {
        gui.unitSetup();
    }
    
    private void play() {
        unitSetup();
        boolean isFinished = false;
        while (!isFinished) {
            
        }
    }
    
    protected void receiveMatch() {
        boolean waiting = true;
        Message msg = null;
        try {
            while (waiting) {
                if (in != null ) { 
                    msg = (Message) in.readObject();
                    if (msg != null) {
                        waiting = false;
                        System.out.println("Received");
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            
        }
        gui.initMainGame();
        //play();
    }
    
    protected void sendTeamName(String name) {
        NameMessage team = new NameMessage(name);
        try {
            out.writeObject(team);
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }
    
    protected void connect() {
        try {
            while(!isConnected) {
                socket = new Socket("localhost", 4444);
                System.out.println("Connected");
                isConnected = true;
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
            }
        } catch (IOException e) {
            
        }
    }
    
    private void start() {
        gui = new ClientGui(this);
        gui.start();
    }
    
    public static void main(String[] args) {
        System.out.println("Client");
        Client client = new Client();
        client.start();
    }
}