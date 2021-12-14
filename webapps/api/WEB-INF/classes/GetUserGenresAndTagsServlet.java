import controllers.BookmarkController;
import controllers.Controller;
import entities.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONArray;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/getUserGenresAndTags")
public class GetUserGenresAndTagsServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("GetUserGenresAndTags endpoint reached");

        HttpSession session = request.getSession(false);

        System.out.println("fra getUserGenresAndTags: " + session.getId());
        User user = (User) request.getSession().getAttribute("user");

        response.addHeader("Access-Control-Allow-Origin", "http://localhost:63342");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        BookmarkController bookmarkController = new Controller();
        ArrayList<String> genres = bookmarkController.getUsersGenresOrTags(user, "genre");
        ArrayList<String> tags = bookmarkController.getUsersGenresOrTags(user, "tag");

        if (genres != null || tags != null) {
            response.setStatus(201);

            JSONArray genresAsJson = new JSONArray(genres);
            JSONArray tagsAsJson = new JSONArray(tags);

            out.println("{\"tags\":"+genresAsJson+",\"genres\":"+tagsAsJson+"}");
        } else {
            response.setStatus(401);
            out.println("{\"getGenresAndTags\":\"false\"}");
        }
        out.close();  // Always close the output writer
    }

}
