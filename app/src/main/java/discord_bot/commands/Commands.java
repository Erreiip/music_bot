package discord_bot.commands;

import discord_bot.commands.audio.playlists.Add;
import discord_bot.commands.audio.playlists.Create;
import discord_bot.commands.audio.playlists.FromQueue;
import discord_bot.commands.audio.playlists.Load;
import discord_bot.commands.audio.playlists.Playlists;
import discord_bot.commands.audio.playlists.Record;
import discord_bot.commands.audio.playlists.Remove;
import discord_bot.commands.audio.playlists.Save;
import discord_bot.commands.audio.playlists.See;
import discord_bot.commands.audio.track.ClearQueue;
import discord_bot.commands.audio.track.Last;
import discord_bot.commands.audio.track.Loop;
import discord_bot.commands.audio.track.Pause;
import discord_bot.commands.audio.track.Play;
import discord_bot.commands.audio.track.Queue;
import discord_bot.commands.audio.track.Shuffle;
import discord_bot.commands.audio.track.Skip;
import discord_bot.commands.audio.track.Stop;
import discord_bot.commands.interfaces.ButtonCommands;
import discord_bot.commands.interfaces.ModalCommands;
import discord_bot.commands.interfaces.SelectedCommands;
import discord_bot.commands.other.Help;
import discord_bot.commands.other.Report;
import discord_bot.commands.other.SeeReport;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

public abstract class Commands implements ButtonCommands, ModalCommands, SelectedCommands {

    public static final int HELP = 16;

    public static final int PLAY = 0;
    public static final int SKIP = 1;
    public static final int PAUSE = 2;
    public static final int CLEAR_QUEUE = 3;
    public static final int LAST = 4;
    public static final int QUEUE = 5;
    public static final int STOP = 6;
    public static final int LOOP = 7;
    
    //Playlist commands
    public static final int ADD = 8;
    public static final int CREATE = 9;
    public static final int LOAD = 10;
    public static final int REMOVE = 11;
    public static final int SAVE = 12;
    public static final int SEE = 13;
    public static final int RECORD = 14;
    public static final int PLAYLISTS = 15;

    public static final int SHUFFLE = 17;
    public static final int REPORT = 18;
    public static final int SEE_REPORT = 19;

    public static final int FROM_QUEUE = 20;



    protected GuildMusicManager musicManager;

    public Commands(GuildMusicManager musicManager) {
        this.musicManager = musicManager;
    }
    
    public final void execute(SlashCommandInteractionEvent event) {

        if ( musicManager.isInSameChannel(event.getMember())) {
            this.executeCommands(event);
            return;
        }

        MessageSender.errorEvent(musicManager.getMessageSender(), "You must be in the same channel as the bot to use this command", event);
    }

    @Override
    public final void execute(ButtonInteractionEvent event) {

        if ( musicManager.isInSameChannel(event.getMember())) {
            this.executeCommands(event);
            return;
        }

        MessageSender.errorEvent(musicManager.getMessageSender(), "You must be in the same channel as the bot to use this command", event);
    }

    @Override
    public final void execute(ModalInteractionEvent event) {
        
        if ( musicManager.isInSameChannel(event.getMember())) {
            this.executeCommands(event);
            return;
        }

        MessageSender.errorEvent(musicManager.getMessageSender(), "You must be in the same channel as the bot to use this command", event);
    }

    @Override
    public final void execute(StringSelectInteractionEvent event) {
        
        if ( musicManager.isInSameChannel(event.getMember())) {
            this.executeCommands(event);
            return;
        }

        MessageSender.errorEvent(musicManager.getMessageSender(), "You must be in the same channel as the bot to use this command", event);
    }

    public void executeCommands(SlashCommandInteractionEvent event) {}

    public void executeCommands(ButtonInteractionEvent event) {}

    public void executeCommands(ModalInteractionEvent event) {}

    public void executeCommands(StringSelectInteractionEvent event) {}


    public static Commands[] getCommands(GuildMusicManager musicManager) {

        return new Commands[] {
            new Play(musicManager),
            new Skip(musicManager),
            new Pause(musicManager),
            new ClearQueue(musicManager),
            new Last(musicManager),
            new Queue(musicManager),
            new Stop(musicManager),
            new Loop(musicManager),
            //Playlist commands
            new Add(musicManager),
            new Create(musicManager),
            new Load(musicManager),
            new Remove(musicManager),
            new Save(musicManager),
            new See(musicManager),
            new Record(musicManager),
            new Playlists(musicManager),
            
            new Help(musicManager),

            new Shuffle(musicManager),
            
            new Report(musicManager),
            new SeeReport(musicManager),
            new FromQueue(musicManager)
        };
    }
}
