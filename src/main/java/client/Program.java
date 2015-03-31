package main.java.client;
import java.sql.*;
import com.microsoft.sqlserver.jdbc.*;
/**
 * Created by Микола on 31.03.15.
 */
public class Program {
    public static void main(String[] args) throws SQLException {
        // Declare the JDBC objects.
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Statement stmt = null;

        try {
            // ESTABLISH CONNECTION
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setUser("nico");
            ds.setPassword("nicopass");
            ds.setServerName("NICO\\SQLEXPRESS");
            ds.setPortNumber(1433);
            ds.setDatabaseName("temp");
            con = ds.getConnection();

            //CREATE TABLE STATEMENT

            String SQL = "CREATE TABLE stud( " +
                            "id INT NOT NULL IDENTITY(1,1) PRIMARY KEY," +
                            "name VARCHAR(30) NOT NULL " +
                         " )";
            stmt = con.createStatement();
            stmt.execute(SQL);

            //CRUD: INSERT

            SQL = "INSERT INTO stud(name) VALUES(?)";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, "Smith");
            pstmt.execute();

            //CRUD: UPDATE

            SQL = "UPDATE stud SET name = ? WHERE name=?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, "Nico");
            pstmt.setString(2, "Smith");
            pstmt.execute();

            //CRUD: SELECT

            SQL = "SELECT name FROM stud";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            while(rs.next()){
                System.out.println(rs.getString("name"));
            }

            //CRUD: DELETE

            SQL = "DELETE FROM stud WHERE name = ?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, "Nico");
            pstmt.execute();

            //DROP TABLE STATEMENT

            SQL = "DROP TABLE stud";
            stmt = con.createStatement();
            stmt.execute(SQL);

        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {}
            if (pstmt != null) try { pstmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            System.exit(1);
        }
    }
}
