package discord_bot.utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import discord_bot.utils.Env;

public class Database {
    
    private static final String TABLE_NAME = "cache";
    private static final String LBL_QUERY = "query";
    private static final String LBL_RESULT = "result";
    private static final String INSERT = "insert";
    private static final String SELECT = "select";
    private static final String INSERT_QUERY = "INSERT INTO " + TABLE_NAME + " VALUES (%s, %s)";
    private static final String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME + " WHERE query = ?";

    private Map<String, PreparedStatement> mapCache;

    private static Database instance;
    private Connection connection;


    private Database() throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        
        this.connection = DriverManager.getConnection(Env.getDatabaseUrl());

        mapCache = new HashMap<>();
        mapCache.put(INSERT, connection.prepareStatement(INSERT_QUERY));
        mapCache.put(SELECT, connection.prepareStatement(SELECT_QUERY));
    }

    public static Database getInstance() {

        if (instance == null) {

            try {
                instance = new Database();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        return instance;
    }

    public String getQuery(String query) throws SQLException {

        mapCache.get(SELECT).setString(0, query);
        ResultSet resultSet = mapCache.get(SELECT).executeQuery();
        
        String response = null;
        if ( resultSet.next() ) {
            response = resultSet.getString(LBL_RESULT);
        }

        resultSet.close();
        return response;
    }

    public boolean insertInto(String query, String result) throws SQLException {

        if ( getQuery(query) != null ) {
            return false;
        }

        mapCache.get(INSERT).setString(0, query);
        mapCache.get(INSERT).setString(1, result);
        mapCache.get(INSERT).executeUpdate();
        
        return true;
    }
    
    public static void addTrack(String query, String uri) {
        
        try {
            Database.getInstance().insertInto(uri, uri);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
