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

        if (username.equalsIgnoreCase("admin") && password.equals("Admin")) {
            response.setStatus(200);
            response.addHeader("Accept", "*");
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            out.println("{\"access_token\":\"21313esadf\"}");
            out.close();  // Always close the output writer
        } else {
            response.setStatus(401);
        }
    }

}