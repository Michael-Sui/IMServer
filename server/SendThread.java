package server;

/**
 * Created by Michael on 2017/4/29.
 */
public class SendThread implements Runnable {
    private User user;
    public SendThread(User user) {
        this.user = user;
    }
    @Override
    public void run() {
        boolean flag = true;
        while (flag) {
            try {
                if (!Server.loginList.contains(user)) {
                    flag = false;
                    break;
                }
                String msg = Server.messageList.get(user.getName()).poll();
                System.out.println("msgSend:" + msg);
                if (msg != null) {
                    user.getOutput().writeUTF(msg);
                    user.getOutput().flush();
                } else {
                    try {
                        Thread.sleep(5000);
                    } catch (Exception ee) {
                        System.out.println("SendThread:线程休眠失败！");
                    }
                }
            } catch (Exception e) {
                flag = false;
                if (Server.loginList.contains(user)) {
                    Server.loginList.remove(user);
                }
            }
        }
    }
}
