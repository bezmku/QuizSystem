package com.beza.quizsystem;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/save-quiz")
public class CreateQuizServlet extends HttpServlet {
    private QuestionDAO questionDAO = new QuestionDAO();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder buffer = new StringBuilder();
        String line;
        try(BufferedReader reader = request.getReader()){
            while((line=reader.readLine())!=null){
                buffer.append(line);
            }
        }
        QuizRequest quizReq = gson.fromJson(buffer.toString(), QuizRequest.class);

        boolean isSaved = false;

        if(quizReq!=null&&quizReq.getQuestions()!=null&&!quizReq.getQuestions().isEmpty()){
            isSaved = questionDAO.saveQuestions(quizReq.getQuestions(), quizReq.getSubjectId());
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if(isSaved){
            out.print("{\"success\":true,\"message\":\"Quiz save to database!\"}");
        }
        else{
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"success\":false,\"message\":\"Failed to save the quiz!\"}");
        }
        out.flush();
    }
}