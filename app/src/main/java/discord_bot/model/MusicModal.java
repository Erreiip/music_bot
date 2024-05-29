package discord_bot.model;

import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

public class MusicModal {

    public static Modal createModalPlaylistRecord() {

        TextInput name = TextInput.create("name", "Name of playlist", TextInputStyle.SHORT)
            .setPlaceholder("Subject of this ticket")
            .setMinLength(10)
            .setMaxLength(30)
            .setPlaceholder("Best playlist ever")
            .build();

        Modal modal = Modal.create("PlaylistR", "Playlist Record")
            .addComponents(ActionRow.of(name))
            .build();

        return modal;
    }
    
}
