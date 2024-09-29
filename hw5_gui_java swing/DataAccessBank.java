import java.sql.*;

public class DataAccessBank {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bank_account";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "pass";

    private Connection getDBConnection(){
        Connection connection = null;
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection to the database
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

        } catch (ClassNotFoundException ex) {
            System.err.println("MySQL JDBC Driver not found.");
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.err.println("SQL Exception occurred.");
            ex.printStackTrace();
        }
        return connection;
    }


    private boolean doesAccountNumberExist(String accountNumber) {
        String query = "SELECT COUNT(*) FROM Account WHERE account_number = ?";
        try (Connection conn = getDBConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if count is greater than 0
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return false; // Default return if an exception occurs or no result
    }

    public double getBalance(String accountNumber) {
        double balance = -1;
        if (!doesAccountNumberExist(accountNumber)) {
            return balance;
        }

        String query = "SELECT balance FROM Account WHERE account_number = ?";
        try (Connection conn = getDBConnection();
            PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, accountNumber);
            // Execute the query
            ResultSet rs = statement.executeQuery();
            
            // Check if a result was returned
            if (rs.next()) {
                balance = rs.getDouble("balance"); // Retrieve the balance
            } else  {
                return balance;
            }
        }catch (SQLException e) {
                System.err.println("SQL Exception occurred: " + e.getMessage());
                e.printStackTrace();
            }
            return balance;
        }
    
    public String getType(String accountNumber) {
        String type = null;
        if (!doesAccountNumberExist(accountNumber)) {
            return type;
        }

        String query = "SELECT type FROM Account WHERE account_number = ?";
        try (Connection conn = getDBConnection();
            PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, accountNumber);
            // Execute the query
            ResultSet rs = statement.executeQuery();
            
            // Check if a result was returned
            if (rs.next()) {
                type = rs.getString("type"); // Retrieve the balance
            } else  {
                return type;
            }
        }catch (SQLException e) {
                System.err.println("SQL Exception occurred: " + e.getMessage());
                e.printStackTrace();
            }
            return type;
        }
    
    public String generateNewAccountNumber() {
        String newAccountNumber = String.valueOf(System.currentTimeMillis()); 

        // Check if the generated account number already exists
        while (doesAccountNumberExist(newAccountNumber)) {
            newAccountNumber = String.valueOf(System.currentTimeMillis()); 
        }
        return newAccountNumber; // Return a unique account number
    }

    public int addNewAccount(String accountNumber, double initialBalance, String type) {
        int status = 0; // Default status
        String query = "INSERT INTO Account (account_number, balance, type) VALUES (?, ?, ?)";
    
        try (Connection conn = getDBConnection(); 
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, accountNumber);
            pstmt.setDouble(2, initialBalance);
            pstmt.setString(3, type);
    
            status = pstmt.executeUpdate(); // Returns number of affected rows
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return status; // Return the status (number of affected rows)
    }

    public int updateBalance(String accountNumber, double newBalance) {
        int rowsAffected = 0;
        // change the balance
        String query = "UPDATE Account SET balance = ? WHERE account_number = ?";
        try (Connection conn = getDBConnection(); 
        PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, accountNumber);
            rowsAffected = pstmt.executeUpdate();  
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return rowsAffected;
    }
}
