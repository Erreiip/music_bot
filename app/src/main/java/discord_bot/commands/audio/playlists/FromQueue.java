package discord_bot.commands.audio.playlists;

import java.util.List;

import org.checkerframework.checker.units.qual.t;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord_bot.commands.Commands;
import discord_bot.database.PlaylistDatabase;
import discord_bot.enumerate.PlaylistRights;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

public class FromQueue extends Commands {

    public static final String MODAL_ID = "FromQueueModal";
    public static final String MODAL_NAME = "name";

    public FromQueue(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void executeCommands(ButtonInteractionEvent event) {
        
        TextInput subject = TextInput.create(MODAL_NAME, "Nom de la playlist", TextInputStyle.SHORT)
            .setPlaceholder("ZiziCacaMixtape")
            .setMaxLength(30)
            .setRequired(true)
            .build();

        Modal modal = Modal.create(MODAL_ID, "Créer une playlist à partir de la queue")
            .addComponents(ActionRow.of(subject))
            .build();

        event.replyModal(modal).queue();
    }

    @Override
    public void executeCommands(ModalInteractionEvent event) {
        
        String name = event.getInteraction().getValues().get(0).getAsString();

        PlaylistDatabase playlistDB = PlaylistDatabase.getInstance();

        List<AudioTrack> queue = musicManager.getScheduler().getQueue();

        if ( playlistDB.createAndAddTrackToPlaylist(event.getGuild().getIdLong(), name, queue, event.getUser().getIdLong(), PlaylistRights.PUBLIC)) {
            MessageSender.infoEvent(musicManager.getMessageSender(), "Playlist créée avec succès", event);
            return;
        }

        MessageSender.errorEvent(musicManager.getMessageSender(), "Une erreur est survenue lors de la création de la playlist", event);
    }
    
}