package netplay.client;

import game.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import map.Map;
import netplay.message.ClientMessage;
import netplay.message.Message;
//import netplay.message.Message;
import netplay.message.ServerMessage;

public class Client {
    private Socket socket = null;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    
    private ClientGui gui = null;
    private ClientMessageBox msgBox= null;
    private int playerID = -1;
    private Game game = null;
    private boolean isConnected = false;
    
    public Map getMap() {
        return game.getMap();
    }
    
    private Client() {
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
        ServerMessage msg = null;
        try {
            msg = (ServerMessage) in.readObject();
            playerID = msg.getID();
            System.out.println("Received ID: " + playerID);
            msgBox.append("\n" + msg.getMsg());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        gui.initMainGame();
        //play();
    }
    
    private void sendTeamName(String name) {
        ClientMessage team = new ClientMessage(Message.CLIENT_NAME, name);
        try {
            out.writeObject(team);
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }
    
    private void connect(String name) {
        while(!isConnected) {
            try {
                socket = new Socket("localhost", 4444);
                System.out.println("Connected");
                isConnected = true;
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                sendTeamName(name);
            } catch (IOException e) {}
        }
    }
    
    protected void start(String name) {
        connect(name);
        receiveMatch();
        try {
            out.writeObject(new ClientMessage(Message.GAME_OVER));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void run() {
        gui = new ClientGui(this);
        msgBox = gui.getMsgBox();
        gui.start();
    }
    
    public static void main(String[] args) {
        System.out.println("Client");
        Client client = new Client();
        client.run();
    }
}