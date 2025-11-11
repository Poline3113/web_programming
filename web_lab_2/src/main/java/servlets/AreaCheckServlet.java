package servlets;

import checker.DataInspector;
import com.google.gson.Gson;
import dao.Point;
import dao.Result;
import exceptions.FormatException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/areaCheck")
public class AreaCheckServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String x = request.getParameter("x");
        String y = request.getParameter("y");
        String r = request.getParameter("r");

        try {
            DataInspector.inspectData(x, y, r);
        } catch (FormatException e) {
            sendError(response, e.getMessage());
            return;
        }

        Point point = new Point(Float.parseFloat(x), new BigDecimal(y), Float.parseFloat(r));
        var session = request.getSession();
        var bean = (Result) session.getAttribute("result");

        if (bean == null) {
            bean = new Result();
            session.setAttribute("result", bean);
        }
        bean.addPoint(point);

        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }

    private void sendError(HttpServletResponse response, String errorMessage) throws IOException {
        var json = new Gson();
        Map<String, Object> jsonResponse = new HashMap<>() {{
            put("error", errorMessage);
        }};

        response.setContentType("application/json");
        response.getWriter().write(json.toJson(jsonResponse));
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}