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
        while (true) {
            try {
                String msg = Server.messageList.get(user.getName()).poll();
                if (msg != null) {
                    user.getOutput().writeUTF(msg);
                    user.getOutput().flush();
                }
            } catch (Exception e) {
                try {
                    Thread.sleep(500);
                } catch (Exception ee) {

                }
            }
        }
    }
}
