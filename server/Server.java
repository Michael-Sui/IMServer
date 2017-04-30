package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Created by Michael on 2017/4/16.
 */
public class Server {
    private static final int PORT = 10001;
    public static ArrayList<User> loginList = new ArrayList<User>();
    public static ArrayList<String> userList = new ArrayList<String>();
    public static HashMap<String, Queue<String>> messageList = new HashMap<String, Queue<String>>();

    public static int getPORT() {
        return PORT;
    }

    private void loadMessageList(HashMap<String, Queue<String>> messageList) {
        for (String name : userList) {
            messageList.put(name, new LinkedList<String>());
        }
    }

    public static void main(String[] args) {
        System.out.println("Server:服务器已经启动！");
        Server server = new Server();
        server.init();
    }

    private void init() {
        try {
            Database.loadUserList(userList);
            loadMessageList(messageList);
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket client = serverSocket.accept();
                User user = new User(client);
                String msg = user.getInput().readUTF();
                String[] msgs = msg.trim().split("#");
                boolean flag = false;
                if (msgs[0].equals("logIn")) {
                    flag = Database.logIn(msgs[1], msgs[2]);
                    user.setName(msgs[1]);
                } else if (msgs[0].equals("signUp")) {
                    flag = Database.signUp(msgs[1], msgs[2]);
                    if (flag) {
                        Server.userList.add(msgs[1]);
                    }
                }
                boolean hasLogin = false;
                for (User u : loginList) {
                    if (u != null && u.getName().equals(msgs[1])) {
                        hasLogin = true;
                    }
                }
                if (flag && !hasLogin) {
                    Server.loginList.add(user);
                    new Thread(new RecvThread(user)).start();
                    new Thread(new SendThread(user)).start();
                    user.getOutput().writeUTF("msg#Server#登陆成功");
                } else {
                    user.getOutput().writeUTF("msg#Server#登录失败");
                    client.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Server:服务器错误");
            e.printStackTrace();
        }

    }
}
