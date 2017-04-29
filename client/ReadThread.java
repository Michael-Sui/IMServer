package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Michael on 2017/4/16.
 */
public class ReadThread implements Runnable {
    private Socket socket;
    public ReadThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            DataInputStream input = new DataInputStream(socket.getInputStream());
            while (true) {
                String msg = input.readUTF();
                String[] msgs = msg.trim().split("#");
                if (msgs[0].equals("msg")) {
                    System.out.println(msgs[1] + ":" + msgs[2]);
                }
            }
        } catch (Exception e) {
            System.out.println("ReadThread:客户端读进程错误！");
            e.printStackTrace();
        }

    }
}
