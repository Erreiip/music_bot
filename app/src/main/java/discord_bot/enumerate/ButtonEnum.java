package discord_bot.enumerate;

import java.util.ArrayList;
import java.util.List;

import discord_bot.utils.message_event.MessageEvent;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class ButtonEnum {

    public static List<ItemComponent> items() {

        List<ItemComponent> items = new ArrayList<>();

        for (CommandsEnum button : CommandsEnum.values()) {

            items.add(Button.success(button.buttonId + "", button.buttonlabel));
        }

        return items;
    }

    public static List<ItemComponent> getPlayButton() {

        List<ItemComponent> items = new ArrayList<>();

        items.add(Button.success(CommandsEnum.PAUSE.buttonId + "", CommandsEnum.PAUSE.buttonlabel));
        items.add(Button.success(CommandsEnum.SKIP.buttonId + "", CommandsEnum.SKIP.buttonlabel));
        items.add(Button.success(CommandsEnum.SHUFFLE.buttonId + "", CommandsEnum.SHUFFLE.buttonlabel));
        items.add(Button.success(CommandsEnum.LOOP.buttonId + "", CommandsEnum.LOOP.buttonlabel));
        items.add(Button.success(CommandsEnum.LAST.buttonId + "", CommandsEnum.LAST.buttonlabel));
        items.add(Button.success(CommandsEnum.QUEUE.buttonId + "", CommandsEnum.QUEUE.buttonlabel));
        items.add(Button.success(CommandsEnum.CLEAR_QUEUE.buttonId + "", CommandsEnum.CLEAR_QUEUE.buttonlabel));
        items.add(Button.success(CommandsEnum.STOP.buttonId + "", CommandsEnum.STOP.buttonlabel));

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

        items.add(Button.primary(CommandsEnum.HELP.buttonId + "", CommandsEnum.HELP.buttonlabel));
        items.add(Button.primary(CommandsEnum.PLAYLISTS.buttonId + "", CommandsEnum.PLAYLISTS.buttonlabel));

        return items;
    }
    
    public static void setButtonHelp(MessageEvent messageEvent) {

        List<List<ItemComponent>> items = new ArrayList<>();

        items.add(getHelpButton());

        setButton(messageEvent, items);
    }

    public static List<ItemComponent> getPlaylistsButton() {

        List<ItemComponent> items = new ArrayList<>();

        items.add(Button.success(CommandsEnum.HELP.buttonId + "", CommandsEnum.HELP.buttonlabel));
        items.add(Button.success(CommandsEnum.RECORD.buttonId + "", CommandsEnum.RECORD.buttonlabel));

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
