package discord_bot.commands;

import discord_bot.Kawaine;
import discord_bot.TrackScheduler;
import discord_bot.embded.MusicEmbded;
import discord_bot.enumerate.ButtonEnum;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public class Help extends Commands {

    public Help(TrackScheduler scheduler) {
        super(scheduler);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, Kawaine kawaine) {
        
        sendEmbdedHelp(event);
    }

    @Override
    public void execute(ButtonInteractionEvent event, Kawaine kawaine) {

        sendEmbdedHelp(event);
    }

    private void sendEmbdedHelp(IDeferrableCallback event) {

        MessageEmbed embded = MusicEmbded.createEmbdedHelp();

        event.getHook().sendMessageEmbeds(embded).addActionRow(ButtonEnum.getHelpButton()).queue();
    }
    
}
