package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Michael on 2017/4/16.
 */
public class WriteThread implements Runnable {
    private Socket socket;
    public WriteThread(Socket sockst) {
        this.socket = sockst;
    }
    @Override
    public void run() {
        try {
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            String name = scanner.nextLine();
            String pwd = scanner.nextLine();
            output.writeUTF("logIn#" + name + "#" + pwd);
            output.flush();
            while (true) {
                String friend = scanner.nextLine();
                String message = scanner.nextLine();
                output.writeUTF("msg#" + friend + "#" + message);
                output.flush();
            }
        } catch (IOException e) {
            System.out.println("WriteThread:客户端写进程错误！");
            e.printStackTrace();
        }
    }
}
