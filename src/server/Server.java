package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Created by Michael on 2017/4/16.
 */
public class Server {
    private final int PORT;
    private ArrayList<User> loginList;
    private ArrayList<String> userList;
    private HashMap<String, Queue<String>> messageList;
    private Database database;
    private static Server instance;

    public Server() {
        PORT = 10001;
        loginList = new ArrayList<User>();
        userList = new ArrayList<String>();
        messageList = new HashMap<String, Queue<String>>();
        database = new Database();
        Server.instance = this;
    }

    public static Server getInstance() {
        return Server.instance;
    }

    public ArrayList<User> getLoginList() {
        return this.loginList;
    }

    public HashMap<String, Queue<String>> getMessageList() {
        return this.messageList;
    }

    private void loadMessageList(HashMap<String, Queue<String>> messageList) {
        for (String name : userList) {
            messageList.put(name, new LinkedList<String>());
        }
    }

    private void init() {
        try {
            database.loadUserList(userList);
            this.loadMessageList(messageList);
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket client = serverSocket.accept();
                User user = new User(client);
                String msg = user.getInput().readUTF();
                String[] msgs = msg.trim().split("#");
                boolean flag = false;
                if (msgs[0].equals("logIn")) {
                    flag = database.logIn(msgs[1], msgs[2]);
                    user.setName(msgs[1]);
                } else if (msgs[0].equals("signUp")) {
                    flag = database.signUp(msgs[1], msgs[2]);
                    user.setName(msgs[1]);
                    if (flag) {
                        this.userList.add(msgs[1]);
                        this.messageList.put(msgs[1], new LinkedList<String>());
                    }
                }
                boolean hasLogin = false;
                for (User u : loginList) {
                    if (u != null && u.getName().equals(msgs[1])) {
                        hasLogin = true;
                    }
                }
                if (flag && !hasLogin) {
                    user.getOutput().writeUTF("msg#Server#SUCCESS");
                    user.getOutput().flush();
                    this.loginList.add(user);
                    new Thread(new RecvThread(user)).start();
                    new Thread(new SendThread(user)).start();
                } else {
                    user.getOutput().writeUTF("msg#Server#FAIL");
                    user.getOutput().flush();
                    client.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Server:服务器错误");
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Server server = new Server();
        server.init();
    }
}
