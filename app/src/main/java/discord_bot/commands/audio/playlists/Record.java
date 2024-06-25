package discord_bot.commands.audio.playlists;

import discord_bot.Main;
import discord_bot.commands.Commands;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import discord_bot.model.MusicModal;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class Record extends Commands {

    public Record(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void executeCommands(SlashCommandInteractionEvent event) {

        String name = event.getOption(Main.PLAYLIST_RECORD_OPTION_NAME).getAsString();

        if ( musicManager.record ) {

            MessageSender.errorEvent(musicManager.getMessageSender(), "A playlist is already being recorded.", event);
            return;
        }

        musicManager.record = true;
        //musicManager.playlist = new Playlist(name);

        MessageSender.infoEvent(musicManager.getMessageSender(), "Start recording", event);
    }

    @Override
    public void executeCommands(ButtonInteractionEvent event) {

        //event.replyModal(MusicModal.createModalPlaylistRecord()).queue();

        System.out.println(event.getButton().getUrl());
    }
    
}
