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

@WebServlet("/booklist")
public class BookList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "1234");

            String query = "SELECT bookId, bookName, bookEdition, bookPrice FROM bookdata";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            pw.println("<table border='1' align='center'>");
            pw.println("<tr>");
            pw.println("<th>BOOK ID</th>");
            pw.println("<th>BOOK NAME</th>");
            pw.println("<th>BOOK EDITION</th>");
            pw.println("<th>BOOK PRICE</th>");
            pw.println("<th>EDIT</th>");
            pw.println("<th>DELETE</th>");
            pw.println("</tr>");

            while (rs.next()) {
                pw.println("<tr>");
                pw.println("<td>" + rs.getInt(1) + "</td>");
                pw.println("<td>" + rs.getString(2) + "</td>");
                pw.println("<td>" + rs.getString(3) + "</td>");
                pw.println("<td>" + rs.getFloat(4) + "</td>");
                pw.println("<td><a href='editeScrene?id=" + rs.getInt(1) + "'>EDIT</a></td>");
                pw.println("<td>");
                pw.println("<form action='deleteScrene' method='POST' style='display:inline;'>");
                pw.println("<input type='hidden' name='id' value='" + rs.getInt(1) + "'>");
                pw.println("<input type='submit' value='DELETE'>");
                pw.println("</form>");
                pw.println("</td>");
                pw.println("</tr>");
            }

            pw.println("</table>");
            pw.println("<br>");
            pw.println("<a href='shop.html'>Home</a>");

            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
