import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/login")
public class HelloWorld extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Hello, World</title></head>");
        out.println("<body>");
        out.println("<h1>Hello, world!</h1>");  // says Hello
        // Echo client's request information
        out.println("<p>Request URI: " + request.getRequestURI() + "</p>");
        out.println("<p>Protocol: " + request.getProtocol() + "</p>");
        out.println("<p>PathInfo: " + request.getPathInfo() + "</p>");
        out.println("<p>Remote Address: " + request.getRemoteAddr() + "</p>");
        // Generate a random number upon each request
        out.println("<p>A Random Number: <strong>" + Math.random() + "</strong></p>");
        out.println("</body></html>");
        out.close();  // Always close the output writer
    }
}