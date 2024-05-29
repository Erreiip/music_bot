package discord_bot.model;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.commands.Commands;
import discord_bot.jda.Kawaine;
import discord_bot.lava_player.AudioLoadResultHandlerImpl;
import discord_bot.lava_player.AudioPlayerSendHandler;
import discord_bot.listeners.leave.ITimeoputListener;
import discord_bot.listeners.leave.TimeoutSong;
import discord_bot.listeners.no_track.INoTrackListener;
import discord_bot.model.playlist_writer.Playlist;
import discord_bot.utils.IProcessAudio;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class GuildMusicManager implements ITimeoputListener, INoTrackListener {

    private final AudioPlayerManager playerManager;
    public  final AudioPlayer player;
    public  final AudioPlayer secondPlayer;

    private final TrackScheduler scheduler;
    private AudioManager audioManager;

    private final MessageSender messageSender;

    public Playlist playlist;
    public boolean record;

    public final Commands[] commands;

    public GuildMusicManager() {

        this.playerManager = Kawaine.getPlayerManager();

        this.player = playerManager.createPlayer();
        this.secondPlayer = playerManager.createPlayer();

        this.messageSender = new MessageSender();
        
        this.scheduler = new TrackScheduler(player, secondPlayer);
        this.commands = Commands.getCommands(this);

        scheduler.addNoTrackListener(this);

        player.addListener(scheduler);
        secondPlayer.addListener(scheduler);
    }

    public AudioManager joinChannel(SlashCommandInteractionEvent event) {

        AudioChannel memberChannel = event.getMember().getVoiceState().getChannel();

        if (memberChannel == null) {
            
            MessageSender.errorEvent(this.getMessageSender(), "An error occurred while saving the playlist.", event);
            return null;
        }
                                        
        AudioManager audioManager = event.getMember().getGuild().getAudioManager();
        audioManager.openAudioConnection(memberChannel);

        this.audioManager = audioManager;

        return audioManager;
    }

    public void addSong(SlashCommandInteractionEvent event, String songIdentifier, Float speed, IProcessAudio callback) {

        AudioLoadResultHandler handler = new AudioLoadResultHandlerImpl(songIdentifier, callback, event, speed, this.getMessageSender());

        this.joinChannel(event);

        playerManager.loadItemOrdered(this, songIdentifier, handler);
    }

    public void reset() {
        
        this.record = false;
        this.playlist = null;

        scheduler.reset();
    }


    /*
     * Getters and Setters
     * */
    public AudioPlayerSendHandler getSendHandler() {
        
        return new AudioPlayerSendHandler(player);
    }

    public Commands getCommand(int command) {
        
        return commands[command];
    }

    public MessageSender getMessageSender() {
        
        return messageSender;
    }

    public TrackScheduler getScheduler() {
        
        return scheduler;
    }

    public AudioManager getAudioManager() {
        
        return audioManager;
    }

    public void setAudioManager(AudioManager audioManager) {
        
        this.audioManager = audioManager;
    }

    /*
    * ITimeoputListener
    * */
    @Override
    public void onTimeoutNoSong() {

        MessageSender.infoEvent(messageSender, "Le bot s'est déconnecté du à l'inactivité");
        this.audioManager.closeAudioConnection();
    }

    public void onNoTrack() {
        
        MessageSender.infoEvent(messageSender, "La musique est terminée, le bot se déconnectera dans 10 secondes");
        new Thread(new TimeoutSong(this)).start();
    }
}
