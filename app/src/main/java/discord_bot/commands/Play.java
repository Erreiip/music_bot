package discord_bot.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.Kawaine;
import discord_bot.Main;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.managers.AudioManager;

public class Play implements Commands {

    @Override
    public void execute(SlashCommandInteractionEvent event, AudioPlayerManager playerManager, Kawaine kawaine) {
        
        String songIdentifier = event.getOption(Main.PLAY_OPTION_URL).getAsString();
        OptionMapping speedO = event.getOption(Main.PLAY_OPTION_SPEED);
        Float speed = speedO == null ? null : Float.parseFloat(speedO.getAsString());

        AudioManager audioChannel = kawaine.joinChannel(event);

        if (audioChannel == null) return;

        songIdentifier = Kawaine.getSongIdentifier(songIdentifier);

        kawaine.addSong(event, songIdentifier, playerManager, speed, kawaine);
    }
    
}
