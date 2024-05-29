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
import discord_bot.jda.Kawaine;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.TrackScheduler;
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

    protected GuildMusicManager musicManager;

    public Commands(GuildMusicManager musicManager) {
        this.musicManager = musicManager;
    }
    
    public abstract void execute(SlashCommandInteractionEvent event);

    public abstract void execute(ButtonInteractionEvent event);

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
            
            new Help(musicManager)
        };
    }
}
