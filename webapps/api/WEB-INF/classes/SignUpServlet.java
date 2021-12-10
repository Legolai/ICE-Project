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
        System.out.println("har ramt");

        HttpSession session = request.getSession(false);
        System.out.println("from signup: "+ session.getId());


        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }

        JSONObject jsonObject = new JSONObject(jb.toString());
        //String surname = jsonObject.getString("firstname");
        //String firstname = jsonObject.getString("surname");
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
        User user = userController.updateUser(username, password, email
            //, String firstname, String surname
        );

        if ( user != null ) {
            session.setAttribute("user", user);

            response.setStatus(201);

            JSONObject userAsJSON = new JSONObject(user);

            out.println(userAsJSON);
        } else {
            response.setStatus(401);
            out.println("{\"access\":\"Denied\"}");
        }
        out.close();  // Always close the output writer
    }


    public Cookie createCookie(String cookieValue) {
        final String cookieName = "Favorite_bookmarks_manager_user_id";
        final int expiryTime = 60 * 60 * 24;  // 24h in seconds
        final String cookiePath = "http://localhost/";
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setDomain("localhost");
        cookie.setMaxAge(expiryTime);  // A negative value means that the cookie is not stored persistently and will be deleted when the Web browser exits. A zero value causes the cookie to be deleted.
        cookie.setPath(cookiePath);  // The cookie is visible to all the pages in the directory you specify, and all the pages in that directory's subdirectories
        return cookie;
    }
}