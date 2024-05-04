package discord_bot.commands.track;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.GuildMusicManager;
import discord_bot.Kawaine;
import discord_bot.TrackScheduler;
import discord_bot.commands.Commands;
import discord_bot.embded.MusicEmbded;
import discord_bot.enumerate.ButtonEnum;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public class Pause extends Commands {
    
    public Pause(TrackScheduler scheduler) {
        super(scheduler);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, Kawaine kawaine) {
        
        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());
        
        musicManager.player.setPaused(!musicManager.player.isPaused());

        sendResponse(event, musicManager);
    }

    @Override
    public void execute(ButtonInteractionEvent event, Kawaine kawaine) {
        
        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());
        
        musicManager.player.setPaused(!musicManager.player.isPaused());

        sendResponse(event, musicManager);
    }

    public void sendResponse(IDeferrableCallback event, GuildMusicManager musicManager) {

        MessageEmbed mb = MusicEmbded.createEmbdedResponse("Player is now " + (musicManager.player.isPaused() ? "paused" : "resumed"));

        event.getHook().sendMessageEmbeds(mb).queue();
    }

    
    
}
