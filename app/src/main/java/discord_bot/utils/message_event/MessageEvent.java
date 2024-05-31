package discord_bot.utils.message_event;

import java.util.List;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.ItemComponent;

public interface MessageEvent {
    

    public MessageEvent sendMessageEmbeds(MessageEmbed... embeds);

    public MessageEvent addActionRow(List<ItemComponent> items);

    public Message complete();
}
