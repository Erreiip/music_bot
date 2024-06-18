package discord_bot.commands.audio.playlists;

import java.util.List;

import discord_bot.commands.audio.Commands;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import discord_bot.model.dao.Playlist;
import discord_bot.utils.database.PlaylistDatabase;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public class Playlists extends Commands {
    
    public Playlists(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void executeCommands(SlashCommandInteractionEvent event) {
        try {
            getPlaylists(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void executeCommands(ButtonInteractionEvent event) {
        try {
            getPlaylists(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPlaylists(IDeferrableCallback event) throws Exception {


        PlaylistDatabase playlistDB = PlaylistDatabase.getInstance();
        List<Playlist> playlists = playlistDB.getPlaylistsFromGuild(event.getGuild().getIdLong());
        
        if (playlists.isEmpty()) {

            MessageSender.errorEvent(musicManager.getMessageSender(), "No playlist found.", event);
            return;
        }

        StringBuilder builder = new StringBuilder();

        playlists.forEach(playlist -> builder.append(":arrow_forward: ").append(playlist.name).append("\n"));

        MessageSender.infoEvent(musicManager.getMessageSender(), builder.toString(), event);
    }
}
