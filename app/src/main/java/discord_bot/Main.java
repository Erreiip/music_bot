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

    private static final String token = "YOUR_BOT_TOKEN";
    
    public static void main(String[] args) throws LoginException, InterruptedException {

        JDA jda = JDABuilder.createDefault(token).build().awaitReady();

        for (Guild guild : jda.getGuilds()) {

            RestAction<List<Command>> commands = guild.retrieveCommands(true);
            commands.submit().thenAccept(registeredCommands -> {
                for (Command command : registeredCommands) {
                    System.out.println(command.getName());
                }
            });
            
            /*
            guild.updateCommands().addCommands(
                    Commands.slash("fortnite", "Create build fight")
                            .addOption(OptionType.CHANNEL, "channel", "The channel to create the build fight in", true))
                    .queue();
            */
        }
    } 
}
