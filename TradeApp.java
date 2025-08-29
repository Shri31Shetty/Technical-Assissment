import java.sql.*;
import java.util.Scanner;

public class TradeApp {
    private static final String URL =
            "jdbc:sqlserver://localhost:1433;databaseName=EnergyTradingDB;encrypt=false;integratedSecurity=true;";
    private static final String USER = ""; // SQL Auth user if needed
    private static final String PASS = ""; // SQL Auth password

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Load SQL Server JDBC Driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
                System.out.println("Connected to database successfully!");

                boolean running = true;
                while (running) {
                    showMenu();
                    int choice = Integer.parseInt(scanner.nextLine());

                    switch (choice) {
                        case 1 -> addTrade(conn, scanner);
                        case 2 -> viewAllTrades(conn);
                        case 3 -> updateTrade(conn, scanner);
                        case 4 -> deleteTrade(conn, scanner);
                        case 5 -> searchTrades(conn, scanner);
                        case 6 -> {
                            System.out.println("Exiting... Goodbye!");
                            running = false;
                        }
                        default -> System.out.println("Invalid choice. Try again.");
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found. Ensure sqljdbc JAR is on classpath.");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private static void showMenu() {
        System.out.println("\n====== Trade Management ======");
        System.out.println("1. Add a Trade");
        System.out.println("2. View All Trades");
        System.out.println("3. Update Trade");
        System.out.println("4. Delete Trade");
        System.out.println("5. Search Trades");
        System.out.println("6. Exit");
        System.out.print("Select an option: ");
    }

    private static void addTrade(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter TradeDate (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter Counterparty: ");
        String cp = scanner.nextLine();
        System.out.print("Enter Commodity: ");
        String cmd = scanner.nextLine();
        System.out.print("Enter Volume: ");
        double vol = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter TradeType (BUY/SELL): ");
        String type = scanner.nextLine();

        String sql = "INSERT INTO Trades (TradeDate, Counterparty, Commodity, Volume, Price, TradeType) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, date);
            ps.setString(2, cp);
            ps.setString(3, cmd);
            ps.setDouble(4, vol);
            ps.setDouble(5, price);
            ps.setString(6, type);
            ps.executeUpdate();
            System.out.println("Trade added successfully!");
        }
    }

    private static void viewAllTrades(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Trades ORDER BY TradeID";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n--- All Trades ---");
            while (rs.next()) {
                System.out.printf("ID:%d | Date:%s | CP:%s | Cmd:%s | Vol:%.2f | Price:%.2f | Type:%s%n",
                        rs.getInt("TradeID"),
                        rs.getDate("TradeDate"),
                        rs.getString("Counterparty"),
                        rs.getString("Commodity"),
                        rs.getDouble("Volume"),
                        rs.getDouble("Price"),
                        rs.getString("TradeType"));
            }
        }
    }

    private static void updateTrade(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter TradeID to update: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter new Price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter new Volume: ");
        double vol = Double.parseDouble(scanner.nextLine());

        String sql = "UPDATE Trades SET Price=?, Volume=? WHERE TradeID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, price);
            ps.setDouble(2, vol);
            ps.setInt(3, id);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Trade updated!" : "Trade not found!");
        }
    }

    private static void deleteTrade(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter TradeID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        String sql = "DELETE FROM Trades WHERE TradeID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "✅ Trade deleted!" : "Trade not found!");
        }
    }

    private static void searchTrades(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Search by (1=Counterparty, 2=Commodity): ");
        int choice = Integer.parseInt(scanner.nextLine());
        String column = (choice == 1) ? "Counterparty" : "Commodity";

        System.out.print("Enter search value: ");
        String value = scanner.nextLine();

        String sql = "SELECT * FROM Trades WHERE " + column + "=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, value);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\n--- Search Results ---");
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.printf("ID:%d | Date:%s | CP:%s | Cmd:%s | Vol:%.2f | Price:%.2f | Type:%s%n",
                            rs.getInt("TradeID"),
                            rs.getDate("TradeDate"),
                            rs.getString("Counterparty"),
                            rs.getString("Commodity"),
                            rs.getDouble("Volume"),
                            rs.getDouble("Price"),
                            rs.getString("TradeType"));
                }
                if (!found) {
                    System.out.println("No matching trades found.");
                }
            }
        }
    }
}
