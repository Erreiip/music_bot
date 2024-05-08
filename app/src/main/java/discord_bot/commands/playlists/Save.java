package discord_bot.commands.playlists;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.GuildMusicManager;
import discord_bot.Kawaine;
import discord_bot.TrackScheduler;
import discord_bot.commands.Commands;
import discord_bot.embded.MusicEmbded;
import discord_bot.playlist_writer.Playlist;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class Save extends Commands {

    public Save(TrackScheduler scheduler) {
        super(scheduler);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, Kawaine kawaine) {
        
        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());

        if ( ! musicManager.record ) {
            event.getHook().sendMessageEmbeds(MusicEmbded.createEmbdedResponse("No playlist is being recorded.")).queue();
            return;
        }

        musicManager.record = false;

        try {
            Playlist.writePlaylist(musicManager.playlist);
        } catch (Exception e) {
            event.getHook().sendMessageEmbeds(MusicEmbded.createEmbdedResponse("An error occurred while saving the playlist.")).queue();
            return;
        }

        event.getHook().sendMessageEmbeds(MusicEmbded.createEmbdedResponse("Playlist saved.")).queue();
    }

    @Override
    public void execute(ButtonInteractionEvent event, Kawaine kawaine) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
}
