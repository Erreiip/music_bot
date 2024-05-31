package discord_bot.utils.message_event;

import java.util.List;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.requests.restaction.WebhookMessageCreateAction;

public class WebHookMessageP implements MessageEvent {

    public WebhookMessageCreateAction<Message> message;

    public WebHookMessageP(WebhookMessageCreateAction<Message> message) {
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

