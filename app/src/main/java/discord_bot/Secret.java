package discord_bot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Secret {

    public static final String SECRET_PATH = "./secret.txt";

    public static String getToken() {

        try {
            return Files.readString(Paths.get(SECRET_PATH));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
