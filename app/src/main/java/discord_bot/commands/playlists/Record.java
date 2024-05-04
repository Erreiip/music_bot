package discord_bot.commands.playlists;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.GuildMusicManager;
import discord_bot.Kawaine;
import discord_bot.Main;
import discord_bot.TrackScheduler;
import discord_bot.commands.Commands;
import discord_bot.playlist_writer.Playlist;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class Record extends Commands {

    public Record(TrackScheduler scheduler) {
        super(scheduler);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, Kawaine kawaine) {
        
        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());

        String name = event.getOption(Main.PLAYLIST_RECORD_OPTION_NAME).getAsString();

        if ( musicManager.record ) {
            event.getHook().sendMessage("A playlist is already being recorded.").queue();
            return;
        }

        musicManager.record = true;
        musicManager.playlist = new Playlist(name);

        event.getHook().sendMessage("Start recording").queue();
    }

    @Override
    public void execute(ButtonInteractionEvent event, Kawaine kawaine) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
}
