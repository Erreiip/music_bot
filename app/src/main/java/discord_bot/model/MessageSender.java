package discord_bot.model;

import java.util.ArrayList;
import java.util.List;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import discord_bot.enumerate.ButtonEnum;
import discord_bot.utils.message_event.MessageEvent;
import discord_bot.utils.message_event.MessageP;
import discord_bot.utils.message_event.WebHookMessageP;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public class MessageSender {

    public enum MessageType {
        PLAY,
        QUEUE,
        INFO,
        ERROR,
        HELP
    }

    private Message lPlayevent;
    private Message lQueueEvent;
    private Message lInfoEvent;
    private Message lErrorEvent;
    private Message lHelpEvent;

    private MessageChannelUnion lastMessageChannel;
    private MessageType lastSentMessageType;

    private List<String> idAlreadyResponded;

    public MessageSender() {

        this.lPlayevent = null;
        this.lQueueEvent = null;
        this.lInfoEvent = null;
        this.lErrorEvent = null;
        this.lHelpEvent = null;

        this.lastMessageChannel = null;
        this.lastSentMessageType = null;

        this.idAlreadyResponded = new ArrayList<>();
    }

    private void removeEvent(Message message) {
        
        if (message == null)
            return;

        try { message.delete().complete(); } catch (Exception e) { e.printStackTrace(); }
    }

    private void removeAllEvents() {

        this.removeEvent(lPlayevent);
        this.removeEvent(lQueueEvent);
        this.removeEvent(lInfoEvent);
        this.removeEvent(lErrorEvent);
        this.removeEvent(lHelpEvent);

        lPlayevent = null;
        lQueueEvent = null;
        lInfoEvent = null;
        lErrorEvent = null;
        lHelpEvent = null;
    }
    
    public void disconnect() {
        
        this.removeEvent(lPlayevent);
        this.removeEvent(lQueueEvent);
        this.removeEvent(lErrorEvent);
        this.removeEvent(lHelpEvent);

        this.lastMessageChannel = null;
        this.lastSentMessageType = null;
        this.idAlreadyResponded.clear();
    }

    private synchronized void sendPlayEvent(MessageEvent message) {
        
        this.removeAllEvents();

        this.lPlayevent = message.complete();

        this.lastMessageChannel = lPlayevent.getChannel();
        this.lastSentMessageType = MessageType.PLAY;
    }

    private synchronized void sendQueueEvent(MessageEvent message) {
        
        this.removeEvent(lQueueEvent);

        this.lQueueEvent = message.complete();

        this.lastMessageChannel = lQueueEvent.getChannel();
        this.lastSentMessageType = MessageType.QUEUE;
    }

    private synchronized void sendInfoEvent(MessageEvent message) {

        this.removeEvent(lInfoEvent);

        lInfoEvent = message.complete();

        lastMessageChannel = lInfoEvent.getChannel();
        this.lastSentMessageType = MessageType.INFO;
    }

    private synchronized void sendErrorEvent(MessageEvent message) {
        
        this.removeEvent(lErrorEvent);

        lErrorEvent = message.complete();

        lastMessageChannel = lErrorEvent.getChannel();
        this.lastSentMessageType = MessageType.ERROR;
    }

    private synchronized void sendHelpEvent(MessageEvent message) { 
        
        this.removeEvent(lHelpEvent);

        lHelpEvent = message.complete();

        lastMessageChannel = lHelpEvent.getChannel();
        this.lastSentMessageType = MessageType.HELP;
    }

    
    /*
     * Static methods
     * */
    public static void playEvent(MessageSender sender, AudioTrackInfo info, boolean looped, IDeferrableCallback event) {

        MessageEvent messageEvent;

        if (event != null) {
            messageEvent = new WebHookMessageP( event.getHook().sendMessageEmbeds(
                MusicEmbded.createEmbded(info, looped)  
            ));
        } else {
            messageEvent = new MessageP( sender.lastMessageChannel.sendMessageEmbeds(
                MusicEmbded.createEmbded(info, looped)
            ));
        }
        
        ButtonEnum.setButtonPlay(messageEvent);

        sender.sendPlayEvent(messageEvent);
    }

    public static void queueEvent(MessageSender sender, List<AudioTrack> info, IDeferrableCallback event) {

        MessageEvent messageEvent;

        if (event != null) {

            if ( sender.idAlreadyResponded.contains(event.getId()) && sender.lastSentMessageType == MessageType.QUEUE) {
                sender.editLastQueueEvent(info);
                return;
            }
            
            messageEvent = new WebHookMessageP( event.getHook().sendMessageEmbeds(
                MusicEmbded.createEmbdedQueue(info)     
            ));
            
            sender.idAlreadyResponded.add(event.getId());

        } else {
            messageEvent = new MessageP( sender.lastMessageChannel.sendMessageEmbeds(
                MusicEmbded.createEmbdedQueue(info)
            ));
        }

        ButtonEnum.setButtonPlay(messageEvent);

        sender.sendQueueEvent(messageEvent);
    }

    public static void infoEvent(MessageSender sender, String message, IDeferrableCallback event) {

        MessageEvent messageEvent;

        if (event != null) {
            messageEvent = new WebHookMessageP( event.getHook().sendMessageEmbeds(
                MusicEmbded.createEmbdedResponse(message)  
            ));
        } else {
            messageEvent = new MessageP( sender.lastMessageChannel.sendMessageEmbeds(
                MusicEmbded.createEmbdedResponse(message)
            ));
        }

        ButtonEnum.setButtonHelp(messageEvent);

        sender.sendInfoEvent(messageEvent);
    }

    public static void errorEvent(MessageSender sender, String message, IDeferrableCallback event) {

        MessageEvent messageEvent;

        if (event != null) {
            messageEvent = new WebHookMessageP( event.getHook().sendMessageEmbeds(
                MusicEmbded.createEmbdedError(message) 
            ));
        } else {
            messageEvent = new MessageP( sender.lastMessageChannel.sendMessageEmbeds(
                MusicEmbded.createEmbdedError(message)
            ));
        }

        ButtonEnum.setButtonHelp(messageEvent);

        sender.sendErrorEvent(messageEvent);
    }

    public static void helpEvent(MessageSender sender, IDeferrableCallback event) {

        MessageEvent messageEvent;

        if (event != null) {
            messageEvent = new WebHookMessageP( event.getHook().sendMessageEmbeds(
                MusicEmbded.createEmbdedHelp() 
            ));
        } else {
            messageEvent = new MessageP( sender.lastMessageChannel.sendMessageEmbeds(
                MusicEmbded.createEmbdedHelp()
            ));
        }

        ButtonEnum.setButtonHelp(messageEvent);

        sender.sendHelpEvent(messageEvent);
    }

    public static void playlistEvent(MessageSender sender, String message, IDeferrableCallback event, Guild guild) {

        MessageEvent messageEvent;

        if (event != null) {
            messageEvent = new WebHookMessageP( event.getHook().sendMessageEmbeds(
                MusicEmbded.createEmbdedResponse(message)  
            ));
        } else {
            messageEvent = new MessageP( sender.lastMessageChannel.sendMessageEmbeds(
                MusicEmbded.createEmbdedResponse(message)
            ));
        }

        //ButtonEnum.setButtonPlaylist(messageEvent, guild);
        ButtonEnum.setButtonOnPlaylist(messageEvent);

        sender.sendInfoEvent(messageEvent);
    }
    
    /*
     * Edit
     * */

    public void editLastPlayEvent(AudioTrackInfo info, boolean looped) {

        if (lPlayevent == null)
            return;

        lPlayevent.editMessageEmbeds(MusicEmbded.createEmbded(info, looped)).queue();
    }

    public void editLastQueueEvent(List<AudioTrack> info) {

        if (lQueueEvent == null)
            return;

        lQueueEvent.editMessageEmbeds(MusicEmbded.createEmbdedQueue(info)).queue();
    }
}
