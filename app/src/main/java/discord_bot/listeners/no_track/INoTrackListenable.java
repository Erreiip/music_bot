package discord_bot.listeners.no_track;

public interface INoTrackListenable extends INoTrackListener {
        
    public void addNoTrackListener(INoTrackListener listener);
    
    public void removeNoTrackListener(INoTrackListener listener);
}
