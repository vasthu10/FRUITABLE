import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signUp")
public class SignUp extends HttpServlet {
    public static final long serialVersionUID = 1L;
    public static final String DB_URL = "jdbc:mysql://localhost:3306/login";
    public static final String DB_USER = "root";
    public static final String DB_PASS = ""; // Change to your MySQL password

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Establish a connection to the database
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            
            // SQL Insert statement with column names
            String sql = "INSERT INTO signup (username, email, password) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                response.sendRedirect("bootstrap.html");
            } else {
                out.println("<h1>User not found!</h1>");
            }

            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<h1>Error occurred: " + e.getMessage() + "</h1>");
        } finally {
            out.close();
        }
    }
}
