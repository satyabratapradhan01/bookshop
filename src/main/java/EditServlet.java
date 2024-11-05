
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


@WebServlet("/editurl")
public class EditServlet extends HttpServlet{
 
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html");
        
        int id = Integer.parseInt(req.getParameter("id")); 
        String bookName = req.getParameter("bookName");
        String bookEdition = req.getParameter("bookEdition");
        Float BookPrice = Float.parseFloat(req.getParameter("bookPrice"));

        try {
            // Load MySQL driver and establish connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "1234");

            // SQL query to fetch the book details by bookId
            String query = "UPDATE bookdata set bookName=?, bookEdition=?, bookPrice=? WHERE bookId = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, bookName);
            ps.setString(2, bookEdition);
			ps.setFloat(3, BookPrice);
			ps.setInt(4, id);
			
			int count = ps.executeUpdate();
            
			if(count == 1) {
				pw.println("<h2>Record is Edition Successfully</h2>");
			}else {
				pw.println("<h2>Record is not Edition Successfully</h2>");
			}
			
			 pw.println("<a href='shop.html'>Home</a>");
	            pw.println("<br>");
	            pw.println("<a href='booklist'>Book List</a>");

            con.close();
        } catch (SQLException | ClassNotFoundException  e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
        }
	}
	

}
