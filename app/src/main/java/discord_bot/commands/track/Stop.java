package discord_bot.commands.track;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.commands.Commands;
import discord_bot.jda_listener.Kawaine;
import discord_bot.jda_listener.model.TrackScheduler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class Stop extends Commands {
    
    public Stop(TrackScheduler scheduler) {
        super(scheduler);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, Kawaine kawaine) {
        
        AudioManager audioManager = event.getMember().getGuild().getAudioManager();
        audioManager.closeAudioConnection();

        event.getHook().sendMessage("Stopped the player and left the voice channel.").queue();
    }

    @Override
    public void execute(ButtonInteractionEvent event, Kawaine kawaine) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}
