import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class resister extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html");

        String bookName = req.getParameter("bookName");
        String bookEdition = req.getParameter("bookEdition");
        String bookprice = req.getParameter("bookprice");

        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "1234");
            String query = "INSERT INTO bookdata (bookName, bookEdition, bookPrice) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, bookName);
            ps.setString(2, bookEdition);
            ps.setDouble(3, Double.parseDouble(bookprice)); // Convert to double for price
            
            int count = ps.executeUpdate();
            if (count == 1) {
                pw.println("<h2>Record registered successfully</h2>");
            } else {
                pw.println("<h2>Record registration failed</h2>");
            }
            pw.println("<a href='bookRegistration.html'>Home</a><br>");
            pw.println("<a href='booklist'>Book List</a>");
        } 
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
        } 
    }
}
