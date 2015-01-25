package netplay.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import netplay.message.*;

public class Server {
    
    private ServerSocket serverSocket = null;
    private Socket clientA = null;
    private Socket clientB = null;
    private ObjectInputStream inputStreamA = null;
    private ObjectInputStream inputStreamB = null;
    private ObjectOutputStream outputStreamA = null;
    private ObjectOutputStream outputStreamB = null;
    
    private boolean isFinished = false;
    private int connected = 0;
    
    private Server() {
        initialize();
    }
    
    /* Message handler */
    private void handleMessage(Message msg) {
        if (msg == null) {
            return;
        }
        ClientMessage clientMsg = (ClientMessage) msg;
        int type = msg.getType();
        switch(type) {
            case Message.CLIENT_NAME:
                try {
                    if (connected == 0) {
                        System.out.println("Received name from A");
                        outputStreamB.writeObject(new ServerMessage(
                                Message.SERVER_NAME, 1, clientMsg.getName(), 
                                "Playing against " + clientMsg.getName()));
                        connected++;
                    }
                    else if (connected == 1) {
                        System.out.println("Received name from B");
                        outputStreamA.writeObject(new ServerMessage(
                                Message.SERVER_NAME, 0, clientMsg.getName(),
                                "Playing against " + clientMsg.getName()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case Message.CLIENT_UNITS:
                try {
                    if (clientMsg.getID() == 0) {
                        System.out.println("Received units from A");
                        outputStreamB.writeObject(new ServerMessage(
                                Message.SERVER_NAME, clientMsg.getUnits()));
                    }
                    else if (clientMsg.getID() == 1) {
                        System.out.println("Received units from B");
                        outputStreamA.writeObject(new ServerMessage(
                                Message.SERVER_NAME, clientMsg.getUnits()));                        
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case Message.CLIENT_COMMAND:
                try {
                    if (clientMsg.getID() == 0) {
                        System.out.println("Received commands from A");
                        outputStreamB.writeObject(new ServerMessage(
                                Message.SERVER_NAME, clientMsg.getUnits()));
                    }
                    else if (clientMsg.getID() == 1) {
                        System.out.println("Received commands from B");
                        outputStreamA.writeObject(new ServerMessage(
                                Message.SERVER_NAME, clientMsg.getUnits()));                        
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case Message.GAME_OVER:
                try {
                    clientA.close();
                    clientB.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                isFinished = true;
            default:
                    break;
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * Connect to clients
     */
    private void connect() {
        try {
            clientA = serverSocket.accept();
            inputStreamA = new ObjectInputStream(clientA.getInputStream());
            outputStreamA = new ObjectOutputStream(clientA.getOutputStream());
            System.out.println("A Connected");
        } catch (IOException e) {}
        try {
            clientB = serverSocket.accept();
            inputStreamB = new ObjectInputStream(clientB.getInputStream());
            outputStreamB = new ObjectOutputStream(clientB.getOutputStream());
            System.out.println("B Connected");
        } catch (IOException e) {}
    }
    
    private void run() {
        connect();
        //getTeamNames();
        Message msgA = null;
        Message msgB = null;
        while (!isFinished) {
            try {
                msgA = (Message) inputStreamA.readObject();
                msgB = (Message) inputStreamB.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            handleMessage(msgA);
            handleMessage(msgB);       
        }
    }
    
    private void initialize() {
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            e.printStackTrace();
        }
        isFinished = false;
    }
    
    public static void main(String[] args) {
        System.out.println("Server");
        Server server = new Server();
        server.run();
    }
}