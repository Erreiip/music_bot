package discord_bot.listeners.commands_listeners.play;

public interface IPlayListenable extends IPlayListener {
    
    public void addPlayListener(IPlayListener listener);

    public void removePlayListener(IPlayListener listener);
}
