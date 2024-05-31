package discord_bot.utils.message_event;

import java.util.List;

import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.ItemComponent;

public class MessageP implements MessageEvent {
    
    public MessageCreateAction message;

    public MessageP(MessageCreateAction message) {
        this.message = message;
    }

    @Override
    public MessageEvent sendMessageEmbeds(MessageEmbed... embeds) {
        message.addEmbeds(embeds);
        return this;
    }

    @Override
    public MessageEvent addActionRow(List<ItemComponent> items) {
        message.addActionRow(items);
        return this;
    }

    @Override
    public Message complete() {
        return message.complete();
    }
}
