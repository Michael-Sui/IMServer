package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 2017/4/16.
 */
public class Server {
    private static final int PORT = 10001;
    public static int getPORT() {
        return PORT;
    }
    private static List<User> usersList = new ArrayList<User>();
    public static List<User> getList() {
        return usersList;
    }
    private void init() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket client = serverSocket.accept();
                User user = new User(client);
                String msg = user.getInput().readUTF();
                String[] msgs = msg.trim().split("#");
                boolean flag = false;
                if (msgs[0].equals("logIn")) {
                    flag = Database.logIn(msgs[1], msgs[2]);
                } else if (msgs[0].equals("signUp")) {
                    flag = Database.signUp(msgs[1], msgs[2]);
                }
                if (flag) {
                    user.setName(msgs[1]);
                    usersList.add(user);
                    new Thread(new ServerThread(user)).start();
                } else {
                    System.out.println("Server:连接服务器失败！");
                    client.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        System.out.println("Server:服务器已经启动！");
        Server server = new Server();
        server.init();
    }
}
