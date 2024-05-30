package discord_bot.model;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import discord_bot.enumerate.ButtonEnum;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.requests.restaction.WebhookMessageCreateAction;

public class MessageSender {

    private Message lPlayevent;
    private Message lQueueEvent;
    private Message lInfoEvent;
    private Message lErrorEvent;
    private Message lHelpEvent;

    private MessageChannelUnion lastMessageChannel;

    public MessageSender() {

        this.lPlayevent = null;
        this.lQueueEvent = null;
        this.lInfoEvent = null;
        this.lErrorEvent = null;
        this.lHelpEvent = null;
    }

    private void removeEvent(Message message) {
        
        if (message == null) return;

        message.delete().complete();
    }

    private void removeAllEvents() {
        
        this.removeEvent(lPlayevent);
        this.removeEvent(lQueueEvent);
        this.removeEvent(lInfoEvent);
        this.removeEvent(lErrorEvent);
        this.removeEvent(lHelpEvent);
    }

    private synchronized void sendPlayEvent(WebhookMessageCreateAction<Message> message) {
        
        this.removeAllEvents();

        lPlayevent = message.complete();

        lastMessageChannel = lPlayevent.getChannel();
    }

    private synchronized void sendQueueEvent(WebhookMessageCreateAction<Message> message) {
        
        this.removeEvent(lQueueEvent);

        lQueueEvent = message.complete();

        lastMessageChannel = lQueueEvent.getChannel();
    }

    private synchronized void sendInfoEvent(WebhookMessageCreateAction<Message> message) {
        
        this.removeEvent(lInfoEvent);

        lInfoEvent = message.complete();

        lastMessageChannel = lInfoEvent.getChannel();
    }

    private synchronized void sendErrorEvent(WebhookMessageCreateAction<Message> message) {
        
        this.removeEvent(lErrorEvent);

        lErrorEvent = message.complete();

        lastMessageChannel = lErrorEvent.getChannel();
    }

    private synchronized void sendHelpEvent(WebhookMessageCreateAction<Message> message) {
        
        this.removeEvent(lHelpEvent);

        lHelpEvent = message.complete();

        lastMessageChannel = lHelpEvent.getChannel();
    }

    /*
     * Pue la merde, incapable de faire une 
     * interface car jda pu la merde
     */
    private synchronized void sendPlayEvent(MessageCreateAction message) {
        
        this.removeAllEvents();

        lPlayevent = message.complete();

        lastMessageChannel = lPlayevent.getChannel();
    }

    private synchronized void sendQueueEvent(MessageCreateAction message) {
        
        this.removeEvent(lQueueEvent);

        lQueueEvent = message.complete();

        lastMessageChannel = lQueueEvent.getChannel();
    }

    private synchronized void sendInfoEvent(MessageCreateAction message) {
        
        this.removeEvent(lInfoEvent);

        lInfoEvent = message.complete();

        lastMessageChannel = lInfoEvent.getChannel();
    }

    private synchronized void sendErrorEvent(MessageCreateAction message) {
        
        this.removeEvent(lErrorEvent);

        lErrorEvent = message.complete();

        lastMessageChannel = lErrorEvent.getChannel();
    }

    private synchronized void sendHelpEvent(MessageCreateAction message) {
        
        this.removeEvent(lHelpEvent);

        lHelpEvent = message.complete();

        lastMessageChannel = lHelpEvent.getChannel();
    }

    
    /*
     * Static methods
     * */
    public static void playEvent(MessageSender sender, AudioTrackInfo info, IDeferrableCallback event) {

        WebhookMessageCreateAction<Message> msg = event.getHook().sendMessageEmbeds(
            MusicEmbded.createEmbded(info)  
        ).addActionRow(ButtonEnum.getPlayButton());

        sender.sendPlayEvent(msg);
    }

    public static void queueEvent(MessageSender sender, List<AudioTrack> info, IDeferrableCallback event) {

        WebhookMessageCreateAction<Message> msg = event.getHook().sendMessageEmbeds(
            MusicEmbded.createEmbdedQueue(info)
        ).addActionRow(ButtonEnum.getPlayButton()); // TODO CHANGER CA

        sender.sendQueueEvent(msg);
    }

    public static void infoEvent(MessageSender sender, String message, IDeferrableCallback event) {
        
        WebhookMessageCreateAction<Message> msg = event.getHook().sendMessageEmbeds(
            MusicEmbded.createEmbdedResponse(message)
        ).addActionRow(ButtonEnum.getHelpButton());

        sender.sendInfoEvent(msg);
    }

    public static void errorEvent(MessageSender sender, String message, IDeferrableCallback event) {

        WebhookMessageCreateAction<Message> msg = event.getHook().sendMessageEmbeds(
            MusicEmbded.createEmbdedError(message)
        ).addActionRow(ButtonEnum.getHelpButton());

        sender.sendErrorEvent(msg);
    }

    public static void helpEvent(MessageSender sender, IDeferrableCallback event) {

        MessageCreateAction msg = sender.lastMessageChannel.sendMessageEmbeds(
            MusicEmbded.createEmbdedHelp()
        ).addActionRow(ButtonEnum.getHelpButton());

        sender.sendHelpEvent(msg);
    }

    public static void playEvent(MessageSender sender, AudioTrackInfo info) {

        MessageCreateAction msg = sender.lastMessageChannel.sendMessageEmbeds(
            MusicEmbded.createEmbded(info)  
        ).addActionRow(ButtonEnum.getPlayButton());

        sender.sendPlayEvent(msg);
    }

    public static void queueEvent(MessageSender sender, List<AudioTrack> info) {

        MessageCreateAction msg = sender.lastMessageChannel.sendMessageEmbeds(
            MusicEmbded.createEmbdedQueue(info)
        ).addActionRow(ButtonEnum.getPlayButton()); // TODO CHANGER CA

        sender.sendQueueEvent(msg);
    }

    public static void infoEvent(MessageSender sender, String message) {
        
        MessageCreateAction msg = sender.lastMessageChannel.sendMessageEmbeds(
            MusicEmbded.createEmbdedResponse(message)
        ).addActionRow(ButtonEnum.getHelpButton());

        sender.sendInfoEvent(msg);
    }

    public static void errorEvent(MessageSender sender, String message) {

        MessageCreateAction msg = sender.lastMessageChannel.sendMessageEmbeds(
            MusicEmbded.createEmbdedError(message)
        ).addActionRow(ButtonEnum.getHelpButton());

        sender.sendErrorEvent(msg);
    }

    public static void helpEvent(MessageSender sender) {

        MessageCreateAction msg = sender.lastMessageChannel.sendMessageEmbeds(
            MusicEmbded.createEmbdedHelp()
        ).addActionRow(ButtonEnum.getHelpButton());

        sender.sendHelpEvent(msg);
    }
}
