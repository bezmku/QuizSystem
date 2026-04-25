package com.beza.quizsystem;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/teacher/all-grades")
public class AllGradesServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final ResultsDAO resultsDAO = new ResultsDAO();
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
         HttpSession session = request.getSession(false);

         String role = (session != null)? session.getAttribute("role").toString() : null;
         if(!"TEACHER".equals(role)){
             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
             return;
         }
         List<QuizResult> results = resultsDAO.getResultsForTeacher((int) session.getAttribute("userId"));
         response.setContentType("application/json");
         response.setCharacterEncoding("UTF-8");

         response.getWriter().write(gson.toJson(results));
     }
}
