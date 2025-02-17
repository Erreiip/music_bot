package discord_bot.jda;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import javax.annotation.Nonnull;

import discord_bot.model.GuildMusicManager;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.guild.voice.*;

public class LeaveListener extends ListenerAdapter {

    private Kawaine kawaine;

    public LeaveListener(Kawaine kawaine) {
        super();

        this.kawaine = kawaine;
    }

    @Override
    public void onGuildVoiceUpdate(@Nonnull GuildVoiceUpdateEvent event) {

        if (event.getChannelLeft() != null && event.getChannelLeft().getMembers().size() == 1) {

            GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());
            AudioManager audioManager = musicManager.getAudioManager();

            if ( audioManager == null ) {
                return;
            }

            AudioChannelUnion channel = audioManager.getConnectedChannel();
            
            if ( channel == event.getChannelLeft() ) {
                musicManager.disconnect();
            }
        }
    }
}
