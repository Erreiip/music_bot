package discord_bot.utils;

import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

public class ButtonCustom {

    private Button button;
    private String id;
    private String data;
    private String label;

    public ButtonCustom(ButtonStyle style, String id, String data, String label) {

        this.id = id;
        this.data = data;
        this.label = label;

        this.button = Button.of(style, id + ";" + data, label);
    }

    public ButtonCustom(Button button) {

        this.button = button;

        String[] idData = button.getId().split(";");
        this.id = idData[0];
        if ( idData.length > 1) this.data = idData[1];
        else this.data = "";
        this.label = button.getLabel();
    }

    public Button get() {
        return button;
    }

    public String getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public String getLabel() {
        return label;
    }
}
