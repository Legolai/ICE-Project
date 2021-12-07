import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, String[]> data = request.getParameterMap();
        response.setStatus(200);
        response.addHeader("Accept", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.println("{");
        for (String key: data.keySet()) {
            for (String s: data.get(key)) {
                out.print("{\"access_token\":\"21313esadf\"}");
            }
        }
        out.println("}");
        out.close();  // Always close the output writer


//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//
//        if (username.equalsIgnoreCase("admin") && password.equals("Admin")) {
//            response.setStatus(200);
//            response.addHeader("Accept", "*");
//            response.setContentType("application/json");
//            response.setCharacterEncoding("utf-8");
//            PrintWriter out = response.getWriter();
//            out.println("{\"access_token\":\"21313esadf\"}");
//            out.close();  // Always close the output writer
//        }
//        else {
//            response.setStatus(401);
//        }
    }

}