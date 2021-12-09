import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/checkSession")
public class SessionServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("ramt");

        HttpSession session = request.getSession(false);
        PrintWriter out = response.getWriter();
        if (session != null) {
        System.out.println("fra Session: " + session.getId());

        response.addHeader("Access-Control-Allow-Origin", "http://localhost:63342");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(201);

        } else {
            System.out.println("ingen session");
            response.setStatus(401);
            out.println("{\"JSESSIONID\":\"Null\"}");
        }
        out.close();  // Always close the output writer
    }
}
