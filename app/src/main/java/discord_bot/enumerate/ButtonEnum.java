package discord_bot.enumerate;

import java.util.ArrayList;
import java.util.List;

import org.checkerframework.checker.units.qual.t;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord_bot.database.PlaylistDatabase;
import discord_bot.jda.SelectListener;
import discord_bot.model.dao.Playlist;
import discord_bot.utils.ButtonCustom;
import discord_bot.utils.message_event.MessageEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

public class ButtonEnum {

    public static List<ItemComponent> items() {

        List<ItemComponent> items = new ArrayList<>();

        for (CommandsEnum button : CommandsEnum.values()) {

            items.add(Button.success(button.label + "", button.buttonlabel));
        }

        return items;
    }

    public static List<ItemComponent> getPlayButton() {

        List<ItemComponent> items = new ArrayList<>();

        items.add(Button.success(CommandsEnum.PAUSE.label, CommandsEnum.PAUSE.buttonlabel));
        items.add(Button.success(CommandsEnum.SKIP.label, CommandsEnum.SKIP.buttonlabel));
        items.add(Button.success(CommandsEnum.SHUFFLE.label, CommandsEnum.SHUFFLE.buttonlabel));
        items.add(Button.success(CommandsEnum.LOOP.label, CommandsEnum.LOOP.buttonlabel));
        items.add(Button.success(CommandsEnum.LAST.label, CommandsEnum.LAST.buttonlabel));
        items.add(Button.success(CommandsEnum.QUEUE.label, CommandsEnum.QUEUE.buttonlabel));
        items.add(Button.success(CommandsEnum.CLEAR_QUEUE.label, CommandsEnum.CLEAR_QUEUE.buttonlabel));
        items.add(Button.success(CommandsEnum.STOP.label, CommandsEnum.STOP.buttonlabel));
        items.add(Button.success(CommandsEnum.FROM_QUEUE.label, CommandsEnum.FROM_QUEUE.buttonlabel));

        return items;
    }

    public static List<ItemComponent> getPlayButton(List<AudioTrack> recommandations) {

        List<ItemComponent> items = getPlayButton();

        StringSelectMenu.Builder selectMenu = StringSelectMenu.create(SelectListener.PREFIX + CommandsEnum.PLAY.label)
            .setPlaceholder("Add to queue");
        
        List<String> tracks = new ArrayList<>();
        for ( AudioTrack track : recommandations ) {

            if ( tracks.contains(track.getInfo().title)) { continue; }

            selectMenu.addOptions(SelectOption.of(track.getInfo().title, track.getInfo().title)
                .withEmoji(Emoji.fromUnicode("U+1F3B5"))
                .withDescription(track.getInfo().author)
            );
            tracks.add(track.getInfo().title);

            if ( selectMenu.getOptions().size() >= SelectMenu.OPTIONS_MAX_AMOUNT) { break; }
        }

        items.add(selectMenu.build());

        return items;
    }

    public static void setButtonPlay(MessageEvent messageEvent) {

        List<List<ItemComponent>> items = new ArrayList<>();

        items.add(getPlayButton());
        items.add(getHelpButton());

        setButton(messageEvent, items);
    }

    public static void setButtonPlay(MessageEvent messageEvent, List<AudioTrack> recommandations) {

        List<List<ItemComponent>> items = new ArrayList<>();

        items.add(getPlayButton(recommandations));
        items.add(getHelpButton());

        setButton(messageEvent, items);
    }
    
    public static List<ItemComponent> getHelpButton() {

        List<ItemComponent> items = new ArrayList<>();

        items.add(Button.primary(CommandsEnum.HELP.label, CommandsEnum.HELP.buttonlabel));
        items.add(Button.primary(CommandsEnum.PLAYLISTS.label, CommandsEnum.PLAYLISTS.buttonlabel));

        return items;
    }
    
    public static void setButtonHelp(MessageEvent messageEvent) {

        List<List<ItemComponent>> items = new ArrayList<>();

        items.add(getHelpButton());

        setButton(messageEvent, items);
    }

    public static List<ItemComponent> getPlaylistsButton(Guild guild) {

        List<ItemComponent> items = new ArrayList<>();

        items.add(Button.success(CommandsEnum.RECORD.label, CommandsEnum.RECORD.buttonlabel));
        
        StringSelectMenu.Builder selectMenu = StringSelectMenu.create(SelectListener.PREFIX + CommandsEnum.PLAYLISTS.label)
            .setPlaceholder("Select a playlist");

        PlaylistDatabase playlistDB = PlaylistDatabase.getInstance();

        for ( Playlist playlist : playlistDB.getPlaylistsFromGuild(guild.getIdLong())) {

            Member user =  guild.getMemberById(playlist.ownerId);

            selectMenu.addOptions(SelectOption.of(playlist.name, playlist.name)
                .withEmoji(Emoji.fromUnicode("U+1F3B5"))
                .withDescription(user.getNickname())
            );
        }
        
        items.add(selectMenu.build());

        return items;
    }

    public static void setButtonPlaylist(MessageEvent messageEvent, Guild guild) {

        List<List<ItemComponent>> items = new ArrayList<>();

        items.add(getPlaylistsButton(guild));
        items.add(getHelpButton());

        setButton(messageEvent, items);
    }

    public static List<ItemComponent> getOnPlaylistButton(String playlistName) {

        List<ItemComponent> items = new ArrayList<>();

        ButtonCustom button = new ButtonCustom(ButtonStyle.SUCCESS, CommandsEnum.LOAD.label, playlistName, CommandsEnum.LOAD.buttonlabel);

        items.add(button.get());

        return items;
    }

    public static void setButtonOnPlaylist(MessageEvent messageEvent, String name) {

        List<List<ItemComponent>> items = new ArrayList<>();

        items.add(getOnPlaylistButton(name));
        items.add(getHelpButton());

        setButton(messageEvent, items);
    }


    private static void setButton(MessageEvent messageEvent, List<List<ItemComponent>> items) {

        for (List<ItemComponent> item : items) {
            
            List<ItemComponent> tempLst = new ArrayList<>();

            for (ItemComponent itemComponent : item) {

                if ( tempLst.size() > 0 && itemComponent.getType() != tempLst.getLast().getType()) {
                    messageEvent.addActionRow(tempLst);
                    tempLst.clear();
                }

                tempLst.add(itemComponent);

                if (tempLst.size() >= 5) {
                    messageEvent.addActionRow(tempLst);
                    tempLst.clear();
                }
            }

            if (tempLst.size() > 0) { messageEvent.addActionRow(tempLst); }
        }
    }
}
