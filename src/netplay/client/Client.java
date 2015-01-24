package netplay.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;





import netplay.message.Message;
//import netplay.message.Message;
import netplay.message.NameMessage;

public class Client {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    private ClientGui gui;
    private boolean isConnected;
    private boolean isFinished;
    
    private Client() {
        socket = null;
        in = null;
        out = null;
        isConnected = false;
        isFinished = false;
    }
    

    
    protected void sendTeamName() {
        NameMessage name = new NameMessage("Team A");
        try {
            out.writeObject(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    protected Message receive() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Message msg = null;
        try {
            msg = (Message) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg;
    }
    
    private void run() {
        connect();
        sendTeamName();
        Message msg = null;
        while(!isFinished) {
            msg = receive();
            if (msg != null) {
                isFinished = true;
            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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