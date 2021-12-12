import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/session")
public class SessionServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Session endpoint reached");

        HttpSession session = request.getSession(false);
        PrintWriter out = response.getWriter();

        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }

        JSONObject jsonObject = new JSONObject(jb.toString());
        String requestParamenter = jsonObject.getString("Session");

        response.addHeader("Access-Control-Allow-Origin", "http://localhost:63342");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");


        if (session != null) {
            response.setStatus(201);

            switch (requestParamenter) {
                case "delete":
                    System.out.println("deleting session: " + session.getId());
                    session.invalidate();
                    out.println("{\"Session\":\"deleted\"}");
                    break;

                default:
                    System.out.println("Session: " + session.getId());
                    out.println("{\"Session\":\""+ session.getId() +"\"}");
                    break;
            }

        } else {
            System.out.println("ingen session");
            response.setStatus(401);
            out.println("{\"Session\":\"Null\"}");
        }
        out.close();  // Always close the output writer
    }
}
