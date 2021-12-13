import controllers.BookmarkController;
import controllers.Controller;
import entities.Bookmark;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/deleteBookmark")
public class DeleteBookmarkServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("DeleteBookmark endpoint reached");

        HttpSession session = request.getSession();
        System.out.println("from deleteBookmark: "+ session.getId());

        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }

        JSONObject jsonObject = new JSONObject(jb.toString());


        response.addHeader("Access-Control-Allow-Origin", "http://localhost:63342");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        BookmarkController bookmarkController = new Controller();
        bookmarkController.removeBookmark(jsonObject);

        System.out.println("status 201");
        response.setStatus(202);
        out.println("{\"BookmarkDelete\":\"true\"}");

        out.close();  // Always close the output writer
    }
}
