package discord_bot.embded;

import java.awt.Color;
import java.util.List;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import discord_bot.enumerate.ButtonEnum;
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

        eb.setAuthor("PLAY", "http://erreip.ciliste.games/shesh/", GIF_SONG);

        eb.setTitle(track.title);
        eb.setUrl(track.uri);

        eb.addField("Author", track.author, true);
        eb.addField("Duration", getTimestamp(track.length), true);
        
        eb.setThumbnail(getThumbnail(track.identifier));

        return eb.build();
    } 

    public static MessageEmbed createEmbdedResponse(String response) {

        EmbedBuilder eb = new EmbedBuilder();
        setColor(eb);

        eb.setAuthor("INFO", "http://erreip.ciliste.games/shesh/", GIF_SONG);

        eb.setDescription(response);

        return eb.build();
    }

    public static MessageEmbed createEmbdedError(String error) {

        EmbedBuilder eb = new EmbedBuilder();
        setColor(eb);

        eb.setAuthor("INFO", "http://erreip.ciliste.games/shesh/", GIF_SONG);

        eb.setDescription("ERREUR : " + error);

        return eb.build();
    }

    public static MessageEmbed createEmbdedQueue(List<AudioTrack> queue) {

        EmbedBuilder eb = new EmbedBuilder();
        setColor(eb);

        eb.setAuthor("QUEUE", "http://erreip.ciliste.games/shesh/", GIF_SONG);

        String description = "";
        for (AudioTrack track : queue) {

            description += "➡️ " + track.getInfo().title + "\n";
        }

        eb.setDescription(description);
        
        return eb.build();
    }

    public static MessageEmbed createEmbdedHelp() {
    
        EmbedBuilder eb = new EmbedBuilder();
        setColor(eb);

        eb.setAuthor("HELP", "http://erreip.ciliste.games/shesh/", GIF_SONG);

        eb.setDescription(
            " ➡️ play [query] : play a song\n " +
            ButtonEnum.STOP.label + " stop : stop the music\n " +
            ButtonEnum.SKIP.label + " skip : skip the current song\n " +
            ButtonEnum.LOOP.label + " loop : loop the current song\n " +
            " ➡️ queue : display the queue\n " +
            ButtonEnum.LAST.label + " last : display the last song\n " +
            ButtonEnum.PAUSE.label + " pause : pause the music\n " +
            " ➡️ clear_queue : clear the queue\n "
            );

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

        long eSeconds = time / 60;
        long eMinutes = time / 3600;

        long seconds = time % 60;
        long minutes = eSeconds % 60;
        long hours = eMinutes % 60;

        if ( hours == 0 ) return String.format("%d:%02d", minutes, seconds);

        return String.format("%d:%02d:%02d",hours, minutes, seconds);
    }
}
