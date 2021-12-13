import controllers.Controller;
import controllers.UserController;
import entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/profile")
public class UserInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Profile endpoint reached");
        HttpSession session = request.getSession(false);
        System.out.println("from profile: "+ session.getId());

        response.addHeader("Access-Control-Allow-Origin", "http://localhost:63342");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        if (session != null) {
            User user = (User) session.getAttribute("user");
            JSONObject userJSON = new JSONObject(user);
            response.setStatus(200);
            out.println("{\"user\":"+userJSON+"}");
        }
        else {
            response.setStatus(401);
            out.println("{\"user\":\"denied\"}");
        }

        out.close();
    }
}
