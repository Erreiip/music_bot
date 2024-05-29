package discord_bot.listeners.leave;
public class TimeoutSong implements Runnable {

    private ITimeoputListener listener;

    public TimeoutSong(ITimeoputListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        try {
            System.out.println("TimeoutSong: run: Sleeping for 10 seconds");
            Thread.sleep(10000);
            
            this.listener.onTimeoutNoSong();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
