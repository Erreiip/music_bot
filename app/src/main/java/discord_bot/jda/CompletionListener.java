package discord_bot.jda;

import java.util.ArrayList;
import java.util.List;

import discord_bot.Main;
import discord_bot.database.PlaylistDatabase;
import discord_bot.model.dao.Playlist;
import discord_bot.model.dao.Playlist.Track;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;

public class CompletionListener extends ListenerAdapter {

    Kawaine kawaine;

    public CompletionListener(Kawaine kawaine) {
        super();

        this.kawaine = kawaine;
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {

        if ( event.getName().equals(Main.PLAYLISTS_SEE) || (event.getName().equals(Main.PLAYLIST_LOAD) || event.getName().equals(Main.PLAYLIST_ADD) || (event.getName().equals(Main.PLAYLIST_REMOVE) && event.getFocusedOption().getName().equals(Main.PLAYLIST_ADD_REMOVE_OPTION_NAME)))) {

            PlaylistDatabase playlistDB = PlaylistDatabase.getInstance();
            List<Playlist> lstPlaylists = playlistDB.getPlaylistsFromGuild(event.getGuild().getIdLong());
            List<Command.Choice> lstValuable = new ArrayList<>();

            for (Playlist playlist : lstPlaylists) {

                String playlistName = playlist.name;

                if (playlistName.startsWith(event.getFocusedOption().getValue())) {

                    lstValuable.add(new Command.Choice(playlistName, playlistName));
                }
            }

            event.replyChoices(lstValuable).queue();
        }

        if ( event.getName().equals(Main.PLAYLIST_REMOVE) && event.getFocusedOption().getName().equals(Main.PLAYLIST_REMOVE_OPTION_TITLE) ) {

            PlaylistDatabase playlistDB = PlaylistDatabase.getInstance();
            Playlist playlist = playlistDB.getPlaylistWithTracks(event.getGuild().getIdLong(), event.getOption(Main.PLAYLIST_ADD_REMOVE_OPTION_NAME).getAsString());
            
            if ( playlist == null ) return;

            List<Command.Choice> lstValuable = new ArrayList<>();


            for (Track track : playlist.getTracks()) {

                if (track.title.startsWith(event.getFocusedOption().getValue())) {

                    lstValuable.add(new Command.Choice(track.title, track.title));
                }
            }

            event.replyChoices(lstValuable).queue();
        }
    }
}