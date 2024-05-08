package discord_bot;

import java.util.HashMap;
import java.util.Map;

import discord_bot.commands.Commands;
import discord_bot.enumerate.ButtonEnum;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ButtonListener extends ListenerAdapter {

    private final static Map<Integer, Integer> buttonMap = new HashMap<>();

    static {

        buttonMap.put(ButtonEnum.SKIP.id, Commands.SKIP);
        buttonMap.put(ButtonEnum.PAUSE.id, Commands.PAUSE);
        buttonMap.put(ButtonEnum.LOOP.id, Commands.LOOP);
        buttonMap.put(ButtonEnum.LAST.id, Commands.LAST);
    }

    private Kawaine kawaine;

    public ButtonListener(Kawaine kawaine) {
     
        this.kawaine = kawaine;
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        
        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());

        event.deferEdit().queue();

        Integer id = Integer.valueOf(event.getButton().getId());

        Commands command = musicManager.getCommand(buttonMap.get(id));

        command.execute(event, kawaine);
    }
    
}
