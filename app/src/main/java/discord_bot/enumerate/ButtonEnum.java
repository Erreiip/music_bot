package discord_bot.enumerate;

import java.util.ArrayList;
import java.util.List;

import discord_bot.utils.message_event.MessageEvent;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public enum ButtonEnum {

    ADD_PLAYLIST(0, "➕"),
    SKIP(1, "⏭️"),
    PAUSE(2, "⏯️"),
    LOOP(3, "🔁"),
    LAST(4, "⏮️➕"),
    PLAYLISTS(5, "🎶"),
    HELP(6, "🆘"),
    CLEAR_QUEUE(7, "🆑"),
    STOP(8, "🛑"),
    SHUFFLE(9, "🔀"),
    RECORD(10, "📼"),
    QUEUE(11, "📜");

    public final int id;
    public final String label;

    private ButtonEnum(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public static List<ItemComponent> items() {

        List<ItemComponent> items = new ArrayList<>();

        for (ButtonEnum button : ButtonEnum.values()) {

            items.add(Button.success(button.id + "", button.label));
        }

        return items;
    }

    public static List<ItemComponent> getPlayButton() {

        List<ItemComponent> items = new ArrayList<>();

        items.add(Button.success(PAUSE.id + "", PAUSE.label));
        items.add(Button.success(SKIP.id + "", SKIP.label));
        items.add(Button.success(SHUFFLE.id + "", SHUFFLE.label));
        items.add(Button.success(LOOP.id + "", LOOP.label));
        items.add(Button.success(LAST.id + "", LAST.label));
        items.add(Button.success(QUEUE.id + "", QUEUE.label));
        items.add(Button.success(CLEAR_QUEUE.id + "", CLEAR_QUEUE.label));
        items.add(Button.success(STOP.id + "", STOP.label));

        return items;
    }

    public static void setButtonPlay(MessageEvent messageEvent) {

        List<List<ItemComponent>> items = new ArrayList<>();

        items.add(getPlayButton());
        items.add(getHelpButton());

        setButton(messageEvent, items);
    }
    
    public static List<ItemComponent> getHelpButton() {

        List<ItemComponent> items = new ArrayList<>();

        items.add(Button.primary(HELP.id + "", HELP.label));
        items.add(Button.primary(PLAYLISTS.id + "", PLAYLISTS.label));

        return items;
    }
    
    public static void setButtonHelp(MessageEvent messageEvent) {

        List<List<ItemComponent>> items = new ArrayList<>();

        items.add(getHelpButton());

        setButton(messageEvent, items);
    }

    public static List<ItemComponent> getPlaylistsButton() {

        List<ItemComponent> items = new ArrayList<>();

        items.add(Button.success(HELP.id + "", HELP.label));
        items.add(Button.success(RECORD.id + "", RECORD.label));

        return items;
    }

    private static void setButton(MessageEvent messageEvent, List<List<ItemComponent>> items) {

        for (List<ItemComponent> item : items) {
            
            List<ItemComponent> tempLst = new ArrayList<>();

            for (ItemComponent itemComponent : item) {
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
