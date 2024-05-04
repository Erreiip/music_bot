package discord_bot;

import discord_bot.commands.Commands;
import discord_bot.commands.track.Skip;
import discord_bot.enumerate.ButtonEnum;
import net.dv8tion.jda.api.entities.Invite.Guild;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ButtonListener extends ListenerAdapter {

    private Kawaine kawaine;

    public ButtonListener(Kawaine kawaine) {
     
        this.kawaine = kawaine;
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        
        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());

        event.deferEdit().queue();

        if ( Integer.valueOf(event.getButton().getId()) == ButtonEnum.SKIP.id) {

            musicManager.getCommand(Commands.SKIP).execute(event, kawaine);
        }

        if ( Integer.valueOf(event.getButton().getId()) == ButtonEnum.PAUSE.id) {

            musicManager.getCommand(Commands.PAUSE).execute(event, kawaine);
        }

        if ( Integer.valueOf(event.getButton().getId()) == ButtonEnum.LOOP.id) {

            musicManager.getCommand(Commands.LOOP).execute(event, kawaine);
        }
    }
    
}
