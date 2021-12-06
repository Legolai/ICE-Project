import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/login")
public class AuthCheck extends Httpservlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = userService.find(username, password);

        if (user != null) {
            request.getSession().setAttribute("user", user);
            response.sendRedirect("home");
        }
        else {
            request.setAttribute("error", "Unknown user, please try again");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }

    }
}