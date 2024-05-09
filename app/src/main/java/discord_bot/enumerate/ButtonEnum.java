package discord_bot.enumerate;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public enum ButtonEnum {

    ADD_PLAYLIST(0, "➕"),
    SKIP(1, "⏭️"),
    PAUSE(2, "⏯️"),
    LOOP(3, "🔃"),
    LAST(4, "Ajouter le dernier"),
    PLAYLISTS(5, "🎶"),
    HELP(6, "❓"),
    CLEAR_QUEUE(7, "Vider la queue"),
    STOP(8, "🛑"),
    SHUFFLE(9, "🔀");

    public final int id;
    public final String label;

    private ButtonEnum(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public static List<ItemComponent> items() {
        
        List<ItemComponent> items = new ArrayList<>();
        
        for ( ButtonEnum button : ButtonEnum.values() ) {

            items.add( Button.success(button.id + "", button.label) );
        }

        return items;
    }

    public static List<ItemComponent> getPlayButton() {

        List<ItemComponent> items = new ArrayList<>();

        items.add( Button.success(PAUSE.id + "", PAUSE.label) );
        items.add( Button.success(SKIP.id + "", SKIP.label) );
        items.add( Button.success(LOOP.id + "", LOOP.label));
        items.add( Button.success(LAST.id + "", LAST.label) );
        items.add( Button.success(CLEAR_QUEUE.id + "", CLEAR_QUEUE.label));
        
        return items;
    }

    public static List<ItemComponent> getHelpButton()  {

        List<ItemComponent> items = new ArrayList<>();

        items.add( Button.success(PLAYLISTS.id + "", PLAYLISTS.label) );

        return items;

    }
}
