package discord_bot;

import java.util.List;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.RestAction;

public class Main {

    private static final String token = "MTIzMTI0MDY1MDc1NDg4MzYzNg.GuknKo.7KpzcPeKWDrmZEXaI0ol65x02BzbxuHTyqk4Gc";

    public static final String PLAY_OPTION = "url";

    public static void main(String[] args) throws LoginException, InterruptedException {

        JDA jda = JDABuilder.createDefault(token).build().awaitReady();

        for (Guild guild : jda.getGuilds()) {

            RestAction<List<Command>> commands = guild.retrieveCommands(true);
            commands.submit().thenAccept(registeredCommands -> {
                for (Command command : registeredCommands) {
                    System.out.println(command.getName());
                }
            });

            guild.updateCommands().addCommands
            (
                Commands.slash("play", "Play a song in your voice channel")
                .addOption(OptionType.STRING, PLAY_OPTION, "url or title of the video", true),
                Commands.slash("skip", "Skip the current song"),
                Commands.slash("loop", "Set or unset the loop mode")
            )
                    .queue();
        }
        
        jda.addEventListener(new Kawaine());
    } 
}
