package discord_bot.commands.playlists;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.Kawaine;
import discord_bot.Main;
import discord_bot.commands.Commands;
import discord_bot.lava_player.GuildMusicManager;
import discord_bot.playlist_writer.Playlist;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Record implements Commands {

    @Override
    public void execute(SlashCommandInteractionEvent event, AudioPlayerManager playerManager, Kawaine kawaine) {
        
        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());

        String name = event.getOption(Main.PLAYLIST_RECORD_OPTION_NAME).getAsString();

        if ( musicManager.record ) {
            event.reply("A playlist is already being recorded.").queue();
            return;
        }

        musicManager.record = true;
        musicManager.playlist = new Playlist(name);

        event.reply("Start recording").queue();
    }
    
}
