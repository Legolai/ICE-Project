import controllers.BookmarkController;
import controllers.Controller;
import entities.Bookmark;
import entities.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/getAll")
public class GetAllServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("har ramt request");

        HttpSession session = request.getSession(false);
        if (session != null )
            System.out.println("er på den anden side");
        else
            System.out.println("den er null");

        response.addHeader("Access-Control-Allow-Origin", "http://localhost:63342");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        BookmarkController bookmarkController = new Controller();
        ArrayList<Bookmark> bookmarks = bookmarkController.getAll(new User());

        if (bookmarks != null ) {
            session.setAttribute("bookmarks", bookmarks);

            response.setStatus(201);
            JSONObject bookmarksAsJSON = new JSONObject(bookmarks);

            //Har gjort klar til at vi kan sende cookies som vi kan bruge til at læse sessions eller tokens
            //response.addCookie("JSESSIONID", session.getId());

            out.println(bookmarksAsJSON);

        } else {
            response.setStatus(401);
            out.println("{\"access\":\"Denied\"}");
        }
        out.close();  // Always close the output writer
    }

}
