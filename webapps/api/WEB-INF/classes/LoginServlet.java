import controllers.BookmarkController;
import controllers.Controller;
import controllers.UserController;
import entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserController userController;
    private BookmarkController bookmarkController;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

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

        System.out.println(username);
        System.out.println(password);

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        userController = new Controller();
        User user = userController.login(username, password);

        if (user != null ) {
            response.setStatus(201);
            out.println(JSONObject.valueToString(user));
        } else {
            response.setStatus(401);
            out.println("{\"access\":\"Denied\"}");
        }
        out.close();  // Always close the output writer
    }

}
