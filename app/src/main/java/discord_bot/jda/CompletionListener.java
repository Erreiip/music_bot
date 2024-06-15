package discord_bot.jda;

import java.util.ArrayList;
import java.util.List;

import discord_bot.Main;
import discord_bot.model.playlist_writer.Playlist;
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

        if ( event.getName().equals(Main.PLAYLISTS_SEE) || (event.getName().equals(Main.PLAYLIST_LOAD) || event.getName().equals(Main.PLAYLIST_ADD) || event.getName().equals(Main.PLAYLIST_REMOVE))) {

            List<String> lstPlaylists = Playlist.getPlaylistsNames();
            List<Command.Choice> lstValuable = new ArrayList<>();

            for (String playlist : lstPlaylists) {
                if (playlist.startsWith(event.getFocusedOption().getValue())) {
                    lstValuable.add(new Command.Choice(playlist, playlist));
                }
            }

            event.replyChoices(lstValuable).queue();
        }
    }
}