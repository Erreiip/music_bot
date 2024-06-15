package discord_bot.commands.audio.track;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord_bot.Main;
import discord_bot.commands.audio.Commands;
import discord_bot.listeners.commands_listeners.play.IPlayListener;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import discord_bot.utils.IProcessAudio;
import discord_bot.utils.SongIdentifier;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class Play extends Commands implements IProcessAudio, IPlayListener {

    public Play(GuildMusicManager musicManager) {
        super(musicManager);

        musicManager.getScheduler().addPlayListener(this);
    }

    @Override
    public void executeCommands(SlashCommandInteractionEvent event) {

        String songIdentifier = event.getOption(Main.PLAY_OPTION_QUERY).getAsString();
        OptionMapping speedO = event.getOption(Main.PLAY_OPTION_SPEED);
        Float speed = speedO == null ? null : Float.parseFloat(speedO.getAsString());

        musicManager.addSong(event, SongIdentifier.getSongIdentifier(songIdentifier), speed, this);
    }


    /*
     * Callback
     * of the application
     * */
    @Override
    public void onTrackGet(SlashCommandInteractionEvent event, AudioTrack track,
            Float speed) {
                
        musicManager.getScheduler().queue(track, speed != null ? speed : 1, event);
        
        if (musicManager.record) musicManager.playlist.addTrack(track.getInfo()); 
    }

    @Override
    public void onPlay(IDeferrableCallback event) {

        MessageSender.playEvent(musicManager.getMessageSender(), musicManager.getScheduler().getCurrentTrack().getInfo(), musicManager.getScheduler().isLooped(), event);
    }

    @Override
    public void onPlayQueue(IDeferrableCallback event) {

        MessageSender.queueEvent(musicManager.getMessageSender(), musicManager.getScheduler().getQueue(), event);
    }
}
