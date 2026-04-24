package com.beza.quizsystem;


import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/questions")
public class QuizServlet extends HttpServlet {
private final QuestionDAO questionDAO = new QuestionDAO();
private final Gson gson = new Gson();

     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{

         String idParam = request.getParameter("subjectId");
         System.out.println("received this idParam: ["+idParam+"]");
         if (idParam == null || idParam.equals("undefined") || idParam.equals("null")) {
             response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
             response.getWriter().write("{\"error\": \"Missing subjectId\"}");
             return;
         }


          response.setContentType("application/json");
          response.setCharacterEncoding("UTF-8");

         int id;
         try {
             id = Integer.parseInt(idParam.trim());
             List<Question> questions = questionDAO.getQuestionsById(id);

             String jsonResponse = this.gson.toJson(questions);
             PrintWriter out = response.getWriter();

             out.println(jsonResponse);
             out.flush();
         } catch (NumberFormatException e) {
             response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
         }


}

}