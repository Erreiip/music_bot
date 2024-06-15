package discord_bot.utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import discord_bot.model.dao.Cache;
import discord_bot.model.dao.Report;
import discord_bot.utils.Env;

public class Database {
    
    private static final String TABLE_NAME = "cache";
    private static final String LBL_QUERY = "query";
    private static final String LBL_RESULT = "result";

    private static final String TABLE_NAME_REPORT = "user_report";
    private static final String LBL_ID = "id";
    private static final String LBL_USER = "username";
    private static final String LBL_REPORT = "report";

    private static final String INSERT = "insert";
    private static final String SELECT = "select";
    private static final String SELECT_WHERE = "select_where";

    private static final String INSERT_REPORT = "insert_report";
    private static final String SELECT_REPORT = "select_report";

    private static final String INSERT_QUERY = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?)";
    private static final String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME;
    private static final String SELECT_QUERY_WHERE = "SELECT * FROM " + TABLE_NAME + " WHERE " + LBL_QUERY + "= ?";

    private static final String INSERT_QUERY_REPORT = "INSERT INTO " +  TABLE_NAME_REPORT + "(" + LBL_USER + "," + LBL_REPORT + ") VALUES (?, ?)";
    private static final String SELECT_QUERY_REPORT = "SELECT * FROM " + TABLE_NAME_REPORT;

    private Map<String, PreparedStatement> mapCache;

    private static Database instance;
    private Connection connection;


    private Database() throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        
        this.connection = DriverManager.getConnection(Env.getDatabaseUrl(), Env.getDatabaseUser(), Env.getDatabasePassword());

        mapCache = new HashMap<>();

        mapCache.put(INSERT, connection.prepareStatement(INSERT_QUERY));
        mapCache.put(SELECT, connection.prepareStatement(SELECT_QUERY));
        mapCache.put(SELECT_WHERE, connection.prepareStatement(SELECT_QUERY_WHERE));

        mapCache.put(INSERT_REPORT, connection.prepareStatement(INSERT_QUERY_REPORT));
        mapCache.put(SELECT_REPORT, connection.prepareStatement(SELECT_QUERY_REPORT));
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

    public List<Cache> getCaches() throws SQLException {

        ResultSet resultSet = mapCache.get(SELECT).executeQuery();

        List<Cache> caches = new ArrayList<>();
        while ( resultSet.next() ) {
            caches.add(new Cache(resultSet.getString(LBL_QUERY), resultSet.getString(LBL_RESULT)));
        }

        resultSet.close();
        return caches;
    }

    private boolean insertReport(String username, String report) throws SQLException {

        mapCache.get(INSERT_REPORT).setString(1, username);
        mapCache.get(INSERT_REPORT).setString(2, report);
        mapCache.get(INSERT_REPORT).executeUpdate();
        
        return true;
    }

    public List<Report> getReports() throws SQLException {

        ResultSet resultSet = mapCache.get(SELECT_REPORT).executeQuery();

        List<Report> reports = new ArrayList<>();
        while ( resultSet.next() ) {
            reports.add(new discord_bot.model.dao.Report(resultSet.getInt(LBL_ID), resultSet.getString(LBL_USER), resultSet.getString(LBL_REPORT)));
        }

        resultSet.close();
        return reports;
    }
    
    public static boolean addReport(String username, String report) {
        
        try {
            return Database.getInstance().insertReport(username, report);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
}
