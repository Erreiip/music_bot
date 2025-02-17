package discord_bot.commands.other;

import java.util.ArrayList;
import java.util.List;

import discord_bot.commands.Commands;
import discord_bot.database.Database;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import discord_bot.model.UtilsEmbed;
import discord_bot.model.dao.Report;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class SeeReport extends Commands {

    public SeeReport(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void executeCommands(SlashCommandInteractionEvent event) {

        List<Report> reports = null;
        /*
        try { reports = Database.getInstance().getReports(); } 
        catch (Exception e) {
            e.printStackTrace();
            return;
        }

        List<MessageEmbed> embeds = new ArrayList<>();

        for (Report report : reports) {

            embeds.add(UtilsEmbed.createReportEmbed(report));
        }

        if (embeds.isEmpty()) {
            MessageSender.errorEvent(musicManager.getMessageSender(), "No reports found", event);
            return;
        }
        
        event.getHook().sendMessageEmbeds(embeds).queue();
        */
    }
}
