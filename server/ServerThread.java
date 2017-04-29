package server;

import java.io.IOException;
import java.util.List;

/**
 * Created by Michael on 2017/4/16.
 */
public class ServerThread implements Runnable {
    private User user;
    private List<User> userList;
    public ServerThread(User user) {
        this.user = user;
        userList = Server.getList();
    }
    @Override
    public void run() {
        boolean flag = true;
        while (flag) {
            try {
                String msg = user.getInput().readUTF();
                String[] msgs = msg.trim().split("#");
                if (msgs[0].equals("msg")) {
                    String friend = msgs[1];
                    String message = msgs[2];
                    for (User u : userList) {
                        if (u.getName().equals(friend)) {
                            u.getOutput().writeUTF("msg#" + user.getName() + "#" + message);
                            u.getOutput().flush();
                        }
                    }
                }
            } catch (IOException e) {
                flag = false;
                //System.out.println("ServerThread:服务器线程错误！");
                //e.printStackTrace();
            }
        }
    }
}
