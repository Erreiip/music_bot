package discord_bot.embded;

import java.awt.Color;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class MusicEmbded {

    public final static String THUMBNAIL = "https://img.youtube.com/vi/";
    public final static String THUMBNAIL_END = "/maxresdefault.jpg";

    public final static String GIF_SONG = "https://i0.wp.com/www.cbvinylrecordart.com/blog/wp-content/uploads/2015/06/donuts.gif"; 

    public final static Color COLOR = Color.RED;
    
    public static MessageEmbed createEmbded() {

        return new EmbedBuilder()
            .setTitle("Music Bot")
            .setColor(Color.BLUE)
            .setDescription("This is a music bot")
            .addField("Commands", "play, stop, skip, loop, queue, last, pause, clear_queue", false)
            .build();
    } 

    public static MessageEmbed createEmbded(AudioTrackInfo track) {

        EmbedBuilder eb  = new EmbedBuilder();
        setColor(eb);

        eb.setTitle(track.title);
        eb.setUrl(track.uri);

        eb.addField("Author", track.author, true);
        eb.addField("Duration", getTimestamp(track.length), true);
        
        eb.setThumbnail(GIF_SONG);
        eb.setImage(getThumbnail(track.identifier));

        return eb.build();
    } 

    private static String getThumbnail(String identifier) {

        return THUMBNAIL + identifier + THUMBNAIL_END;
    }
    
    private static EmbedBuilder setColor(EmbedBuilder eb) {

        return eb.setColor(COLOR);
    }

    private static String getTimestamp(long timestamp) {

        long time = timestamp / 1000;

        long seconds = time % 60;
        long minutes = Math.floorDiv(time, 60);
        long hours = Math.floorDiv(minutes, 60);

        if ( hours == 0 ) return String.format("%d:%02d", minutes, seconds);

        return String.format("%d:%02d:%02d",hours, minutes, seconds);
    }
}
