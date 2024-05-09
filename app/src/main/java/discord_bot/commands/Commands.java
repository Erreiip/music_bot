package discord_bot.commands;

import discord_bot.commands.playlists.Add;
import discord_bot.commands.playlists.Create;
import discord_bot.commands.playlists.Load;
import discord_bot.commands.playlists.Playlists;
import discord_bot.commands.playlists.Record;
import discord_bot.commands.playlists.Remove;
import discord_bot.commands.playlists.Save;
import discord_bot.commands.playlists.See;
import discord_bot.commands.track.ClearQueue;
import discord_bot.commands.track.Last;
import discord_bot.commands.track.Loop;
import discord_bot.commands.track.Pause;
import discord_bot.commands.track.Play;
import discord_bot.commands.track.Queue;
import discord_bot.commands.track.Skip;
import discord_bot.commands.track.Stop;
import discord_bot.jda_listener.Kawaine;
import discord_bot.jda_listener.model.TrackScheduler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public abstract class Commands implements ButtonCommands {

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

    TrackScheduler scheduler;

    public Commands(TrackScheduler scheduler) {
        this.scheduler = scheduler;
    }
    
    public abstract void execute(SlashCommandInteractionEvent event, Kawaine kawaine);

    public abstract void execute(ButtonInteractionEvent event, Kawaine kawaine);

    public static Commands[] getCommands(TrackScheduler scheduler) {

        return new Commands[] {
            new Play(scheduler),
            new Skip(scheduler),
            new Pause(scheduler),
            new ClearQueue(scheduler),
            new Last(scheduler),
            new Queue(scheduler),
            new Stop(scheduler),
            new Loop(scheduler),
            //Playlist commands
            new Add(scheduler),
            new Create(scheduler),
            new Load(scheduler),
            new Remove(scheduler),
            new Save(scheduler),
            new See(scheduler),
            new Record(scheduler),
            new Playlists(scheduler),
            
            new Help(scheduler)
        };
    }
}
