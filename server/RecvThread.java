package server;

/**
 * Created by Michael on 2017/4/29.
 */
public class RecvThread implements Runnable {
    private User user;
    private boolean flag;
    private Server server;

    public RecvThread(User user) {
        this.user = user;
        flag = true;
        server = Server.getInstance();
    }

    @Override
    public void run() {
        while (flag) {
            try {
                if (!server.getLoginList().contains(user)) {
                    flag = false;
                    break;
                }
                String msg = user.getInput().readUTF();
                String[] msgs = msg.trim().split("#");
                if (msgs[0].equals("msg")) {
                    server.getMessageList().get(msgs[1]).offer("msg#" + user.getName() + "#" + msgs[2]);
                }
            } catch (Exception e) {
                flag = false;
                if (server.getLoginList().contains(user)) {
                    server.getLoginList().remove(user);
                }
            }
        }
    }
}
