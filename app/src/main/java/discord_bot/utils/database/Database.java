package discord_bot.utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import discord_bot.utils.Env;

public class Database {
    
    private static Database instance;
    private Connection connection;

    Map<String, PreparedStatement> mapCache;


    private Database() throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        
        this.connection = DriverManager.getConnection(Env.getDatabaseUrl(), Env.getDatabaseUser(), Env.getDatabasePassword());
        
        this.mapCache = new HashMap<>();
    }

    public static Database getInstance() {

        if (instance == null) {

            try {
                Database.instance = new Database();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        return instance;
    }

    void addPreparedStatement(String key, String query) {

        try {
            mapCache.put(key, connection.prepareStatement(query));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
