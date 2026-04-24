package com.beza.quizsystem;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("api/save-result")
public class SaveResultsServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final ResultsDAO resultsDAO = new ResultsDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject data = gson.fromJson(request.getReader(), JsonObject.class);

        HttpSession session = request.getSession(false);

        if(session == null || !"STUDENT".equals(session.getAttribute("role"))){
            response.setStatus(403);
            response.getWriter().write("{\"success\":false,\"message\":\"Unauthorized!\"}");
            return;
        }
        int userId = (int) session.getAttribute("userId");

        int subjectId = data.get("subjectId").getAsInt();
        int total = data.get("totalQuestions").getAsInt();
        int score = data.get("score").getAsInt();

        System.out.println(userId+subjectId+total+score);
        boolean isSaved = resultsDAO.saveResults(userId,subjectId,total,score);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if(isSaved){
            response.getWriter().write("{\"success\":true}");
        }
        else{
            response.setStatus(500);
            response.getWriter().write("{\"success\":false}");
        }

    }
}
