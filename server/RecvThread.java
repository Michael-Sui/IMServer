package server;

/**
 * Created by Michael on 2017/4/29.
 */
public class RecvThread implements Runnable {
    private User user;
    private boolean flag;
    public RecvThread(User user) {
        this.user = user;
        flag = true;
    }
    @Override
    public void run() {
        while (flag) {
            try {
                String msg = user.getInput().readUTF();
                String[] msgs = msg.trim().split("#");
                if (msgs[0].equals("msg")) {
                    Server.messageList.get(msgs[1]).offer("msg#" + user.getName() + "#" + msgs[2]);
                }
            } catch (Exception e) {
                flag = false;
                if (Server.loginList.contains(user.getName())) {
                    Server.loginList.remove(user.getName());
                }
            }
        }
    }
}