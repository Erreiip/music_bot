package discord_bot.commands.audio.playlists;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.Main;
import discord_bot.commands.audio.Commands;
import discord_bot.enumerate.ButtonEnum;
import discord_bot.jda.Kawaine;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import discord_bot.model.MusicEmbded;
import discord_bot.model.MusicModal;
import discord_bot.model.TrackScheduler;
import discord_bot.model.playlist_writer.Playlist;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.WebhookMessageCreateAction;

public class Record extends Commands {

    public Record(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        String name = event.getOption(Main.PLAYLIST_RECORD_OPTION_NAME).getAsString();

        if ( musicManager.record ) {

            MessageSender.errorEvent(musicManager.getMessageSender(), "A playlist is already being recorded.", event);
            return;
        }

        musicManager.record = true;
        musicManager.playlist = new Playlist(name);

        MessageSender.infoEvent(musicManager.getMessageSender(), "Start recording", event);
    }

    @Override
    public void execute(ButtonInteractionEvent event) {

        event.replyModal(MusicModal.createModalPlaylistRecord()).queue();
    }
    
}
