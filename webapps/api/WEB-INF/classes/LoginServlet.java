import controllers.BookmarkController;
import controllers.Controller;
import controllers.UserController;
import entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
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
            JSONObject userAsJSON = new JSONObject(user);
            //Har gjort klar til at vi kan sende cookies som vi kan bruge til at læse sessions eller tokens
            //response.addCookie(createCookie(userAsJSON));
            out.println(userAsJSON);
        } else {
            response.setStatus(401);
            out.println("{\"access\":\"Denied\"}");
        }
        out.close();  // Always close the output writer
    }


    public Cookie createCookie(JSONObject cookieValue) {
        final String cookieName = "Faverite_manager_cookie";
        final int expiryTime = 60 * 60 * 24;  // 24h in seconds
        final String cookiePath = "/";
        Cookie cookie = new Cookie(cookieName, cookieValue.toString().replace(" ",""));
        cookie.setMaxAge(expiryTime);  // A negative value means that the cookie is not stored persistently and will be deleted when the Web browser exits. A zero value causes the cookie to be deleted.
        cookie.setPath(cookiePath);  // The cookie is visible to all the pages in the directory you specify, and all the pages in that directory's subdirectories
        return cookie;
    }
}
