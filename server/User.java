package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Michael on 2017/4/16.
 */
public class User {
    private Socket client;
    private String name;
    public User(Socket client) {
        this.client = client;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public DataInputStream getInput() {
        DataInputStream input = null;
        try {
            input = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            System.out.println("User:获取输入流失败！");
            e.printStackTrace();
        }
        return input;
    }
    public DataOutputStream getOutput() {
        DataOutputStream output = null;
        try {
            output = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            System.out.println("User:获取输出流失败！");
            e.printStackTrace();
        }
        return output;
    }
}
