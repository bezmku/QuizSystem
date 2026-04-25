package com.beza.quizsystem;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("api/teacher/get-questions")
public class MyQuestionServlet extends HttpServlet {
    private final QuestionDAO questionDAO = new QuestionDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);

        if(session == null || !"TEACHER".equals((String) session.getAttribute("role"))){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        int teacherId = (int) session.getAttribute("userId");

        List<Question> questions = questionDAO.getQuestionsByTeacherId(teacherId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(questions));
    }
}
