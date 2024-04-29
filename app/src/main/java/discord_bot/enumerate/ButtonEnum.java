package discord_bot.enumerate;

public enum ButtonEnum {

    ADD_PLAYLIST(0, "Ajouter à la playlist"),
    SKIP(1, "Suivant");

    public final int id;
    public final String label;

    private ButtonEnum(int id, String label) {
        this.id = id;
        this.label = label;
    }
}
