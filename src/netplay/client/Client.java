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
    private boolean isConnected;
    private boolean isFinished;
    
    public Client() {
        socket = null;
        in = null;
        out = null;
        isConnected = false;
        isFinished = false;
    }
    
    private void connect() {
        try {
            while(!isConnected) {
                socket = new Socket("localhost", 4444);
                System.out.println("Connected");
                isConnected = true;
                out = new ObjectOutputStream(socket.getOutputStream());
            }
        } catch (IOException e) {
            
        }
    }
    
    private void sendTeamName() {
        NameMessage name = new NameMessage("Team A");
        try {
            out.writeObject(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    private Message receive() {
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
    
    public void run() {
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
    
    public static void main(String[] args) {
        System.out.println("Client");
        Client client = new Client();
        client.run();
    }
}