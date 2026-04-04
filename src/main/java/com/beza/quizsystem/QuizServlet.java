package com.beza.quizsystem;


import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name="quizServlet", value="/api/questions")
public class QuizServlet extends HttpServlet {
private final QuestionDAO questionDAO = new QuestionDAO();
private final Gson gson = new Gson();

     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
          response.setContentType("application/json");
          response.setCharacterEncoding("UTF-8");

          List<Question> questions = questionDAO.getAllQuestions();

          String jsonResponse = this.gson.toJson(questions);

    PrintWriter out = response.getWriter();

    out.println(jsonResponse);
    out.flush();
}
}