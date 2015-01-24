package netplay.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import netplay.message.*;

public class Server {
    private static final int LISTENING = 0;
    private static final int UNIT_SETUP = 1;
    private static final int TURN = 2;
    private static final int FINISHED = 3;
    
    private ServerSocket serverSocket;
    private Socket clientA;
    private Socket clientB;
    private ObjectInputStream inputStreamA;
    private ObjectInputStream inputStreamB;
    private ObjectOutputStream outputStreamA;
    private ObjectOutputStream outputStreamB;
    private int connected;
    
    private int status;
    
    private Server() {
        serverSocket = null;
        clientA = null;
        clientB = null;
        inputStreamA = null;
        inputStreamB = null;
        outputStreamA = null;
        outputStreamB = null;
        connected = 0;
        status = 0;
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
    
    private void run1() {
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
    
    private void run() {
        while (status != FINISHED) {
            switch(status) {
                case LISTENING:
                    
            }
        }
    }
    private void testRun() {
        try {
            clientA = serverSocket.accept();
            NameMessage msgA = null;
            inputStreamA = new ObjectInputStream(clientA.getInputStream());
            outputStreamA = new ObjectOutputStream(clientA.getOutputStream());
            msgA = (NameMessage) inputStreamA.readObject();
            System.out.println(msgA);
            Message msg = new Message("Ready");
            outputStreamA.writeObject(msg);
            System.out.println("Message sent");
            //clientA.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error");
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Server");
        Server server = new Server();
        server.testRun();
    }
}