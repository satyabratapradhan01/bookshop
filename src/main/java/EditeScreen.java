import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editeScrene")
public class EditeScreen extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html");
        
        int id = Integer.parseInt(req.getParameter("id")); // Fetch book ID from request
        try {
            // Load MySQL driver and establish connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "1234");
            // SQL query to fetch the book details by bookId
            String query = "SELECT bookName, bookEdition, bookPrice FROM bookdata WHERE bookId = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            // Check if a record exists for the provided bookId
            if (rs.next()) {
                // Generate an HTML form for editing book details
                pw.println("<form action='editurl?id=" + id + "' method='post'>");
                pw.println("<table align='center'>");
                pw.println("<tr>");
                pw.println("<td>Book Name</td>");
                pw.println("<td><input type='text' name='bookName' value='" + rs.getString(1) + "'></td>");
                pw.println("</tr>");
                pw.println("<tr>");
                pw.println("<td>Book Edition</td>");
                pw.println("<td><input type='text' name='bookEdition' value='" + rs.getString(2) + "'></td>");
                pw.println("</tr>");
                pw.println("<tr>");
                pw.println("<td>Book Price</td>");
                pw.println("<td><input type='number' name='bookPrice' value='" + rs.getFloat(3) + "'></td>");
                pw.println("</tr>");
                pw.println("<tr>");
                pw.println("<td><input type='submit' value='Edit'></td>");
                pw.println("<td><input type='reset' value='Cancel'></td>");
                pw.println("</tr>");
                pw.println("</table>");
                pw.println("</form>");
            } else {
                pw.println("<h1>No record found for the provided ID</h1>");
            }
            con.close();
        } catch (SQLException | ClassNotFoundException  e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
        }
    }
}
