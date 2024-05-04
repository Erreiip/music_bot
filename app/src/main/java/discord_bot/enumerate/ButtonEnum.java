package discord_bot.enumerate;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public enum ButtonEnum {

    ADD_PLAYLIST(0, "Ajouter à la playlist"),
    SKIP(1, "Suivant"),
    PAUSE(2, "Pause"),
    LOOP(3, "Loop"),
    LAST(4, "Ajouter le dernier"),
    PLAYLISTS(5, "Playlists"),
    HELP(6, "Aide"),
    CLEAR_QUEUE(7, "Vider la queue"),
    STOP(8, "Stop");

    public final int id;
    public final String label;

    private ButtonEnum(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public static List<ItemComponent> items() {
        
        List<ItemComponent> items = new ArrayList<>();
        
        for ( ButtonEnum button : ButtonEnum.values() ) {
            
            if (button.id > 4) break;  

            items.add( Button.success(button.id + "", button.label) );
        }

        return items;
    }

    public static List<ItemComponent> getQueueButton() {

        List<ItemComponent> items = new ArrayList<>();

        items.add( Button.primary(SKIP.id + "", SKIP.label) );
        // loop
        // clear
        // shuffle
        // createPlaylistByThis
        

        return items;
    }

    public static List<ItemComponent> getHelpButton()  {

        List<ItemComponent> items = new ArrayList<>();

        items.add( Button.primary(PLAYLISTS.id + "", PLAYLISTS.label) );
        items.add( Button.primary(HELP.id + "", HELP.label) );
        items.add( Button.primary(STOP.id + "", STOP.label) );
        items.add( Button.primary(CLEAR_QUEUE.id + "", CLEAR_QUEUE.label) );
        items.add( Button.primary(LOOP.id + "", LOOP.label) );
        items.add( Button.primary(PAUSE.id + "", PAUSE.label) );

        return items;

    }
}
