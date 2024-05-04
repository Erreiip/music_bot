package discord_bot.commands.track;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord_bot.GuildMusicManager;
import discord_bot.Kawaine;
import discord_bot.Main;
import discord_bot.TrackScheduler;
import discord_bot.commands.Commands;
import discord_bot.commands_listeners.play.IPlayListener;
import discord_bot.common.IProcessAudio;
import discord_bot.embded.MusicEmbded;
import discord_bot.enumerate.ButtonEnum;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.managers.AudioManager;

public class Play extends Commands implements IProcessAudio, IPlayListener {

    public Play(TrackScheduler scheduler) {
        super(scheduler);

        scheduler.addPlayListener(this);
    }

    private Kawaine kawaine;

    @Override
    public void execute(SlashCommandInteractionEvent event, Kawaine kawaine) {
        
        this.kawaine = kawaine;

        String songIdentifier = event.getOption(Main.PLAY_OPTION_QUERY).getAsString();
        OptionMapping speedO = event.getOption(Main.PLAY_OPTION_SPEED);
        Float speed = speedO == null ? null : Float.parseFloat(speedO.getAsString());

        AudioManager audioChannel = kawaine.joinChannel(event);

        if (audioChannel == null) return;

        songIdentifier = Kawaine.getSongIdentifier(songIdentifier);

        kawaine.addSong(event, songIdentifier, speed, this);
    }

    @Override
    public void onTrackGet(SlashCommandInteractionEvent event, GuildMusicManager musicManager, AudioTrack track,
            Float speed) {
        
        kawaine.joinChannel(event);

        musicManager.scheduler.queue(track, speed != null ? speed : 1, event);
        
        if (musicManager.record) musicManager.playlist.addTrack(track.getInfo()); 
    }

    @Override
    public void onPlay(IDeferrableCallback event, TrackScheduler scheduler) {

        
        
        MessageEmbed mb = MusicEmbded.createEmbded(scheduler.getCurrentTrack().getInfo());

        event.getHook().sendMessageEmbeds(mb).setActionRow(ButtonEnum.items()).queue();
    }

    @Override
    public void onPlayQueue(IDeferrableCallback event, TrackScheduler scheduler) {

        MessageEmbed mb = MusicEmbded.createEmbdedQueue(scheduler.getQueue());

        event.getHook().sendMessageEmbeds(mb).setActionRow(ButtonEnum.getQueueButton()).queue();
    }

    @Override
    public void execute(ButtonInteractionEvent event, Kawaine kawaine) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
}
