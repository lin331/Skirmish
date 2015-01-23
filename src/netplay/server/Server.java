package netplay.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import netplay.message.*;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientA;
    private Socket clientB;
    private ObjectInputStream inputStreamA;
    private ObjectInputStream inputStreamB;
    private ObjectOutputStream outputStreamA;
    private ObjectOutputStream outputStreamB;
    private int connected;
    
    private Server() {
        serverSocket = null;
        clientA = null;
        clientB = null;
        inputStreamA = null;
        inputStreamB = null;
        outputStreamA = null;
        outputStreamB = null;
        //out = null;
        connected = 0;
        initialize();
    }
    
    private void initialize() {
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void connectClients() {
        try {
            while (connected != 2) {
                if (clientA == null) {
                    clientA = serverSocket.accept();            
                    connected++;
                    System.out.println("Connected " + connected);
                }
                if (clientB == null) {
                    clientB = serverSocket.accept();
                    connected++;
                    System.out.println("Connected " + connected);
                }
            }
        } catch (IOException e) {
            
        }   
        try {
            inputStreamA = new ObjectInputStream(clientA.getInputStream());
            inputStreamB = new ObjectInputStream(clientB.getInputStream());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            outputStreamA = new ObjectOutputStream(clientA.getOutputStream());
            outputStreamB = new ObjectOutputStream(clientB.getOutputStream());
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    private void getTeamNames() {
        int received = 0;
        NameMessage msgA = null;
        NameMessage msgB = null;
        try {
            while (received != 2) {
                if (msgA == null) {
                    msgA = (NameMessage) inputStreamA.readObject();
                    received++;
                    System.out.println("msgA: " + msgA);
                }
                if (msgB == null) {
                    msgB = (NameMessage) inputStreamB.readObject();
                    received++;
                    System.out.println("msgB: " + msgB);
                }                
            }
        } catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }
    
    private void send(Message msg) {
        try {
            outputStreamA.writeObject(msg);
            outputStreamB.writeObject(msg); 
        } catch (IOException e) {
            e.printStackTrace();
        }       
    }
    
    private void run() {
        connectClients();
        getTeamNames();
        send(new Message());
        try {
            serverSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Server");
        Server server = new Server();
        server.run();
    }
}