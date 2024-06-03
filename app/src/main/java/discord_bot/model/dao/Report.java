package discord_bot.model.dao;

public class Report {

    private final int id;
    private final String username;
    private final String report;

    public Report(int id, String username, String report) {
        this.id = id;
        this.username = username;
        this.report = report;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getReport() {
        return report;
    }
}
