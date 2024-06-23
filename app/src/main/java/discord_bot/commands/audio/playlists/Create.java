package discord_bot.commands.audio.playlists;

import discord_bot.Main;
import discord_bot.commands.Commands;
import discord_bot.database.PlaylistDatabase;
import discord_bot.enumerate.PlaylistRights;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class Create extends Commands {

    public Create(GuildMusicManager musicManager) {
        super(musicManager);
    }

   @Override
    public void executeCommands(SlashCommandInteractionEvent event) {
     
        String name = event.getOption(Main.PLAYLIST_CREATE_OPTION_NAME).getAsString();
        OptionMapping rights = event.getOption(Main.PLAYLIST_CREATE_OPTION_RIGTHS);

        PlaylistRights playlistRights = rights == null ? PlaylistRights.PRIVATE : PlaylistRights.get(rights.getAsString());

        PlaylistDatabase playlistDB = PlaylistDatabase.getInstance();
        playlistDB.createPlaylist(event.getGuild().getIdLong(), name, event.getUser().getIdLong(), playlistRights);

        MessageSender.infoEvent(musicManager.getMessageSender(), "Playlist created.", event);
    }
}
