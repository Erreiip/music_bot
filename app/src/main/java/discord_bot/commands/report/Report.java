package discord_bot.commands.report;

import discord_bot.Main;
import discord_bot.commands.audio.Commands;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import discord_bot.utils.database.Database;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class Report extends Commands {

    public Report(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        
        String message = event.getOption(Main.REPORT_OPTION_MESSAGE).getAsString();

        if ( ! Database.addReport(event.getUser().getName(), message)) {
            MessageSender.errorEvent(musicManager.getMessageSender(), "An error occurred while sending your report. Please try again later.", event);
            return;
        }

        MessageSender.infoEvent(musicManager.getMessageSender(), "Your report has been sent to the developers. Thank you for your feedback.", event);    
    }

    @Override
    public void execute(ButtonInteractionEvent event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}
