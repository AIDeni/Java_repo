import java.sql.*;
import java.util.Scanner;

public class Main {

    private static Connection connection;
    private static Statement stmt;

    public static void main(String[] args) {

        try {
            connect();
            createTable();
//            insertData();
//            dropTable();
//            selectCountry();
//            deleteOneCountry();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    public static void connect() throws Exception {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
        stmt = connection.createStatement();
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTable() throws SQLException {

        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Countries (\n" +
                "    id    INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    country  STRING,\n" +
                "    capital STRING\n" +
                ");");
    }

    private static void insertData() throws SQLException {
        System.out.println("Введите название страны");
        Scanner scanner = new Scanner(System.in);
        String country = scanner.nextLine();
        System.out.println("Введите название столицы");
        String capital = scanner.nextLine();
        String sql = String.format("INSERT INTO Countries (country, capital) VALUES ('%s', '%s');", country, capital);
        stmt.executeUpdate(sql);
    }

    private static void selectCountry() throws SQLException {
        System.out.println("Введите название страны, чтобы узнать ее столицу");
        Scanner scanner = new Scanner(System.in);
        String country = scanner.nextLine();
        String sql = String.format("SELECT capital FROM Countries where country = '%s';", country);
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString("capital"));
        }
    }


    private static void deleteOneCountry() throws SQLException {
        System.out.println("Введите название страны, данные о которой хотите удалить");
        Scanner scanner = new Scanner(System.in);
        String country = scanner.nextLine();
        String sql2 = String.format("DELETE FROM Countries where country = '%s';", country);
        stmt.executeUpdate(sql2);
    }

    private static void dropTable() throws SQLException {
        stmt.executeUpdate("DROP TABLE IF EXISTS capitalTable;");
    }

}

