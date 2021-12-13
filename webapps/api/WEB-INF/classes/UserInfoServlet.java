import entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/profile")
public class UserInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        PrintWriter out = resp.getWriter();

        if (session != null) {
            User user = (User) session.getAttribute("user");
            JSONObject userJSON = new JSONObject(user);
            resp.setStatus(200);
            out.println("{\"user\":"+userJSON.toString()+"}");
        }
        else {
            resp.setStatus(401);
            out.println("{\"user\":\"denied\"}");
        }

        out.close();
    }
}
