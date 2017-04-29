package client;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Michael on 2017/4/16.
 */
public class Client {
    private static final String IP = "127.0.0.1";
    private static final int PORT = 10001;
    public static void main(String[] args) {
        try {
            Socket socket = new Socket(IP, PORT);
            new Thread(new ReadThread(socket)).start();
            new Thread(new WriteThread(socket)).start();
        } catch (IOException e) {
            System.out.println("Client:客户端错误！");
            e.printStackTrace();
        }

    }
}
