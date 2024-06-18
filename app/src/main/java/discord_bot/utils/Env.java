package discord_bot.utils;

public class Env {

    public static String YTB_TOKEN = "YTB_TOKEN";
    public static String DATABASE_URL = "DATABASE_URL";
    public static String DATABASE_USER = "DATABASE_USER";
    public static String DATABASE_PASSWORD = "DATABASE_PASSWORD";
    public static String SPOTIFY_PB = "SPOTIFY_PB";
    public static String SPOTIFY_PV = "SPOTIFY_PV";
    public static String DISCORD_TOKEN = "DISCORD_TOKEN";
    public static String DISCORD_TOKEN_DEBUG = "DISCORD_TOKEN_DEBUG";
    public static String DEBUG = "DEBUG";

    public static String getToken() {

        if (isDebug()) return System.getenv(DISCORD_TOKEN_DEBUG);
        else return System.getenv(DISCORD_TOKEN);
    }

    public static String getYtbToken() {
        return System.getenv(YTB_TOKEN);
    }

    public static String getSpotifyClientI() {
        return System.getenv(SPOTIFY_PB);
    }

    public static String getSpotifyClientS() {
        return System.getenv(SPOTIFY_PV);
    }

    public static String getDatabaseUrl() {
        return System.getenv(DATABASE_URL);
    }

    public static String getDatabaseUser() {
        return System.getenv(DATABASE_USER);
    }

    public static String getDatabasePassword() {
        return System.getenv(DATABASE_PASSWORD);
    }

    public static boolean isDebug() {
        return System.getenv(DEBUG).equals("true");
    }
}
