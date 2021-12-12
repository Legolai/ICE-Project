import controllers.Controller;
import controllers.UserController;
import entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("SignUp endpoint reached");

        HttpSession session = request.getSession();
        System.out.println("from signup: "+ session.getId());

        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }

        JSONObject jsonObject = new JSONObject(jb.toString());
        String surname = jsonObject.getString("firstname");
        String firstname = jsonObject.getString("surname");
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String email = jsonObject.getString("email");

        response.addHeader("Access-Control-Allow-Origin", "http://localhost:63342");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        UserController userController = new Controller();
        User user = userController.newUser(username, password, email, firstname, surname);

        if ( user != null ) {
            System.out.println("status 201");
            session.setAttribute("user", user);

            response.setStatus(201);

            JSONObject userAsJSON = new JSONObject(user);

            out.println(userAsJSON);
        } else {
            System.out.println("status 202");
            response.setStatus(202);
            out.println("{\"UserCreated\":\"false\"}");
        }
        out.close();  // Always close the output writer
    }
}
