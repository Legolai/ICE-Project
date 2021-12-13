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

@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("updateUser endpoint reached");

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        System.out.println("from updateUser: "+ session.getId());

        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }

        JSONObject jsonObject = new JSONObject(jb.toString());

        String key = jsonObject.getString("key");
        String value = jsonObject.getString("value");

        response.addHeader("Access-Control-Allow-Origin", "http://localhost:63342");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        UserController userController = new Controller();
        Boolean userUpdated = userController.updateUser(user, key, value);
        user = userController.getUser(user);
        if ( userUpdated && user != null) {
            System.out.println("status 201");

            session.setAttribute("user", user);

            response.setStatus(201);

            out.println("{\"userUpdated\":\"true\"}");
        } else {
            System.out.println("status 202");
            response.setStatus(202);
            out.println("{\"userUpdated\":\"false\"}");
        }
        out.close();  // Always close the output writer
    }
}
