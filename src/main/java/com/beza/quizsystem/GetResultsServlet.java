package com.beza.quizsystem;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/get-results")
public class GetResultsServlet extends HttpServlet {
private final Gson gson = new Gson();
private final ResultsDAO resultsDAO = new ResultsDAO();

@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    HttpSession session = request.getSession(false);
    if(session == null || session.getAttribute("userId") == null){
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
    }

    int userId = (int) session.getAttribute("userId");

    List<QuizResult> results = resultsDAO.getResultsByUserId(userId);
    String json = this.gson.toJson(results);
    response.getWriter().write(json);
}

}
