package discord_bot.commands.track;

import discord_bot.commands.Commands;
import discord_bot.jda.Kawaine;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import discord_bot.model.TrackScheduler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class Stop extends Commands {
    
    public Stop(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        musicManager.getScheduler().reset();

        AudioManager audioManager = event.getMember().getGuild().getAudioManager();
        audioManager.closeAudioConnection();

        MessageSender.infoEvent(musicManager.getMessageSender(), "Stopped the player and left the voice channel.", event);
    }

    @Override
    public void execute(ButtonInteractionEvent event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}
