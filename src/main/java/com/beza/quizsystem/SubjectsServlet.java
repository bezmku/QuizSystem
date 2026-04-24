package com.beza.quizsystem;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/subjects")
public class SubjectsServlet extends HttpServlet {
           QuestionDAO questionDAO = new QuestionDAO();
           Gson gson = new Gson();
           @Override
    protected void doGet(HttpServletRequest request , HttpServletResponse response ) throws IOException {
               response.setContentType("application/json");
               response.setCharacterEncoding("UTF-8");

               List<Subject> subjects = questionDAO.getSubjects();
               String jsonResponse = this.gson.toJson(subjects);

               PrintWriter out = response.getWriter();
               out.println(jsonResponse);

               out.flush();

    }


}
