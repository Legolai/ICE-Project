import controllers.BookmarkController;
import controllers.Controller;
import controllers.UserController;
import entities.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("Login endpoint reached");

        HttpSession session = request.getSession();
        System.out.println("from login: "+ session.getId());

        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }

        JSONObject jsonObject = new JSONObject(jb.toString());
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        response.addHeader("Access-Control-Allow-Origin", "http://localhost:63342");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        UserController userController = new Controller();
        User user = userController.login(username, password);

        if (user != null ) {
            session.setAttribute("user", user);

            response.setStatus(201);

            out.println("{\"authenticated\":\"true\"}");
        } else {
            response.setStatus(401);
            out.println("{\"authenticated\":\"false\"}");
        }
        out.close();  // Always close the output writer
    }
}
