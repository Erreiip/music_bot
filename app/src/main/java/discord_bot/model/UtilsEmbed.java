package discord_bot.model;

import java.awt.Color;
import java.util.List;

import discord_bot.model.dao.Report;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class UtilsEmbed {

    public final static String GIF = "https://cdn.dribbble.com/users/3968/screenshots/2240185/integrations-animated.gif"; 

    public final static Color COLOR = Color.GREEN;

    public static MessageEmbed createReportEmbed(Report report) {

        EmbedBuilder eb = new EmbedBuilder();
        setColor(eb);

        eb.setAuthor("REPORT", "http://erreip.ciliste.games/shesh/", GIF);

        eb.addField("Id", report.getId() + "", true);
        eb.addField("Username", report.getUsername(), true);
        eb.addField("For", report.getReport(), false);

        return eb.build();
    }

    private static EmbedBuilder setColor(EmbedBuilder eb) {

        return eb.setColor(COLOR);
    }
}
