import controllers.BookmarkController;
import controllers.Controller;
import controllers.UserController;
import entities.Bookmark;
import entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/addBookmark")
public class addBookmarkServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("AddBookmark endpoint reached");

        HttpSession session = request.getSession();
        System.out.println("from addbookmark: "+ session.getId());

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
        Bookmark bookmark = bookmarkController.addBookmark(jsonObject);

        if ( bookmark != null ) {
            System.out.println("status 201");
            session.setAttribute("user", bookmark);

            response.setStatus(201);

            JSONObject bookmarkAsJSON = new JSONObject(bookmark);

            out.println(bookmarkAsJSON);
        } else {
            System.out.println("status 202");
            response.setStatus(202);
            out.println("{\"BookmarkUpdated\":\"false\"}");
        }
        out.close();  // Always close the output writer
    }
}