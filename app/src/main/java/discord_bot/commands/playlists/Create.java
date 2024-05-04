package discord_bot.commands.playlists;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.Kawaine;
import discord_bot.Main;
import discord_bot.TrackScheduler;
import discord_bot.commands.Commands;
import discord_bot.playlist_writer.Playlist;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class Create extends Commands {

    public Create(TrackScheduler scheduler) {
        super(scheduler);
        //TODO Auto-generated constructor stub
    }

   @Override
    public void execute(SlashCommandInteractionEvent event, Kawaine kawaine) {
     
        String name = event.getOption(Main.PLAYLIST_CREATE_OPTION_NAME).getAsString();

        try { Playlist.createPlaylist(name); } 
        catch (Exception e) { 
            event.getHook().sendMessage("An error occurred while creating the playlist.").queue();
            return;
        }

        event.getHook().sendMessage("Playlist created.").queue();
    }

@Override
public void execute(ButtonInteractionEvent event, Kawaine kawaine) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'execute'");
}
    
}
