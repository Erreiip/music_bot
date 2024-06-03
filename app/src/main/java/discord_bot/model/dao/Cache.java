package discord_bot.model.dao;

public class Cache {

    private final String query;
    private final String result;

    public Cache(String query, String result) {
        this.query = query;
        this.result = result;
    }

    public String getQuery() {
        return query;
    }

    public String getResult() {
        return result;
    }
    
}
