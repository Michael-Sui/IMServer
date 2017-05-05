package client;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Michael on 2017/4/16.
 */
public class Client {
    private final String IP;
    private final int PORT;

    public Client() {
        IP = "127.0.0.1";
        PORT = 10001;
    }

    public void init() {
        try {
            Socket socket = new Socket(IP, PORT);
            new Thread(new ReadThread(socket)).start();
            new Thread(new WriteThread(socket)).start();
        } catch (IOException e) {
            System.out.println("Client:客户端错误！");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.init();
    }
}
