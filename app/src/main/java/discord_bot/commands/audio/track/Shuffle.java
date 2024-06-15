package discord_bot.commands.audio.track;

import discord_bot.commands.audio.Commands;
import discord_bot.model.GuildMusicManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class Shuffle extends Commands {

    public Shuffle(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void executeCommands(SlashCommandInteractionEvent event) {
        
        shuffle();
    }

    @Override
    public void executeCommands(ButtonInteractionEvent event) {
        
        shuffle();
    }

    private void shuffle() {
        
        this.musicManager.getScheduler().shuffle();
    }
    
}
