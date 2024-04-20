package discord_bot;

import java.io.IOException;

import discord_bot.common.Couple;
import discord_bot.youtube.ApiYoutube;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Widget.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class Kawaine extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (!event.isFromGuild()) return;
        
        Guild guild = event.getGuild();

        if (guild != event.getMember().getGuild())
            System.out.println("shesh");

        if (event.getName().equals("")) {

            Couple<String, String> video = this.queryByName(event.getOption("name").getAsString());
            String URL = video.second;
            String title = video.first;

            this.joinChannel(event.getMember());
        }
    }
    
    private AudioManager joinChannel(Member member) {

        AudioChannel memberChannel = member.getVoiceState().getChannel();

        if (memberChannel != null) {
            fatalMessages("You are not in a voice channel.");
        }

        AudioManager audioManager = member.getGuild().getAudioManager();
        audioManager.openAudioConnection(memberChannel);

        return audioManager;
    }

    private void playSong(Member member) {

        AudioManager audioManager = member.getGuild().getAudioManager();
    }
    
    private Couple<String, String> queryByName(String name) {

        try {
            return ApiYoutube.search(name);
        } catch (IOException e) {
            fatalMessages("An error occurred while searching for the video.");
            return null;
        }
    }
    
    private void fatalMessages(String message) {

        System.out.println(message);
    }
    
}
