package server;

/**
 * Created by Michael on 2017/4/29.
 */
public class SendThread implements Runnable {
    private User user;
    private Server server;

    public SendThread(User user) {
        this.user = user;
        server = Server.getInstance();
    }

    @Override
    public void run() {
        boolean flag = true;
        while (flag) {
            try {
                if (!server.getLoginList().contains(user)) {
                    flag = false;
                    break;
                }
                String msg = server.getMessageList().get(user.getName()).poll();
                if (msg != null) {
                    user.getOutput().writeUTF(msg);
                    user.getOutput().flush();
                } else {
                    try {
                        Thread.sleep(3000);
                    } catch (Exception ee) {
                        System.out.println("SendThread:线程休眠失败！");
                    }
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
